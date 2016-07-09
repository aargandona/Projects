package bo.ara.com.demoapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import bo.ara.com.demoapp.NewPostActivity;
import bo.ara.com.demoapp.model.Post;
import bo.ara.com.demoapp.model.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 6/18/2016.
 */
public class NewPostAsyncTask extends AsyncTask<Post, Void, Post> {

    private NewPostActivity newPostActivity;

    public NewPostAsyncTask(NewPostActivity newPostActivity) {
        this.newPostActivity = newPostActivity;
    }

    @Override
    protected Post doInBackground(Post... params) {
        ConnectivityManager cm = (ConnectivityManager)newPostActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Log.d("NewPostActivity", "Connectivity FAIL");
            return new Post();
        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://dip-androiducbv2.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostService service = retrofit.create(PostService.class);
            Call<Post> call = service.post(params[0]);

            try {
                Response<Post> response = call.execute();
                Post post = response.body();

                return post;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Post post) {
        if(post == null) {
            Toast.makeText(newPostActivity, "Post save fail", Toast.LENGTH_LONG).show();
        }
        else{
            if(post.getPosted_date() != null) {
                Toast.makeText(newPostActivity, "Post saved successful, username: " + post.getUser().getUsername() + ", posted_date: " +
                        post.getPosted_date(), Toast.LENGTH_LONG).show();

                newPostActivity.finish();
            }
            else
                Toast.makeText(newPostActivity, "Save a new post has failed, connectivity is unavailable. Please wait and tray again", Toast.LENGTH_LONG).show();

        }
    }
}
