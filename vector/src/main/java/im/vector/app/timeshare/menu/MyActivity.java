package im.vector.app.timeshare.menu;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import im.vector.app.timeshare.api_response_body.EventResponse;
import im.vector.app.timeshare.api_response_body.GetMyActivityResponse;
import im.vector.app.timeshare.home.RvEventsAdapter;
import im.vector.app.timeshare.home.RvOngoingAdapter;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.myactivities.RvMyActivityAdapter;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    TSSessionManager tsSessionManager;
    ImageView iv_back;
    RecyclerView rv_myevents;
    ArrayList<Event> eventList = new ArrayList<>();
    RvMyActivityAdapter rvMyActivityAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    public static LinearLayout ll_empty_activity;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        String uuid =  user.get(TSSessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+uuid);
        if (uuid!=null){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    getMyActvity(uuid);
                }
            };
            thread.start();

        }

    }

    private void getMyActvity(String uuid) {
        CommonRequest commonRequest = new CommonRequest(uuid);
        Call<GetMyActivityResponse> call = mAPIService.getMyActivities(commonRequest);
        call.enqueue(new Callback<GetMyActivityResponse>() {
            @Override
            public void onResponse(Call<GetMyActivityResponse> call, retrofit2.Response<GetMyActivityResponse> response) {
                System.out.println("timeline>>" + response.toString());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                if(response.body()!=null){

                    GetMyActivityResponse myActivityResponse = response.body();
                    String message = myActivityResponse.getMsg();
                    List<Event> events = myActivityResponse.getGet_posts();
                    if (events.size()>0){
                        for (Event event:events){
                            String activity_uuid = event.getActivity_uuid();
                            String activity_name = event.getActivity_name();
                            String activity_description = event.getActivity_description();
                            String user_uuid = event.getUser_uuid();
                            String profile_name = event.getProfile_name();
                            String user_pic = event.getUser_pic();
                            String first_name = event.getFirst_name();
                            String last_name = event.getLast_name();
                            String category_name = event.getCategory_name();
                            String sub_category = event.getSub_category();
                            String start_date_and_time = event.getStart_date_and_time();
                            String end_date_and_time = event.getEnd_date_and_time();
                            String post_path = event.getPost_path();
                            String like_count = event.getLike_count();
                            String is_like = event.getIs_like();
                            String created_at = event.getCreated_at();
                            String location = event.getLocation();
                            List joiningUserList = event.getGetActivityJoinings();

                            // add static data in eventlist
                            eventList.add(new Event(activity_uuid,activity_name,activity_description,
                                    user_uuid,profile_name,user_pic,first_name,last_name,category_name,
                                    sub_category,start_date_and_time,end_date_and_time,post_path,like_count,is_like,created_at,location,null));

                        }

                    }else {
                        ll_empty_activity.setVisibility(View.VISIBLE);
                    }

                    rv_myevents.setLayoutManager(new LinearLayoutManager(mActivity));
                    rv_myevents.setHasFixedSize(true);
                    rvMyActivityAdapter = new RvMyActivityAdapter(mActivity, eventList);
                    rv_myevents.setAdapter(rvMyActivityAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetMyActivityResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());

            }
        });

    }

    private void initView() {
        mActivity = MyActivity.this;
        tsSessionManager = new TSSessionManager(mActivity);
        rv_myevents = findViewById(R.id.rv_myevents);
        iv_back = findViewById(R.id.iv_back);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        ll_empty_activity = findViewById(R.id.ll_empty_activity);

        //listenre's
        iv_back.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }
}
