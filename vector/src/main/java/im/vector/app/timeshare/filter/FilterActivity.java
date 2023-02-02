package im.vector.app.timeshare.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import im.vector.app.R;
import im.vector.app.features.home.HomeActivity;
import im.vector.app.timeshare.categ.Category;
import im.vector.app.timeshare.categ.SubCategory;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    ImageView iv_close_filter;
    TextView tv_custom_date,tv_calendar_cancel,tv_calendar_ok;
    ImageView iv_calendar;
    RelativeLayout rl_select_category,rl_select_subcategory;
    LinearLayout ll_category_space,ll_subcategory_space,ll_select_date,ll_calendar_view_custom_date;
   // CalendarView calendarView;
    RecyclerView rv_category,rv_spinnersubcategory;
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<SubCategory> subCategoryList = new ArrayList<>();
    RvSpinnerCategoryAdapter rvSpinnerCategoryAdapter;
    RvSpinnerSubCategoryAdapter rvSpinnerSubCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mActivity = FilterActivity.this;

        findView();

        iv_close_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



     /*   calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                tv_custom_date.setText(getDate(eventDay.getCalendar().getTimeInMillis(), "MMMM dd, yyyy"));
                tv_custom_date.setError(null);
                ll_select_date.setBackground(ContextCompat.getDrawable(mActivity,R.drawable.red_rounded_bg));
                tv_custom_date.setTextColor(ContextCompat.getColor(mActivity,R.color.white));
                iv_calendar.setImageResource(R.drawable.ic_calendar_white);

                }
        });
*/
        rl_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_category_space.getVisibility()==View.VISIBLE)
                {
                    ll_category_space.setVisibility(View.GONE);
                    ll_category_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_up));
                }else {
                    ll_category_space.setVisibility(View.VISIBLE);
                    ll_category_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_down));
                }
            }
        });

        rl_select_subcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_subcategory_space.getVisibility()==View.VISIBLE)
                {
                    ll_subcategory_space.setVisibility(View.GONE);
                    ll_subcategory_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_up));
                }else {
                    ll_subcategory_space.setVisibility(View.VISIBLE);
                    ll_subcategory_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_down));
                }
            }
        });

        rv_category.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_category.setHasFixedSize(true);
        /*categoryList.add(new Category(R.drawable.ic_work,"Work"));
        categoryList.add(new Category(R.drawable.ic_work,"Health"));
        categoryList.add(new Category(R.drawable.ic_work,"Holiday"));
        categoryList.add(new Category(R.drawable.ic_work,"Gift"));
        categoryList.add(new Category(R.drawable.ic_work,"Ideas"));
        categoryList.add(new Category(R.drawable.ic_work,"Sports"));
        categoryList.add(new Category(R.drawable.ic_work,"Music"));
        categoryList.add(new Category(R.drawable.ic_work,"Hiking"));*/
        rvSpinnerCategoryAdapter = new RvSpinnerCategoryAdapter(mActivity, categoryList);
        rv_category.setAdapter(rvSpinnerCategoryAdapter);

        // set data in subcategory

        rv_spinnersubcategory.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_spinnersubcategory.setHasFixedSize(true);
        subCategoryList.add(new SubCategory("Adventure",0,"Work"));
        subCategoryList.add(new SubCategory("Business",0,"Work"));
        subCategoryList.add(new SubCategory("Beach",0,"Work"));
        subCategoryList.add(new SubCategory("Family",0,"Work"));
        subCategoryList.add(new SubCategory("Bucket List",0,"Work"));
        subCategoryList.add(new SubCategory("Trekking",0,"Work"));


        rvSpinnerSubCategoryAdapter = new RvSpinnerSubCategoryAdapter(mActivity, subCategoryList);
        rv_spinnersubcategory.setAdapter(rvSpinnerSubCategoryAdapter);
    }

    private String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void updateDisplay(int _birthYear,int _month,int _day) {
        tv_custom_date.setText(splitDate(new StringBuilder().append(_birthYear).append("-").append(_month).append("-").append(_day).append("")));
        tv_custom_date.setError(null);
        ll_select_date.setBackground(ContextCompat.getDrawable(mActivity,R.drawable.red_rounded_bg));
        tv_custom_date.setTextColor(ContextCompat.getColor(mActivity,R.color.white));
        iv_calendar.setImageResource(R.drawable.ic_calendar_white);

    }

    private String splitDate(StringBuilder strDate) {
        String finalDate="";
        try{
            //current date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date objDate = dateFormat.parse(String.valueOf(strDate));

            //Expected date format
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMMM dd, yyyy");

            finalDate = dateFormat2.format(objDate);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return finalDate;
    }

    private void findView() {
        iv_close_filter = findViewById(R.id.iv_close_filter);
        rl_select_category = findViewById(R.id.rl_select_category);
        rl_select_subcategory = findViewById(R.id.rl_select_subcategory);
        rv_category = findViewById(R.id.rv_spinnercategory);
        rv_spinnersubcategory = findViewById(R.id.rv_spinnersubcategory);
        ll_category_space = findViewById(R.id.ll_category_space);
        ll_subcategory_space = findViewById(R.id.ll_subcategory_space);
        ll_select_date = findViewById(R.id.ll_select_date);
        tv_custom_date = findViewById(R.id.tv_custom_date);
        iv_calendar = findViewById(R.id.iv_calendar);
        ll_calendar_view_custom_date = findViewById(R.id.ll_calendar_view_custom_date);
       // calendarView_customdate = findViewById(R.id.calendarView_customdate);
        tv_calendar_cancel = findViewById(R.id.tv_calendar_cancel);
        tv_calendar_ok = findViewById(R.id.tv_calendar_ok);

         //calendarView = (CalendarView) findViewById(R.id.calendarView);

//        calendarView.setHeaderColor([color]);
//        calendarView.setHeaderLabelColor([color]);
//        calendarView.setForwardButtonImage([drawable]);
//        calendarView.setPreviousButtonImage([drawable]);

        //listener's

        ll_select_date.setOnClickListener(this);
        tv_calendar_ok.setOnClickListener(this);
        tv_calendar_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.id.tv_calendar_ok:
            {
                ll_calendar_view_custom_date.setVisibility(View.GONE);
                break;
            }
            case R.id.tv_calendar_cancel:
            {
                ll_calendar_view_custom_date.setVisibility(View.GONE);
                break;
            }
            case R.id.ll_select_date:
            {
                if (!(ll_calendar_view_custom_date.getVisibility()==View.VISIBLE)){
                    ll_calendar_view_custom_date.setVisibility(View.VISIBLE);
                }else {
                    ll_calendar_view_custom_date.setVisibility(View.GONE);
                }
                break;
            }
*/
        }
    }
}
