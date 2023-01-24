package im.vector.app.timeshare.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import im.vector.app.R;
import im.vector.app.timeshare.friends.FriendsViewPagerAdapter;

public class FollowActivity extends AppCompatActivity {
    FriendsViewPagerAdapter viewPagerAdapter;
    ViewPager viewpager;
    TabLayout tabLayout;
    ImageView iv_close_follow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        iv_close_follow = findViewById(R.id.iv_close_follow);
        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewpager);

        viewPagerAdapter  = new FriendsViewPagerAdapter(getSupportFragmentManager());

        // add your fragments
        viewPagerAdapter.addFrag(new FollowersFragment(), "Followers");
        viewPagerAdapter.addFrag(new FollowingFragment(), "Following");
        // set adapter on viewpager
        viewpager.setAdapter(viewPagerAdapter);

        iv_close_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
