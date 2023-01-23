package im.vector.app.timeshare.friends;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import im.vector.app.timeshare.api_response_body.EventResponse;
import im.vector.app.timeshare.api_response_body.GetFriendListResponse;
import im.vector.app.timeshare.api_response_body.GetFriendRequestResponse;
import im.vector.app.timeshare.home.RvEventsAdapter;
import im.vector.app.timeshare.home.RvOngoingAdapter;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;


public class FriendListFragment extends Fragment {
    Context mContext;
    FriendListAdapter friendListAdapter;
    YourFriendsAdapter yourFriendsAdapter;
    ArrayList<RequestModel>requestArrayList = new ArrayList<>();
    ArrayList<FriendModel>friendList = new ArrayList<>();
    RecyclerView rv_friend_request;
    RecyclerView rv_your_friends;
    TSSessionManager tsSessionManager;
   public static LinearLayout ll_empty_req_list,ll_empty_friend_list;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    MyDialog myDialog;
    //ShimmerFrameLayout shimmerFrameLayout1,shimmerFrameLayout2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_friend_list, container, false);
      findView(view);
        mContext = getActivity();
        myDialog = new MyDialog(mContext);
        tsSessionManager = new TSSessionManager(getActivity());
        // set data for friend request

            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String user_uuid =  user.get(TSSessionManager.KEY_user_uuid);
           // System.out.println("userid>>"+user_uuid);

            if (user_uuid!=null){
                getFriendRequest(user_uuid);
                getFriends(user_uuid);
            }

        return view;
    }

    private void getFriendRequest(String user_uuid) {
        //myDialog.showProgresbar(mContext);
        CommonRequest commonRequest = new CommonRequest(user_uuid);
        Call<GetFriendRequestResponse> call = mAPIService.getFriendRequest(commonRequest);
        call.enqueue(new Callback<GetFriendRequestResponse>() {
            @Override
            public void onResponse(Call<GetFriendRequestResponse> call, retrofit2.Response<GetFriendRequestResponse> response) {
                System.out.println("friend-request>>" + response.toString());
              //  myDialog.hideDialog(mContext);
                if(response.body()!=null){

                    GetFriendRequestResponse requestResponse = response.body();
                    String message = requestResponse.getMsg();

                    List<RequestModel> requests= requestResponse.getGet_friend_requests();
                    if (requests!=null){
                        if (requests.size()>0){
                            ll_empty_req_list.setVisibility(View.GONE);
                            for (RequestModel requestModel:requests){
                                String friend_request_uuid = requestModel.getFriend_request_uuid();
                                String sender_uuid = requestModel.getSender_uuid();
                                String sender_name = requestModel.getSender_name();
                                String sender_pic = requestModel.getSender_pic();
                                String mutual_friends = requestModel.getMutual_friends();
                                String created_at = requestModel.getCreated_at();

                                // add static data in eventlist
                                requestArrayList.add(new RequestModel(friend_request_uuid,sender_uuid,sender_name,sender_pic,mutual_friends,created_at));
                            }

                        }else {
                            ll_empty_req_list.setVisibility(View.VISIBLE);
                        }
                    }else {
                        ll_empty_req_list.setVisibility(View.VISIBLE);
                    }

                    // set data in adapter
                    rv_friend_request.setLayoutManager(new LinearLayoutManager(mContext));
                    rv_friend_request.setHasFixedSize(true);
                    friendListAdapter = new FriendListAdapter(mContext, requestArrayList);
                    rv_friend_request.setAdapter(friendListAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetFriendRequestResponse> call, Throwable t) {
              //  myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());
            }
        });
    }

    private void getFriends(String user_uuid) {
        myDialog.showProgresbar(mContext);
        CommonRequest commonRequest = new CommonRequest(user_uuid);
        Call<GetFriendListResponse> call = mAPIService.getFriends(commonRequest);
        call.enqueue(new Callback<GetFriendListResponse>() {
            @Override
            public void onResponse(Call<GetFriendListResponse> call, retrofit2.Response<GetFriendListResponse> response) {
                System.out.println("friend-list>>" + response.toString());
                myDialog.hideDialog(mContext);
                if(response.body()!=null){

                    GetFriendListResponse friendListResponse = response.body();
                    String message = friendListResponse.getMsg();

                    List<FriendModel> friends= friendListResponse.getFriend_list();
                   if (friends!=null){
                       if (friends.size()>0){
                           ll_empty_friend_list.setVisibility(View.GONE);
                           for (FriendModel friendModel:friends){
                               String friend_uuid = friendModel.getFriend_uuid();
                               String name = friendModel.getName();
                               String profile_pic = friendModel.getProfile_pic();

                               // add static data in eventlist
                               friendList.add(new FriendModel(friend_uuid,name,profile_pic));
                           }

                       }else {
                           ll_empty_friend_list.setVisibility(View.VISIBLE);
                       }
                   }else {
                       ll_empty_friend_list.setVisibility(View.VISIBLE);
                   }

                    // set data in adapter
                    rv_your_friends.setLayoutManager(new LinearLayoutManager(mContext));
                    rv_your_friends.setHasFixedSize(true);
                    yourFriendsAdapter = new YourFriendsAdapter(mContext, friendList,"notInvite",null);
                    rv_your_friends.setAdapter(yourFriendsAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetFriendListResponse> call, Throwable t) {
                myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());

            }
        });
    }


    private void findView(View view) {

        rv_friend_request = view.findViewById(R.id.friend_request);
        rv_your_friends = view.findViewById(R.id.your_friends);
        ll_empty_req_list = view.findViewById(R.id.ll_empty_req_list);
        ll_empty_friend_list = view.findViewById(R.id.ll_empty_friend_list);


      //  shimmerFrameLayout1 = view.findViewById(R.id.shimmer_view_container1);
      //  shimmerFrameLayout2 = view.findViewById(R.id.shimmer_view_container2);
       // shimmerFrameLayout1.startShimmer();
       // shimmerFrameLayout2.startShimmer();

    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
