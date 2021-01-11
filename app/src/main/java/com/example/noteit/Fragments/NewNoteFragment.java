package com.example.noteit.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteit.DatabaseClasses.NotesSqliteDatabase;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NewNoteFragment extends Fragment {
    private EditText title;
    private EditText note;
    private TextView time;
    private ImageView back;
    private ImageView undo;
    private ImageView redo;
    private ImageView save;
    private Notes notes;
    private SelectedItemInterface selectedItemInterface;
    private NotesSqliteDatabase notesSqliteDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_note, container, false);
        notesSqliteDatabase = new NotesSqliteDatabase(getContext());
        title = (EditText) v.findViewById(R.id.titleNew);
        note = (EditText) v.findViewById(R.id.notesNew);
        time = (TextView) v.findViewById(R.id.timeNew);
        back = (ImageView) v.findViewById(R.id.backNew);
        undo = (ImageView) v.findViewById(R.id.undoNew);
        redo = (ImageView) v.findViewById(R.id.redoNew);
        save = (ImageView) v.findViewById(R.id.saveNew);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.onBackPressed();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        return v;
    }

    private void saveData() {
        String name = title.getText().toString();
        String body = note.getText().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String time = simpleDateFormat.format(c);
        if (TextUtils.isEmpty(name)){
            boolean confirmAdded = notesSqliteDatabase.addNote("", body, time);
            if (confirmAdded == true){
                selectedItemInterface.onBackPressed();
            }
            else {
                Toast.makeText(getContext(), "Not Added", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            boolean confirmAdded = notesSqliteDatabase.addNote(name, body, time);
            if (confirmAdded == true){
                selectedItemInterface.onBackPressed();
            }
            else {
                Toast.makeText(getContext(), "Not Added", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }
}