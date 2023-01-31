/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.timeshare.menu;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import im.vector.app.R;
import im.vector.app.features.MainActivity;
import im.vector.app.features.workers.signout.SignOutUiWorker;
import im.vector.app.timeshare.auth.TSLoginActivity;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_request_body.CommonRequest;
import im.vector.app.timeshare.api_response_body.CommonResponse;
import im.vector.app.timeshare.menu.ExpandableListAdapter;
import im.vector.app.timeshare.menu.FollowActivity;
import im.vector.app.timeshare.menu.MyActivity;
import im.vector.app.timeshare.profile.MyProfileActivity;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;


public class MenuFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rl_toggle_notification,rl_toggle_about,rl_change_pass,rl_permissions,rl_category,
            rl_logout;
    LinearLayout ll_profile,ll_follow,ll_myactivity,ll_about;
    RelativeLayout rl_toggle_notif_child,rl_chat;
    View view_notif;
    private AlertDialog logoutDialog,deleteDialog;
    TSSessionManager tsSessionManager;

    MyDialog myDialog;
    ListView listview_about;
     ArrayList<String> arrayList = new ArrayList<>();
    BottomSheetBehavior bottomSheetBehavior;
    boolean oldpass = false,newpass=false,renewpass=false;
    EditText edt_oldpass,edt_newpass,edt_repass;
   public static ExpandableListAdapter expandableListAdapter;
    private static ExpandableListView expandableListView;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view  =  inflater.inflate(R.layout.fragment_menu, container, false);
     tsSessionManager = new TSSessionManager(getActivity());

     findView(view);

        // Setting group indicator null for custom indicator
       // expandableListView.setGroupIndicator(null);

        setItems();
        setListener();

     return view;
    }

    private void setListener() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
              //  Toast.makeText(getActivity(), "You clicked : " + expandableListAdapter.getGroup(groupPosition), Toast.LENGTH_SHORT).show();
                if (expandableListAdapter.getGroup(groupPosition).equals("Change Password")){
                    changePasswordBottomSheetDialog();
                }else if (expandableListAdapter.getGroup(groupPosition).equals("Permissions")){
                //    startActivity(new Intent(getActivity(), PermissionActivity.class));
                }else if (expandableListAdapter.getGroup(groupPosition).equals("Category")){
                  //  startActivity(new Intent(getActivity(), CategoryActivity.class));
                }else if (expandableListAdapter.getGroup(groupPosition).equals("Logout")){
                    alertDialog();
                }else if (expandableListAdapter.getGroup(groupPosition).equals("Start Chat")){
                  //  startActivity(new Intent(getActivity(), ChatLoginActivity.class));
                }
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
               /* Toast.makeText(
                        getActivity(),
                        "You clicked : " + expandableListAdapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();*/
                if (expandableListAdapter.getChild(groupPos,childPos).equals("Delete This Account")){
                    alertDialog_deleteAccount();
                }else if (expandableListAdapter.getChild(groupPos,childPos).equals("Rate Timeshare")){
                 //   startActivity(new Intent(getActivity(), AddReviewActivity.class));
                }
                return false;
            }
        });
    }

    private void setItems() {
        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();
        List<String> child5 = new ArrayList<String>();
        List<String> child6 = new ArrayList<String>();
        List<String> child7 = new ArrayList<String>();
        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        // Adding headers to list
        header.add("Notification");
        header.add("About");
        header.add("Change Password");
        header.add("Permissions");
        header.add("Category");
        header.add("Logout");
       // header.add("Start Chat");

        // Adding Notification child data
        child1.add(0,"Push Notifications");

        // Adding About child data
        child2.add(0,"Rate Timeshare");
        child2.add(1,"Privacy Policy");
        child2.add(2,"App Info");
        child2.add(3,"Delete This Account");

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);
        hashMap.put(header.get(4), child5);
        hashMap.put(header.get(5), child6);
       // hashMap.put(header.get(6), child7);

        expandableListAdapter = new ExpandableListAdapter(getActivity(), header, hashMap);
        expandableListView.setAdapter(expandableListAdapter);

    }

    private void findView(View v) {
        myDialog = new MyDialog(getActivity());

        ll_profile = v.findViewById(R.id.ll_profile);
        ll_follow = v.findViewById(R.id.ll_follow);
        ll_myactivity = v.findViewById(R.id.ll_myactivity);
        expandableListView = v.findViewById(R.id.simple_expandable_listview);

        // Listener's
        ll_profile.setOnClickListener(this);
        ll_follow.setOnClickListener(this);
        ll_myactivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_profile) {
            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String user_uuid = user.get(TSSessionManager.KEY_user_uuid);
            Intent intent = new Intent(getActivity(), MyProfileActivity.class);
            intent.putExtra("friend_uuid", user_uuid);
            getActivity().startActivity(intent);
        } else if (id == R.id.ll_follow) {
            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String user_uuid = user.get(TSSessionManager.KEY_user_uuid);
            Intent intent = new Intent(getActivity(), FollowActivity.class);
            intent.putExtra("friend_uuid", user_uuid);
            getActivity().startActivity(intent);
        } else if (id == R.id.ll_myactivity) {
            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String user_uuid = user.get(TSSessionManager.KEY_user_uuid);
            Intent intent = new Intent(getActivity(), MyActivity.class);
            intent.putExtra("friend_uuid", user_uuid);
            getActivity().startActivity(intent);
        }
    }

    private void changePasswordBottomSheetDialog() {
        BottomSheetDialog builder = new BottomSheetDialog(getActivity());
        builder.setCancelable(false);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_change_password, null);
        builder.setContentView(view);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int maxHeight = (int) (height*0.92);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(maxHeight);

        ImageView img_oldpass,img_newpass,img_renewpass;


        TextView tv_cancel,tv_save;

        ImageView iv_close = builder.findViewById(R.id.iv_close_change_pass);

        img_oldpass = builder.findViewById(R.id.img_oldpass);
        img_newpass = builder.findViewById(R.id.img_newpass);
        img_renewpass = builder.findViewById(R.id.img_renewpass);

        edt_oldpass = builder.findViewById(R.id.edt_oldpass);
        edt_newpass = builder.findViewById(R.id.edt_newpass);
        edt_repass = builder.findViewById(R.id.edt_repass);

        tv_cancel = builder.findViewById(R.id.tv_cancel);
        tv_save = builder.findViewById(R.id.tv_save);


        img_oldpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_oldpass.getText().length() > 0) {
                    if (!oldpass) {
                        oldpass = true;
                        img_oldpass.setImageResource(R.drawable.ic_eye_crossed);
                        edt_oldpass.setTransformationMethod(
                                HideReturnsTransformationMethod.getInstance());
                    } else {
                        oldpass = false;
                        img_oldpass.setImageResource(R.drawable.ic_eye);
                        edt_oldpass.setTransformationMethod(
                                PasswordTransformationMethod.getInstance());

                    }
                }
                edt_oldpass.setSelection(edt_oldpass.getText().toString().length());

            }
        });

        img_newpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_newpass.getText().length() > 0) {
                    if (!newpass) {
                        newpass = true;
                        img_newpass.setImageResource(R.drawable.ic_eye_crossed);
                        edt_newpass.setTransformationMethod(
                                HideReturnsTransformationMethod.getInstance());
                    } else {
                        newpass = false;
                        img_newpass.setImageResource(R.drawable.ic_eye);
                        edt_newpass.setTransformationMethod(
                                PasswordTransformationMethod.getInstance());

                    }
                }
                edt_newpass.setSelection(edt_newpass.getText().toString().length());
            }
        });
        img_renewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_repass.getText().length() > 0) {
                    if (!renewpass) {
                        renewpass = true;
                        img_renewpass.setImageResource(R.drawable.ic_eye_crossed);
                        edt_repass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        renewpass = false;
                        img_renewpass.setImageResource(R.drawable.ic_eye);
                        edt_repass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }
                edt_repass.setSelection(edt_repass.getText().toString().length());
            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tsSessionManager.isLoggedIn()) {
                    HashMap<String, String> user = new HashMap<>();
                    user = tsSessionManager.getUserDetails();
                    String uuid =  user.get(TSSessionManager.KEY_user_uuid);

                    String oldpass = edt_oldpass.getText().toString().trim();
                    String newpass = edt_newpass.getText().toString().trim();
                    String repass = edt_repass.getText().toString().trim();
                    if (validate(uuid,oldpass,newpass,repass)) {
                       // change_password(uuid,oldpass,newpass,repass,builder);
                    }
                }
            }
        });


        builder.show();
    }

