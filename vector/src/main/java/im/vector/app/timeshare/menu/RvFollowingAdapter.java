package im.vector.app.timeshare.menu;



import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;

public class RvFollowingAdapter extends RecyclerView.Adapter<RvFollowingAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Following> followingList = new ArrayList<>();
    TSSessionManager tsTSSessionManager;
    //RequestQueue requestQueue;

    public RvFollowingAdapter(Context mContext, ArrayList<Following> followingList) {
        this.mContext = mContext;
        this.followingList = followingList;
        tsTSSessionManager = new TSSessionManager(mContext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_following_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Following following = followingList.get(position);
        holder.tv_following_user.setText(following.getFirst_name()+" "+following.getLast_name());
        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+following.getUser_pic()).circleCrop()
                .into(holder.iv_following_avtar);
        holder.tv_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user = new HashMap<>();
                user = tsTSSessionManager.getUserDetails();
                String uuid =  user.get(TSSessionManager.KEY_user_uuid);
                alertMessage(uuid,following.getUser_uuid(),following.getFirst_name()+" "+following.getLast_name(),holder.getAdapterPosition());

            }
        });
    }

    private void alertMessage(String user_uuid, String friend_uuid, String username, int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to unfriend "+username+" ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
          //  unfriend(user_uuid,friend_uuid,adapterPosition);
            dialog.cancel();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

   /* private void unfriend(String user_uuid, String friend_uuid, int adapterPosition) {
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",user_uuid);
        params.put("friend_uuid",friend_uuid);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + api_unfriend, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("unfriend>>"+response);
                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {

                        removeAt(adapterPosition);
                        if (followingList.size()==0){

                            FollowingFragment.ll_empty_following_list.setVisibility(View.VISIBLE);

                        }
                       // Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("unfriend>>"+error);
            }
        });

        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonObjectRequest);
    }*/

    private void removeAt(int adapterPosition) {
        followingList.remove(adapterPosition);
        notifyDataSetChanged();
        notifyItemChanged(adapterPosition);
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_following_avtar;
        TextView tv_following_user,tv_unfollow;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_following_avtar = itemView.findViewById(R.id.iv_following_avtar);
            tv_following_user = itemView.findViewById(R.id.tv_following_user);
            tv_unfollow = itemView.findViewById(R.id.tv_unfollow);
        }
    }
}
