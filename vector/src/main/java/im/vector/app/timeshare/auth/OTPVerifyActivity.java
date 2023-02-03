package im.vector.app.timeshare.auth;



import static im.vector.app.timeshare.ApiClass.verify_otp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.jetbrains.annotations.ApiStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.features.MainActivity;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSUtils.MyDialog;

public class OTPVerifyActivity extends AppCompatActivity {
    AppCompatActivity mActivity;
    String strEmail;
    TextView tv_registered_email;
    EditText et_otp,edt_password,edt_repassword;
    Button btn_verify_otp;
    RequestQueue requestQueue;
    ImageView eye1,eye2;
    private boolean passShow = false,repassshow=false;
    MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        mActivity = OTPVerifyActivity.this;
        myDialog = new MyDialog(mActivity);

        findView();
        Intent intent = getIntent();
        strEmail = intent.getStringExtra("email");
        if (strEmail!=null){
            tv_registered_email.setText(strEmail);
        }

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (strEmail!=null){
                        String otp = et_otp.getText().toString().trim();
                        String pass = edt_password.getText().toString().trim();
                        String repass = edt_repassword.getText().toString().trim();
                        if (validate(strEmail,otp,pass,repass)) {
                            verifyOTP(strEmail,otp,pass,repass);
                        }
                    }else {
                        Toast.makeText(mActivity, "Email is empty", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_password.getText().length() > 0) {
                    if (!passShow) {
                        passShow = true;
                        eye1.setImageResource(R.drawable.ic_eye_crossed);
                        edt_password.setTransformationMethod(
                                HideReturnsTransformationMethod.getInstance());
                    } else {
                        passShow = false;
                        eye1.setImageResource(R.drawable.ic_eye);
                        edt_password.setTransformationMethod(
                                PasswordTransformationMethod.getInstance());

                    }
                }
                edt_password.setSelection(edt_password.getText().toString().length());
            }
        });

        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_password.getText().length() > 0) {
                    if (!repassshow) {
                        repassshow = true;
                        eye2.setImageResource(R.drawable.ic_eye_crossed);
                        edt_repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        repassshow = false;
                        eye2.setImageResource(R.drawable.ic_eye);
                        edt_repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }
                edt_repassword.setSelection(edt_repassword.getText().toString().length());
            }
        });
    }

    private boolean validate(String strEmail, String otp,String pass,String repass) {
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
        } else if(pass.equals("")) {
            edt_password.setError("Please enter password!");
            edt_password.requestFocus();
            return false;
        } else if(repass.equals("")) {
            edt_repassword.setError("Please enter re-password!");
            edt_repassword.requestFocus();
            return false;
        }
        return true;
    }

    private void verifyOTP(String strEmail, String otp,String pass,String repass) {
     myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",strEmail);
        params.put("otp",otp);
        params.put("new_password",pass);
        params.put("retype_new_password",repass);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiClass.BASE_URL + verify_otp, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("otpverify>>"+response);
           myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(mActivity, MainActivity.class));
                        finish();

                    }else {
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("otpverify>>"+error);
           myDialog.hideDialog(mActivity);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }

    private void findView() {
        tv_registered_email = findViewById(R.id.tv_registered_email);
        et_otp = findViewById(R.id.et_otp);
        edt_password = findViewById(R.id.edt_password);
        edt_repassword = findViewById(R.id.edt_repassword);
        btn_verify_otp = findViewById(R.id.btn_verify_otp);
        eye1 = findViewById(R.id.eye1);
        eye2 = findViewById(R.id.eye2);

    }
}
