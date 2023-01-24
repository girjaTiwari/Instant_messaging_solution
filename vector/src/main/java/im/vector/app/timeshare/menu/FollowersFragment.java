package im.vector.app.timeshare.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;

public class FollowersFragment extends Fragment {
    Context mContext;
    ArrayList<Followers> followersList = new ArrayList<>();
    RvFollowersAdapter rvFollowersAdapter;
    RecyclerView rv_followers;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;

    TextView tv_no_followers_found;
    ImageView iv_follow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_followers, container, false);
     initView(view);

        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        String uuid =  user.get(TSSessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+uuid);
        if (uuid!=null){
            //getFollowerList(uuid);
        }
        return view;
    }

 /*   private void getFollowerList(String uuid) {
        myDialog.showProgresbar(mContext);
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_follower_list, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("followers>>"+response);
                myDialog.hideDialog(mContext);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        tv_no_followers_found.setVisibility(View.GONE);
                        iv_follow.setVisibility(View.GONE);
                        JSONArray jsonArray = response.getJSONArray("Follower");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String user_uuid = object.getString("user_uuid");
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String profile_name = object.getString("profile_name");
                                String user_pic = object.getString("user_pic");

                                followersList.add(new Followers(user_uuid,first_name,last_name,profile_name,user_pic));


                            }
                        }


                        rv_followers.setLayoutManager(new LinearLayoutManager(mContext));
                        rv_followers.setHasFixedSize(true);
                        rvFollowersAdapter = new RvFollowersAdapter(mContext, followersList);
                        rv_followers.setAdapter(rvFollowersAdapter);
                        //    Toast.makeText(getActivity(), ""+mesage, Toast.LENGTH_SHORT).show();

                        // finish();

                    }else {
                        myDialog.hideDialog(mContext);
                        tv_no_followers_found.setVisibility(View.VISIBLE);
                        iv_follow.setVisibility(View.VISIBLE);
                        tv_no_followers_found.setText(mesage);
                        //  Toast.makeText(getActivity(), ""+mesage, Toast.LENGTH_SHORT).show();
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

    private void initView(View view) {
        mContext = getActivity();
        myDialog= new MyDialog(mContext);
        tsSessionManager = new TSSessionManager(mContext);
        rv_followers = view.findViewById(R.id.rv_followers);
        tv_no_followers_found = view.findViewById(R.id.tv_no_followers_found);
        iv_follow = view.findViewById(R.id.iv_follow);
    }
}
