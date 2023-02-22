package im.vector.app.timeshare.categ;



import static im.vector.app.timeshare.ApiClass.add_sub_category;
import static im.vector.app.timeshare.ApiClass.get_category;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;

public class SubCategoryActivity extends AppCompatActivity {
    AppCompatActivity mActivity;
    RecyclerView rvCategory;
    RequestQueue requestQueue;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    Button btn_continue;
    ArrayList<Category> categoryList = new ArrayList<>();
    RvSubCategoryAdapter rvSubCategoryAdapter;
    ImageView iv_close_subcategory;
    String user_uuid,first_name,last_name,email_id,profile_name,mobile_number,chat_id,chat_password;
    boolean isCategory;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    public static ArrayList<String>selectedSubCategoryList1 = new ArrayList<>();
    public static ArrayList<String>selectedSubCategoryList2 = new ArrayList<>();
    public static ArrayList<String>selectedSubCategoryList3 = new ArrayList<>();
    public static ArrayList<String>selectedSubCategoryList4 = new ArrayList<>();
    public static ArrayList<String>selectedSubCategoryList5 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        mActivity = SubCategoryActivity.this;
        tsSessionManager = new TSSessionManager(mActivity);
        findView();

        if (tsSessionManager.isLoggedIn()){
            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            user_uuid =  user.get(TSSessionManager.KEY_user_uuid);
            first_name =  user.get(TSSessionManager.KEY_first_name);
            last_name =  user.get(TSSessionManager.KEY_last_name);
            email_id =  user.get(TSSessionManager.KEY_email_id);
            profile_name =  user.get(TSSessionManager.KEY_profile_name);
            mobile_number =  user.get(TSSessionManager.KEY_mobile_number);
            chat_id =  user.get(TSSessionManager.KEY_chat_id);
            chat_password =  user.get(TSSessionManager.KEY_chat_password);
            if (tsSessionManager.isCategory()){
                isCategory=true;
            }
            getCategories(email_id);
        }

    }

    private void getCategories(String email_id) {
        myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",email_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiClass.BASE_URL + get_category,new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("categorylist>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        JSONObject object = response.getJSONObject("Cat");
                        String category1 = object.getString("category1");
                        String category2 = object.getString("category2");
                        String category3 = object.getString("category3");
                        String category4 = object.getString("category4");
                        String category5 = object.getString("category5");


                        rvCategory.setLayoutManager(new LinearLayoutManager(mActivity));
                        rvCategory.setHasFixedSize(true);
                        // add static data in eventlist
                        categoryList.add(new Category(R.drawable.ic_work,category1));
                        categoryList.add(new Category(R.drawable.ic_work,category2));
                        categoryList.add(new Category(R.drawable.ic_work,category3));
                        categoryList.add(new Category(R.drawable.ic_work,category4));
                        categoryList.add(new Category(R.drawable.ic_ideas,category5));

                        rvSubCategoryAdapter = new RvSubCategoryAdapter(mActivity, categoryList);
                        rvCategory.setAdapter(rvSubCategoryAdapter);

                    }else {
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("categorylist>>"+error);
                myDialog.hideDialog(mActivity);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    private void findView() {
        myDialog = new MyDialog(mActivity);
        tsSessionManager = new TSSessionManager(mActivity);
        myDialog = new MyDialog(mActivity);
        rvCategory = findViewById(R.id.rvCategory);
        iv_close_subcategory = findViewById(R.id.iv_close_subcategory);
        btn_continue = findViewById(R.id.btn_continue);

        // listener

        iv_close_subcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tsSessionManager.isLoggedIn()){
                    HashMap<String, String> user = new HashMap<>();
                    user = tsSessionManager.getUserDetails();
                    String user_uuid =  user.get(TSSessionManager.KEY_user_uuid);
                    String email_id =  user.get(TSSessionManager.KEY_email_id);
                   if (selectedSubCategoryList1.size()>0 ||
                           selectedSubCategoryList2.size()>0 ||
                           selectedSubCategoryList3.size()>0 ||
                           selectedSubCategoryList3.size()>0 ||
                           selectedSubCategoryList4.size()>0 ||
                           selectedSubCategoryList5.size()>0){
                       addSubCategories(user_uuid,email_id,selectedSubCategoryList1.size(),
                                selectedSubCategoryList2.size(),
                                selectedSubCategoryList3.size(),
                                selectedSubCategoryList4.size(),
                                selectedSubCategoryList5.size());
                    } else {
                        Toast.makeText(mActivity, "Please Select Sub Category", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });

    }

    private void addSubCategories(String user_uuid, String email_id,int count1,int count2,int count3,int count4,int count5) {
        myDialog.showProgresbar(mActivity);
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("user_uuid",user_uuid);
            jsonObject.put("email_id",email_id);

            for (int i=0;i<5;i++){

                JSONObject innerObj = new JSONObject();
                if (i==0) { //first
                    if (count1==0){

                    }else
                    if (count1 == 1) {
                        innerObj.put("sub_category1", selectedSubCategoryList1.get(0));
                    } else if (count1 == 2) {
                        innerObj.put("sub_category1", selectedSubCategoryList1.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList1.get(1));
                    } else if (count1 == 3) {
                        innerObj.put("sub_category1", selectedSubCategoryList1.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList1.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList1.get(2));
                    } else if (count1 == 4) {
                        innerObj.put("sub_category1", selectedSubCategoryList1.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList1.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList1.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList1.get(3));
                    } else {
                        innerObj.put("sub_category1", selectedSubCategoryList1.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList1.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList1.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList1.get(3));
                        innerObj.put("sub_category5", selectedSubCategoryList1.get(4));
                    }
                }

                if (i==1) { //second
                    if (count2==0){

                    }else
                    if (count2 == 1) {
                        innerObj.put("sub_category1", selectedSubCategoryList2.get(0));
                    } else if (count2 == 2) {
                        innerObj.put("sub_category1", selectedSubCategoryList2.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList2.get(1));
                    } else if (count2 == 3) {
                        innerObj.put("sub_category1", selectedSubCategoryList2.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList2.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList2.get(2));
                    } else if (count2 == 4) {
                        innerObj.put("sub_category1", selectedSubCategoryList2.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList2.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList2.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList2.get(3));
                    } else {
                        innerObj.put("sub_category1", selectedSubCategoryList2.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList2.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList2.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList2.get(3));
                        innerObj.put("sub_category5", selectedSubCategoryList2.get(4));
                    }
                }

                if (i==2) { //third
                    if (count3==0){

                    }else
                    if (count3 == 1) {
                        innerObj.put("sub_category1", selectedSubCategoryList3.get(0));
                    } else if (count3 == 2) {
                        innerObj.put("sub_category1", selectedSubCategoryList3.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList3.get(1));
                    } else if (count3 == 3) {
                        innerObj.put("sub_category1", selectedSubCategoryList3.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList3.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList3.get(2));
                    } else if (count3 == 4) {
                        innerObj.put("sub_category1", selectedSubCategoryList3.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList3.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList3.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList3.get(3));
                    } else {
                        innerObj.put("sub_category1", selectedSubCategoryList3.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList3.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList3.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList3.get(3));
                        innerObj.put("sub_category5", selectedSubCategoryList3.get(4));
                    }
                }

                if (i==3) { //third
                    if (count4==0){

                    }else
                    if (count4 == 1) {
                        innerObj.put("sub_category1", selectedSubCategoryList4.get(0));
                    } else if (count4 == 2) {
                        innerObj.put("sub_category1", selectedSubCategoryList4.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList4.get(1));
                    } else if (count4 == 3) {
                        innerObj.put("sub_category1", selectedSubCategoryList4.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList4.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList4.get(2));
                    } else if (count4 == 4) {
                        innerObj.put("sub_category1", selectedSubCategoryList4.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList4.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList4.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList4.get(3));
                    } else {
                        innerObj.put("sub_category1", selectedSubCategoryList4.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList4.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList4.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList4.get(3));
                        innerObj.put("sub_category5", selectedSubCategoryList4.get(4));
                    }
                }

                if (i==4) { //third
                    if (count5==0){

                    }
                    else if (count5 == 1) {
                        innerObj.put("sub_category1", selectedSubCategoryList5.get(0));
                    } else if (count5 == 2) {
                        innerObj.put("sub_category1", selectedSubCategoryList5.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList5.get(1));
                    } else if (count5 == 3) {
                        innerObj.put("sub_category1", selectedSubCategoryList5.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList5.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList5.get(2));
                    } else if (count5 == 4) {
                        innerObj.put("sub_category1", selectedSubCategoryList5.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList5.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList5.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList5.get(3));
                    } else {
                        innerObj.put("sub_category1", selectedSubCategoryList5.get(0));
                        innerObj.put("sub_category2", selectedSubCategoryList5.get(1));
                        innerObj.put("sub_category3", selectedSubCategoryList5.get(2));
                        innerObj.put("sub_category4", selectedSubCategoryList5.get(3));
                        innerObj.put("sub_category5", selectedSubCategoryList5.get(4));
                    }
                }

                jsonArray.put(innerObj);
            }

            jsonObject.put("sub_category",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("param>>"+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiClass.BASE_URL + add_sub_category,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("subcategory>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        tsSessionManager.createLoginSession(true,user_uuid,first_name,last_name,email_id,profile_name,mobile_number,chat_id,chat_password,isCategory,true);

                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("subcategory>>"+error);
                myDialog.hideDialog(mActivity);

            }
        });

        requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}
