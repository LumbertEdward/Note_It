package com.example.noteit.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.R;

public class ToDoFragment extends Fragment {
    private LinearLayout all;
    private LinearLayout personal;
    private LinearLayout work;
    private LinearLayout others;
    private SelectedItemInterface selectedItemInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_to_do, container, false);
        all = (LinearLayout) v.findViewById(R.id.linearAll);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.showToDoList();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }
}