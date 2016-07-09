package bo.ara.com.demoapp.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import bo.ara.com.demoapp.DashboardActivity;
import bo.ara.com.demoapp.LoginActivity;
import bo.ara.com.demoapp.R;
import bo.ara.com.demoapp.model.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 15/06/2016.
 */
public class LoginAsyncTask extends AsyncTask<User, Void, User> {

    private LoginActivity loginActivity;

    public LoginAsyncTask(LoginActivity activity) {
        this.loginActivity = activity;
    }
    @Override
    protected User doInBackground(User... params) {
        ConnectivityManager cm = (ConnectivityManager)loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Log.d("LoginActivity", "Connectivity FAIL");
            return new User();
        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://dip-androiducbv2.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostService service = retrofit.create(PostService.class);
            Call<User> call = service.login(params[0]);

            try {
                Response<User> response = call.execute();
                User user = response.body();

                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        if (user == null) {
            // Login Fail
            Toast.makeText(loginActivity, "Login fail", Toast.LENGTH_LONG).show();
        }
        else {
            if(user.getId() != null) {
                // Store username into SharedPreferences
                SharedPreferences sharedPreferences = loginActivity.getSharedPreferences(loginActivity.getString(R.string.app_name), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putInt("user_id", user.getId());
                editor.putLong("user_id", user.getId());
                editor.commit();
                Toast.makeText(loginActivity, "Login successful, username: " + user.getUsername(), Toast.LENGTH_LONG).show();

                //Call to Dashboard Activity
                Intent intent = new Intent(loginActivity, DashboardActivity.class);
                loginActivity.startActivity(intent);
            }
            else{
                Toast.makeText(loginActivity, "Login has failed, connectivity is unavailable. Please wait and tray again", Toast.LENGTH_LONG).show();
            }
        }
    }
}
