package com.example.noteit.Interfaces;

import com.example.noteit.ModelClasses.Notes;

public interface SelectedItemInterface {
    void getNotes(Notes notes);
    void onBackPressed();
    void addNote();
    void performSearch();
    void newNote();
    void showToDoList();
}
