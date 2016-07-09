package bo.ara.com.demoapp.network;

import java.util.List;

import bo.ara.com.demoapp.model.Post;
import bo.ara.com.demoapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by LENOVO on 14/06/2016.
 */
public interface PostService {

    @GET("/users.json")
    Call<List<User>> getAllUsers();

    @POST("/login.json")
    Call<User> login(@Body User user);

    @GET("/posts?user_id=1")
    Call<List<Post>> getPosts();

    @POST("/posts?user_id=1")
    Call<Post> post(@Body Post post);
}
