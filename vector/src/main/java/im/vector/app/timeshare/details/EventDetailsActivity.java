package im.vector.app.timeshare.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.GetActivityDetailsRequest;
import im.vector.app.timeshare.api_request_body.GetProfileRequest;
import im.vector.app.timeshare.api_response_body.GetActivityDetailsResponse;
import im.vector.app.timeshare.api_response_body.GetProfileResponse;
import im.vector.app.timeshare.api_response_body.ProfileData;
import im.vector.app.timeshare.api_response_body.TimelineDetails;
import im.vector.app.timeshare.home.RvInterestedAdapter;
import im.vector.app.timeshare.home.TSRespondActivity;
import im.vector.app.timeshare.home.model.JoiningUser;
import im.vector.app.timeshare.profile.MyProfileActivity;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    ViewPager vpBanner;
    Button btn_upload_media;
   // DotsIndicator dots_indicator;
    ImageView ic_back_arrow,iv_LIKE,iv_profilepic;
    LinearLayout rl_attendi;
    RecyclerView rv_attendies;
    RvInterestedAdapter rvInterestedAdapter;
    MyDialog myDialog;
    TSSessionManager tsSessionManager;
    TextView tv_activity_name,tv_loaction,tv_date,tv_desc,tv_createdby,tv_like_count,tv_joining_count,tv_join,tv_joining;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();

    ArrayList<String> imageList;
    ArrayList<JoiningUser>joiningUsers = new ArrayList<>();
    String friend_uuid="",user_uuid="",is_joining="",activity_uuid="",first_name,last_name,user_pic="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        mActivity = EventDetailsActivity.this;
        initView();

        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        user_uuid =  user.get(TSSessionManager.KEY_user_uuid);

        Intent intent = getIntent();
         activity_uuid = intent.getStringExtra("activity_uuid");

        if (user_uuid!=null){
         //   getActvityDetails(activity_uuid,user_uuid);
        }

    }

    /*private void getActvityDetails(String activity_id,String user_uuid) {
        myDialog.showProgresbar(mActivity);
        GetActivityDetailsRequest detailsRequest = new GetActivityDetailsRequest(activity_id,user_uuid);
        Call<GetActivityDetailsResponse> call = mAPIService.getActvityDetails(detailsRequest);
        call.enqueue(new Callback<GetActivityDetailsResponse>() {
            @Override
            public void onResponse(Call<GetActivityDetailsResponse> call, retrofit2.Response<GetActivityDetailsResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);
                if(response.body()!=null){
                    GetActivityDetailsResponse detailsResponse = response.body();
                    String message = detailsResponse.getMsg();
                    TimelineDetails details = detailsResponse.getGet_activities();
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
            public void onFailure(Call<GetActivityDetailsResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_activity_by_activity_uuid, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("details>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        getJoiningActivity(activity_uuid);
                        // tv_no_request_found.setVisibility(View.GONE);

                                JSONObject object = response.getJSONObject("get_activities");
                                 activity_uuid = object.getString("activity_uuid");
                                 friend_uuid = object.getString("user_uuid");
                                String activity_name = object.getString("activity_name");


                                String activity_description = object.getString("activity_description");
                                 first_name = object.getString("first_name");
                                 last_name = object.getString("last_name");
                                String profile_name = object.getString("profile_name");
                                 user_pic = object.getString("user_pic");


                                String category_name = object.getString("category_name");
                                String sub_category = object.getString("sub_category");
                                String start_date_and_time = object.getString("start_date_and_time");
                                String end_date_and_time = object.getString("end_date_and_time");
                                String post_path = object.getString("post_path");
                                String location = object.getString("location");
                                String comment_list = object.getString("comment_list");

                                String like_count = object.getString("like_count");
                                String joining_count = object.getString("joining_count");
                                String is_like = object.getString("is_like");
                                 is_joining = object.getString("is_joining");
                                String date = object.getString("created_at");


                                Glide.with(mActivity)
                                .load(ApiStatus.IMAGE_BASE_URL+user_pic)
                                .into(iv_profilepic);


                                if (is_joining.equals("true")){
                                    tv_join.setVisibility(View.GONE);
                                    tv_joining.setVisibility(View.VISIBLE);
                                }else {
                                    tv_joining.setVisibility(View.GONE);
                                    tv_join.setVisibility(View.VISIBLE);
                                }

                                 tv_activity_name.setText(activity_name);
                                 tv_loaction.setText(location);
                                tv_date.setText(start_date_and_time);
                                tv_desc.setText(activity_description);
                                tv_like_count.setText(like_count);
                                tv_createdby.setText(first_name+" "+last_name);

                                tv_like_count.setText(like_count);
                                if (is_like.equals("true")){
                                    iv_LIKE.setImageResource(R.drawable.ic_like_a);
                                }else {
                                    iv_LIKE.setImageResource(R.drawable.ic_like_i);
                                }


                                String strImages = remove_last_charectar(post_path);
                                imageList = new ArrayList<>(Arrays.asList(strImages.split(",")));


                                try {
                                    BannerSliderAdapterTest adapter = new BannerSliderAdapterTest(mActivity,imageList);
                                    vpBanner.setAdapter(adapter);
                                    vpBanner.setCurrentItem(adapter.getCount()-1);
                                    dots_indicator.setViewPager(vpBanner);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                                Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);



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
                System.out.println("details>>"+error);
                myDialog.hideDialog(mActivity);


            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
    }*/

  /*  private void getJoiningActivity(String activity_uuid) {
        HashMap<String,String> params = new HashMap<>();
        params.put("activity_uuid",activity_uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_joining_activity, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("getJoin>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {

                        JSONArray jsonArray = response.getJSONArray("GetActivityJoining");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                               String activity_uuid = object.getString("activity_uuid");
                               String first_name  = object.getString("first_name");
                               String last_name = object.getString("last_name");
                               String user_name = object.getString("user_name");
                               String profile_pic = object.getString("profile_pic");

                                joiningUsers.add(new JoiningUser(activity_uuid,first_name,last_name,user_name,profile_pic));
                            }
                            tv_joining_count.setText(joiningUsers.size()+" Attendees");
                         //   GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3, LinearLayoutManager.VERTICAL,false);
                            rv_attendies.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
                            rvInterestedAdapter=new RvInterestedAdapter(mActivity,joiningUsers);
                            rv_attendies.setAdapter(rvInterestedAdapter);

                        }

                    }else {

                        tv_joining_count.setVisibility(View.VISIBLE);
                        tv_joining_count.setText("No Attendies");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("getJoin>>"+error);
            }
        });
        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);

    }
*/

    private String remove_last_charectar(String str) {
            if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }



    private void initView() {
        tsSessionManager = new TSSessionManager(mActivity);
        myDialog = new MyDialog(mActivity);
        vpBanner = findViewById(R.id.viewpager_banner);
       // dots_indicator = findViewById(R.id.dots_indicator);
        ic_back_arrow = findViewById(R.id.iv_back_);
        iv_LIKE = findViewById(R.id.iv_LIKE);
        tv_join = findViewById(R.id.tv_join);
        tv_joining = findViewById(R.id.tv_joining);
        rl_attendi = findViewById(R.id.rl_attendi);
        rv_attendies = findViewById(R.id.rv_attendies);
        btn_upload_media = findViewById(R.id.btn_upload_media);

        tv_activity_name = findViewById(R.id.tv_activity_name);
        tv_loaction = findViewById(R.id.tv_loaction);
        tv_date = findViewById(R.id.tv_date);
        tv_desc = findViewById(R.id.tv_desc);
        tv_like_count = findViewById(R.id.tv_like_count);
        tv_joining_count = findViewById(R.id.tv_joining_count);
        tv_createdby = findViewById(R.id.tv_createdby);
        iv_profilepic = findViewById(R.id.iv_profilepic);

        //listener's
        ic_back_arrow.setOnClickListener(this);
        iv_LIKE.setOnClickListener(this);
        iv_profilepic.setOnClickListener(this);
        tv_join.setOnClickListener(this);
        tv_joining.setOnClickListener(this);
        rl_attendi.setOnClickListener(this);
        btn_upload_media.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back_) {
            finish();
        } else if (id == R.id.btn_upload_media) {
           /* Intent intent = new Intent(mActivity, GalleryActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) imageList);
            intent.putExtra("BUNDLE", args);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("user_pic", user_pic);
            intent.putExtra("friend_uuid", friend_uuid);
            startActivity(intent);*/
        } else if (id == R.id.tv_join) {
            if (is_joining.equals("false")) {
              //  getJoinActivity(activity_uuid, user_uuid, "true");
            } else {
              //  getJoinActivity(activity_uuid, user_uuid, "false");
            }

            Intent intent = new Intent(mActivity, TSRespondActivity.class);
            intent.putExtra("activity_uuid", activity_uuid);
            startActivity(intent);
        } else if (id == R.id.rl_attendi) {
            Intent intent = new Intent(mActivity, TSRespondActivity.class);
            intent.putExtra("activity_uuid", activity_uuid);
            startActivity(intent);
        } else if (id == R.id.iv_LIKE) {
            if (user_uuid != null) {
              //  getLikePost(activity_uuid, user_uuid, false);
            }
        } else if (id == R.id.iv_profilepic) {
            Intent intent = new Intent(mActivity, MyProfileActivity.class);
            intent.putExtra("friend_uuid", friend_uuid);
            startActivity(intent);
        }

    }


 /*   private void getJoinActivity(String activity_uuid, String user_uuid, String is_joining) {
        myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("activity_uuid",activity_uuid);
        params.put("user_uuid",user_uuid);
        params.put("is_joining",is_joining);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + joining_activity, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("joinActivity>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject jsonObject = response.getJSONObject("JoiningCount");
                        String count = jsonObject.getString("joining_count");
                        tv_joining_count.setText(count);
                        tv_joining_count.setText(count+" Attendees");
                        if (mesage.equalsIgnoreCase("Joining")){
                            tv_joining.setVisibility(View.VISIBLE);
                            tv_join.setVisibility(View.GONE);
                            tv_joining.setClickable(false);
                        }else {
                            tv_joining.setVisibility(View.GONE);
                            tv_join.setVisibility(View.VISIBLE);
                            tv_joining.setClickable(true);
                        }

                       // Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }else {

                      //  Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("join>>"+error);
                myDialog.hideDialog(mActivity);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }*/

/*    private void getLikePost(String activity_uuid, String uuid, boolean like) {
        myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("activity_uuid",activity_uuid);
        params.put("user_uuid",uuid);
        params.put("like",String.valueOf(like));

        System.out.println("param>>"+params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + like_post, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("like_post>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject object = response.getJSONObject("like_count");
                        String count = object.getString("like_count");
                        tv_like_count.setText(count);
                        if (mesage.equalsIgnoreCase("Liked")){
                            iv_LIKE.setImageResource(R.drawable.ic_like_a);
                        }else {
                            iv_LIKE.setImageResource(R.drawable.ic_like_i);
                        }
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
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
                System.out.println("like_post>>"+error);
                myDialog.hideDialog(mActivity);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }*/

    public class SliderTimer extends TimerTask {
        @Override
        public void run() {
            try{
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (vpBanner.getCurrentItem() < imageList.size() - 1) {
                            vpBanner.setCurrentItem(vpBanner.getCurrentItem() + 1);
                        } else {
                            vpBanner.setCurrentItem(0);
                        }
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
