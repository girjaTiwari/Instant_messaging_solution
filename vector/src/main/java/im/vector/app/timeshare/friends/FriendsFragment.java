package im.vector.app.timeshare.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

import im.vector.app.R;


public class FriendsFragment extends Fragment {
    Context mContext;

    FriendsViewPagerAdapter viewPagerAdapter;
    ViewPager viewpager;
    TabLayout tabLayout;
    private ArrayList<String> arrayList = new ArrayList<>();


    @Override
    public void onResume() {
        super.onResume();
      //  configFragments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mContext = getActivity();

        viewpager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab_layout);
        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewpager);

        viewPagerAdapter  = new FriendsViewPagerAdapter(getChildFragmentManager());

        // add your fragments
        viewPagerAdapter.addFrag(new FriendListFragment(), "Friends List");
        viewPagerAdapter.addFrag(new FindFriendFragment(), "Find a Friend");
        // set adapter on viewpager
        viewpager.setAdapter(viewPagerAdapter);

        return  view;
    }



}
