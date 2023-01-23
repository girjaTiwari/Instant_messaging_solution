/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.timeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import im.vector.app.R;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.webservices.AccountStatus;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.LoginRequest;
import im.vector.app.timeshare.webservices.LoginResponse;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class TSLoginActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;

    Button btn_login;
    TextView forgotpassword,tv_dont_account;
    EditText edt_username,edt_password;
    ImageView img_show_pin;
    private boolean passShow = false;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tslogin);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.element_accent_light));
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.element_accent_light));
        }
        mActivity = TSLoginActivity.this;
        myDialog = new MyDialog(mActivity);
        tsSessionManager = new TSSessionManager(mActivity);

        findView();



        forgotpassword.setPaintFlags(forgotpassword.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        tv_dont_account.setPaintFlags(tv_dont_account.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(mActivity, MainActivity.class));

                String email = edt_username.getText().toString().trim();
                String pass = edt_password.getText().toString().trim();
                if (validate(email,pass)) {
                  //  loggedin(email,pass);
                   /* JSONObject params = new  JSONObject();
                    try {
                        params.put("email_id",email);
                        params.put("password",pass);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }*/
                    LoginRequest loginRequest = new LoginRequest(email,pass);
                    Call<LoginResponse> call = mAPIService.postJson(loginRequest);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                          //  System.out.println("error>>  response " + response.toString());

                            if(response.body()!=null){
                                LoginResponse loginResponse = response.body();
                                String message = loginResponse.getMsg();
                                AccountStatus account_status = loginResponse.getAccount_status();
                                String user_uuid = account_status.getUser_uuid();
                                String first_name = account_status.getFirst_name();
                                String last_name = account_status.getLast_name();
                                String email_id = account_status.getEmail_id();
                                String profile_name = account_status.getProfile_name();
                                String mobile_number = account_status.getMobile_number();
                                String is_category = account_status.getIs_category();
                                String is_sub_category = account_status.getIs_sub_category();

                                tsSessionManager.createLoginSession(true,user_uuid,first_name,last_name,
                                        email_id,profile_name,mobile_number,Boolean.parseBoolean(is_category),Boolean.parseBoolean(is_sub_category));


                                if (Boolean.parseBoolean(is_category) && Boolean.parseBoolean(is_sub_category)){
                                    startActivity(new Intent(mActivity, TSMainActivity.class));
                                }else if (Boolean.parseBoolean(is_category) && !Boolean.parseBoolean(is_sub_category)){
                                    startActivity(new Intent(mActivity, TSSubCategoryActivity.class));
                                }else if (!Boolean.parseBoolean(is_category) && !Boolean.parseBoolean(is_sub_category)){
                                    startActivity(new Intent(mActivity, TSCategoryActivity.class));
                                }

                                Toast.makeText(TSLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                             System.out.println("error>>" + t.getCause());
                            Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

   /* private void loggedin(String email, String pass) {
        myDialog.showProgresbar(mActivity);
      //  HttpsTrustManager.allowAllSSL();
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",email);
        params.put("password",pass);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + loggedin, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("login>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject account_status = response.getJSONObject("account_status");
                        String user_uuid = account_status.getString("user_uuid");
                        String first_name = account_status.getString("first_name");
                        String last_name = account_status.getString("last_name");
                        String email_id = account_status.getString("email_id");
                        String profile_name = account_status.getString("profile_name");
                        String mobile_number = account_status.getString("mobile_number");
                        String is_category = account_status.getString("is_category");
                        String is_sub_category = account_status.getString("is_sub_category");

                        sessionManager.createLoginSession(true,user_uuid,first_name,last_name,
                                email_id,profile_name,mobile_number,Boolean.parseBoolean(is_category),Boolean.parseBoolean(is_sub_category));

                        if (Boolean.parseBoolean(is_category) && Boolean.parseBoolean(is_sub_category)){
                            startActivity(new Intent(mActivity, MainActivity.class));
                        }else if (Boolean.parseBoolean(is_category) && !Boolean.parseBoolean(is_sub_category)){
                            startActivity(new Intent(mActivity, SubCategoryActivity.class));
                        }else if (!Boolean.parseBoolean(is_category) && !Boolean.parseBoolean(is_sub_category)){
                            startActivity(new Intent(mActivity, CategoryActivity.class));
                        }
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
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
                System.out.println("login>>"+error);
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
    }*/

    private boolean validate(String email, String pass) {
        if (email.equals("")) {
            edt_username.setError("Please enter username!");
            edt_username.requestFocus();
            return false;

        } else if(pass.equals("")) {
            edt_password.setError("Please enter password!");
            edt_password.requestFocus();
            return false;

        }
        return true;
    }
    private void findView() {
        forgotpassword = findViewById(R.id.tv_forgot_pass);
        tv_dont_account = findViewById(R.id.tv_dont_account);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        img_show_pin = findViewById(R.id.img_show_pin);

        //listener
        tv_dont_account.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        img_show_pin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_dont_account) {
            startActivity(new Intent(mActivity, TSSignupActivity.class));
        } else if (id == R.id.tv_forgot_pass || id == R.id.img_show_pin) {
            //  startActivity(new Intent(mActivity, ForgotPasswordActivity.class));

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
}
