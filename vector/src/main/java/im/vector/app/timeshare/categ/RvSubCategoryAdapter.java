package im.vector.app.timeshare.categ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import im.vector.app.R;

public class RvSubCategoryAdapter extends RecyclerView.Adapter<RvSubCategoryAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<SubCategory> subCategoryList = new ArrayList<>();
    RvSubCategoryInternalAdapter rvSubCategoryInternalAdapter;
    public static boolean cate1,cate2,cate3,cate4,cate5;

    public RvSubCategoryAdapter(Context mContext, ArrayList<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_sub_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Category model = categoryList.get(position);
        holder.iv_categ_image.setImageResource(model.getImage());
        holder.tv_categ_name.setText(model.getCategory());

        if (!model.getCategory().isEmpty()){
            holder.img_categ.setImageResource(R.drawable.ic_checkbox_checked);
        }else {
            holder.img_categ.setImageResource(R.drawable.ic_checkbox_unchecked);
        }

        subCategoryList.clear();

        holder.rv_sub_category.setLayoutManager(new GridLayoutManager(mContext,3));
        holder.rv_sub_category.setHasFixedSize(true);
        // add static data in eventlist
        if (model.getCategory().equals("Travelling")){

            subCategoryList.add(new SubCategory("Mountains",0,model.getCategory()));
            subCategoryList.add(new SubCategory("Desert",0, model.getCategory()));
            subCategoryList.add(new SubCategory("Sea",0,model.getCategory()));
            subCategoryList.add(new SubCategory("Plains",0,model.getCategory()));
            subCategoryList.add(new SubCategory("City",0,model.getCategory()));

        }
        if (model.getCategory().equals("Education")){
            subCategoryList.add(new SubCategory("Engineering",1,model.getCategory()));
            subCategoryList.add(new SubCategory("Medical",1,model.getCategory()));
            subCategoryList.add(new SubCategory("Astrophysics",1,model.getCategory()));
            subCategoryList.add(new SubCategory("Science",1,model.getCategory()));
            subCategoryList.add(new SubCategory("Economics",1,model.getCategory()));
        }
        if (model.getCategory().equals("Sports")){
            subCategoryList.add(new SubCategory("Cricket",2,model.getCategory()));
            subCategoryList.add(new SubCategory("Hockey",2,model.getCategory()));
            subCategoryList.add(new SubCategory("Tenis",2,model.getCategory()));
            subCategoryList.add(new SubCategory("Football",2,model.getCategory()));
            subCategoryList.add(new SubCategory("Chess",2,model.getCategory()));
        }

        if (model.getCategory().equals("Cars")){
            subCategoryList.add(new SubCategory("BMW",3,model.getCategory()));
            subCategoryList.add(new SubCategory("Mercedes ",3,model.getCategory()));
            subCategoryList.add(new SubCategory("Maruti Suzuki ",3,model.getCategory()));
            subCategoryList.add(new SubCategory("Honda",3,model.getCategory()));
            subCategoryList.add(new SubCategory("Ferrari ",3,model.getCategory()));
        }

        if (model.getCategory().equals("Movie")){
            subCategoryList.add(new SubCategory("Action",4,model.getCategory()));
            subCategoryList.add(new SubCategory("Drama ",4,model.getCategory()));
            subCategoryList.add(new SubCategory("Horror ",4,model.getCategory()));
            subCategoryList.add(new SubCategory("Thrill",4,model.getCategory()));
            subCategoryList.add(new SubCategory("3D ",4,model.getCategory()));
        }


        rvSubCategoryInternalAdapter = new RvSubCategoryInternalAdapter(mContext, subCategoryList);
        holder.rv_sub_category.setAdapter(rvSubCategoryInternalAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Category category = categoryList.get(holder.getAdapterPosition());
                if (!model.isSelected()){
                    model.setSelected(true);
                    holder.img_categ.setImageResource(R.drawable.ic_checkbox_checked);
                    if (holder.getAdapterPosition()==0){
                        cate1=true;
                    }else if (holder.getAdapterPosition()==1){
                        cate2=true;
                    }else if (holder.getAdapterPosition()==2){
                        cate3=true;
                    }else if (holder.getAdapterPosition()==3){
                        cate4=true;
                    }else if (holder.getAdapterPosition()==4){
                        cate5=true;
                    }

                    // Toast.makeText(mContext, ""+holder.getAdapterPosition()+"-"+category.getCategory(), Toast.LENGTH_SHORT).show();
                }else {
                    model.setSelected(false);
                    holder.img_categ.setImageResource(R.drawable.ic_checkbox_unchecked);
                    if (holder.getAdapterPosition()==0){
                        cate1=false;
                    }else if (holder.getAdapterPosition()==1){
                        cate2=false;
                    }else if (holder.getAdapterPosition()==2){
                        cate3=false;
                    }else if (holder.getAdapterPosition()==3){
                        cate4=false;
                    }else if (holder.getAdapterPosition()==4){
                        cate5=false;
                    }
                    //   Toast.makeText(mContext, ""+holder.getAdapterPosition()+"-"+category.getCategory(), Toast.LENGTH_SHORT).show();
                }

            }
        });
       /* holder.checkbox_categ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(((CompoundButton) buttonView).isChecked()){
                    holder.checkbox_categ.setChecked(false);
                    System.out.println("Checked");
                } else {
                    holder.checkbox_categ.setChecked(true);
                    System.out.println("Un-Checked");
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return CategoryActivity.selectedCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_categ_image;
        TextView tv_categ_name;
        RecyclerView rv_sub_category;
        ImageView img_categ;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_categ_image = itemView.findViewById(R.id.iv_categ_image);
            tv_categ_name = itemView.findViewById(R.id.tv_categ_name);
            rv_sub_category = itemView.findViewById(R.id.rv_sub_category);
            img_categ = itemView.findViewById(R.id.img_categ);

        }
    }
}
