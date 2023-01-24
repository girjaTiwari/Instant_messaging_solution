package im.vector.app.timeshare.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;

public class RvFollowersAdapter extends RecyclerView.Adapter<RvFollowersAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Followers> followersList = new ArrayList<>();

    public RvFollowersAdapter(Context mContext, ArrayList<Followers> followersList) {
        this.mContext = mContext;
        this.followersList = followersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_followers_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Followers followers = followersList.get(position);
        holder.tv_follower_name.setText(followers.getFirst_name()+" "+followers.getLast_name());
        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+followers.getUser_pic()).circleCrop()
                .into(holder.iv_follower_avtar);
    }

    @Override
    public int getItemCount() {
        return followersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_follower_avtar;
        TextView tv_follower_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_follower_avtar = itemView.findViewById(R.id.iv_follower_avtar);
            tv_follower_name = itemView.findViewById(R.id.tv_follower_name);
        }
    }
}
