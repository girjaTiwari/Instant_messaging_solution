package im.vector.app.timeshare.details;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.myactivities.ImageViewerActivity;

public class RvGalleryAdapter extends RecyclerView.Adapter<RvGalleryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> dataList=new ArrayList<>();
    String first_name,last_name,user_pic,friend_uuid;

    public RvGalleryAdapter(Context context, ArrayList<String> dataList, String fistname, String lastname, String userpic, String frienduuid) {
        this.context = context;
        this.dataList = dataList;
        this.first_name = fistname;
        this.last_name=lastname;
        this.user_pic=userpic;
        this.friend_uuid=frienduuid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvitem_gallery,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiClass.IMAGE_BASE_URL+dataList.get(position))
                .placeholder(context.getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .into(holder.iv_iamgeview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageViewerActivity.class);
                intent.putExtra("image",ApiClass.IMAGE_BASE_URL+dataList.get(holder.getAdapterPosition()));
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name",last_name);
                intent.putExtra("user_pic",user_pic);
                intent.putExtra("friend_uuid",friend_uuid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_iamgeview;
        private  ImageView deleteBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_iamgeview=itemView.findViewById(R.id.iv_iamgeview);
          //  deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }
    }
}
