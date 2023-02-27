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
import kotlin.Suppress;

public class SingleRecyclerViewAdapter
        extends RecyclerView.Adapter<SingleRecyclerViewAdapter.DataObjectHolder> {

    List<Category> categoryList = new ArrayList<>();
    private static SingleClickListener sClickListener;
    public static int sSelected_category = -1;
    Context mContext;

    public SingleRecyclerViewAdapter(Context context, List<Category> mData) {
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
        @Suppress(names = "DEPRECATION")
        @Override
        public void onClick(View view) {
            sSelected_category = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
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

            holder.tv_text_category_name.setText(categoryList.get(position).getCategory());

            if (sSelected_category == position) {
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
        void onItemClickListener(int position, View view);
    }
}
