package im.vector.app.timeshare.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import im.vector.app.R;
import im.vector.app.features.MainActivity;


public class ChatFragment extends Fragment {
    RvChatAdapter rvChatAdapter;
    ArrayList<ChatModel> chatList = new ArrayList<>();
    RecyclerView rv_chats;
    ImageView iv_start_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_chat, container, false);
      findView(view);


        // set data in all events
        rv_chats.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_chats.setHasFixedSize(true);
        // add static data in eventlist
       /* chatList.add(new ChatModel(R.drawable.avtar,"Roadies","John: lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","7:30 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"Jessica","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","7:30 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"Julienne Mark","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 7:30 PM"));
        chatList.add(new ChatModel(R.drawable.e1,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 6:30 PM"));
        chatList.add(new ChatModel(R.drawable.e2,"Buddy Holly","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 4:00 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"Martha Kimbley","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 4:10 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 5:20 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 5:20 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 5:20 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 5:20 PM"));
        chatList.add(new ChatModel(R.drawable.avtar,"John Stewart","lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor...","Yesterday 5:20 PM"));
*/
        rvChatAdapter = new RvChatAdapter(getActivity(), chatList);
        rv_chats.setAdapter(rvChatAdapter);


        return view;
    }

    private void findView(View view) {
        rv_chats = view.findViewById(R.id.rv_chats);
        iv_start_chat = view.findViewById(R.id.iv_start_chat);

        iv_start_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }
}
