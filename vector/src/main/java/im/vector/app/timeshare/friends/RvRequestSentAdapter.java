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
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.SendRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class RvRequestSentAdapter extends RecyclerView.Adapter<RvRequestSentAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<RequestSentModel>arrayList = new ArrayList<>();
    TSSessionManager tsSessionManager;
    String receiver_uuid="";
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    MyDialog myDialog;
    public RvRequestSentAdapter(Context mContext, ArrayList<RequestSentModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        myDialog = new MyDialog(mContext);
        tsSessionManager = new TSSessionManager(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_friend_request_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestSentModel model = arrayList.get(position);
        holder.tv_friend_name.setText(model.getReciever_name());
        // holder.tv_message.setText(model.getMessage());
         holder.tv_ispending.setVisibility(View.VISIBLE);
         holder.iv_accept.setVisibility(View.GONE);
         holder.iv_decline.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+model.getReciever_pic())
                .into(holder.iv_friend_avtar);

        holder.iv_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user = new HashMap<>();
                user = tsSessionManager.getUserDetails();
                String sender_uuid=  user.get(TSSessionManager.KEY_user_uuid);
                if (sender_uuid!=null){
                    receiver_uuid = model.getReciever_uuid();
                    unduFriendRequest(sender_uuid,receiver_uuid,holder.getAdapterPosition());

                }
            }
        });

    }

    private void unduFriendRequest(String sender_uuid, String receiver_uuid,int pos) {
        myDialog.showProgresbar(mContext);
        SendRequest sendRequest = new SendRequest(sender_uuid, receiver_uuid);
        Call<CommonResponse> call = mAPIService.undoFriendRequest(sendRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                System.out.println("reuest-undo>>" + response.toString());
                myDialog.hideDialog(mContext);
                if (response.body() != null) {

                    CommonResponse sendRequest = response.body();
                    String status = sendRequest.getStatus();
                    String mesage = sendRequest.getMsg();
                    if (status.equals("1"))
                    {
                        removeAt(pos);
                        if (arrayList.size()==0){

                            FindFriendFragment.ll_your_request_empty.setVisibility(View.VISIBLE);

                        }
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
