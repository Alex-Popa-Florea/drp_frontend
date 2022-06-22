package ic.ac.drp02;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ic.ac.drp02.analytics.TimeToLike;
import ic.ac.drp02.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;

    ProfileFragment profileFragment = new ProfileFragment();
    FeedFragment feedFragment = new FeedFragment();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            TimeToLike timeToLike = TimeToLike.getInstance();
            timeToLike.setAppStartTime(LocalDateTime.now());

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Log.e("adhithi", "lkfjklfj");
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavController navController = Navigation.findNavController(this, R.id.container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.container);

        switch (item.getItemId()) {
            case R.id.profile:
                navController.navigate(R.id.action_global_profileFragment);                Log.e("adhithi", "oh dear");
                return true;

            case R.id.home:
                navController.navigate(R.id.action_global_feedFragment);
                return true;

            case R.id.discover:
                navController.navigate(R.id.action_global_discoverFragment);
                return true;

            case R.id.addFriends:
                navController.navigate(R.id.action_global_addFriend);
                return true;

        }
        return false;
    }
}