package im.vector.app.timeshare.details;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;

import im.vector.app.R;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatActivity mActivity;
    BottomSheetBehavior bottomSheetBehavior;
    private final int PICK_IMAGE_MULTIPLE = 1;
    private final int TAKE_PHOTO_CODE = 2;
    ImageView iv_back_;
    RecyclerView rv_allmedia;
    RvGalleryAdapter rvGalleryAdapter;
    String first_name,last_name,user_pic,friend_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mActivity = GalleryActivity.this;
        initView();
      //  uploadMediaDialog();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> arraylist = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");
        user_pic = intent.getStringExtra("user_pic");
        friend_uuid = intent.getStringExtra("friend_uuid");

        //set data on adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3, LinearLayoutManager.VERTICAL,false);
        rv_allmedia.setLayoutManager(gridLayoutManager);
        rvGalleryAdapter=new RvGalleryAdapter(mActivity,arraylist,first_name,last_name,user_pic,friend_uuid);
        rv_allmedia.setAdapter(rvGalleryAdapter);
    }


    /*private void uploadMediaDialog() {
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
               // ll_content_area.setVisibility(View.GONE);
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
              //  ll_content_area.setVisibility(View.GONE);
                builder.dismiss();
            }
        });

        tv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   ll_content_area.setVisibility(View.VISIBLE);
                builder.dismiss();

            }
        });

        builder.show();
    }*/

    private void initView() {
        iv_back_ = findViewById(R.id.iv_back_);
        rv_allmedia = findViewById(R.id.rv_allmedia);

        //listener's
        iv_back_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_) {
            finish();
        }
    }
}
