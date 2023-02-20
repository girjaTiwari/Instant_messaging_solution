package im.vector.app.timeshare.categ;



import static im.vector.app.timeshare.ApiClass.add_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.AddCategoryRequest;
import im.vector.app.timeshare.api_request_body.Categories;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class CategoryActivity extends AppCompatActivity {
    AppCompatActivity mActivity;

    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    RecyclerView rv_category;
    ArrayList<Category>categoryList = new ArrayList<>();
    RvCategoryAdapter rvCategoryAdapter;
    ImageView iv_close_category;
    RequestQueue requestQueue;
    Button btn_continue;
  public static ArrayList<String>selectedCategoryList = new ArrayList<>();
  String user_uuid,first_name,last_name,email_id,profile_name,mobile_number,chat_id,chat_password;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mActivity = CategoryActivity.this;
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

           // getCategories(email_id);

            //add static category statically for some time

            rv_category.setLayoutManager(new GridLayoutManager(mActivity,2));
            rv_category.setHasFixedSize(true);
            // add static data in eventlist
            categoryList.add(new Category(R.drawable.ic_ideas,"Travelling"));
            categoryList.add(new Category(R.drawable.ic_ideas,"Education"));
            categoryList.add(new Category(R.drawable.ic_ideas,"Sports"));
            categoryList.add(new Category(R.drawable.ic_ideas,"Cars"));
            categoryList.add(new Category(R.drawable.ic_ideas,"Movie"));

            rvCategoryAdapter = new RvCategoryAdapter(mActivity, categoryList);
            rv_category.setAdapter(rvCategoryAdapter);


        }

    }

    /*private void getCategories(String email_id) {
        myDialog.showProgresbar(mActivity);
        HashMap<String,String> params = new HashMap<>();
        params.put("email_id",email_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + get_category,new JSONObject(params), new Response.Listener<JSONObject>() {
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

                        rv_category.setLayoutManager(new GridLayoutManager(mActivity,2));
                        rv_category.setHasFixedSize(true);
                        // add static data in eventlist
                        categoryList.add(new Category(R.drawable.ic_work,category1));
                        categoryList.add(new Category(R.drawable.ic_health,category2));
                        categoryList.add(new Category(R.drawable.ic_holiday,category3));
                        categoryList.add(new Category(R.drawable.ic_gift,category4));
                        categoryList.add(new Category(R.drawable.ic_ideas,category5));

                        rvCategoryAdapter = new RvCategoryAdapter(mActivity, categoryList);
                        rv_category.setAdapter(rvCategoryAdapter);

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
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }*/

    private void findView() {
        myDialog = new MyDialog(mActivity);
        tsSessionManager = new TSSessionManager(mActivity);
        rv_category = findViewById(R.id.rv_category);
        iv_close_category = findViewById(R.id.iv_close_category);
        btn_continue = findViewById(R.id.btn_continue);

        //listener

        iv_close_category.setOnClickListener(new View.OnClickListener() {
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
                    String email_id =  user.get(TSSessionManager.KEY_email_id);
                    if (selectedCategoryList.size()>0){
                        addCategory(email_id,selectedCategoryList.size());
                    } else {
                        Toast.makeText(mActivity, "Select Category", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }


    private void addCategory(String email_id,int count) {
        myDialog.showProgresbar(mActivity);
        JSONObject jsonObject = new JSONObject();
        JSONObject innerObj = new JSONObject();
        try {
            jsonObject.put("email_id",email_id);

            if (count==1){
                innerObj.put("category1",selectedCategoryList.get(0));
            }else if (count==2){
                innerObj.put("category1",selectedCategoryList.get(0));
                innerObj.put("category2",selectedCategoryList.get(1));
            }else if (count==3){
                innerObj.put("category1",selectedCategoryList.get(0));
                innerObj.put("category2",selectedCategoryList.get(1));
                innerObj.put("category3",selectedCategoryList.get(2));
            }else if (count==4){
                innerObj.put("category1",selectedCategoryList.get(0));
                innerObj.put("category2",selectedCategoryList.get(1));
                innerObj.put("category3",selectedCategoryList.get(2));
                innerObj.put("category4",selectedCategoryList.get(3));
            }else {
                innerObj.put("category1",selectedCategoryList.get(0));
                innerObj.put("category2",selectedCategoryList.get(1));
                innerObj.put("category3",selectedCategoryList.get(2));
                innerObj.put("category4",selectedCategoryList.get(3));
                innerObj.put("category5",selectedCategoryList.get(4));
            }

            jsonObject.put("categorys",innerObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("param>>"+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiClass.BASE_URL + add_category,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("category>>"+response);
                myDialog.hideDialog(mActivity);

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        tsSessionManager.createLoginSession(true,user_uuid,first_name,last_name,email_id,profile_name,mobile_number,chat_id,chat_password,true,false);
                        Toast.makeText(mActivity, ""+mesage, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mActivity, SubCategoryActivity.class));
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
                System.out.println("category>>"+error);
                myDialog.hideDialog(mActivity);

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
        });
    }
}
