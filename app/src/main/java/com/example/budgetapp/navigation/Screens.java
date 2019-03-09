package com.example.budgetapp.navigation;

import android.support.v4.app.Fragment;
import com.example.budgetapp.ui.fragment.TransactionFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class MainScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return TransactionFragment.newInstance();
        }
    }
}
