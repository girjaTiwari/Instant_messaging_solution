package im.vector.app.timeshare.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import im.vector.app.R;

public class RvChatAdapter extends RecyclerView.Adapter<RvChatAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<ChatModel>chatList = new ArrayList<>();

    public RvChatAdapter(Context mContext, ArrayList<ChatModel> chatList) {
        this.mContext = mContext;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_chat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatModel model = chatList.get(position);
        holder.tv_friend_name.setText(model.getName());
        holder.tv_message.setText(model.getMessage());
        holder.tv_time.setText(model.getTime());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile_image;
        TextView tv_friend_name,tv_time,tv_message;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
            tv_friend_name = itemView.findViewById(R.id.tv_friend_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_message = itemView.findViewById(R.id.tv_message);
        }
    }
}
