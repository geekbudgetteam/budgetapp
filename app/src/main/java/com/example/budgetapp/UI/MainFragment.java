package com.example.budgetapp.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.budgetapp.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    MainFragmentTransactionViewAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // data to populate the RecyclerView with
        ArrayList<String> transactions = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            transactions.add("Транзакция" + i);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.transactionsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new MainFragmentTransactionViewAdapter(this.getActivity(), transactions);
        recyclerView.setAdapter(adapter);
    }
}
