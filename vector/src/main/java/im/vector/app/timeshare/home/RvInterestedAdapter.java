package im.vector.app.timeshare.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.home.model.JoiningUser;

public class RvInterestedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_MORE = 1;
    private final Context mContext;
    private  List<JoiningUser> dataList=new ArrayList<>();

    public RvInterestedAdapter(Context context, List<JoiningUser>list) {
        this.mContext = context;
        this.dataList=list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                return new MyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_interested, parent, false));
            case VIEW_TYPE_MORE:
                return new MoreThan3ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grey_circle, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyViewHolder) {

            populateItemMedia((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof MoreThan3ViewHolder) {

            showViewMore((MoreThan3ViewHolder) viewHolder, position);
        }

    }

    private void showViewMore(MoreThan3ViewHolder viewHolder, int position) {

    }

    private void populateItemMedia(MyViewHolder viewHolder, int position) {
        if (dataList.size()>0) {
            JoiningUser model = dataList.get(position);
            Glide.with(mContext).load(ApiClass.IMAGE_BASE_URL + model.getProfile_pic()).circleCrop()
                    .placeholder(mContext.getResources().getDrawable(R.drawable.avtar))
                    .into(viewHolder.ivImage);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
            if (position != 0) {
                params.setMargins(-10, 0, 0, 0);
            }

            viewHolder.ivImage.setLayoutParams(params);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TSRespondActivity.class);
                    intent.putExtra("activity_uuid", model.getActivity_uuid());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (dataList==null){
           return 0;
        }else if (dataList.size()>3){
            return 3;
        }else {
            return dataList.size();
        }
     //   return dataList == null ? 0 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position>1){
            return  VIEW_TYPE_MORE;
        }else {
            return VIEW_TYPE_ITEM;
        }
        //  return dataList.get(position) == null ? VIEW_TYPE_MORE : VIEW_TYPE_ITEM;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
         ImageView ivImage;
        public MyViewHolder(View itemview) {
            super(itemview);
            ivImage = itemview.findViewById(R.id.iv_attendi);
        }
    }

    private class MoreThan3ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_total;
        public MoreThan3ViewHolder(View itemview) {
            super(itemview);
            tv_total = itemview.findViewById(R.id.tv_total);
            int total = dataList.size()-2;
            tv_total.setText("+"+total);
        }
    }
}
