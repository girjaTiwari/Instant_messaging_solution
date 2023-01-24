package im.vector.app.timeshare.menu;


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

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;


public class FollowingFragment extends Fragment {
    Context mContext;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    ArrayList<Following> followingList = new ArrayList<>();
    RecyclerView rv_following;

    public static LinearLayout ll_empty_following_list;
    RvFollowingAdapter rvFollowingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        initView(view);
        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        String uuid =  user.get(TSSessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+uuid);
        if (uuid!=null){
           // getFollowingList(uuid);
        }
        return view;
    }

   /* private void getFollowingList(String uuid) {
        myDialog.showProgresbar(mContext);
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_following_list, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("following>>"+response);
                myDialog.hideDialog(mContext);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        ll_empty_following_list.setVisibility(View.GONE);
                        JSONArray jsonArray = response.getJSONArray("Following");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_uuid = object.getString("user_uuid");
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String profile_name = object.getString("profile_name");
                                String user_pic = object.getString("user_pic");

                                followingList.add(new Following(user_uuid,first_name,last_name,profile_name,user_pic));

                            }
                        }


                        rv_following.setLayoutManager(new LinearLayoutManager(mContext));
                        rv_following.setHasFixedSize(true);
                        rvFollowingAdapter = new RvFollowingAdapter(mContext, followingList);
                        rv_following.setAdapter(rvFollowingAdapter);

                    }else {
                        myDialog.hideDialog(mContext);
                        ll_empty_following_list.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("get_friend_request>>"+error);
                myDialog.hideDialog(mContext);

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
       myDialog = new MyDialog(mContext);
        rv_following = view.findViewById(R.id.rv_following);
        ll_empty_following_list = view.findViewById(R.id.ll_empty_following_list);
    }
}
