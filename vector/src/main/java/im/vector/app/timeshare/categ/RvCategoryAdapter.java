package im.vector.app.timeshare.categ;



import static im.vector.app.timeshare.categ.CategoryActivity.selectedCategoryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import im.vector.app.R;

public class RvCategoryAdapter extends RecyclerView.Adapter<RvCategoryAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Category> categoryList = new ArrayList<>();



    public RvCategoryAdapter(Context mContext, ArrayList<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        selectedCategoryList.clear();
        Category model = categoryList.get(position);

        holder.iv_category_image.setImageResource(model.getImage());
        holder.tv_category_name.setText(model.getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!model.isSelected()){
                    if (selectedCategoryList.size()<5){
                        holder.rl_item_category.setBackgroundResource(R.drawable.selected_category_bg);
                        holder.iv_check.setVisibility(View.VISIBLE);
                        model.setSelected(true);
                        selectedCategoryList.add(model.getCategory());
                    }else {
                        Toast.makeText(mContext, "You can select only 5 category", Toast.LENGTH_SHORT).show();
                    }
                    // System.out.println("list>>" + selectedCategoryList.toString());
                }else {
                    model.setSelected(false);
                    selectedCategoryList.remove(selectedCategoryList.size()-1);
                    holder.rl_item_category.setBackgroundResource(model.isSelected() ? R.drawable.selected_category_bg : R.color.white);
                    holder.iv_check.setVisibility(View.INVISIBLE);
                   // System.out.println("list>>" + selectedCategoryList.toString());
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_category_image,iv_check;
        TextView tv_category_name;
        RelativeLayout rl_item_category;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_category_image = itemView.findViewById(R.id.iv_category_image);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            rl_item_category = itemView.findViewById(R.id.rl_item_category);
            iv_check = itemView.findViewById(R.id.iv_check);
        }
    }
}
