package com.example.noteit.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.noteit.Adapters.ToDoAdapter;
import com.example.noteit.Classes.AllConstants;
import com.example.noteit.DatabaseClasses.ToDoDatabase;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.ToDo;
import com.example.noteit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class ViewToDoFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private ImageView back;
    private SelectedItemInterface selectedItemInterface;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ToDoAdapter toDoAdapter;
    private ToDoDatabase toDoDatabase;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ToDo> toDoArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_to_do, container, false);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.floatToDo);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerToDo);
        linearLayoutManager = new LinearLayoutManager(getContext());
        toDoAdapter = new ToDoAdapter(getContext());
        recyclerView.setAdapter(toDoAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        toDoDatabase = new ToDoDatabase(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeToDo);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toDoAdapter.clear();
                toDoArrayList = toDoDatabase.getToDo();
                toDoAdapter.addAll(toDoArrayList);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        back = (ImageView) v.findViewById(R.id.backToDo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.onBackPressed();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                View vA = layoutInflater.inflate(R.layout.alert_item, null);
                EditText editText = (EditText) vA.findViewById(R.id.editTo);
                Button button = (Button) vA.findViewById(R.id.btnDone);
                builder.setView(vA);
                AlertDialog alertDialog = builder.create();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        String title = editText.getText().toString().trim();
                        Random random = new Random();
                        int val = random.nextInt(100);
                        String id = String.valueOf(val);
                        if (TextUtils.isEmpty(title)){

                        }
                        else {
                            ToDo toDo = new ToDo();
                            toDo.setTitle(title);
                            toDo.setId(id);
                            toDo.setStatus(AllConstants.CATEGORY_ALL);
                            toDo.setCategory(AllConstants.OPEN);
                            boolean out = toDoDatabase.addToDo(toDo);
                            if (out){
                                Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                alertDialog.show();
            }
        });
        getData();
        return v;
    }

    private void getData() {
        toDoArrayList = toDoDatabase.getToDo();
        if (!toDoArrayList.isEmpty()){
            toDoAdapter.addAll(toDoArrayList);
        }
        else {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }
}