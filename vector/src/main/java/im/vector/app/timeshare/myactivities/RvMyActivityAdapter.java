package im.vector.app.timeshare.myactivities;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.DeleteActivityRequest;
import im.vector.app.timeshare.api_request_body.SendRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.friends.FindFriendFragment;
import im.vector.app.timeshare.home.model.Event;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class RvMyActivityAdapter extends RecyclerView.Adapter<RvMyActivityAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Event> eventList = new ArrayList<>();
    TSSessionManager sessionManager;

    MyDialog myDialog;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    String isLike = "";
    public RvMyActivityAdapter(Context mctx, ArrayList<Event> eventList) {
        this.mContext = mctx;
        this.eventList = eventList;
        sessionManager = new TSSessionManager(mctx);
        myDialog = new MyDialog(mctx);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_my_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event model = eventList.get(position);

        holder.tv_activity_name.setText(model.getActivity_name());
        holder.tv_date.setText(model.getStart_date_and_time());
        holder.tv_location.setText(model.getLocation());
        holder.tv_like_count.setText(model.getLike_count());


     /*   String strDate = model.getCreated_at();
        if (strDate!=null && !strDate.equals("")){
            String[] separated = strDate.split("T");
            String date = separated[0];
            String time = separated[1];
            String[] t= time.split("Z");
            String tspit = t[0];
            holder.tv_date.setText(date+" ");
        }*/

        isLike = model.getIs_like();

        if (isLike.equals("true")){
            holder.iv_like.setImageResource(R.drawable.ic_like_a);
        }else {
            holder.iv_like.setImageResource(R.drawable.ic_like_i);
        }
        holder.iv_edit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext,EditActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//               mContext.startActivity(intent);
            }
        });

        holder.iv_eventpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EventDetailsActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//                mContext.startActivity(intent);
            }
        });

        holder.ll_show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EventDetailsActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//                mContext.startActivity(intent);
            }
        });

        String strImages = remove_last_charectar(model.getPost_path());
        //  List<String> imageList = Arrays.asList(strImages.split(","));
        ArrayList<String> imageList = new ArrayList<>(Arrays.asList(strImages.split(",")));


        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+imageList.get(0))
                .into(holder.iv_eventpic);


        holder.iv_delete_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMessage(model.getActivity_uuid(),position);
            }
        });
    }

    private void alertMessage(String activity_uuid,int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to delete this activity ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            deleteActivity(activity_uuid,pos);
            dialog.cancel();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteActivity(String activity_uuid,int delete_pos) {

        DeleteActivityRequest delete_activity = new DeleteActivityRequest(activity_uuid);
        Call<CommonResponse> call = mAPIService.deleteActivity(delete_activity);
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
                        removeAt(delete_pos);
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

    private void removeAt(int delete_pos) {
        eventList.remove(delete_pos);
        notifyItemRemoved(delete_pos);
        notifyItemRangeChanged(delete_pos, eventList.size());
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
        ImageView iv_eventpic,iv_like,iv_edit_activity,iv_delete_activity;
        LinearLayout ll_show_details;
        TextView tv_activity_name,tv_location,tv_date,tv_like_count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_eventpic = itemView.findViewById(R.id.iv_eventpic);
            iv_like = itemView.findViewById(R.id.iv_like);
            tv_activity_name = itemView.findViewById(R.id.tv_activity_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            ll_show_details = itemView.findViewById(R.id.ll_show_details);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            iv_edit_activity = itemView.findViewById(R.id.iv_edit_activity);
            iv_delete_activity = itemView.findViewById(R.id.iv_delete_activity);
        }
    }
}
