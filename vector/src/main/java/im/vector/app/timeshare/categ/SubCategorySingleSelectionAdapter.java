package im.vector.app.timeshare.categ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import im.vector.app.R;

public class SubCategorySingleSelectionAdapter extends RecyclerView.Adapter<SubCategorySingleSelectionAdapter.DataObjectHolder> {

    List<SubCategory> categoryList = new ArrayList<>();
    private static SingleClickListener mSubCategoryListener;
    public static int sSelected_subcategory = -1;
    Context mContext;

    public SubCategorySingleSelectionAdapter(Context context, List<SubCategory> mData) {
        this.mContext = context;
        this.categoryList = mData;
    }

    class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_checked;
        TextView tv_text_category_name;
        public DataObjectHolder(View itemView) {
            super(itemView);
            iv_checked = itemView.findViewById(R.id.iv_checked);
            tv_text_category_name = itemView.findViewById(R.id.tv_text_category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected_subcategory = getAdapterPosition();
            mSubCategoryListener.onSubCategoryListener(getAdapterPosition(), view);
        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        mSubCategoryListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_spinner_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if (categoryList.size() != 0) {

            holder.tv_text_category_name.setText(categoryList.get(position).getSub_categ_name());

            if (sSelected_subcategory == position) {
                holder.iv_checked.setImageResource(R.drawable.ic_checkbox_checked);

            } else {
                holder.iv_checked.setImageResource(R.drawable.ic_checkbox_unchecked);
            }
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface SingleClickListener {
        void onSubCategoryListener(int position, View view);
    }
}
