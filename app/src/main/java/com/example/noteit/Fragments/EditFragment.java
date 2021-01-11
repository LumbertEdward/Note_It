package com.example.noteit.Fragments;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.Image;
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

import com.example.noteit.Adapters.NotesAdapter;
import com.example.noteit.DatabaseClasses.NotesSqliteDatabase;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditFragment extends Fragment {
    private EditText title;
    private EditText note;
    private TextView time;
    private ImageView back;
    private ImageView undo;
    private ImageView redo;
    private ImageView save;
    private ImageView delete;
    private Notes notes;
    private SelectedItemInterface selectedItemInterface;
    private NotesSqliteDatabase notesSqliteDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            notes = bundle.getParcelable("Note");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        notesSqliteDatabase = new NotesSqliteDatabase(getContext());
        title = (EditText) v.findViewById(R.id.titleEdit);
        note = (EditText) v.findViewById(R.id.notesEdit);
        time = (TextView) v.findViewById(R.id.timeEdit);
        back = (ImageView) v.findViewById(R.id.backEdit);
        undo = (ImageView) v.findViewById(R.id.undo);
        redo = (ImageView) v.findViewById(R.id.redo);
        save = (ImageView) v.findViewById(R.id.save);
        delete = (ImageView) v.findViewById(R.id.deleteEdit);
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
        init();
        return v;
    }

    private void deleteItem() {
        String id = notes.getId();
        notesSqliteDatabase.deleteNote(id);
        selectedItemInterface.onBackPressed();
    }

    private void saveData() {
        String name = title.getText().toString().trim();
        String body = note.getText().toString().trim();
        String id = notes.getId();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String timeN = simpleDateFormat.format(c);
        if (TextUtils.isEmpty(name)){
            notesSqliteDatabase.updateNote("", body, timeN, id);
            selectedItemInterface.onBackPressed();
        }
        else {
            notesSqliteDatabase.updateNote(name, body, timeN, id);
            selectedItemInterface.onBackPressed();
        }
    }

    private void init() {
        if (notes.getTitle() != null){
            title.setText(notes.getTitle());
            note.setText(notes.getNote());
            time.setText(notes.getTime());
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedItemInterface = (SelectedItemInterface) context;
    }
}