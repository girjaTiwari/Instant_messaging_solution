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

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.Accept_and_DeclineRequest;
import im.vector.app.timeshare.api_request_body.SendRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.MyViewHolder> {
    Context mContext;
   ArrayList<RequestModel>arrayList = new ArrayList<>();
   MyDialog myDialog;

    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
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

        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+model.getSender_pic()).circleCrop()
                .into(holder.iv_friend_avtar);


        holder.iv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_friend_request(model.getFriend_request_uuid(),true);
            }
        });
        holder.iv_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_friend_request(model.getFriend_request_uuid(),false);
            }
        });
    }

    private void accept_friend_request(String friend_request_uuid, boolean isAccepted) {
        myDialog.showProgresbar(mContext);
        Accept_and_DeclineRequest accept_and_declineRequest = new Accept_and_DeclineRequest(friend_request_uuid, String.valueOf(isAccepted));
        Call<CommonResponse> call = mAPIService.accept_and_decline(accept_and_declineRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                System.out.println("accept-decline>>" + response.toString());
                myDialog.hideDialog(mContext);
                if (response.body() != null) {

                    CommonResponse sendRequest = response.body();
                    String status = sendRequest.getStatus();
                    String mesage = sendRequest.getMsg();
                    if (status.equals("1"))
                    {
                        Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, ""+mesage, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                myDialog.hideDialog(mContext);
                System.out.println("error>>" + t.getCause());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_friend_name,tv_message,tv_add_frnd_request,tv_ispending;
        ImageView iv_accept,iv_decline,iv_friend_avtar;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_friend_name = itemView.findViewById(R.id.tv_friend_name);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_add_frnd_request = itemView.findViewById(R.id.tv_add_frnd_request);
            iv_accept = itemView.findViewById(R.id.iv_accept_request);
            iv_decline = itemView.findViewById(R.id.iv_cancel_request);
            tv_ispending = itemView.findViewById(R.id.tv_ispending);
            iv_friend_avtar = itemView.findViewById(R.id.iv_friend_avtar);
        }
    }
}
