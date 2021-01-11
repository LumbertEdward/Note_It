package com.example.noteit.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.noteit.Adapters.NotesAdapter;
import com.example.noteit.DatabaseClasses.NotesSqliteDatabase;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NotesFragment extends Fragment {
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private NotesAdapter notesAdapter;
    private RelativeLayout search;
    private ImageView layout;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SelectedItemInterface selectedItemInterface;
    private NotesSqliteDatabase notesSqliteDatabase;
    private ArrayList<Notes> notesArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        notesSqliteDatabase = new NotesSqliteDatabase(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerNotes);
        search = (RelativeLayout) v.findViewById(R.id.relSearch);
        layout = (ImageView) v.findViewById(R.id.layoutNotes);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.floatAdd);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRef);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesAdapter = new NotesAdapter(getContext());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.newNote();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.performSearch();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notesAdapter.clear();
                        notesArrayList = notesSqliteDatabase.getNotes();
                        notesAdapter.addAll(notesArrayList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        getData();
        return v;
    }

    private void getData() {
        notesArrayList = notesSqliteDatabase.getNotes();
        if (!notesArrayList.isEmpty()){
            notesAdapter.addAll(notesArrayList);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(notesAdapter);
        }
        else {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}