package bo.ara.com.demoapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bo.ara.com.demoapp.PostsFragment;
import bo.ara.com.demoapp.model.Post;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 15/06/2016.
 */
public class RequestPostsAsyncTask extends AsyncTask<Void, Void, List<Post>> {

    private PostsFragment postsFragment;

    public RequestPostsAsyncTask(PostsFragment postsFragment) {
        this.postsFragment = postsFragment;
    }

    @Override
    protected List<Post> doInBackground(Void... params) {
        //Check connectvity status
        ConnectivityManager cm = (ConnectivityManager)postsFragment.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Log.d("PostFragment", "Connectivity FAIL");
            Log.d("PostFragment", "Retrieving Posts Data from DB");
            List<Post> list = Post.listAll(Post.class);
            return list;
        }
        else {
            //Post.deleteAll(Post.class);
            Log.d("PostFragment", "Connectivity OK");
            Log.d("PostFragment", "Retrieving Posts Data from API Service");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://dip-androiducbv2.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostService service = retrofit.create(PostService.class);

            Call<List<Post>> posts = service.getPosts();
            try {
                Response<List<Post>> response = posts.execute();
                List<Post> list = response.body();

                //Store into DB
                storePostsIntoDB(list);

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Post> posts) {
        super.onPostExecute(posts);

        postsFragment.getPostAdapter().clear();
        postsFragment.getPostAdapter().addAll(posts);
    }
    
    private void storePostsIntoDB(List<Post> list){
        for (Post post: list) {
            try {
                post.save();//store int DB
                post.getUser().save();
            }
            catch (Exception ex){
                Log.d("Store DB procedure", "Error to store post = " + ex.getMessage());
            }
        }
    }
}
