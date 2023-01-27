package im.vector.app.timeshare.details;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;

public class BannerSliderAdapterTest extends PagerAdapter {

    Context mContext;

    ArrayList<String> images;

    public BannerSliderAdapterTest(Context mContext, ArrayList<String>images) {
        this.mContext = mContext;
        this.images=images;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.vp_banner_slider, container, false);
       // LinearLayout banner_layout = viewItem.findViewById(R.id.banner_layout);
        ImageView ivBanner=viewItem.findViewById(R.id.iv_banner);


        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+images.get(position))
                .into(ivBanner);


        ((ViewPager) container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public float getPageWidth(int position) {
        return (super.getPageWidth(position) / Float.valueOf("1"));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);

      /*  ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);*/

    }
}
