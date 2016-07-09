package bo.ara.com.demoapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bo.ara.com.demoapp.UsersFragment;
import bo.ara.com.demoapp.model.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 15/06/2016.
 */
public class RequestUsersAsyncTask extends AsyncTask<Void, Void, List<User>> {

    private UsersFragment usersFragment;

    public RequestUsersAsyncTask(UsersFragment usersFragment) {
        this.usersFragment = usersFragment;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        //Check connectvity status
        ConnectivityManager cm = (ConnectivityManager)usersFragment.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Log.d("UserFragment", "Connectivity FAIL");
            Log.d("UserFragment", "Retrieving Users Data from DB");
            List<User> list = User.listAll(User.class);
            return list;
        }
        else {
            //User.deleteAll(User.class);
            Log.d("UserFragment", "Connectivity OK");
            Log.d("UserFragment", "Retrieving Users Data from API Service");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://dip-androiducbv2.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostService service = retrofit.create(PostService.class);

            Call<List<User>> users = service.getAllUsers();
            try {
                Response<List<User>> response = users.execute();
                List<User> list = response.body();

                storeUsersIntoDB(list);

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);

        usersFragment.getUserAdapter().clear();
        usersFragment.getUserAdapter().addAll(users);
    }


    private void storeUsersIntoDB(List<User> list){
        for (User user: list) {
            try {
                List<User> users = User.find(User.class, "username=?", user.getUsername());
                Log.d("Users", "list size = " + users.size());
                if(users.size() == 0) {
                    user.save();//store int DB
                }
            }
            catch (Exception ex){
                Log.d("Store DB procedure", "Error to store post = " + ex.getMessage());
            }
        }
    }

}
