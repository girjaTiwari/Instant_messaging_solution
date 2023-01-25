package im.vector.app.timeshare;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import im.vector.app.R;
import im.vector.app.features.MainActivity;
import im.vector.app.timeshare.TSUtils.MYUtil;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.categ.Category;
import im.vector.app.timeshare.categ.SingleRecyclerViewAdapter;
import im.vector.app.timeshare.categ.SubCategory;
import im.vector.app.timeshare.categ.SubCategorySingleSelectionAdapter;
import im.vector.app.timeshare.chat.ChatFragment;
import im.vector.app.timeshare.friends.FriendsFragment;
import im.vector.app.timeshare.home.HomeFragment;
import im.vector.app.timeshare.menu.MenuFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class TSMainActivity extends AppCompatActivity implements View.OnClickListener, SingleRecyclerViewAdapter.SingleClickListener,
        SubCategorySingleSelectionAdapter.SingleClickListener {

     AppCompatActivity mActivity;
     MyDialog myDialog;
     RelativeLayout toolbar_main,rl_home,rl_users,rl_chat,rl_menu;
     View mHomeSelected;
     View mUsersSelected;
     View mChatSelected;
     View mMenuSelected;
     ImageView iv_home,iv_users,iv_chat,iv_menu;
     LinearLayout footer;

    RelativeLayout fragmentContainer;

   // MovableFloatingActionButton fab;
    private BroadcastReceiver mNetworkReceiver;

    private final int PICK_IMAGE_MULTIPLE = 1;
    private final int TAKE_PHOTO_CODE = 2;
    public static Uri outputFileUri;
   // public static ImageListAdapter imageListAdapter;
   public static LinearLayout ll_content_area;
    public static TextView tv_continue_upload_photo;
    TSSessionManager tsSessionManager;
  //  RequestQueue requestQueue;
   // ArrayList<FriendModel> friendList = new ArrayList<>();
 //   YourFriendsAdapter yourFriendsAdapter;
  //  private RetrofitAPI mAPIService = ApiUtils.getAPIService();
  //  Call<UploadImage> call;
    // More info

    TextView tv_continue;
    ImageView iv_close,img_select_start_date,img_select_end_date;
    LinearLayout ll_calendar_view_start_date,ll_calendar_view_end_date;
    TextView tv_calendar_cancel1,tv_calendar_ok1,tv_calendar_cancel2,tv_calendar_ok2;
    TextView tv_activity_start_date,tv_activity_end_date;
    RelativeLayout rl_select_category,rl_select_subcategory;
    LinearLayout ll_category_space,ll_subcategory_space;
    LinearLayout ll_start_time,ll_end_time;
    TimePicker timePicker1,timePicker2;
    TextView tv_time_cancel1,tv_time_cancel2,tv_time_ok1,tv_time_ok2;
    RecyclerView rv_category,rv_spinnersubcategory;
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<SubCategory> subCategoryList = new ArrayList<>();
    SingleRecyclerViewAdapter singleRecyclerViewAdapter;
    SubCategorySingleSelectionAdapter subCategorySingleSelectionAdapter;
    EditText et_limits;
    Switch switch_limits,switch_make_public;


    CalendarView calendarView_startdate,calendarView_enddate;
    String email_id;

    BottomSheetBehavior bottomSheetBehavior;
    boolean isHome,isFriend,isChat,isMenu;
    EditText edit_venu;
    Bitmap gallerybitmap;
    String activityName,activityDescription,numberOfFiles="1",categoryName="",subCategory="",userUuid,postLocation,startDate,endDate;
    MultipartBody.Part filePart;
    List<MultipartBody.Part>filePartList=new ArrayList<>();
    RequestBody activityname,activitydesc,numberoffiles,category,subcategory,user_uuid,location,start_date_and_time,end_date_and_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ts_main);
        mActivity = TSMainActivity.this;
        myDialog = new MyDialog(mActivity);
        tsSessionManager = new TSSessionManager(mActivity);
        findViews();

        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
         userUuid = user.get(TSSessionManager.KEY_user_uuid);
         email_id =  user.get(TSSessionManager.KEY_email_id);


       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });*/


