package bo.ara.com.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bo.ara.com.demoapp.model.User;
import bo.ara.com.demoapp.network.LoginAsyncTask;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText)findViewById(R.id.inputUsername);
        inputPassword = (EditText)findViewById(R.id.inputPassword);

        hideActionBar();
    }

    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void loginClick(View view) {
        Log.d("LoginActivity", "You clicked me!");

        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        Log.d("LoginActivity", "Username: " + username);
        Log.d("LoginActivity", "Password: " + password);

        //HTTP Request
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        LoginAsyncTask taskAsync = new LoginAsyncTask(this);
        taskAsync.execute(user);
    }
}