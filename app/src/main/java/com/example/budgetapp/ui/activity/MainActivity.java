package com.example.budgetapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.navigation.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends AppCompatActivity {
    private Navigator navigator = new SupportAppNavigator(this,R.id.fl_master ){};

    @Inject NavigatorHolder navigatorHolder;
    @Inject Router router;

    private DrawerLayout drawerLayout;
    private Fragment fragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setNavigationItemSelectedListener();

        fragment = getSupportFragmentManager().findFragmentById(R.id.fl_master);
        if (savedInstanceState == null && fragment == null){
            router.replaceScreen(new Screens.TransactionFragmentScreen());
        }
    }

    public void setNavigationItemSelectedListener(){
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.main_fragment:
                            router.replaceScreen(new Screens.TransactionFragmentScreen());
                            break;
                        case R.id.projects_fragment:
                            router.replaceScreen(new Screens.ProjectsFragmentScreen());
                            break;
                        case R.id.nav_about_programmer:
                            router.replaceScreen(new Screens.DeveloperFragmentScreen());
                            break;
                        case R.id.nav_feedback:
                            router.replaceScreen(new Screens.FeedbackFragmentScreen());
                            break;
                    }
                    drawerLayout.closeDrawers();
                    return true;
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
       navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }
}
