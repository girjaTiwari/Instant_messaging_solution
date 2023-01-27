package im.vector.app.timeshare.myactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.profile.MyProfileActivity;

public class ImageViewerActivity extends AppCompatActivity {
    ImageView iv_close,iv_profilepic;
    TextView tv_delete,tv_posteddby;
    String first_name,last_name,user_pic,friend_uuid;
    ZoomageView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Intent intent = getIntent();
        String imagepath = intent.getStringExtra("image");
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");
        user_pic = intent.getStringExtra("user_pic");
        friend_uuid = intent.getStringExtra("friend_uuid");
        initView();


       // System.out.println("iamgepath>>"+imagepath);

        Glide.with(ImageViewerActivity.this)
                .load(imagepath)
                .placeholder(getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .into(photoView);


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        photoView = findViewById(R.id.photo_view);
        iv_close = findViewById(R.id.iv_close);
        iv_profilepic = findViewById(R.id.iv_profilepic);
        tv_delete = findViewById(R.id.tv_delete);
        tv_posteddby = findViewById(R.id.tv_posteddby);

        tv_posteddby.setText(first_name+" "+last_name);
        Glide.with(ImageViewerActivity.this)
                .load(ApiClass.IMAGE_BASE_URL+user_pic)
                .placeholder(getResources().getDrawable(R.drawable.ic_baseline_image_24))
                .into(iv_profilepic);

        iv_profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageViewerActivity.this, MyProfileActivity.class);
                intent.putExtra("friend_uuid",friend_uuid);
                startActivity(intent);
            }
        });
    }


}
