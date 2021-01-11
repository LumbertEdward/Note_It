package com.example.noteit.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.noteit.Adapters.NotesAdapter;
import com.example.noteit.Adapters.SearchAdapter;
import com.example.noteit.DatabaseClasses.NotesSqliteDatabase;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ImageView back;
    private ImageView clear;
    private EditText search;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchAdapter searchAdapter;
    private ArrayList<Notes> notesArrayList;
    private NotesSqliteDatabase notesSqliteDatabase;
    private SelectedItemInterface selectedItemInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        notesSqliteDatabase = new NotesSqliteDatabase(getContext());
        back = (ImageView) v.findViewById(R.id.back_search);
        clear = (ImageView) v.findViewById(R.id.clear_search);
        search = (EditText) v.findViewById(R.id.editSearch);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_search);
        linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.onBackPressed();
            }
        });
        getData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    private void getData() {
        notesArrayList = notesSqliteDatabase.getNotes();
        searchAdapter = new SearchAdapter(notesArrayList, getContext());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }
}