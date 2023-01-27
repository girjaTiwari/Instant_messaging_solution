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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.GetActivityDetailsRequest;
import im.vector.app.timeshare.api_request_body.GetAttendiesRequest;
import im.vector.app.timeshare.api_request_body.GetProfileRequest;
import im.vector.app.timeshare.api_request_body.JoinActivityRequest;
import im.vector.app.timeshare.api_response_body.GetActivityDetailsResponse;
import im.vector.app.timeshare.api_response_body.GetActivityJoingingResponse;
import im.vector.app.timeshare.api_response_body.GetProfileResponse;
import im.vector.app.timeshare.api_response_body.JoinActivityResponse;
import im.vector.app.timeshare.api_response_body.JoiningCountData;
import im.vector.app.timeshare.api_response_body.ProfileData;
import im.vector.app.timeshare.api_response_body.TimelineDetails;
import im.vector.app.timeshare.home.RvAttendyAdapter;
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
            getActvityDetails(activity_uuid,user_uuid);
        }

    }

    private void getActvityDetails(String activity_id,String user_uuid) {
        myDialog.showProgresbar(mActivity);
        GetActivityDetailsRequest detailsRequest = new GetActivityDetailsRequest(activity_id,user_uuid);
        Call<GetActivityDetailsResponse> call = mAPIService.getActvityDetails(detailsRequest);
        call.enqueue(new Callback<GetActivityDetailsResponse>() {
            @Override
            public void onResponse(Call<GetActivityDetailsResponse> call, retrofit2.Response<GetActivityDetailsResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);
                getJoiningActivity(activity_uuid);
                if(response.body()!=null){
                    GetActivityDetailsResponse detailsResponse = response.body();
                    String message = detailsResponse.getMsg();
                    TimelineDetails details = detailsResponse.getGet_activities();
                    activity_uuid = details.getActivity_uuid();
                    friend_uuid = details.getUser_uuid();
                    String activity_name = details.getActivity_name();
                    String activity_description = details.getActivity_description();
                    first_name = details.getFirst_name();
                    last_name = details.getLast_name();
                    String profile_name = details.getProfile_name();
                    user_pic = details.getUser_pic();
                    String category_name = details.getCategory_name();
                    String sub_category = details.getSub_category();
                    String start_date_and_time = details.getStart_date_and_time();
                    String end_date_and_time = details.getEnd_date_and_time();
                    String post_path = details.getPost_path();
                    String location = details.getLocation();
                    String comment_list = details.getComment_list();

                    String like_count = details.getLike_count();
                    String joining_count = details.getJoining_count();
                    String is_like = details.getIs_like();
                    is_joining = details.getIs_joining();
                    String date = details.getCreated_at();

                    Glide.with(mActivity)
                            .load(ApiClass.IMAGE_BASE_URL+user_pic)
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
                      //  dots_indicator.setViewPager(vpBanner);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);


                }
            }

            @Override
            public void onFailure(Call<GetActivityDetailsResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });


    }

    private void getJoiningActivity(String activity_uuid) {
        GetAttendiesRequest attendiesRequest = new GetAttendiesRequest(activity_uuid);
        Call<GetActivityJoingingResponse> call = mAPIService.getAttendies(attendiesRequest);
        call.enqueue(new Callback<GetActivityJoingingResponse>() {
            @Override
            public void onResponse(Call<GetActivityJoingingResponse> call, retrofit2.Response<GetActivityJoingingResponse> response) {
                //   System.out.println("timeline>>" + response.toString());

                if(response.body()!=null){

                    GetActivityJoingingResponse joingingResponse = response.body();
                    String message = joingingResponse.getMsg();
                    List<JoiningUser> joiningUserList = joingingResponse.getGetActivityJoining();
                    if (joiningUserList.size()>0) {
                       // view.setVisibility(View.VISIBLE);
                        for (JoiningUser joiningUser : joiningUserList) {
                            String activity_uuid = joiningUser.getActivity_uuid();
                            String first_name = joiningUser.getFirst_name();
                            String last_name = joiningUser.getLast_name();
                            String user_name = joiningUser.getUser_name();
                            String profile_pic = joiningUser.getProfile_pic();

                            joiningUsers.add(new JoiningUser(activity_uuid, first_name, last_name, user_name, profile_pic));
                        }

                        tv_joining_count.setText(joiningUsers.size()+" Attendees");
                        rv_attendies.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
                        rvInterestedAdapter=new RvInterestedAdapter(mActivity,joiningUsers);
                        rv_attendies.setAdapter(rvInterestedAdapter);

                    }
                }else {
                    tv_joining_count.setVisibility(View.VISIBLE);
                    tv_joining_count.setText("No Attendies");
                }
            }

            @Override
            public void onFailure(Call<GetActivityJoingingResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
            }
        });

    }


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
            Intent intent = new Intent(mActivity, GalleryActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) imageList);
            intent.putExtra("BUNDLE", args);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("user_pic", user_pic);
            intent.putExtra("friend_uuid", friend_uuid);
            startActivity(intent);
        } else if (id == R.id.tv_join) {
            if (is_joining.equals("false")) {
                getJoinActivity(activity_uuid, user_uuid, "true");
            } else {
                getJoinActivity(activity_uuid, user_uuid, "false");
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


    private void getJoinActivity(String activity_uuid, String user_uuid, String is_joining) {
        myDialog.showProgresbar(mActivity);
        JoinActivityRequest joinActivityRequest = new JoinActivityRequest(activity_uuid,user_uuid,is_joining);
        Call<JoinActivityResponse> call = mAPIService.joinActivity(joinActivityRequest);
        call.enqueue(new Callback<JoinActivityResponse>() {
            @Override
            public void onResponse(Call<JoinActivityResponse> call, retrofit2.Response<JoinActivityResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(mActivity);

                if(response.body()!=null){
                    JoinActivityResponse joinActivityResponse = response.body();
                    String message = joinActivityResponse.getMsg();
                    JoiningCountData joiningCount = joinActivityResponse.getJoiningCount();

                    String count = joiningCount.getJoining_count();
                    tv_joining_count.setText(count);
                    tv_joining_count.setText(count+" Attendees");
                    if (message.equalsIgnoreCase("Joining")){
                        tv_joining.setVisibility(View.VISIBLE);
                        tv_join.setVisibility(View.GONE);
                        tv_joining.setClickable(false);
                    }else {
                        tv_joining.setVisibility(View.GONE);
                        tv_join.setVisibility(View.VISIBLE);
                        tv_joining.setClickable(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinActivityResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(mActivity);
            }
        });

    }

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
