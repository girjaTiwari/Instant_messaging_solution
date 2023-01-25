package im.vector.app.timeshare.home;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import im.vector.app.timeshare.api_request_body.CreateRespondRequest;
import im.vector.app.timeshare.api_request_body.DeleteActivityRequest;
import im.vector.app.timeshare.api_request_body.GetAttendiesRequest;
import im.vector.app.timeshare.api_request_body.GetRespondRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.api_response_body.GetActivityJoingingResponse;
import im.vector.app.timeshare.api_response_body.GetMyActivityResponse;
import im.vector.app.timeshare.api_response_body.GetRespondResponse;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.home.model.JoiningUser;
import im.vector.app.timeshare.home.model.Respond;
import im.vector.app.timeshare.myactivities.RvMyActivityAdapter;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class TSRespondActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    ImageView iv_back;
    RecyclerView rv_attendies,rv_responds;
    ArrayList<JoiningUser>joiningUsers = new ArrayList<>();
    ArrayList<Respond>respondList = new ArrayList<>();
    RvAttendyAdapter rvAttendyAdapter;
    RvRespondAdapter rvRespondAdapter;
    ImageView send_icon;
    String activity_uuid,user_uuid;
    public static EditText et_message;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout ll_no_comment;
    View view;

    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ts_respond);
        initView();
        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        user_uuid =  user.get(TSSessionManager.KEY_user_uuid);

        Intent intent = getIntent();
        if (intent!=null){
             activity_uuid = intent.getStringExtra("activity_uuid");
            if (activity_uuid!=null){
                getAttendies(activity_uuid);
                getRespond(activity_uuid);
            }
        }


    }

    private void getAttendies(String activity_uuid) {
        GetAttendiesRequest attendiesRequest = new GetAttendiesRequest(activity_uuid);
        Call<GetActivityJoingingResponse> call = mAPIService.getAttendies(attendiesRequest);
        call.enqueue(new Callback<GetActivityJoingingResponse>() {
            @Override
            public void onResponse(Call<GetActivityJoingingResponse> call, retrofit2.Response<GetActivityJoingingResponse> response) {
             //   System.out.println("timeline>>" + response.toString());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                if(response.body()!=null){

                    GetActivityJoingingResponse joingingResponse = response.body();
                    String message = joingingResponse.getMsg();
                    List<JoiningUser> joiningUserList = joingingResponse.getGetActivityJoining();
                    if (joiningUserList.size()>0) {
                        view.setVisibility(View.VISIBLE);
                        for (JoiningUser joiningUser : joiningUserList) {
                            String activity_uuid = joiningUser.getActivity_uuid();
                            String first_name = joiningUser.getFirst_name();
                            String last_name = joiningUser.getLast_name();
                            String user_name = joiningUser.getUser_name();
                            String profile_pic = joiningUser.getProfile_pic();

                            joiningUsers.add(new JoiningUser(activity_uuid, first_name, last_name, user_name, profile_pic));
                        }

                        if (joiningUsers.size() > 0) {
                            rv_attendies.setVisibility(View.VISIBLE);
                        } else {
                            rv_attendies.setVisibility(View.GONE);
                            // tv_joining_count.setText("0 Attendees");
                        }

                        // set data in attendy
                        rv_attendies.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                        rv_attendies.setHasFixedSize(true);
                        rvAttendyAdapter = new RvAttendyAdapter(mActivity, joiningUsers);
                        rv_attendies.setAdapter(rvAttendyAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetActivityJoingingResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
            }
        });

    }

    private void initView() {
        mActivity = TSRespondActivity.this;
        tsSessionManager = new TSSessionManager(mActivity);
        myDialog = new MyDialog(mActivity);
        rv_attendies = findViewById(R.id.rv_attendies);
        rv_responds = findViewById(R.id.rv_responds);
        iv_back = findViewById(R.id.iv_back);
        send_icon = findViewById(R.id.iv_send);
        et_message = findViewById(R.id.et_message);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        ll_no_comment = findViewById(R.id.ll_no_comment);
        view = findViewById(R.id.view_below_attendies);


        //listenre's

        iv_back.setOnClickListener(this);
        send_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_send) {
            String message = et_message.getText().toString().trim();
            if (activity_uuid != null && user_uuid != null) {
                createRespond(activity_uuid, user_uuid, message);
            }
        }
    }


    private void createRespond(String activity_uuid, String user_uuid, String message) {
        myDialog.showProgresbar(mActivity);
        CreateRespondRequest createRespondRequest = new CreateRespondRequest(activity_uuid,user_uuid,message);
        Call<CommonResponse> call = mAPIService.createRespond(createRespondRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
               // System.out.println("reuest-undo>>" + response.toString());
                myDialog.hideDialog(mActivity);
                if (response.body() != null) {

                    CommonResponse sendRequest = response.body();
                    String status = sendRequest.getStatus();
                    String mesage = sendRequest.getMsg();
                    if (status.equals("1"))
                    {
                        et_message.setText("");
                        getRespond(activity_uuid);
                    }else {
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                myDialog.hideDialog(mActivity);
                System.out.println("error>>" + t.getCause());
            }
        });

    }

    private void getRespond(String activity_uuid) {
        respondList.clear();
        GetRespondRequest respondRequest = new GetRespondRequest(activity_uuid);
        Call<GetRespondResponse> call = mAPIService.getResponds(respondRequest);
        call.enqueue(new Callback<GetRespondResponse>() {
            @Override
            public void onResponse(Call<GetRespondResponse> call, retrofit2.Response<GetRespondResponse> response) {
               // System.out.println("timeline>>" + response.toString());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                if(response.body()!=null){

                    GetRespondResponse respondResponse = response.body();
                    String message = respondResponse.getMsg();
                    List<Respond> responds = respondResponse.getResponds();
                    if (responds.size()>0) {
                        view.setVisibility(View.VISIBLE);
                        for (Respond respond : responds) {
                            String respond_uuid = respond.getRespond_uuid();
                            String activity_uuid = respond.getActivity_uuid();
                            String user_name = respond.getUser_name();
                            String user_pic = respond.getUser_pic();
                            String respond_msg = respond.getRespond();
                            String created_at = respond.getCreated_at();
                            String EpochTime = respond.getEpochTime();

                            respondList.add(new Respond(respond_uuid, activity_uuid, user_name, user_pic, respond_msg, created_at, EpochTime));
                        }

                        rv_responds.setLayoutManager(new LinearLayoutManager(mActivity));
                        rv_responds.setHasFixedSize(true);
                        rvRespondAdapter = new RvRespondAdapter(mActivity, respondList);
                        rv_responds.setAdapter(rvRespondAdapter);

                    }else {
                        ll_no_comment.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRespondResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
            }
        });


    }
}
