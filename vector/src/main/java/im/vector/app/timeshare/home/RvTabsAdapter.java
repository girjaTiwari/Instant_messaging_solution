package im.vector.app.timeshare.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import im.vector.app.R;

public class RvTabsAdapter extends RecyclerView.Adapter<RvTabsAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String>arrayList=new ArrayList<>();
    int selectedPosition=0;

    public RvTabsAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_tabs,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.tab_name.setText(arrayList.get(position));

     /*   holder.rl_tab_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.tab_name.setTextColor(Color.parseColor("#000000"));*/

        if(selectedPosition==position){
            holder.tab_name.setTextColor(Color.parseColor("#000000"));
           // holder.rl_tab_layout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.selected_white_bg));
            holder.rl_tab_layout.setBackgroundColor(Color.parseColor("#ffffff"));

        }
        else {
            holder.rl_tab_layout.setBackground(null);
            holder.tab_name.setTextColor(Color.parseColor("#ffffff"));
          //  holder.tab_name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }

        holder.rl_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tab_name;
       public  RelativeLayout rl_tab_layout;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tab_name = itemView.findViewById(R.id.tab_name);
            rl_tab_layout = itemView.findViewById(R.id.rl_tab_layout);
        }
    }
}
