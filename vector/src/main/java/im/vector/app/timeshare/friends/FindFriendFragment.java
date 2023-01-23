package im.vector.app.timeshare.friends;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import im.vector.app.timeshare.api_response_body.GetFriendRequestResponse;
import im.vector.app.timeshare.api_response_body.GetFriendSuggetionResponse;
import im.vector.app.timeshare.api_response_body.GetSentFriendRequestResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;


public class FindFriendFragment extends Fragment {
    Context mContext;
    RvSuggetionListAdapter rvSuggetionListAdapter;
    ArrayList<Suggetion> suggetionArrayList = new ArrayList<>();
    ArrayList<RequestSentModel>sentArrayList = new ArrayList<>();
    RecyclerView sent_friends;
    RecyclerView rv_suggetions;
    TSSessionManager tsSessionManager;

   // MyDialog myDialog;
   // ShimmerFrameLayout shimmerFrameLayout1,shimmerFrameLayout2;
    RvRequestSentAdapter sentAdapter;
   public static LinearLayout ll_your_request_empty,ll_guggetion_list_empty;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find_friend, container, false);
        initView(view);


        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        String user_uuid =  user.get(TSSessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+user_uuid);
        if (user_uuid!=null){
            //suggestion_list(uuid);
           // getSentFriendRequestList(uuid);

            getSentFriendRequestList(user_uuid);
            getSuggetionList(user_uuid);

        }

        return view;
    }

    private void getSentFriendRequestList(String user_uuid) {
        //myDialog.showProgresbar(mContext);
        CommonRequest commonRequest = new CommonRequest(user_uuid);
        Call<GetSentFriendRequestResponse> call = mAPIService.getSentFriendRequest(commonRequest);
        call.enqueue(new Callback<GetSentFriendRequestResponse>() {
            @Override
            public void onResponse(Call<GetSentFriendRequestResponse> call, retrofit2.Response<GetSentFriendRequestResponse> response) {
                System.out.println("sent-list>>" + response.toString());
                //  myDialog.hideDialog(mContext);
                if(response.body()!=null){

                    GetSentFriendRequestResponse sentFriendRequestResponse = response.body();

                    List<RequestSentModel> sentModelList= sentFriendRequestResponse.getGet_sent_friend_request();
                    if (sentModelList!=null){
                        if (sentModelList.size()>0){
                            ll_your_request_empty.setVisibility(View.GONE);
                            for (RequestSentModel sentModel:sentModelList){
                                String friend_request_uuid = sentModel.getFriend_request_uuid();
                                String reciever_uuid = sentModel.getReciever_uuid();
                                String reciever_name = sentModel.getReciever_name();
                                String reciever_pic = sentModel.getReciever_pic();
                                String mutual_friends = sentModel.getMutual_friends();
                                String created_at = sentModel.getCreated_at();

                                // add static data in eventlist
                                sentArrayList.add(new RequestSentModel(friend_request_uuid,reciever_uuid,reciever_name,reciever_pic,mutual_friends,created_at));
                            }

                        }else {
                            ll_your_request_empty.setVisibility(View.VISIBLE);
                        }
                    }else {
                        ll_your_request_empty.setVisibility(View.VISIBLE);
                    }

                    // set data in adapter
                    sent_friends.setLayoutManager(new LinearLayoutManager(mContext));
                    sent_friends.setHasFixedSize(true);
                    sentAdapter = new RvRequestSentAdapter(mContext, sentArrayList);
                    sent_friends.setAdapter(sentAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetSentFriendRequestResponse> call, Throwable t) {
                //  myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());
            }
        });
    }

    private void getSuggetionList(String user_uuid) {
        //myDialog.showProgresbar(mContext);
        CommonRequest commonRequest = new CommonRequest(user_uuid);
        Call<GetFriendSuggetionResponse> call = mAPIService.getFriendSuggetion(commonRequest);
        call.enqueue(new Callback<GetFriendSuggetionResponse>() {
            @Override
            public void onResponse(Call<GetFriendSuggetionResponse> call, retrofit2.Response<GetFriendSuggetionResponse> response) {
                System.out.println("suggetion-list>>" + response.toString());
                //  myDialog.hideDialog(mContext);
                if(response.body()!=null){

                    GetFriendSuggetionResponse suggetionResponse = response.body();
                    String message = suggetionResponse.getMsg();

                    List<Suggetion> suggetionList= suggetionResponse.getSuggestion_list();
                    if (suggetionList!=null){
                        if (suggetionList.size()>0){
                            ll_guggetion_list_empty.setVisibility(View.GONE);
                            for (Suggetion suggetion:suggetionList){
                                String user_id = suggetion.getUser_id();
                                String first_name = suggetion.getFirst_name();
                                String last_name = suggetion.getLast_name();
                                String name = suggetion.getName();
                                String profile_pic = suggetion.getProfile_pic();
                                String mutual_friends = suggetion.getMutual_friends();

                                // add static data in eventlist
                                suggetionArrayList.add(new Suggetion(user_id,first_name,last_name,name,profile_pic,mutual_friends));
                            }

                        }else {
                            ll_guggetion_list_empty.setVisibility(View.VISIBLE);
                        }
                    }else {
                        ll_guggetion_list_empty.setVisibility(View.VISIBLE);
                    }

                    // set data in adapter
                    rv_suggetions.setLayoutManager(new LinearLayoutManager(mContext));
                    rv_suggetions.setHasFixedSize(true);
                    rvSuggetionListAdapter = new RvSuggetionListAdapter(mContext, suggetionArrayList);
                    rv_suggetions.setAdapter(rvSuggetionListAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetFriendSuggetionResponse> call, Throwable t) {
                //  myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());
            }
        });
    }

   /* private void getSentFriendRequestList(String uuid) {
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_sent_friend_request, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("sentList>>"+response);
                shimmerFrameLayout1.stopShimmer();
                shimmerFrameLayout1.setVisibility(View.GONE);
                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {

                        JSONArray jsonArray = response.getJSONArray("get_sent_friend_request");
                        if (jsonArray.length()>0){
                            ll_your_request_empty.setVisibility(View.GONE);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String friend_request_uuid = object.getString("friend_request_uuid");
                                String reciever_uuid = object.getString("reciever_uuid");
                                String reciever_name = object.getString("reciever_name");
                                String reciever_pic = object.getString("reciever_pic");
                                String mutual_friends = object.getString("mutual_friends");
                                String created_at = object.getString("created_at");

                                sentArrayList.add(new RequestSentModel(friend_request_uuid,reciever_uuid,reciever_name,reciever_pic,mutual_friends,created_at));

                            }
                        }else {
                            ll_your_request_empty.setVisibility(View.VISIBLE);
                        }

                        sent_friends.setLayoutManager(new LinearLayoutManager(mContext));
                        sent_friends.setHasFixedSize(true);
                        sentAdapter = new RvRequestSentAdapter(mContext, sentArrayList);
                        sent_friends.setAdapter(sentAdapter);
                    }else {
                        ll_your_request_empty.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sentList>>"+error);


            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
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

   /* private void suggestion_list(String uuid) {
        //myDialog.showProgresbar(mContext);
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_suggestion_list, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("suggetion_list>>"+response);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        shimmerFrameLayout2.stopShimmer();
                        shimmerFrameLayout2.setVisibility(View.GONE);
                        JSONArray jsonArray = response.getJSONArray("suggestion_list");
                        if (jsonArray.length()>0){
                            ll_guggetion_list_empty.setVisibility(View.GONE);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_uuid = object.getString("user_id");
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String profile_name = object.getString("name");
                                String user_pic = object.getString("profile_pic");
                                String mutual_friends = object.getString("mutual_friends");

                                suggetionArrayList.add(new Suggetion(user_uuid,first_name,last_name,profile_name,user_pic,mutual_friends));

                            }
                        }else {
                            ll_guggetion_list_empty.setVisibility(View.VISIBLE);
                        }
                        rv_suggetions.setLayoutManager(new LinearLayoutManager(mContext));
                        rv_suggetions.setHasFixedSize(true);
                        rvSuggetionListAdapter = new RvSuggetionListAdapter(mContext, suggetionArrayList);
                        rv_suggetions.setAdapter(rvSuggetionListAdapter);
                    }else {
                        shimmerFrameLayout2.stopShimmer();
                        shimmerFrameLayout2.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("suggetionlist>>"+error);


            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
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

    private void initView(View view) {
        mContext = getActivity();
        tsSessionManager = new TSSessionManager(mContext);
        sent_friends = view.findViewById(R.id.sent_friends);
        rv_suggetions = view.findViewById(R.id.rv_suggetions);
        ll_your_request_empty = view.findViewById(R.id.ll_your_request_empty);
        ll_guggetion_list_empty = view.findViewById(R.id.ll_guggetion_list_empty);

//        shimmerFrameLayout1 = view.findViewById(R.id.shimmer_view_container1);
//        shimmerFrameLayout2 = view.findViewById(R.id.shimmer_view_container2);
//        shimmerFrameLayout1.startShimmer();
//        shimmerFrameLayout2.startShimmer();

    }

    @Override
    public void onResume() {
        super.onResume();
        // set data for friend request
//        requestArrayList.add(new FriendModel("Andrew","sent you a friend request"));
//        requestArrayList.add(new FriendModel("Annie","sent you a friend request"));
//        requestArrayList.add(new FriendModel("Sammie","sent you a friend request"));
//

//        rv_your_friend.setLayoutManager(new LinearLayoutManager(mContext));
//        rv_your_friend.setHasFixedSize(true);
//        friendListAdapter = new FriendListAdapter(mContext, requestArrayList,true);
//        rv_your_friend.setAdapter(friendListAdapter);

        // set data for your friends

//        friendsuggetionList.add(new FriendModel("Jackie","8 mutual friends"));
//        friendsuggetionList.add(new FriendModel("Samuel","2 mutual friends"));
//        friendsuggetionList.add(new FriendModel("Remy","7 mutual friends"));
//        friendsuggetionList.add(new FriendModel("Mark",""));
//        rv_suggetions.setLayoutManager(new LinearLayoutManager(mContext));
//        rv_suggetions.setHasFixedSize(true);
//        yourFriendsAdapter = new YourFriendsAdapter(mContext, friendsuggetionList,true);
//        rv_suggetions.setAdapter(yourFriendsAdapter);

    }
}
