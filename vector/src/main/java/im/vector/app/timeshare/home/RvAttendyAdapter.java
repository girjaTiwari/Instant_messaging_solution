package im.vector.app.timeshare.home;

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
import im.vector.app.timeshare.home.model.JoiningUser;

public class RvAttendyAdapter extends RecyclerView.Adapter<RvAttendyAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<JoiningUser>joiningUserArrayList=new ArrayList<>();

    public RvAttendyAdapter(Context mContext, ArrayList<JoiningUser> joiningUserArrayList) {
        this.mContext = mContext;
        this.joiningUserArrayList = joiningUserArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_attendy,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JoiningUser model = joiningUserArrayList.get(position);
        holder.tv_name.setText(model.getFirst_name());
        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+model.getProfile_pic())
                .placeholder(R.drawable.avtar)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return joiningUserArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_iamge);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
