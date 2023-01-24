package im.vector.app.timeshare.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jsibbold.zoomage.ZoomageView;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;

import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.TSSessionManager;
import im.vector.app.timeshare.TSUtils.MyDialog;
import im.vector.app.timeshare.api_response_body.UploadImage;
import im.vector.app.timeshare.webservices.ApiUtils;
import im.vector.app.timeshare.webservices.RetrofitAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileImageViewerActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    TSSessionManager tsSessionManager;
    MyDialog myDialog;
    ImageView iv_back,iv_edit_iamge;
    ZoomageView profile_photo_view;
    String user_uuid, friend_uuid;
    BottomSheetDialog builder;
    private final int SELECT_PICTURE = 1;
    private final int TAKE_PHOTO_CODE = 2;
    private RetrofitAPI mAPIService = ApiUtils.getAPIService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_viewer);
        initView();
        HashMap<String, String> user = new HashMap<>();
        user = tsSessionManager.getUserDetails();
        user_uuid =  user.get(TSSessionManager.KEY_user_uuid);


        Intent intent = getIntent();
        friend_uuid = intent.getStringExtra("friend_uuid");
        String imagepath = intent.getStringExtra("user_pic");

        if (friend_uuid.equals(user_uuid)){
            iv_edit_iamge.setVisibility(View.VISIBLE);
        }else {
            iv_edit_iamge.setVisibility(View.GONE);
        }


        Glide.with(mActivity)
                .load(ApiClass.IMAGE_BASE_URL+imagepath)
                .placeholder(getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .error(getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .into(profile_photo_view);
    }

    private void initView() {
        mActivity = ProfileImageViewerActivity.this;
        tsSessionManager = new TSSessionManager(mActivity);
        iv_back = findViewById(R.id.iv_back_profile_viewer);
        iv_edit_iamge = findViewById(R.id.iv_edit_iamge);
        profile_photo_view = findViewById(R.id.myZoomageView);

        //listener's
        iv_back.setOnClickListener(this);
        iv_edit_iamge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back_profile_viewer) {
            finish();
        } else if (id == R.id.iv_edit_iamge) {
            choosePhoto();
        }
    }

    private void choosePhoto() {
        builder = new BottomSheetDialog(mActivity);
        builder.setCancelable(false);
        builder.setContentView(R.layout.update_profile_pic_dialog);

        TextView tv_gallery = builder.findViewById(R.id.tv_gallery);
        TextView tv_camera = builder.findViewById(R.id.tv_camera);
        TextView tv_delete_profile_photo = builder.findViewById(R.id.tv_delete_profile_photo);
        TextView tv_cancel_dialog = builder.findViewById(R.id.tv_cancel_dialog);

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO_CODE);
                builder.dismiss();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
                builder.dismiss();
            }
        });

        tv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

            }
        });

        tv_delete_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_profile_photo(builder);
            }
        });

        builder.show();
    }

    private void delete_profile_photo(BottomSheetDialog builder) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {

            Bitmap gallerybitmap;
            try {
                Uri imageUrl = data.getData();
                gallerybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                //if you want to encode the image into base64
                if (gallerybitmap!=null) {
                    profile_photo_view.setImageBitmap(gallerybitmap);
                    File defaultFile=new File(this.getExternalFilesDir(null),"XXX"+"XXX");
                    if (!defaultFile.exists())
                        defaultFile.mkdirs();
                    Date d = new Date();
                    CharSequence s  = DateFormat.format("dd-MM-yyyy_hh:mm:ss", d.getTime());

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
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_pic", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
                        RequestBody userID=RequestBody.create(MediaType.parse("text/plain"),user_uuid);
                        // System.out.println("error>> udid " + uuid);
                        Call<UploadImage> call = mAPIService.uploadProfileImage(filePart, userID);
                        call.enqueue(new Callback<UploadImage>() {
                            @Override
                            public void onResponse(Call<UploadImage> call, retrofit2.Response<UploadImage> response) {
                                System.out.println("error>>  response " + response.toString());
                                // Toast.makeText(EditProfileActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UploadImage> call, Throwable t) {
                                // System.out.println("error>>" + t.getCause());
                                Toast.makeText(mActivity, "" + t.getCause(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            HashMap<String, String> user = new HashMap<>();
            user = tsSessionManager.getUserDetails();
            String uuid = user.get(TSSessionManager.KEY_user_uuid);

            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                //if you want to encode the image into base64
                if (imageBitmap!=null) {
                    profile_photo_view.setImageBitmap(imageBitmap);
                    File defaultFile=new File(this.getExternalFilesDir(null),"XXX"+"XXX");
                    if (!defaultFile.exists())
                        defaultFile.mkdirs();
                    Date d = new Date();
                    CharSequence s  = DateFormat.format("dd-MM-yyyy_hh:mm:ss", d.getTime());

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
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_pic", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
                        RequestBody userID=RequestBody.create(MediaType.parse("text/plain"),uuid);
                        // System.out.println("error>> udid " + uuid);
                        Call<UploadImage> call = mAPIService.uploadProfileImage(filePart, userID);
                        call.enqueue(new Callback<UploadImage>() {
                            @Override
                            public void onResponse(Call<UploadImage> call, retrofit2.Response<UploadImage> response) {
                                System.out.println("error>>  response " + response.toString());
                                // Toast.makeText(EditProfileActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UploadImage> call, Throwable t) {
                                // System.out.println("error>>" + t.getCause());
                                Toast.makeText(mActivity, "" + t.getCause(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }





        }
    }
}