/*        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_MOVE:
                                view.setX(event.getRawX() - 120);
                                view.setY(event.getRawY() - 425);
                                break;
                            case MotionEvent.ACTION_UP:
                                view.setOnTouchListener(null);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                return true;
            }
        });*/


       Fragment newCase = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, newCase)
                .addToBackStack(null)
                .commit();

        rl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHome = true;
                isFriend = false;
                isChat = false;
                isMenu = false;

                iv_home.setImageResource(R.drawable.ic_home_selected);
                iv_users.setImageResource(R.drawable.ic_users);
                iv_chat.setImageResource(R.drawable.ic_chat_ts);
                iv_menu.setImageResource(R.drawable.ic_menu);


                mHomeSelected.setVisibility(View.VISIBLE);
                mUsersSelected.setVisibility(View.GONE);
                mChatSelected.setVisibility(View.GONE);
                mMenuSelected.setVisibility(View.GONE);

                Fragment newCase = new HomeFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, newCase)
                        .addToBackStack(null)
                        .commit();
            }
        });

       rl_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHome = false;
                isFriend = true;
                isChat = false;
                isMenu = false;

                iv_home.setImageResource(R.drawable.ic_home);
                iv_users.setImageResource(R.drawable.ic_users_selected);
                iv_chat.setImageResource(R.drawable.ic_chat_ts);
                iv_menu.setImageResource(R.drawable.ic_menu);



                mHomeSelected.setVisibility(View.GONE);
                mUsersSelected.setVisibility(View.VISIBLE);
                mChatSelected.setVisibility(View.GONE);
                mMenuSelected.setVisibility(View.GONE);

                Fragment newCase = new FriendsFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, newCase)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isHome = false;
                isFriend = false;
                isChat = true;
                isMenu = false;

                iv_home.setImageResource(R.drawable.ic_home);
                iv_users.setImageResource(R.drawable.ic_users);
                iv_chat.setImageResource(R.drawable.ic_chat_selected);
                iv_menu.setImageResource(R.drawable.ic_menu);


                mHomeSelected.setVisibility(View.GONE);
                mUsersSelected.setVisibility(View.GONE);
                mChatSelected.setVisibility(View.VISIBLE);
                mMenuSelected.setVisibility(View.GONE);

                Fragment newCase = new ChatFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, newCase)
                        .addToBackStack(null)
                        .commit();
            }
        });


        rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isHome = false;
                isFriend = false;
                isChat = false;
                isMenu = true;

                iv_home.setImageResource(R.drawable.ic_home);
                iv_users.setImageResource(R.drawable.ic_users);
                iv_chat.setImageResource(R.drawable.ic_chat_ts);
                iv_menu.setImageResource(R.drawable.ic_menu_selected);


                mHomeSelected.setVisibility(View.GONE);
                mUsersSelected.setVisibility(View.GONE);
                mChatSelected.setVisibility(View.GONE);
                mMenuSelected.setVisibility(View.VISIBLE);


                Fragment newCase = new MenuFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, newCase)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

