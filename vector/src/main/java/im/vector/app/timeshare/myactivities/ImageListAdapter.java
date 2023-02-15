package im.vector.app.timeshare.myactivities;




import static im.vector.app.features.home.HomeActivity.ll_content_area;
import static im.vector.app.features.home.HomeActivity.tv_continue_upload_photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import im.vector.app.R;
import im.vector.app.features.home.HomeActivity;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Bitmap> dataList=new ArrayList<>();

    public ImageListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_adapter,parent,false);
        if (dataList.size()>0){
            tv_continue_upload_photo.setVisibility(View.VISIBLE);
        }else {
            ll_content_area.setVisibility(View.VISIBLE);
            tv_continue_upload_photo.setVisibility(View.GONE);
        }
        return new ProductViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(context)
                .load(dataList.get(position))
                .placeholder(context.getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .into(holder.ivImage);
//  .apply(RequestOptions.circleCropTransform()).into(holder.ivImage);
        holder.deleteBtn.setOnClickListener(v -> {
            dataList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            if (dataList.size()>0){
                tv_continue_upload_photo.setVisibility(View.VISIBLE);
            }else {
                ll_content_area.setVisibility(View.VISIBLE);
                tv_continue_upload_photo.setVisibility(View.GONE);
            }

        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(Bitmap bitmap){
        this.dataList.add(bitmap);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivImage;
        private final ImageView deleteBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage=itemView.findViewById(R.id.imageCard);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }
    }
}
