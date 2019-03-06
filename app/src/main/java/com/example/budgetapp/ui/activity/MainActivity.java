package com.example.budgetapp.ui.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.budgetapp.R;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.fragment.DeveloperFragment;
import com.example.budgetapp.ui.fragment.FeedbackFragment;
import com.example.budgetapp.ui.fragment.MainFragment;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private NavigationView navigationView;
    @Inject NavigatorHolder navigatorHolder;


    private Navigator navigator = new SupportAppNavigator(this,R.id.fl_master ){
        @Override
        protected void applyCommand(Command command) {
            if(command instanceof Replace){
                Replace replace = (Replace) command;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        fragmentManager = getSupportFragmentManager();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setNavigationItemSelectedListener();

        fragment = getSupportFragmentManager().findFragmentById(R.id.fl_master);
        if (savedInstanceState == null && fragment == null){
            Command[] commands = {new Replace(new Screens.MainScreen())};
            navigator.applyCommands(commands);
        }
    }

    public void setNavigationItemSelectedListener(){
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.main_fragment:
                            fragment = new MainFragment();
                            break;
                        case R.id.nav_about_programmer:
                            fragment = new DeveloperFragment();
                            break;
                        case R.id.nav_feedback:
                            fragment = new FeedbackFragment();
                            break;
                    }
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fl_master, fragment).commit();
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
