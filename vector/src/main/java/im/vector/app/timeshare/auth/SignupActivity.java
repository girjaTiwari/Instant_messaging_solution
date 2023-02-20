package im.vector.app.timeshare.auth;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.matrix.android.sdk.api.auth.login.LoginWizard;

import im.vector.app.R;
import im.vector.app.features.MainActivity;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.SignupRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    Button btn_signup;
    TextView tv_already_account;
    ImageView iv_gmail,iv_apple,iv_facebook,img_show_pin;
    EditText edt_firstname,edt_lastname,edt_profilename,edt_email,edt_password,edt_mobilenumber;
    private boolean passShow = false;
    String firstname,lastname,profilename,email,password,mobilenumber;
    TSSessionManager tsSessionManager;

    MyDialog myDialog;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mActivity = SignupActivity.this;
        findView();


    }

    private void findView() {
        myDialog = new MyDialog(mActivity);
        tsSessionManager = new TSSessionManager(mActivity);
        btn_signup = findViewById(R.id.btn_signup);
        tv_already_account = findViewById(R.id.tv_already_account);
        iv_gmail = findViewById(R.id.iv_gmail);
        iv_apple = findViewById(R.id.iv_apple);
        iv_facebook = findViewById(R.id.iv_facebook);
        img_show_pin = findViewById(R.id.img_show_pin);

        //Inputfields
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_profilename = findViewById(R.id.edt_profilename);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_mobilenumber = findViewById(R.id.edt_mobilenumber);

        //Listener
        btn_signup.setOnClickListener(this);
        tv_already_account.setOnClickListener(this);
        iv_gmail.setOnClickListener(this);
        iv_apple.setOnClickListener(this);
        iv_facebook.setOnClickListener(this);
        img_show_pin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_signup) {
            closeKeypad();
            firstname = edt_firstname.getText().toString().trim();
            lastname = edt_lastname.getText().toString().trim();
            profilename = edt_profilename.getText().toString().trim();
            email = edt_email.getText().toString().trim();
            password = edt_password.getText().toString().trim();
            mobilenumber = edt_mobilenumber.getText().toString().trim();
            String device_type = getDeviceName();
            if (validate(firstname, lastname, profilename, email, password, mobilenumber)) {
                userSignupApi(firstname, lastname, profilename, email, password, mobilenumber, device_type);
            }
        } else if (id == R.id.tv_already_account) {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        } else if (id == R.id.iv_gmail) {
          //  startActivity(new Intent(SignupActivity.this, GmailSignupActivity.class));
            //  finish();
        } else if (id == R.id.iv_apple) {
           // startActivity(new Intent(SignupActivity.this, AppleSignupActivity.class));
            //  finish();
        } else if (id == R.id.iv_facebook) {
           // startActivity(new Intent(SignupActivity.this, FacebookSignupActivity.class));
            // finish();
        } else if (id == R.id.img_show_pin) {
            if (edt_password.getText().length() > 0) {
                if (!passShow) {
                    passShow = true;
                    img_show_pin.setImageResource(R.drawable.ic_eye_crossed);
                    edt_password.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance());
                } else {
                    passShow = false;
                    img_show_pin.setImageResource(R.drawable.ic_eye);
                    edt_password.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());

                }
            }
            edt_password.setSelection(edt_password.getText().toString().length());
        }
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private void userSignupApi(String firstname, String lastname, String profilename, String email, String password, String mobilenumber,String device_type)
    {
            myDialog.showProgresbar(mActivity);
          /*  Note:chat_id == profilename and chat_password == password*/

            SignupRequest signupRequest = new SignupRequest(firstname,lastname,email,profilename,password,mobilenumber,"android",device_type,profilename,password);
            Call<CommonResponse> call = mAPIService.signup(signupRequest);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                    //  System.out.println("error>>  response " + response.toString());
                    myDialog.hideDialog(mActivity);
                    if(response.body()!=null){
                        CommonResponse signupResponse = response.body();
                       // String message = signupResponse.getMsg();
                        Intent intent = new Intent(mActivity,EmailVerifyActivity.class);
                        intent.putExtra("email",email);
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

    private boolean validate(String firstname, String lastname, String profilename, String email, String password, String mobilenumber) {
        if (firstname.equals("")) {
            edt_firstname.setError("Please enter firstname");
            edt_firstname.requestFocus();
            return false;

        } else if(lastname.equals("")) {
            edt_lastname.setError("Please enter lastname");
            edt_lastname.requestFocus();
            return false;
        } else if(profilename.equals("")) {
            edt_profilename.setError("Please enter profilename");
            edt_profilename.requestFocus();
            return false;
        }
        else if(email.equals("")) {
            edt_email.setError("Please enter email");
            edt_email.requestFocus();
            return false;
        }
        else if(password.equals("")) {
            edt_password.setError("Please enter password");
            edt_password.requestFocus();
            return false;
        }else if (password.length()<8){
            edt_password.setError("Please enter minimum 8 char for password");
            edt_password.requestFocus();
            return false;
        }
        else if(mobilenumber.equals("")) {
            edt_mobilenumber.setError("Please enter mobile number");
            edt_mobilenumber.requestFocus();
            return false;
        }
        return true;
    }

    private void closeKeypad() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager manager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
