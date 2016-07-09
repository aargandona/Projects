package bo.ara.com.demoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import bo.ara.com.demoapp.model.Post;
import bo.ara.com.demoapp.network.NewPostAsyncTask;

public class NewPostActivity extends AppCompatActivity {

    private EditText titleText;
    private EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        titleText = (EditText)findViewById(R.id.input_title);
        contentText = (EditText)findViewById(R.id.input_content);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_new_post);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void savePost(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        //int userId = sharedPreferences.getInt("user_id", 0);
        long userId = sharedPreferences.getLong("user_id", 0);

        Post post = new Post();
        post.setTitle(titleText.getText().toString());
        post.setContent(contentText.getText().toString());
        post.setUser_id(userId);

        //post.save();//Store into Database

        NewPostAsyncTask newPostAsyncTask = new NewPostAsyncTask(this);
        newPostAsyncTask.execute(post);
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
