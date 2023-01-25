package im.vector.app.timeshare.profile;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSCategoryActivity;
import im.vector.app.timeshare.TSLoginActivity;
import im.vector.app.timeshare.TSMainActivity;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSSubCategoryActivity;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.GetProfileRequest;
import im.vector.app.timeshare.api_request_body.LoginRequest;
import im.vector.app.timeshare.api_response_body.GetProfileResponse;
import im.vector.app.timeshare.api_response_body.LoginResponse;
import im.vector.app.timeshare.api_response_body.ProfileData;
import im.vector.app.timeshare.webservices.AccountStatus;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    ImageView iv_back,iv_edit_profile,iv_prof_pic;
    TextView tv_name,tv_prifile_name,tv_interested_categories,tv_mobile_number,
            tv_email_address,tv_dob,tv_address,tv_city,tv_country,tv_gender;

    String firstname,lastname,profile_name,user_pic,email,mobile_number,dob,city,address,country;
    String cat1,cat2,cat3,cat4,cat5;
    String friend_uuid="";
    RelativeLayout rl_top;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();


    @Override
    protected void onResume() {
        super.onResume();

            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String uuid =  user.get(TSSessionManager.KEY_user_uuid);
            System.out.println("uuid>>"+uuid);
            if (uuid!=null) {
               // get_profile(friend_uuid,uuid);
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

          if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
           // window.setNavigationBarColor(ContextCompat.getColor(this, R.color.primary_dark));
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }

        mActivity = MyProfileActivity.this;
        tsSessionManager = new TSSessionManager(mActivity);
        myDialog = new MyDialog(mActivity);;

        Intent intent = getIntent();
        friend_uuid = intent.getStringExtra("friend_uuid");

        initView();

        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        String uuid =  user.get(TSSessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+uuid);
        if (uuid!=null){
            get_profile(friend_uuid,uuid);

        }

        iv_prof_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,ProfileImageViewerActivity.class);
                intent.putExtra("user_pic",user_pic);
                intent.putExtra("friend_uuid",friend_uuid);
                startActivity(intent);
            }
        });

    }

    private void get_profile(String friend_uuid,String user_uuid) {
        myDialog.showProgresbar(mActivity);

        if (friend_uuid.equals(user_uuid)){
            iv_edit_profile.setVisibility(View.VISIBLE);
        }else {
            iv_edit_profile.setVisibility(View.GONE);
        }

        GetProfileRequest profileRequest = new GetProfileRequest(friend_uuid,user_uuid);
        Call<GetProfileResponse> call = mAPIService.getProfile(profileRequest);
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, retrofit2.Response<GetProfileResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);
                if(response.body()!=null){
                    GetProfileResponse profileResponse = response.body();
                    String message = profileResponse.getMsg();
                    ProfileData profileData = profileResponse.getGet_profile();
                    firstname = profileData.getFirst_name();
                    lastname = profileData.getLast_name();
                    user_pic = profileData.getUser_pic();
                    profile_name = profileData.getProfile_name();
                    email = profileData.getEmail_id();
                    mobile_number = profileData.getMobile_number();
                    String gender = profileData.getGender();
                    dob = profileData.getDate_of_birth();
                    city = profileData.getCity();
                    address = profileData.getAddress();
                    country = profileData.getCountry();
                    cat1 = profileData.getCategory1();
                    cat2 = profileData.getCategory2();
                    cat3 = profileData.getCategory3();
                    cat4 = profileData.getCategory4();
                    cat5 = profileData.getCategory5();
                    String mutual_friends=profileData.getMutual_friends();

                    Glide.with(getApplicationContext())
                            .load(ApiClass.IMAGE_BASE_URL+user_pic).circleCrop()
                            .into(iv_prof_pic);


                    tv_name.setText(firstname+" "+lastname);
                    tv_prifile_name.setText(profile_name);
                    if (cat1.equals("")){
                        tv_interested_categories.setText(null);
                    }else{
                        tv_interested_categories.setText(cat1 + "," + cat2 + "," + cat3 + "," + cat4 + "," + cat5);
                    }
                    tv_email_address.setText(email);
                    tv_mobile_number.setText(mobile_number);
                    tv_dob.setText(dob);
                    tv_address.setText(address);
                    tv_city.setText(city);
                    tv_country.setText(country);

                    if (gender.equalsIgnoreCase("male")){
                        tv_gender.setText("Male");
                    }else if (gender.equalsIgnoreCase("female")){
                        tv_gender.setText("Female");
                    }

                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });


    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_edit_profile = findViewById(R.id.iv_edit_profile);
        iv_prof_pic = findViewById(R.id.iv_prof_pic);
        tv_name = findViewById(R.id.tv_name);
        tv_prifile_name = findViewById(R.id.tv_prifile_name);
        tv_interested_categories = findViewById(R.id.tv_interested_categories);
        tv_mobile_number = findViewById(R.id.tv_mobile_number);
        tv_email_address = findViewById(R.id.tv_email);
        tv_dob = findViewById(R.id.tv_dob);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_country = findViewById(R.id.tv_country);

        tv_gender = findViewById(R.id.tv_gender);

        rl_top = findViewById(R.id.rl_top);


        // listener's
        iv_back.setOnClickListener(this);
        iv_edit_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_edit_profile) {
           /* Intent intent = new Intent(mActivity, EditProfileActivity.class);
            intent.putExtra("firstname", firstname);
            intent.putExtra("lastname", lastname);
            intent.putExtra("profile_name", profile_name);
            intent.putExtra("user_pic", user_pic);
            intent.putExtra("mobile_number", mobile_number);
            intent.putExtra("email", email);
            intent.putExtra("dob", dob);
            intent.putExtra("address", address);
            intent.putExtra("city", city);
            intent.putExtra("country", country);
            intent.putExtra("cat1", cat1);
            intent.putExtra("cat2", cat2);
            intent.putExtra("cat3", cat3);
            intent.putExtra("cat4", cat4);
            intent.putExtra("cat5", cat5);
            startActivity(intent);*/
        }
    }
}
