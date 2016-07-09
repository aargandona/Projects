package bo.ara.com.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import bo.ara.com.demoapp.adapter.ViewPagerAdapter;

public class DashboardActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //remove the action-bar elevation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        tabLayout = (TabLayout)findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        //Evento cambiar de pagina
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Evento cambiar de tab (tab seleccionado)
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void logout() {
        Log.d("PostsFragment", "You clicked logout!");

        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();//abrir archivo para escritura
        editor.remove("username");//eliminar el valor
        editor.commit();//Guardar cambios

        Log.d("PostsFragment", "return to login");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void logoutClick(View view) {
        logout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_option:
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                logout();
                return true;
            case R.id.register_option:
                //Toast.makeText(this, "Register", Toast.LENGTH_LONG).show();
                register();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void register() {
        Log.d("PostsFragment", "Go to NewPostActivity");
        Intent intent = new Intent(this, NewPostActivity.class);
        startActivity(intent);
    }
}
