package com.example.budgetapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.navigation.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity implements ChangeFragmentTitleListener {

    private Navigator navigator = new SupportAppNavigator(this, R.id.fl_master);

    @Inject
    NavigatorHolder navigatorHolder;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private View.OnClickListener toggleListener;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarNavigation();
        navigationView = findViewById(R.id.nav_view);
        setNavigationItemSelectedListener();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_master);
        if (savedInstanceState == null && fragment == null) {
            navigator.applyCommands(new Command[]{new Replace(new Screens.TransactionsFragmentScreen())});
        }
    }

    private void setToolbarNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggleListener = toggle.getToolbarNavigationClickListener();
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportFragmentManager().addOnBackStackChangedListener(() -> {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    toggle.setDrawerIndicatorEnabled(false);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    toggle.setToolbarNavigationClickListener((v) -> navigator.applyCommands(new Command[]{new Back()}));
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    toggle.setToolbarNavigationClickListener(toggleListener);
                }
            });
        }
    }

    public void setNavigationItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.main_fragment:
                            navigator.applyCommands(new Command[]{new Replace(new Screens.TransactionsFragmentScreen())});
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

    @Override
    public void setToolbarTitle(int title) {
        toolbar.post(() -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        });
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
