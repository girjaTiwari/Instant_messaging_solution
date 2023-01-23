package im.vector.app.timeshare.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.home.model.Event;

public class RvOngoingAdapter extends RecyclerView.Adapter<RvOngoingAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Event>eventList = new ArrayList<>();

    public RvOngoingAdapter(Context mContext, ArrayList<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_ongoing_events,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Event model = eventList.get(position);
        holder.tv_eventname.setText(model.getActivity_name());

        String strImages = remove_last_charectar(model.getPost_path());
        ArrayList<String> imageList = new ArrayList<>(Arrays.asList(strImages.split(",")));

        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+imageList.get(0))
                .into(holder.iv_eventimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EventDetailsActivity.class);
//                intent.putExtra("activity_uuid",model.getActivity_uuid());
//                mContext.startActivity(intent);
            }
        });
    }

    private String remove_last_charectar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_eventimage;
        TextView tv_eventname;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_eventimage = itemView.findViewById(R.id.iv_eventimage);
            tv_eventname = itemView.findViewById(R.id.tv_eventname);
        }
    }
}