/*    private void getCategories(String email_id) {
        categoryList.clear();
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",email_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_category,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("categorylist>>"+response);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject object = response.getJSONObject("Cat");
                        String cat1 = object.getString("category1");
                        String cat2 = object.getString("category2");
                        String cat3 = object.getString("category3");
                        String cat4 = object.getString("category4");
                        String cat5 = object.getString("category5");

                        rv_category.setLayoutManager(new GridLayoutManager(mActivity,2));
                        rv_category.setHasFixedSize(true);
                        // add static data in eventlist

                       *//* if (!cat1.equals("")){
                            categoryList.add(new Category(R.drawable.ic_work,cat1));
                        }
                        if (!cat2.equals("")){
                            categoryList.add(new Category(R.drawable.ic_health,cat2));
                        }
                        if (!cat3.equals("")){
                            categoryList.add(new Category(R.drawable.ic_health,cat3));
                        }
                        if (!cat4.equals("")){
                            categoryList.add(new Category(R.drawable.ic_health,cat4));
                        }
                        if (!cat5.equals("")){
                            categoryList.add(new Category(R.drawable.ic_health,cat5));
                        }*//*

                        categoryList.add(new Category(R.drawable.ic_hiking,"Travelling"));
                        categoryList.add(new Category(R.drawable.ic_ideas,"Education"));
                        categoryList.add(new Category(R.drawable.ic_cycling,"Sports"));
                        categoryList.add(new Category(R.drawable.ic_gift,"Cars"));
                        categoryList.add(new Category(R.drawable.ic_holiday,"Movie"));

                        singleRecyclerViewAdapter = new SingleRecyclerViewAdapter(mActivity,categoryList);
                        rv_category.setAdapter(singleRecyclerViewAdapter);
                        singleRecyclerViewAdapter.setOnItemClickListener(TSMainActivity.this);

                    }else {
                      //  Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("categorylist>>"+error);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);

    }

    private void startActivity() {
        BottomSheetDialog builder = new BottomSheetDialog(mActivity);
        builder.setCancelable(false);

        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_start_activity, null);
        builder.setContentView(view);

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height*0.93);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(maxHeight);

        TextView tv_continue = builder.findViewById(R.id.tv_continue);
        ImageView iv_close = builder.findViewById(R.id.iv_close_activity);
        EditText et_activity_name = builder.findViewById(R.id.et_activity_name);
        EditText et_activity_desc = builder.findViewById(R.id.et_activity_desc);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityName = et_activity_name.getText().toString().trim();
                activityDescription = et_activity_desc.getText().toString().trim();
                if (validateActivity(activityName,activityDescription,et_activity_name,et_activity_desc)) {
                    activityname = RequestBody.create(MediaType.parse("text/plain"),activityName);
                    activitydesc = RequestBody.create(MediaType.parse("text/plain"),activityDescription);
                    uploadMediaDialog();
                    builder.dismiss();
                }

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               builder.dismiss();
            }
        });


        builder.show();
    }*/

    private boolean validateActivity(String activityName, String activityDescription,EditText et_activity_name,EditText et_activity_desc) {
        if (activityName.isEmpty()) {
            et_activity_name.setError("Please enter activity name!");
            et_activity_name.requestFocus();
            return false;

        } else if(activityDescription.equals("")) {
            et_activity_desc.setError("Please enter activity description!");
            et_activity_desc.requestFocus();
            return false;

        }
        return true;
    }

  /*  private void uploadMediaDialog() {
        BottomSheetDialog builder = new BottomSheetDialog(mActivity);
        builder.setCancelable(false);

        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_upload_photo, null);
        builder.setContentView(view);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height*0.93);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(maxHeight);

        ImageView iv_close = builder.findViewById(R.id.iv_close_upload_media);

        ll_content_area = builder.findViewById(R.id.ll_content_area);
        RecyclerView recyclerView = builder.findViewById(R.id.rv_imageList);
        tv_continue_upload_photo = builder.findViewById(R.id.tv_continue_upload_photo);
        TextView tv_add_media = builder.findViewById(R.id.tv_add_media);

        //set data

        //    GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        imageListAdapter=new ImageListAdapter(mActivity);
        recyclerView.setAdapter(imageListAdapter);

        tv_continue_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreInfoDialog();
                builder.dismiss();

            }
        });

        tv_add_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

            }
        });


        builder.show();

    }*/

   /* private void moreInfoDialog(){
        BottomSheetDialog builder_moreinfo = new BottomSheetDialog(mActivity);
        builder_moreinfo.setCancelable(false);


        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_more_details, null);
        builder_moreinfo.setContentView(view);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height*0.93);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(maxHeight);

        TextView tv_continue = builder_moreinfo.findViewById(R.id.tv_continue);
        ImageView iv_close = builder_moreinfo.findViewById(R.id.iv_close_more_details);
         edit_venu = builder_moreinfo.findViewById(R.id.edit_venu);

        calendarView_startdate = builder_moreinfo.findViewById(R.id.calendarView_startdate);
        calendarView_enddate = builder_moreinfo.findViewById(R.id.calendarView_enddate);


        tv_continue = builder_moreinfo.findViewById(R.id.tv_continue);
        iv_close = builder_moreinfo.findViewById(R.id.iv_close_more_details);

        rl_select_category = builder_moreinfo.findViewById(R.id.rl_select_category);
        rl_select_subcategory = builder_moreinfo.findViewById(R.id.rl_select_subcategory);

        ll_category_space = builder_moreinfo.findViewById(R.id.ll_category_space);
        ll_subcategory_space = builder_moreinfo.findViewById(R.id.ll_subcategory_space);

        rv_category = builder_moreinfo.findViewById(R.id.rv_spinnercategory);
        rv_spinnersubcategory = builder_moreinfo.findViewById(R.id.rv_spinnersubcategory);

        ll_calendar_view_start_date = builder_moreinfo.findViewById(R.id.ll_calendar_view_start_date);
        ll_calendar_view_end_date = builder_moreinfo.findViewById(R.id.ll_calendar_view_end_date);


        img_select_start_date = builder_moreinfo.findViewById(R.id.img_select_start_date);
        img_select_end_date = builder_moreinfo.findViewById(R.id.img_select_end_date);

        tv_calendar_cancel1 = builder_moreinfo.findViewById(R.id.tv_calendar_cancel1);
        tv_calendar_cancel2 = builder_moreinfo.findViewById(R.id.tv_calendar_cancel2);

        tv_calendar_ok1 = builder_moreinfo.findViewById(R.id.tv_calendar_ok1);
        tv_calendar_ok2 = builder_moreinfo.findViewById(R.id.tv_calendar_ok2);

        tv_activity_start_date = builder_moreinfo.findViewById(R.id.tv_activity_start_date);
        tv_activity_end_date = builder_moreinfo.findViewById(R.id.tv_activity_end_date);


        ll_start_time = builder_moreinfo.findViewById(R.id.ll_start_time);
        ll_end_time = builder_moreinfo.findViewById(R.id.ll_end_time);

        timePicker1 = builder_moreinfo.findViewById(R.id.timePicker1);
        timePicker2 = builder_moreinfo.findViewById(R.id.timePicker2);

        tv_time_cancel1 = builder_moreinfo.findViewById(R.id.tv_time_cancel1);
        tv_time_cancel2 = builder_moreinfo.findViewById(R.id.tv_time_cancel2);
        tv_time_ok1 = builder_moreinfo.findViewById(R.id.tv_time_ok1);
        tv_time_ok2 = builder_moreinfo.findViewById(R.id.tv_time_ok2);

        et_limits = builder_moreinfo.findViewById(R.id.et_limits);

        switch_limits = builder_moreinfo.findViewById(R.id.switch_limits);
        switch_make_public = builder_moreinfo.findViewById(R.id.switch_make_public);

        switch_limits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switch_limits.isChecked()){
                    et_limits.setVisibility(View.VISIBLE);
                }else {
                    et_limits.setVisibility(View.GONE);

                }
            }
        });

        getCategories(email_id);


       *//* DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        Date date = new Date();
        tv_activity_start_date.setText(dateFormat.format(date));
        tv_activity_end_date.setText(dateFormat.format(date));*//*

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        String hour,minute;
        String hour2,minute2;

        if (timePicker1.getCurrentHour()<10){
            hour = "0"+timePicker1.getCurrentHour();
            hour2 = "0"+timePicker2.getCurrentHour();
        }else {
            hour = ""+timePicker1.getCurrentHour();
            hour2 = ""+timePicker2.getCurrentHour();
        }
        if (timePicker1.getCurrentMinute()<10){
            minute = "0"+timePicker1.getCurrentMinute();
            minute2 = "0"+timePicker2.getCurrentMinute();
        }else {
            minute = ""+timePicker1.getCurrentMinute();
            minute2 = ""+timePicker2.getCurrentMinute();
        }

        String currentTime_start=hour+":"+minute;
        String currentTime2_end=hour2+":"+minute2;

        tv_activity_start_date.setText(splitDate(new StringBuilder().append(currentYear).append("-").append(currentMonth).append("-").append(currentDay).append(""))+"-"+currentTime_start);
        tv_activity_end_date.setText(splitDate(new StringBuilder().append(currentYear).append("-").append(currentMonth).append("-").append(currentDay).append(""))+"-"+currentTime2_end);


        //Listener's
        tv_continue.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        img_select_start_date.setOnClickListener(this);
        img_select_end_date.setOnClickListener(this);

        tv_calendar_cancel1.setOnClickListener(this);
        tv_calendar_ok1.setOnClickListener(this);
        tv_calendar_cancel2.setOnClickListener(this);
        tv_calendar_ok2.setOnClickListener(this);

        tv_time_ok1.setOnClickListener(this);
        tv_time_ok2.setOnClickListener(this);


        tv_time_cancel1.setOnClickListener(this);
        tv_time_cancel1.setOnClickListener(this);

        rl_select_category.setOnClickListener(this);
        rl_select_subcategory.setOnClickListener(this);

        //Setting custom font
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/poppins_medium.ttf");
        if (null != typeface) {
            //calendar_view_start_date.setCustomTypeface(typeface);
            // calendar_view_start_date.refreshCalendar(currentCalendar);
        }

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.showProgresbar(mActivity);
                categoryList.clear();
                subCategoryList.clear();
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                postLocation = edit_venu.getText().toString().trim();
                startDate = tv_activity_start_date.getText().toString().trim();
                endDate = tv_activity_end_date.getText().toString().trim();
                if (validateMoreInfo(postLocation,startDate,endDate,categoryName,subCategory,edit_venu)) {

                    numberoffiles = RequestBody.create(MediaType.parse("text/plain"),numberOfFiles);
                    category = RequestBody.create(MediaType.parse("text/plain"),categoryName);
                    subcategory = RequestBody.create(MediaType.parse("text/plain"),subCategory);
                    user_uuid=RequestBody.create(MediaType.parse("text/plain"),userUuid);
                    location=RequestBody.create(MediaType.parse("text/plain"),postLocation);
                    start_date_and_time=RequestBody.create(MediaType.parse("text/plain"),startDate);
                    end_date_and_time=RequestBody.create(MediaType.parse("text/plain"),endDate);

                    // System.out.println("error>> udid " + uuid);
                    if (filePartList.size()>0){
                         call = mAPIService.createPostImages(filePartList, activityname,activitydesc,numberoffiles,category,subcategory,user_uuid,
                                location,start_date_and_time,end_date_and_time);
                    }else {
                         call = mAPIService.createPost(filePart, activityname,activitydesc,numberoffiles,category,subcategory,user_uuid,
                                location,start_date_and_time,end_date_and_time);
                    }

                    call.enqueue(new Callback<UploadImage>() {
                        @Override
                        public void onResponse(Call<UploadImage> call, retrofit2.Response<UploadImage> response) {
                            System.out.println("msg>>  response: " + response.toString());
                            myDialog.hideDialog(mActivity);
                            SingleRecyclerViewAdapter.sSelected_category=-1;
                            SubCategorySingleSelectionAdapter.sSelected_subcategory = -1;
                            if(response.body()!=null){
                                UploadImage uploadImage = response.body();
                                String Activity_UUID = uploadImage.getActivity_UUID();
                                System.out.println("Activity_UUID>>"+Activity_UUID);
                                if (Activity_UUID!=null){

                                    inviteDialog(Activity_UUID);   // pass the Activity_UUID for invite friend api
                                    builder_moreinfo.dismiss();
                                }

                            } else {
                                Toast.makeText(TSMainActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<UploadImage> call, Throwable t) {
                            myDialog.hideDialog(mActivity);
                            System.out.println("msg>> failure: " + t.getCause());
                          //  Toast.makeText(MainActivity.this, "" + t.getCause(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }


            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                builder_moreinfo.dismiss();

            }
        });

        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);


        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);


        calendarView_startdate.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                tv_activity_start_date.setText(getDate(eventDay.getCalendar().getTimeInMillis(), "MMMM dd, yyyy"));
                tv_activity_start_date.setError(null);

            }
        });

        calendarView_enddate.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                tv_activity_end_date.setText(getDate(eventDay.getCalendar().getTimeInMillis(), "MMMM dd, yyyy"));
                tv_activity_end_date.setError(null);
            }
        });

        builder_moreinfo.show();
    }*/

    private String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void getSubCategories(String user_uuid,String category) {
        subCategoryList.clear();
        // add static data in subCategory
        if (category.equals("Travelling")){

            subCategoryList.add(new SubCategory("Mountains",0,"Travelling"));
            subCategoryList.add(new SubCategory("Desert",0, "Travelling"));
            subCategoryList.add(new SubCategory("Sea",0,"Travelling"));
            subCategoryList.add(new SubCategory("Plains",0,"Travelling"));
            subCategoryList.add(new SubCategory("City",0,"Travelling"));

        }
        if (category.equals("Education")){
            subCategoryList.add(new SubCategory("Engineering",1,"Education"));
            subCategoryList.add(new SubCategory("Medical",1,"Education"));
            subCategoryList.add(new SubCategory("Astrophysics",1,"Education"));
            subCategoryList.add(new SubCategory("Science",1,"Education"));
            subCategoryList.add(new SubCategory("Economics",1,"Education"));
        }
        if (category.equals("Sports")){
            subCategoryList.add(new SubCategory("Cricket",2,"Sports"));
            subCategoryList.add(new SubCategory("Hockey",2,"Sports"));
            subCategoryList.add(new SubCategory("Tenis",2,"Sports"));
            subCategoryList.add(new SubCategory("Football",2,"Sports"));
            subCategoryList.add(new SubCategory("Chess",2,"Sports"));
        }

        if (category.equals("Cars")){
            subCategoryList.add(new SubCategory("BMW",3,"Cars"));
            subCategoryList.add(new SubCategory("Mercedes ",3,"Cars"));
            subCategoryList.add(new SubCategory("Maruti Suzuki ",3,"Cars"));
            subCategoryList.add(new SubCategory("Honda",3,"Cars"));
            subCategoryList.add(new SubCategory("Ferrari ",3,"Cars"));
        }

        if (category.equals("Movie")){
            subCategoryList.add(new SubCategory("Action",4,"Movie"));
            subCategoryList.add(new SubCategory("Drama ",4,"Movie"));
            subCategoryList.add(new SubCategory("Horror ",4,"Movie"));
            subCategoryList.add(new SubCategory("Thrill",4,"Movie"));
            subCategoryList.add(new SubCategory("3D ",4,"Movie"));
        }
        rv_spinnersubcategory.setLayoutManager(new GridLayoutManager(mActivity,2));
        rv_spinnersubcategory.setHasFixedSize(true);
        subCategorySingleSelectionAdapter = new SubCategorySingleSelectionAdapter(mActivity, subCategoryList);
        rv_spinnersubcategory.setAdapter(subCategorySingleSelectionAdapter);
        subCategorySingleSelectionAdapter.setOnItemClickListener(TSMainActivity.this);

/*        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",user_uuid);
        params.put("category",category);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_sub_category_by_category,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("subcategorylist>>"+response);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject object = response.getJSONObject("SubCats");
                        String sub_1 = object.getString("sub_category1");
                        String sub_2 = object.getString("sub_category2");
                        String sub_3 = object.getString("sub_category3");
                        String sub_4 = object.getString("sub_category4");
                        String sub_5 = object.getString("sub_category5");

                        if (!sub_1.equals("")){
                            subCategoryList.add(new SubCategory(sub_1,1,category));
                        }
                        if (!sub_2.equals("")){
                            subCategoryList.add(new SubCategory(sub_2,1,category));
                        }

                        if (!sub_3.equals("")){
                            subCategoryList.add(new SubCategory(sub_3,1,category));
                        }
                        if (!sub_4.equals("")){
                            subCategoryList.add(new SubCategory(sub_4,1,category));
                        }
                        if (!sub_5.equals("")){
                            subCategoryList.add(new SubCategory(sub_5,1,category));
                        }

                        rv_spinnersubcategory.setLayoutManager(new GridLayoutManager(mActivity,2));
                        rv_spinnersubcategory.setHasFixedSize(true);
                        subCategorySingleSelectionAdapter = new SubCategorySingleSelectionAdapter(mActivity, subCategoryList);
                        rv_spinnersubcategory.setAdapter(subCategorySingleSelectionAdapter);
                        subCategorySingleSelectionAdapter.setOnItemClickListener(MainActivity.this);

                    }else {
                     //   Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("subcategorylist>>"+error);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
    }

    private boolean validateMoreInfo(String postLocation, String startDate, String endDate, String categoryName, String subCategory,EditText edit_venu) {
        if (postLocation.isEmpty()) {
            edit_venu.setError("Please enter location!");
            edit_venu.requestFocus();
            return false;

        } else if(startDate.equals("")) {
            Toast.makeText(mActivity, "Please select activity start date and time", Toast.LENGTH_SHORT).show();
            return false;

        }else if(endDate.equals("")) {
            Toast.makeText(mActivity, "Please select activity end date and time", Toast.LENGTH_SHORT).show();
            return false;

        }else if(categoryName.equals("")) {
            Toast.makeText(mActivity, "Please select category name", Toast.LENGTH_SHORT).show();
            return false;

        }else if(subCategory.equals("")) {
            Toast.makeText(mActivity, "Please select sub-category name", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
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

/*    private void get_friend_list(String uuid,RecyclerView rv_friendList,TextView tv_no_friend_found,String activity_uuid) {
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_friend_list, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("get_friend_list>>"+response);
                friendList.clear();
                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        tv_no_friend_found.setVisibility(View.GONE);
                        JSONArray jsonArray = response.getJSONArray("friend_list");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String friend_uuid = object.getString("friend_uuid");
                                String name = object.getString("name");
                                String profile_pic = object.getString("profile_pic");

                                // set data for your friends

                                friendList.add(new FriendModel(friend_uuid,name,profile_pic));

                            }
                        }

                        rv_friendList.setLayoutManager(new LinearLayoutManager(mActivity));
                        rv_friendList.setHasFixedSize(true);
                        yourFriendsAdapter = new YourFriendsAdapter(mActivity, friendList,"Invite",activity_uuid);
                        rv_friendList.setAdapter(yourFriendsAdapter);


                    }else {

                        tv_no_friend_found.setVisibility(View.VISIBLE);
                        tv_no_friend_found.setText(mesage);
                        // Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("get_friend_list>>"+error);
            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);

    }*/

    /*private void inviteDialog(String activity_uuid) {
        BottomSheetDialog builder = new BottomSheetDialog(mActivity);
        builder.setCancelable(false);

        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_invite_friend_bottom_sheet, null);
        builder.setContentView(view);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height*0.93);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(maxHeight);

        TextView  tv_finish_activity = builder.findViewById(R.id.tv_finish_activity);
      //  ImageView iv_close = builder.findViewById(R.id.iv_close_invite_frnd);
        RecyclerView rv_friendList = builder.findViewById(R.id.rv_friendList);
        TextView tv_no_friend_found = builder.findViewById(R.id.tv_no_friend_found);

        tv_finish_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Activity Created Successfully", Toast.LENGTH_SHORT).show();
            builder.dismiss();
            }
        });

       *//* iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });*//*


        HashMap<String, String> user = new HashMap<>();
        user = sessionManager.getUserDetails();
        String uuid =  user.get(SessionManager.KEY_user_uuid);
        System.out.println("uuid>>"+uuid);
        if (uuid!=null){
            get_friend_list(uuid,rv_friendList,tv_no_friend_found,activity_uuid);
        }

        builder.show();
    }*/


   /* private void choosePhoto() {
        BottomSheetDialog builder = new BottomSheetDialog(mActivity);
        builder.setCancelable(false);
        builder.setContentView(R.layout.fragment_custom_dialog_image__camera_bottom_sheet);

        TextView  tv_gallery = builder.findViewById(R.id.tv_gallery);
        TextView tv_camera = builder.findViewById(R.id.tv_camera);
        TextView tv_cancel_dialog = builder.findViewById(R.id.tv_cancel_dialog);

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO_CODE);
                ll_content_area.setVisibility(View.GONE);
                builder.dismiss();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent();
                intentGallery.setType("image/*");
                intentGallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intentGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentGallery, getString(R.string.select_image)), PICK_IMAGE_MULTIPLE);
                ll_content_area.setVisibility(View.GONE);
                builder.dismiss();
            }
        });

        tv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_content_area.setVisibility(View.VISIBLE);
                builder.dismiss();

            }
        });

        builder.show();
    }*/

    private void findViews() {
        tsSessionManager = new TSSessionManager(mActivity);
      //  mNetworkReceiver = new NetworkChangeReceiver(mActivity);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        rl_home = findViewById(R.id.rl_home);
        rl_users = findViewById(R.id.rl_users);
        rl_chat = findViewById(R.id.rl_chat);
        rl_menu = findViewById(R.id.rl_menu);

       // fab = findViewById(R.id.fab_add_activity);
        footer = findViewById(R.id.footer);

        toolbar_main = findViewById(R.id.rl_toolbar_main);

        //bottombar option's id

        iv_home = findViewById(R.id.iv_home);
        iv_users = findViewById(R.id.iv_users);
        iv_chat = findViewById(R.id.iv_chat);
        iv_menu = findViewById(R.id.iv_menu);

        // selected view's id
        mHomeSelected = findViewById(R.id.home_select);
        mUsersSelected = findViewById(R.id.users_select);
        mChatSelected = findViewById(R.id.chat_select);
        mMenuSelected = findViewById(R.id.menu_select);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeSelected = null;
        mUsersSelected = null;
        mChatSelected = null;
        mMenuSelected = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
      //  fab.setVisibility(View.VISIBLE);
        footer.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this, TSMainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        footer.setVisibility(View.VISIBLE);

       // fab.setVisibility(View.VISIBLE);

        if (isHome){
            Fragment newCase = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, newCase)
                    .addToBackStack(null)
                    .commit();

            iv_home.setImageResource(R.drawable.ic_home_selected);
            iv_users.setImageResource(R.drawable.ic_users);
            iv_chat.setImageResource(R.drawable.ic_chat_ts);
            iv_menu.setImageResource(R.drawable.ic_menu);

            mHomeSelected.setVisibility(View.VISIBLE);
            mUsersSelected.setVisibility(View.GONE);
            mChatSelected.setVisibility(View.GONE);
            mMenuSelected.setVisibility(View.GONE);
        }else if (isFriend){
            iv_home.setImageResource(R.drawable.ic_home);
            iv_users.setImageResource(R.drawable.ic_users_selected);
            iv_chat.setImageResource(R.drawable.ic_chat_ts);
            iv_menu.setImageResource(R.drawable.ic_menu);



            mHomeSelected.setVisibility(View.GONE);
            mUsersSelected.setVisibility(View.VISIBLE);
            mChatSelected.setVisibility(View.GONE);
            mMenuSelected.setVisibility(View.GONE);

           /* Fragment newCase = new FriendsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, newCase)
                    .addToBackStack(null)
                    .commit();*/
        }else if (isChat){
            iv_home.setImageResource(R.drawable.ic_home);
            iv_users.setImageResource(R.drawable.ic_users);
            iv_chat.setImageResource(R.drawable.ic_chat_selected);
            iv_menu.setImageResource(R.drawable.ic_menu);


            mHomeSelected.setVisibility(View.GONE);
            mUsersSelected.setVisibility(View.GONE);
            mChatSelected.setVisibility(View.VISIBLE);
            mMenuSelected.setVisibility(View.GONE);

            mActivity.startActivity(new Intent(mActivity, MainActivity.class));


           /* Fragment newCase = new ChatFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, newCase)
                    .addToBackStack(null)
                    .commit();*/
        }else if (isMenu){
            iv_home.setImageResource(R.drawable.ic_home);
            iv_users.setImageResource(R.drawable.ic_users);
            iv_chat.setImageResource(R.drawable.ic_chat_ts);
            iv_menu.setImageResource(R.drawable.ic_menu_selected);


            mHomeSelected.setVisibility(View.GONE);
            mUsersSelected.setVisibility(View.GONE);
            mChatSelected.setVisibility(View.GONE);
            mMenuSelected.setVisibility(View.VISIBLE);


           /* Fragment newCase = new MenuFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, newCase)
                    .addToBackStack(null)
                    .commit();*/
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

            try {
                if (data.getClipData() != null) {
                    int cout = data.getClipData().getItemCount();
                    for (int i = 0; i < cout; i++) {
                        Uri imageUrl = data.getClipData().getItemAt(i).getUri();
                         gallerybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                        imageListAdapter.addItem(gallerybitmap);
                        File defaultFile=new File(this.getExternalFilesDir(null),"XXX"+"XXX");
                        if (!defaultFile.exists())
                            defaultFile.mkdirs();

                        String random = MYUtil.random();//need to random file name
                        String fileName="IMG_"+random;
                        //   System.out.println("filename>>"+fileName);
                        File file=new File(defaultFile,fileName);
                        if (file.exists()){
                            file.delete();
                            file=new File(defaultFile,fileName);
                        }
                        uploadPhoto(file,defaultFile,fileName);


                       // System.out.println("filePartList>>"+filePartList.size());
                    }

                }
                else {

                    Uri imageUrl = data.getData();
                    gallerybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                    imageListAdapter.addItem(gallerybitmap);
                    File defaultFile=new File(this.getExternalFilesDir(null),"XXX"+"XXX");
                    if (!defaultFile.exists())
                        defaultFile.mkdirs();
                    Date d = new Date();
                    CharSequence s  = android.text.format.DateFormat.format("dd-MM-yyyy_hh:mm:ss", d.getTime());

                    String fileName="IMG_"+s;  //need to implement
                    //   System.out.println("filename>>"+fileName);
                    File file=new File(defaultFile,fileName);
                    if (file.exists()){
                        file.delete();
                        file=new File(defaultFile,fileName);
                    }
                    FileOutputStream output=new FileOutputStream(file);
                    gallerybitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                    output.flush();
                    output.close();

                    File imgFile=new File(defaultFile.getAbsolutePath()+"/"+fileName);
                    if (imgFile.exists()){
                        filePart = MultipartBody.Part.createFormData("media_file", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
                    }
                }
            }catch (IOException e){

            }


        }else if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            try {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap!=null){
                   // Bitmap scaledBitmap = Utils.scaleDown(imageBitmap, 180, true);
                    //Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, 400, 180, true);
                    imageListAdapter.addItem(imageBitmap);
                    File defaultFile=new File(this.getExternalFilesDir(null),"XXX"+"XXX");
                    if (!defaultFile.exists())
                        defaultFile.mkdirs();
                    Date d = new Date();
                    CharSequence s  = android.text.format.DateFormat.format("dd-MM-yyyy_hh:mm:ss", d.getTime());

                    String fileName="IMG_"+s;  //need to implement
                    //   System.out.println("filename>>"+fileName);
                    File file=new File(defaultFile,fileName);
                    if (file.exists()){
                        file.delete();
                        file=new File(defaultFile,fileName);
                    }
                    FileOutputStream output=new FileOutputStream(file);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                    output.flush();
                    output.close();

                    File imgFile=new File(defaultFile.getAbsolutePath()+"/"+fileName);
                    if (imgFile.exists()){
                         filePart = MultipartBody.Part.createFormData("media_file", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            ll_content_area.setVisibility(View.VISIBLE);
            Toast.makeText(mActivity, getString(R.string.not_picked_image), Toast.LENGTH_LONG).show();
        }

    }
*/

    private void uploadPhoto(File file,File defaultFile,String fileName) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                        FileOutputStream output=new FileOutputStream(file);
                        gallerybitmap.compress(Bitmap.CompressFormat.PNG,100,output);
                        output.flush();
                        output.close();

                        File imgFile=new File(defaultFile.getAbsolutePath()+"/"+fileName);
                        if (imgFile.exists()){

                            filePart = MultipartBody.Part.createFormData("media_file", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
                            filePartList.add(filePart);
                        }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }


    @Override
    public void onClick(View v) {
/*        switch (v.getId()){
            case R.id.img_select_start_date:
            {
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                if (!(ll_calendar_view_start_date.getVisibility()==View.VISIBLE)){
                    ll_calendar_view_start_date.setVisibility(View.VISIBLE);
                }else {
                    ll_calendar_view_start_date.setVisibility(View.GONE);
                }

                break;
            }

            case R.id.img_select_end_date:
            {
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                if (!(ll_calendar_view_end_date.getVisibility()==View.VISIBLE)){
                    ll_calendar_view_end_date.setVisibility(View.VISIBLE);
                }else {
                    ll_calendar_view_end_date.setVisibility(View.GONE);
                }

                break;
            }

            case R.id.tv_calendar_cancel1:
            {

                ll_calendar_view_start_date.setVisibility(View.GONE);

                break;
            }

            case R.id.tv_calendar_cancel2:
            {
                ll_calendar_view_end_date.setVisibility(View.GONE);
                break;
            }

            case R.id.tv_calendar_ok1:
            {
                ll_calendar_view_start_date.setVisibility(View.GONE);
                ll_start_time.setVisibility(View.VISIBLE);
                break;
            }

            case R.id.tv_calendar_ok2:
            {
                ll_calendar_view_end_date.setVisibility(View.GONE);
                ll_end_time.setVisibility(View.VISIBLE);
                break;
            }


            case R.id.tv_time_ok1:
            {
                String hour,minute;
                ll_start_time.setVisibility(View.GONE);
                if (timePicker1.getCurrentHour()<10){
                    hour = "0"+timePicker1.getCurrentHour();
                }else {
                    hour = ""+timePicker1.getCurrentHour();
                }
                if (timePicker1.getCurrentMinute()<10){
                    minute = "0"+timePicker1.getCurrentMinute();
                }else {
                    minute = ""+timePicker1.getCurrentMinute();
                }

                String currentTime=hour+":"+minute;
               // String currentTime=timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();

                if (tv_activity_start_date.getText().toString()!=null){
                    tv_activity_start_date.setText(tv_activity_start_date.getText().toString()+"-"+currentTime);
                }else {
                    DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
                    Date date = new Date();
                    tv_activity_start_date.setText(dateFormat.format(date)+"-"+currentTime);
                }

                break;
            }

            case R.id.tv_time_ok2:
            {
                String hour,minute;
                ll_end_time.setVisibility(View.GONE);
                if (timePicker2.getCurrentHour()<10){
                    hour = "0"+timePicker2.getCurrentHour();
                }else {
                    hour = ""+timePicker2.getCurrentHour();
                }
                if (timePicker2.getCurrentMinute()<10){
                    minute = "0"+timePicker2.getCurrentMinute();
                }else {
                    minute = ""+timePicker2.getCurrentMinute();
                }
                String currentTime=hour+":"+minute;
               // String currentTime=timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();

                if (tv_activity_end_date.getText().toString()!=null){
                    tv_activity_end_date.setText(tv_activity_end_date.getText().toString()+"-"+currentTime);
                }else {
                    DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
                    Date date = new Date();
                    tv_activity_end_date.setText(dateFormat.format(date)+"-"+currentTime);
                }
                break;
            }

            case R.id.tv_time_cancel1:
            {

                ll_start_time.setVisibility(View.GONE);

                break;
            }

            case R.id.tv_time_cancel2:
            {

                ll_end_time.setVisibility(View.GONE);

                break;
            }

            case R.id.rl_select_category:
            {
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                if (ll_category_space.getVisibility()==View.VISIBLE)
                {
                    ll_category_space.setVisibility(View.GONE);
                    ll_category_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_up));
                }else {
                    ll_category_space.setVisibility(View.VISIBLE);
                    ll_category_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_down));
                }
                break;
            }
            case R.id.rl_select_subcategory:
            {
                final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_venu.getWindowToken(), 0);
                if (ll_subcategory_space.getVisibility()==View.VISIBLE)
                {
                    ll_subcategory_space.setVisibility(View.GONE);
                    ll_subcategory_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_up));
                }else {
                    ll_subcategory_space.setVisibility(View.VISIBLE);
                    ll_subcategory_space.startAnimation( AnimationUtils.loadAnimation(mActivity,R.anim.slide_down));
                }

                break;
            }
        }*/
    }


    @Override
    public void onItemClickListener(int position, View view) {
        singleRecyclerViewAdapter.selectedItem();
      //  Toast.makeText(mActivity, ""+categoryList.get(position).getCategory(), Toast.LENGTH_SHORT).show();
        categoryName = categoryList.get(position).getCategory();
        getSubCategories(userUuid,categoryList.get(position).getCategory());
    }

    @Override
    public void onSubCategoryListener(int position, View view) {
        subCategorySingleSelectionAdapter.selectedItem();
        // Toast.makeText(mActivity, ""+subCategoryList.get(position).getSub_categ_name(), Toast.LENGTH_SHORT).show();
        subCategory = subCategoryList.get(position).getSub_categ_name();
    }
}
