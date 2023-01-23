package im.vector.app.timeshare.friends;


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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.TSSessionManager;

public class YourFriendsAdapter extends RecyclerView.Adapter<YourFriendsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<FriendModel> arrayList = new ArrayList<>();
    TSSessionManager tsSessionManager;
    String flag_string,activity_uuid;


    public YourFriendsAdapter(Context mContext, ArrayList<FriendModel> arrayList, String string, String activityuuid) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        tsSessionManager = new TSSessionManager(mContext);
        flag_string = string;
        activity_uuid = activityuuid;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_your_friends,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        FriendModel model = arrayList.get(position);
        holder.tv_friend_name.setText(model.getName());
        if (flag_string.equalsIgnoreCase("notInvite")){
            holder.tv_invite.setVisibility(View.GONE);
            holder.tv_invited.setVisibility(View.GONE);
            holder.iv_cross.setVisibility(View.VISIBLE);
        }else {
            holder.tv_invite.setVisibility(View.VISIBLE);
            holder.tv_invited.setVisibility(View.GONE);
            holder.iv_cross.setVisibility(View.GONE);
        }
        holder.iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user = new HashMap<>();
                user = tsSessionManager.getUserDetails();
                String uuid =  user.get(TSSessionManager.KEY_user_uuid);
                alertMessage(uuid,model.getFriend_uuid(),model.getName(),holder.getAdapterPosition());

            }
        });
        holder.tv_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user = new HashMap<>();
                user = tsSessionManager.getUserDetails();
                String user_uuid =  user.get(TSSessionManager.KEY_user_uuid);
                String friend_uuid=model.getFriend_uuid();
                if (user_uuid!=null && friend_uuid!=null && activity_uuid!=null){
                   // inviteFriend(user_uuid,friend_uuid,activity_uuid,holder.tv_invite,holder.tv_invited);
                }
            }
        });
    }

    /*private void inviteFriend(String user_uuid,String friend_uuid,String activity_uuid,TextView tv_invite,TextView tv_invited) {
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",user_uuid);
        params.put("friend_uuid",friend_uuid);
        params.put("activity_uuid",activity_uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + invite_friends, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("invite>>"+response);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        tv_invite.setVisibility(View.GONE);
                        tv_invited.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
                    }else {
                        // Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("invite>>"+error);



            }
        });

        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonObjectRequest);
    }*/


    private void alertMessage(String user_uuid,String friend_uuid,String username,int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to unfriend "+username+" ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            //unfriend(user_uuid,friend_uuid,pos);
            dialog.cancel();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

/*    private void unfriend(String uuid, String friend_uuid,int position) {
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);
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

                        removeAt(position);
                        if (arrayList.size()==0){

                            FriendListFragment.ll_empty_friend_list.setVisibility(View.VISIBLE);

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

    private void removeAt(int position) {
        arrayList.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_friend_name,tv_message,tv_invited,tv_invite;
        ImageView iv_cross;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_friend_name = itemView.findViewById(R.id.tv_friend_name);
            tv_message = itemView.findViewById(R.id.tv_message);
            iv_cross = itemView.findViewById(R.id.iv_cross);
            tv_invited = itemView.findViewById(R.id.tv_invited);
            tv_invite = itemView.findViewById(R.id.tv_invite);
        }
    }
}
