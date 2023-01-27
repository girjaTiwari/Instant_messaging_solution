package im.vector.app.timeshare.auth;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import im.vector.app.R;
import im.vector.app.timeshare.TSLoginActivity;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.ResentOtpRequest;
import im.vector.app.timeshare.api_request_body.SignupRequest;
import im.vector.app.timeshare.api_request_body.VerifyEmailRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class EmailVerifyActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    int noOfMinutes = 60 * 1000;
    private static CountDownTimer countDownTimer;
    TextView tv_registered_email,otpGetTimmerTxt,otpReSent;
    String strEmail;
    EditText et_otp;
    Button btn_verify_email;
    MyDialog myDialog;

    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);
        mActivity = EmailVerifyActivity.this;
        myDialog = new MyDialog(mActivity);
        findView();
        Intent intent = getIntent();
        strEmail = intent.getStringExtra("email");
        if (strEmail!=null){
            tv_registered_email.setText(strEmail);
        }

        tv_registered_email.setPaintFlags(tv_registered_email.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        btn_verify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String otp = et_otp.getText().toString().trim();
                    if (validate(strEmail,otp)) {
                        verifyEmail(strEmail,otp);
                }

            }
        });
    }

    private void verifyEmail(String strEmail, String otp) {
      myDialog.showProgresbar(mActivity);
        VerifyEmailRequest verify_email_req = new VerifyEmailRequest(strEmail,otp);
        Call<CommonResponse> call = mAPIService.verifyEmail(verify_email_req);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);
                if(response.body()!=null){
                    CommonResponse signupResponse = response.body();
                     String message = signupResponse.getMsg();
                    Toast.makeText(mActivity, ""+message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mActivity, TSLoginActivity.class);
                    intent.putExtra("flag","1");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });
    }

    private boolean validate(String strEmail, String otp) {
        if (strEmail.equals("")) {
            Toast.makeText(mActivity, "Email is empty!", Toast.LENGTH_SHORT).show();
            return false;

        } else if(otp.equals("")) {
            et_otp.setError("Please enter 6 digit otp!");
            et_otp.requestFocus();
            return false;

        }else if (otp.length()<6){
            et_otp.setError("Please enter 6 digit otp!");
            et_otp.requestFocus();
            return false;
        }
        return true;
    }

    private void findView() {
        tv_registered_email = findViewById(R.id.tv_registered_email);
        et_otp = findViewById(R.id.et_otp);
        btn_verify_email = findViewById(R.id.btn_verify_email);
        otpGetTimmerTxt = findViewById(R.id.otpGetTimmerTxt);
        otpReSent = findViewById(R.id.otpReSent);


        startTimer(noOfMinutes); // start countdown


        otpReSent.setOnClickListener(null);
    }

    // Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer =
                new CountDownTimer(noOfMinutes, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Convert milliseconds into hour,minute and seconds
                        String text =
                                String.format(
                                        Locale.getDefault(),
                                        "%02d:%02d",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                        otpGetTimmerTxt.setText(text); // set text
                    }

                    public void onFinish() {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(400);
                        otpGetTimmerTxt.setText(
                                "OTP has been expired, pls generate a new OTP by tapping on ' Resent OTP' option"); // On finish change timer text
                        countDownTimer = null; // set CountDownTimer to null
                        otpReSent.setTextColor(
                                ContextCompat.getColor(mActivity, R.color.white));
                        // otpReSendBtn.setBackgroundResource(R.drawable.btn_bg);
                        otpReSent.setOnClickListener(EmailVerifyActivity.this);
                    }
                }.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.otpReSent) {
            stopCountdown();
            startTimer(noOfMinutes); // start countdown
            otpReSent.setOnClickListener(null);
            otpReSent.setTextColor(ContextCompat.getColor(mActivity, R.color.text_grey1));
            // otpReSendBtn.setBackgroundResource(R.drawable.btn_greey_bg);

            resend_otp(strEmail);
        }
    }

    private void resend_otp(String strEmail) {
        myDialog.showProgresbar(mActivity);
        ResentOtpRequest resentOtpRequest = new ResentOtpRequest(strEmail);
        Call<CommonResponse> call = mAPIService.resendOtp(resentOtpRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);
                if(response.body()!=null){
                    CommonResponse signupResponse = response.body();
                    String message = signupResponse.getMsg();
                    String status = signupResponse.getStatus();
                    if (status.equals("1"))
                    {
                        Toast.makeText(mActivity, ""+message, Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(mActivity, ""+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });

    }

    // Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