/*    private void change_password(String uuid, String oldpass, String newpass, String repass,BottomSheetDialog builder) {
        myDialog.showProgresbar(getActivity());
        HashMap<String,String> params = new HashMap<>();
        params.put("user_uuid",uuid);
        params.put("old_password",oldpass);
        params.put("new_password",newpass);
        params.put("retype_new_password",repass);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiStatus.BASE_URL + change_password, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("change_password>>"+response);
                myDialog.hideDialog(getActivity());

                try {
                    String status = response.getString("Status");
                    String mesage = response.getString("Msg");
                    if (status.equals("1"))
                    {
                        Toast.makeText(getActivity(), ""+mesage, Toast.LENGTH_SHORT).show();

                        builder.dismiss();

                    }else {
                        Toast.makeText(getActivity(), ""+mesage, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("change_password>>"+error);
                myDialog.hideDialog(getActivity());

            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
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
    }*/

    private boolean validate(String uuid, String oldpass, String newpass, String repass) {
        if (uuid.equals("")) {
            Toast.makeText(getActivity(), "uuid is empty!", Toast.LENGTH_SHORT).show();
            return false;

        } else if(oldpass.equals("")) {
            edt_oldpass.setError("Please enter old password!");
            edt_oldpass.requestFocus();
            return false;

        } else if(newpass.equals("")) {
            edt_newpass.setError("Please enter new password!");
            edt_newpass.requestFocus();
            return false;
        } else if(repass.equals("")) {
            edt_repass.setError("Please enter re-password!");
            edt_repass.requestFocus();
            return false;
        }else if (renewpass!=renewpass){
            Toast.makeText(getActivity(), "password mismatched!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    private void alertDialog_deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.delete_account, null);
        builder.setView(view);
        TextView no = (TextView) view.findViewById(R.id.tv_no);
        TextView yes = (TextView) view.findViewById(R.id.tv_yes);
        yes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tsSessionManager.isLoggedIn()) {
                            HashMap<String, String> user = new HashMap<>();
                            user = tsSessionManager.getUserDetails();
                            String uuid =  user.get(TSSessionManager.KEY_user_uuid);

                            delete_account(uuid,deleteDialog);
                            deleteDialog.dismiss();
                        }

                    }
                });
        no.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();
                    }
                });
        deleteDialog = builder.show();
    }


    private void delete_account(String uuid,AlertDialog deleteDialog) {
        myDialog.showProgresbar(getActivity());
        CommonRequest delete_account_request = new CommonRequest(uuid);
        Call<CommonResponse> call = mAPIService.deleteAccount(delete_account_request);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(getActivity());
                if(response.body()!=null){
                    CommonResponse signupResponse = response.body();
                    String message = signupResponse.getMsg();
                    String status = signupResponse.getStatus();
                    if (status.equals("1"))
                    {
                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                        tsSessionManager.logoutUser();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }else {

                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(getActivity());
            }
        });

    }


    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        builder.setView(view);
        TextView cancelView = (TextView) view.findViewById(R.id.cancel);
        TextView mLogout = (TextView) view.findViewById(R.id.mLogout);
        mLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tsSessionManager.isLoggedIn()) {
                            HashMap<String, String> user = new HashMap<>();
                            user = tsSessionManager.getUserDetails();
                           String uuid =  user.get(TSSessionManager.KEY_user_uuid);

                            logout(uuid);
                            logoutDialog.dismiss();

                        }

                    }
                });
        cancelView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logoutDialog.dismiss();
                    }
                });
        logoutDialog = builder.show();
    }

    private void logout(String uuid) {
        myDialog.showProgresbar(getActivity());
        CommonRequest logoutRequest = new CommonRequest(uuid);
        Call<CommonResponse> call = mAPIService.logout(logoutRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                //  System.out.println("error>>  response " + response.toString());
                myDialog.hideDialog(getActivity());
                if(response.body()!=null){
                    CommonResponse signupResponse = response.body();
                    String message = signupResponse.getMsg();
                    String status = signupResponse.getStatus();
                    if (status.equals("1"))
                    {
                        tsSessionManager.logoutUser();
                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(getActivity(), MainActivity.class));
                       // new SignOutUiWorker(requireActivity()).perform();
                        getActivity().finish();

                    }else {
                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                System.out.println("error>>" + t.getCause());
                myDialog.hideDialog(getActivity());
            }
        });

    }
}
