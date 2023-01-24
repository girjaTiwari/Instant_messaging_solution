package im.vector.app.timeshare.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import im.vector.app.R;
import im.vector.app.timeshare.categ.SubCategory;

public class RvSpinnerSubCategoryAdapter extends RecyclerView.Adapter<RvSpinnerSubCategoryAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<SubCategory> subCategoryList= new ArrayList<>();

    public RvSpinnerSubCategoryAdapter(Context mContext, ArrayList<SubCategory> subCategoryList) {
        this.mContext = mContext;
        this.subCategoryList = subCategoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_spinner_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        SubCategory model = subCategoryList.get(position);
        holder.tv_text_category_name.setText(model.getSub_categ_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.iv_checked.setImageResource(model.isSelected() ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_unchecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_checked;
        TextView tv_text_category_name;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_checked = itemView.findViewById(R.id.iv_checked);
            tv_text_category_name = itemView.findViewById(R.id.tv_text_category_name);
        }
    }
}
