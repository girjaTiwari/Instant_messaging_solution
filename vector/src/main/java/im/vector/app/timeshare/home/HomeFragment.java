package im.vector.app.timeshare.home;




import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.home.model.OngoingModel;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.EventResponse;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import im.vector.app.timeshare.webservices.TimeLineRequest;
import retrofit2.Call;
import retrofit2.Callback;


public class HomeFragment extends Fragment {
    Context mContext;
    MyDialog myDialog;

   // RequestQueue requestQueue;
    RecyclerView rv_ongoing_events;
   public static RecyclerView rv_allevents;
    ArrayList<OngoingModel> ongoingEventList = new ArrayList<>();
    ArrayList<Event> eventList = new ArrayList<>();
    RvOngoingAdapter rvOngoingAdapter;
    public static RvEventsAdapter rvEventsAdapter;
    ImageView iv_filter,iv_search;
    TSSessionManager tsSessionManager;
    androidx.appcompat.widget.SearchView mSearchView;
    RelativeLayout rl_searchlayout,rl_toolbar_main;
    //ShimmerFrameLayout shimmer_ongoing_event,shimmerFrameLayout;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view =  inflater.inflate(R.layout.fragment_home, container, false);
     mContext = getActivity();
        tsSessionManager = new TSSessionManager(mContext);
        myDialog = new MyDialog(getActivity());
        iv_filter = view.findViewById(R.id.iv_filter);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(mContext, FilterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);*/
            }
        });

           findView(view);

            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String uuid =  user.get(TSSessionManager.KEY_user_uuid);
            System.out.println("uuid>>"+uuid);
            if (uuid!=null){

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        getAllActvity(uuid);
                    }
                };
                thread.start();
            }



        mSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchEvent(newText);
                return true;
            }
        });


        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_searchlayout.setVisibility(View.VISIBLE);
                rl_toolbar_main.setVisibility(View.GONE);

            }
        });


        return view;
    }

    private void searchEvent(String string) {
        ArrayList<Event> filteredlist = new ArrayList<Event>();
        for (Event item : eventList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getActivity_name().toLowerCase().contains(string)) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(mContext, "No Event Found!", Toast.LENGTH_SHORT).show();
        } else {
          //  rvEventsAdapter.filterList(filteredlist);
        }
    }

    private void getAllActvity(String uuid) {
        myDialog.showProgresbar(mContext);
        TimeLineRequest timeLineRequest = new TimeLineRequest(uuid);
        Call<EventResponse> call = mAPIService.postJson(timeLineRequest);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, retrofit2.Response<EventResponse> response) {
                 System.out.println("error>>  response " + response.toString());
                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                myDialog.hideDialog(mContext);
                if(response.body()!=null){

                   EventResponse eventResponse = response.body();
                    String message = eventResponse.getMsg();
                     List<Event> events = eventResponse.getGet_timelines();
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
                                String start_date_and_time = event.getStart_time();
                                String end_date_and_time = event.getEnd_time();
                                String post_path = event.getPost_path();
                                String like_count = event.getLike_count();
                                String is_like = event.getIs_like();
                                String created_at = event.getCreated_at();
                                String location = event.getLocation();
                                List joiningUserList = event.getGetActivityJoinings();

                                // add static data in eventlist
                                eventList.add(new Event(activity_uuid,activity_name,activity_description,
                                        user_uuid,profile_name,user_pic,first_name,last_name,category_name,sub_category,
                                        start_date_and_time,end_date_and_time,post_path,like_count,is_like,created_at,location,joiningUserList));
                            }

                        }

                        // set data in all events
                        rv_allevents.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_allevents.setHasFixedSize(true);
                        rvEventsAdapter = new RvEventsAdapter(getActivity(), eventList);
                        rv_allevents.setAdapter(rvEventsAdapter);
                        // ******************************************//* // ongoing event list
                        rv_ongoing_events.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
                        rv_ongoing_events.setHasFixedSize(true);
                        rvOngoingAdapter = new RvOngoingAdapter(getActivity(), eventList);
                        rv_ongoing_events.setAdapter(rvOngoingAdapter);

                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

/*        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_timeline_by_user_uuid, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("timeline>>"+response);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmer_ongoing_event.stopShimmer();
                shimmer_ongoing_event.setVisibility(View.GONE);
                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                       // tv_no_request_found.setVisibility(View.GONE);
                        JSONArray jsonArray = response.getJSONArray("get_timelines");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String activity_uuid = object.getString("activity_uuid");
                                String activity_name = object.getString("activity_name");
                                String activity_description = object.getString("activity_description");
                                String user_uuid = object.getString("user_uuid");
                                String profile_name = object.getString("profile_name");
                                String user_pic = object.getString("user_pic");
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String category_name = object.getString("category_name");
                                String sub_category = object.getString("sub_category");
                                String start_date_and_time = object.getString("start_date_and_time");
                                String end_date_and_time = object.getString("end_date_and_time");
                                String post_path = object.getString("post_path");
                                String like_count = object.getString("like_count");
                                String is_like = object.getString("is_like");
                                String created_at = object.getString("created_at");
                                String location = object.getString("location");
                                JSONArray joinings = object.getJSONArray("GetActivityJoinings");


                                // add static data in eventlist
                               eventList.add(new Event(activity_uuid,activity_name,activity_description,
                                       user_uuid,profile_name,user_pic,first_name,last_name,category_name,sub_category,
                                       start_date_and_time,end_date_and_time,post_path,like_count,is_like,created_at,location,joinings));

                            }
                        }

                        // set data in all events
                        rv_allevents.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_allevents.setHasFixedSize(true);
                        rvEventsAdapter = new RvEventsAdapter(getActivity(), eventList);
                        rv_allevents.setAdapter(rvEventsAdapter);
                       *//* ******************************************//* // ongoing event list
                        rv_ongoing_events.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                        rv_ongoing_events.setHasFixedSize(true);
                        rvOngoingAdapter = new RvOngoingAdapter(getActivity(), eventList);
                        rv_ongoing_events.setAdapter(rvOngoingAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("timeline>>"+error);

            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
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
        });*/
    }


    private void findView(View view) {
        rv_ongoing_events = view.findViewById(R.id.rv_ongoing_events);
        rv_allevents = view.findViewById(R.id.rv_allevents);
        mSearchView = view.findViewById(R.id.searchView);
        rl_searchlayout = view.findViewById(R.id.rl_searchlayout);
        rl_toolbar_main = view.findViewById(R.id.rl_toolbar_main);
        iv_search = view.findViewById(R.id.iv_search);
        //shimmer_ongoing_event = view.findViewById(R.id.shimmer_ongoing_event);
       // shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

    }
}
