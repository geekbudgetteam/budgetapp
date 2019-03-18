package com.example.budgetapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.fragment.BackFragment;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity {

    private Navigator navigator = new SupportAppNavigator(this,R.id.fl_master ) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
        }

//        @Override
//        protected void setupFragmentTransaction(Command command, Fragment currentFragment, Fragment nextFragment, FragmentTransaction fragmentTransaction) {
//            super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction);
//            if (nextFragment instanceof BackFragment){
//                showBackIcon();
//            } else {
//                showHamburgerIcon();
//            }
//        }
    };

    @Inject NavigatorHolder navigatorHolder;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
//    private View.OnClickListener toggleListener;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggleListener = toggle.getToolbarNavigationClickListener()
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        setNavigationItemSelectedListener();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_master);
        if (savedInstanceState == null && fragment == null){
            navigator.applyCommands(new Command[]{new Replace(new Screens.TransactionFragmentScreen())});
        }
    }

    public void setNavigationItemSelectedListener(){
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.main_fragment:
                            navigator.applyCommands(new Command[]{new Replace(new Screens.TransactionFragmentScreen())});
                            break;
                        case R.id.projects_fragment:
                            navigator.applyCommands(new Command[]{new Replace(new Screens.ProjectsFragmentScreen())});
                            break;
                        case R.id.nav_about_programmer:
                            navigator.applyCommands(new Command[]{new Replace(new Screens.DeveloperFragmentScreen())});
                            break;
                        case R.id.nav_feedback:
                            navigator.applyCommands(new Command[]{new Replace(new Screens.FeedbackFragmentScreen())});
                            break;
                    }
                    drawer.closeDrawers();
                    return true;
                });
    }

    public void setToolbarTitle(int title) {
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    public void showHamburgerIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.setNavigationOnClickListener((v) -> drawer.openDrawer(GravityCompat.START));
//            toolbar.setNavigationOnClickListener(toggleListener);
        }
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void showBackIcon() {
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener((v) -> navigator.applyCommands(new Command[]{new Back()}));
        }
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
