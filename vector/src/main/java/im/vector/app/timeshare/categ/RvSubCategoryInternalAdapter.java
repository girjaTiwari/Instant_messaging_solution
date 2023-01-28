package im.vector.app.timeshare.categ;

import static im.vector.app.timeshare.categ.SubCategoryActivity.selectedSubCategoryList1;
import static im.vector.app.timeshare.categ.SubCategoryActivity.selectedSubCategoryList2;
import static im.vector.app.timeshare.categ.SubCategoryActivity.selectedSubCategoryList3;
import static im.vector.app.timeshare.categ.SubCategoryActivity.selectedSubCategoryList4;
import static im.vector.app.timeshare.categ.SubCategoryActivity.selectedSubCategoryList5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import im.vector.app.R;

public class RvSubCategoryInternalAdapter extends RecyclerView.Adapter<RvSubCategoryInternalAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<SubCategory> subCategoryList = new ArrayList<>();

    public RvSubCategoryInternalAdapter(Context mContext, ArrayList<SubCategory> subCategoryList) {
        this.mContext = mContext;
        this.subCategoryList = subCategoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_subcategory_internal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        selectedSubCategoryList1.clear();
        selectedSubCategoryList2.clear();
        selectedSubCategoryList3.clear();
        selectedSubCategoryList4.clear();
        selectedSubCategoryList5.clear();
        SubCategory model = subCategoryList.get(position);
        holder.tv_subcateg_name.setText(model.getSub_categ_name());

       /* if (position==0){
            holder.tv_subcateg_name.setBackground(ContextCompat.getDrawable(mContext,R.drawable.red_rounded_bg));
            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model.getCateg_posi()==0){

                        if (!model.isSelected()){
                            if (selectedSubCategoryList1.size()<5){
                                holder.tv_subcateg_name.setBackgroundResource(R.drawable.red_rounded_bg);
                                holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                model.setSelected(true);
                                selectedSubCategoryList1.add(model.getSub_categ_name());
                            }else {
                                Toast.makeText(mContext, "You can select only 5 Sub Category in Each Category", Toast.LENGTH_SHORT).show();
                            }
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }else {
                            model.setSelected(false);
                            selectedSubCategoryList1.remove(selectedSubCategoryList1.size()-1);
                            holder.tv_subcateg_name.setBackgroundResource(model.isSelected() ? R.drawable.red_rounded_bg : R.color.white);
                            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.text_grey2));
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }

                }

                if (model.getCateg_posi()==1){

                        if (!model.isSelected()){
                            if (selectedSubCategoryList2.size()<5){
                                holder.tv_subcateg_name.setBackgroundResource(R.drawable.red_rounded_bg);
                                holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                model.setSelected(true);
                                selectedSubCategoryList2.add(model.getSub_categ_name());
                            }else {
                                Toast.makeText(mContext, "You can select only 5 Sub Category in Each Category", Toast.LENGTH_SHORT).show();
                            }
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }else {
                            model.setSelected(false);
                            selectedSubCategoryList2.remove(selectedSubCategoryList2.size()-1);
                            holder.tv_subcateg_name.setBackgroundResource(model.isSelected() ? R.drawable.red_rounded_bg : R.color.white);
                            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.text_grey2));
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }
                }

                if (model.getCateg_posi()==2){

                        if (!model.isSelected()){
                            if (selectedSubCategoryList3.size()<5){
                                holder.tv_subcateg_name.setBackgroundResource(R.drawable.red_rounded_bg);
                                holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                model.setSelected(true);
                                selectedSubCategoryList3.add(model.getSub_categ_name());
                            }else {
                                Toast.makeText(mContext, "You can select only 5 Sub Category in Each Category", Toast.LENGTH_SHORT).show();
                            }
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }else {
                            model.setSelected(false);
                            selectedSubCategoryList3.remove(selectedSubCategoryList3.size()-1);
                            holder.tv_subcateg_name.setBackgroundResource(model.isSelected() ? R.drawable.red_rounded_bg : R.color.white);
                            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.text_grey2));
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }

                }

                if (model.getCateg_posi()==3){

                        if (!model.isSelected()){
                            if (selectedSubCategoryList4.size()<5){
                                holder.tv_subcateg_name.setBackgroundResource(R.drawable.red_rounded_bg);
                                holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                model.setSelected(true);
                                selectedSubCategoryList4.add(model.getSub_categ_name());
                            }else {
                                Toast.makeText(mContext, "You can select only 5 Sub Category in Each Category", Toast.LENGTH_SHORT).show();
                            }
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }else {
                            model.setSelected(false);
                            selectedSubCategoryList4.remove(selectedSubCategoryList4.size()-1);
                            holder.tv_subcateg_name.setBackgroundResource(model.isSelected() ? R.drawable.red_rounded_bg : R.color.white);
                            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.text_grey2));
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }

                }


                if (model.getCateg_posi()==4){

                        if (!model.isSelected()){
                            if (selectedSubCategoryList5.size()<5){
                                holder.tv_subcateg_name.setBackgroundResource(R.drawable.red_rounded_bg);
                                holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                model.setSelected(true);
                                selectedSubCategoryList5.add(model.getSub_categ_name());
                            }else {
                                Toast.makeText(mContext, "You can select only 5 Sub Category in Each Category", Toast.LENGTH_SHORT).show();
                            }
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }else {
                            model.setSelected(false);
                            selectedSubCategoryList5.remove(selectedSubCategoryList5.size()-1);
                            holder.tv_subcateg_name.setBackgroundResource(model.isSelected() ? R.drawable.red_rounded_bg : R.color.white);
                            holder.tv_subcateg_name.setTextColor(ContextCompat.getColor(mContext,R.color.text_grey2));
                            // System.out.println("list>>" + selectedCategoryList.toString());
                        }

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subcateg_name;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_subcateg_name = itemView.findViewById(R.id.tv_subcateg_name);
        }
    }
}
