package bo.ara.com.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PostDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titleTextView;
    TextView contentTextView;
    TextView dateTextView;
    TextView userInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        imageView = (ImageView) findViewById(R.id.postdetail_image);
        titleTextView = (TextView)findViewById(R.id.postdetail_title);
        contentTextView = (TextView)findViewById(R.id.postdetail_content);
        dateTextView = (TextView)findViewById(R.id.postdetail_date);
        userInfoTextView = (TextView)findViewById(R.id.postdetail_userInfo);

        String userInfo = getIntent().getExtras().getString("UserName");
        String userImageUri = getIntent().getExtras().getString("UserImageUri");

        String title = getIntent().getExtras().getString("Title");
        String content = getIntent().getExtras().getString("Content");

        String postDate = getIntent().getExtras().getString("PostedDate");
        postDate = postDate.substring(0, 10).replace('-', '/');

        Log.d("PostDetailActivity", "User uri = " + userImageUri);
        Glide.with(this).load(userImageUri).into(imageView);
        titleTextView.setText(title);
        contentTextView.setText(content);
        dateTextView.setText(postDate);
        userInfoTextView.setText(userInfo);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_postInfo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
