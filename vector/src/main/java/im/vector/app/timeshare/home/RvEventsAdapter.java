package im.vector.app.timeshare.home;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.home.model.JoiningUser;

public class RvEventsAdapter extends RecyclerView.Adapter<RvEventsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Event> eventList = new ArrayList<>();
    TSSessionManager tsSessionManager;
  //  RequestQueue requestQueue;
    MyDialog myDialog;
    ArrayList<JoiningUser>joiningUsers = new ArrayList<>();
    RvInterestedAdapter rvInterestedAdapter;
    JSONArray jsonArray;

    public RvEventsAdapter(Context mContext, ArrayList<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
        tsSessionManager = new TSSessionManager(mContext);
        myDialog = new MyDialog(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_events_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Event model = eventList.get(position);
       /* jsonArray = model.getJsonArray();
        joiningUsers.clear();
        try {
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String activity_uuid = object.getString("activity_uuid");
                String first_name  = object.getString("first_name");
                String last_name = object.getString("last_name");
                String user_name = object.getString("user_name");
                String profile_pic = object.getString("profile_pic");

                joiningUsers.add(new JoiningUser(activity_uuid,first_name,last_name,user_name,profile_pic));
                holder.rv_attendies.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                rvInterestedAdapter=new RvInterestedAdapter(mContext,joiningUsers);
                holder.rv_attendies.setAdapter(rvInterestedAdapter);

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
*/
        holder.tv_name.setText(model.getActivity_name());
        holder.tv_date.setText(model.getStart_time());
        holder.tv_location.setText(model.getLocation());
        holder.tv_category.setText(model.getCategory_name());

        if (!model.getLike_count().equals("0")){
            holder.tv_like_count.setText(model.getLike_count());
        }

        if (Boolean.parseBoolean(model.getIs_like())) {
            holder.iv_like.setImageResource(R.drawable.ic_like_a);
        } else {
            holder.iv_like.setImageResource(R.drawable.ic_like_i);
        }

        String strImages = remove_last_charectar(model.getPost_path());
        ArrayList<String> imageList = new ArrayList<>(Arrays.asList(strImages.split(",")));

        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+imageList.get(0))
                .into(holder.iv_eventpic);

        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+model.getUser_pic())
                .into(holder.iv_profilepic);

        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mContext.startActivity(new Intent(mContext, EventDetailsActivity.class));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, RespondActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//                mContext.startActivity(intent);
            }
        });

        holder.iv_profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MyProfileActivity.class);
//                intent.putExtra("friend_uuid",model.getUser_uuid());
//                mContext.startActivity(intent);
            }
        });

        holder.iv_eventpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext,EventDetailsActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//               mContext.startActivity(intent);
            }
        });

        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                HashMap<String, String> user = new HashMap<>();
                user = tsSessionManager.getUserDetails();
                String uuid =  user.get(TSSessionManager.KEY_user_uuid);
                Event model=eventList.get(position);
                String isLike = model.getIs_like();
                HashMap<String,String> params = new HashMap<>();
                params.put("activity_uuid",model.getActivity_uuid());
                params.put("user_uuid",uuid);
                if (Boolean.parseBoolean(isLike))
                {
                    params.put("like","false");
                }else {
                    params.put("like","true");

                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + like_post, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("like-post>>"+response);

                      //  rvEventsAdapter.notifyItemChanged(position);
                        try {
                            String status = response.getString("Status");
                            String mesage = response.getString("Msg");
                            if (status.equals("1"))
                            {

                               JSONObject object = response.getJSONObject("like_count");
                                String count = object.getString("like_count");
                                holder.tv_like_count.setText(count);
                                if (mesage.equalsIgnoreCase("Liked")){
                                    holder.iv_like.setImageResource(R.drawable.ic_like_a);
                                }else {
                                    holder.iv_like.setImageResource(R.drawable.ic_like_i);

                                }
                                rvEventsAdapter.notifyItemChanged(position);
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
                        System.out.println("timeline>>"+error);
                    }
                });

                requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(jsonObjectRequest);*/
            }
        });

    }



    public void filterList(ArrayList<Event> filteredlist) {
        eventList = filteredlist;
        notifyDataSetChanged();
    }

    private String remove_last_charectar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profilepic,iv_eventpic,iv_like;
        LinearLayout ll_details;
        RecyclerView rv_attendies;
        TextView tv_name,tv_location,tv_category,tv_date,tv_like_count;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_profilepic = itemView.findViewById(R.id.iv_profilepic);
            iv_eventpic = itemView.findViewById(R.id.iv_eventpic);
            iv_like = itemView.findViewById(R.id.iv_like);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            ll_details = itemView.findViewById(R.id.ll_details);
            rv_attendies = itemView.findViewById(R.id.rv_attendies);
        }
    }
}
