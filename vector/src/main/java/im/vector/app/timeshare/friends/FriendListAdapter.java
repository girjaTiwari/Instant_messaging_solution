package im.vector.app.timeshare.friends;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.TSUtils.MyDialog;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.MyViewHolder> {
    Context mContext;
   ArrayList<RequestModel>arrayList = new ArrayList<>();
   MyDialog myDialog;


    public FriendListAdapter(Context mContext, ArrayList<RequestModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        myDialog = new MyDialog(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_friend_request_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        RequestModel model = arrayList.get(position);
        holder.tv_friend_name.setText(model.getSender_name());

            holder.iv_accept.setVisibility(View.VISIBLE);
            holder.iv_decline.setVisibility(View.VISIBLE);
             holder.tv_ispending.setVisibility(View.GONE);



        holder.iv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // accept_friend_request(model.getFriend_request_uuid(),true);
            }
        });
        holder.iv_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // accept_friend_request(model.getFriend_request_uuid(),false);
            }
        });
    }

   /* private void accept_friend_request(String friend_request_uuid, boolean isAccepted) {
        myDialog.showProgresbar(mContext);
        HashMap<String,String> params = new HashMap<>();
        params.put("friend_request_uuid",friend_request_uuid);
        params.put("is_accepted",String.valueOf(isAccepted));

        System.out.println("param>>"+params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + friend_request, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("accept_request>>"+response);
                 myDialog.hideDialog(mContext);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
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
                System.out.println("accept_request>>"+error);
    myDialog.hideDialog(mContext);

            }
        });

        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonObjectRequest);

    }*/

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_friend_name,tv_message,tv_add_frnd_request,tv_ispending;
        ImageView iv_accept,iv_decline;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_friend_name = itemView.findViewById(R.id.tv_friend_name);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_add_frnd_request = itemView.findViewById(R.id.tv_add_frnd_request);
            iv_accept = itemView.findViewById(R.id.iv_accept_request);
            iv_decline = itemView.findViewById(R.id.iv_cancel_request);
            tv_ispending = itemView.findViewById(R.id.tv_ispending);
        }
    }
}
