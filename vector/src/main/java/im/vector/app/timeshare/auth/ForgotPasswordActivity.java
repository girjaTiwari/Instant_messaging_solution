package im.vector.app.timeshare.auth;


import static im.vector.app.timeshare.ApiClass.forget_password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSUtils.MyDialog;

public class ForgotPasswordActivity extends AppCompatActivity {
    AppCompatActivity mActivity;

    Button btn_login;
    ImageView iv_back;
    EditText edt_email;
    RequestQueue requestQueue;
    MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mActivity = ForgotPasswordActivity.this;
        myDialog = new MyDialog(mActivity);

        findView();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String email = edt_email.getText().toString().trim();
                    if (validate(email)) {
                        forget_password(email);
                    }

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private boolean validate(String email) {
        if (email.equals("")) {
            edt_email.setError("Please enter email!");
            edt_email.requestFocus();
            return false;

        }
        return true;
    }

   private void forget_password(String email) {
     myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",email);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiClass.BASE_URL + forget_password, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("forgot>>"+response);
           myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                       // Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mActivity,OTPVerifyActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
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
                System.out.println("forgot>>"+error);
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
        edt_email = findViewById(R.id.edt_email);
        btn_login = findViewById(R.id.btn_login);
        iv_back = findViewById(R.id.iv_back);
    }
}
