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

public class RvSuggetionListAdapter extends RecyclerView.Adapter<RvSuggetionListAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Suggetion>arrayList = new ArrayList<>();
    TSSessionManager tsSessionManager;
    String receiver_uuid="";
    MyDialog myDialog;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    public RvSuggetionListAdapter(Context mContext, ArrayList<Suggetion> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        myDialog = new MyDialog(mContext);
        tsSessionManager = new TSSessionManager(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_suggetion_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Suggetion suggetion = arrayList.get(position);
        holder.tv_friend_name.setText(suggetion.getName());
        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+suggetion.getProfile_pic()).circleCrop()
                .into(holder.iv_profile_pic);


         String mutual = suggetion.getMutual_friends();
         if (!mutual.equals("")){
             if (Integer.parseInt(mutual)>0){
                 holder.tv_mutual.setVisibility(View.VISIBLE);
                 holder.tv_mutual.setText(mutual);
             }else {
                 holder.tv_mutual.setVisibility(View.GONE);
             }
         }


        holder.tv_add_frnd_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user = new HashMap<>();
                user = tsSessionManager.getUserDetails();
                String sender_uuid=  user.get(TSSessionManager.KEY_user_uuid);
                if (sender_uuid!=null){
                    receiver_uuid = suggetion.getUser_id();
                    sendFriendRequest(holder,sender_uuid,receiver_uuid);

                }
            }
        });

         holder.iv_profile_pic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               /*  Intent intent = new Intent(mContext,MyProfileActivity.class);
                 intent.putExtra("friend_uuid",suggetion.getUser_uuid());
                 mContext.startActivity(intent);*/
             }
         });

    }

    private void sendFriendRequest(MyViewHolder holder,String sender_uuid, String receiver_uuid) {
        myDialog.showProgresbar(mContext);
        SendRequest sendRequest = new SendRequest(sender_uuid, receiver_uuid);
        Call<CommonResponse> call = mAPIService.sendFriendRequest(sendRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                System.out.println("reuest-send>>" + response.toString());
                  myDialog.hideDialog(mContext);
                if (response.body() != null) {

                    CommonResponse sendRequest = response.body();
                    String status = sendRequest.getStatus();
                    String mesage = sendRequest.getMsg();
                    if (status.equals("1")) {
                        holder.tv_add_frnd_request.setText("Requested");
                        holder.tv_add_frnd_request.setClickable(false);
                        Toast.makeText(mContext, "" + mesage, Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(mContext, "" + mesage, Toast.LENGTH_SHORT).show();
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
        ImageView iv_profile_pic;
        TextView tv_friend_name,tv_add_frnd_request,tv_mutual;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_pic = itemView.findViewById(R.id.iv_profile_pic);
            tv_friend_name = itemView.findViewById(R.id.tv_friend_name);
            tv_add_frnd_request = itemView.findViewById(R.id.tv_add_frnd_request);
            tv_mutual = itemView.findViewById(R.id.tv_mutual);
        }
    }
}
