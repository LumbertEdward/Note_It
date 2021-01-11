package com.example.noteit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.noteit.Fragments.EditFragment;
import com.example.noteit.Fragments.NewNoteFragment;
import com.example.noteit.Fragments.NotesFragment;
import com.example.noteit.Fragments.SearchFragment;
import com.example.noteit.Fragments.SettingsFragment;
import com.example.noteit.Fragments.ToDoFragment;
import com.example.noteit.Fragments.ViewToDoFragment;
import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.FragmentsInit;
import com.example.noteit.ModelClasses.Notes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectedItemInterface {
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CoordinatorLayout coordinatorLayout;
    private NotesFragment notesFragment;
    private EditFragment editFragment;
    private SearchFragment searchFragment;
    private ToDoFragment toDoFragment;
    private SettingsFragment settingsFragment;
    private NewNoteFragment newNoteFragment;
    private ViewToDoFragment viewToDoFragment;

    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<FragmentsInit> fragmentsInit = new ArrayList<>();
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setNavigationBarColor(getColor(R.color.back));
        }
        init();
        initFragment();
        setNavigations();
    }

    private void initFragment() {
        if (notesFragment == null){
            notesFragment = new NotesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame, notesFragment, "Notes");
            fragmentTransaction.commit();
            tags.add("Notes");
            fragmentsInit.add(new FragmentsInit(notesFragment, "Notes"));
        }
        else {
            tags.remove("Notes");
            tags.add("Notes");
        }

    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorTool);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    private void setNavigations() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.notesBot:
                        if (notesFragment == null){
                            notesFragment = new NotesFragment();
                            fragmentTransaction.add(R.id.frame, notesFragment, "Notes");
                            fragmentTransaction.commit();
                            tags.add("Notes");
                            fragmentsInit.add(new FragmentsInit(notesFragment, "Notes"));
                        }
                        else {
                            tags.remove("Notes");
                            tags.add("Notes");
                        }
                        setVisibility("Notes");
                        return true;
                    case R.id.todoBot:
                        if (toDoFragment == null){
                            toDoFragment = new ToDoFragment();
                            fragmentTransaction.add(R.id.frame, toDoFragment, "ToDo");
                            fragmentTransaction.commit();
                            tags.add("ToDo");
                            fragmentsInit.add(new FragmentsInit(toDoFragment, "ToDo"));
                        }
                        else {
                            tags.remove("ToDo");
                            tags.add("ToDo");
                        }
                        setVisibility("ToDo");
                        return true;
                    case R.id.settingsBot:
                        if (settingsFragment == null){
                            settingsFragment = new SettingsFragment();
                            fragmentTransaction.add(R.id.frame, settingsFragment, "Settings");
                            fragmentTransaction.commit();
                            tags.add("Settings");
                            fragmentsInit.add(new FragmentsInit(settingsFragment, "Settings"));
                        }
                        else {
                            tags.remove("Settings");
                            tags.add("Settings");
                        }
                        setVisibility("Settings");
                        return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.notesNav:
                        if (notesFragment == null){
                            notesFragment = new NotesFragment();
                            fragmentTransaction.add(R.id.frame, notesFragment, "Notes");
                            fragmentTransaction.commit();
                            tags.add("Notes");
                            fragmentsInit.add(new FragmentsInit(notesFragment, "Notes"));
                        }
                        else {
                            tags.remove("Notes");
                            tags.add("Notes");
                        }
                        setVisibility("Notes");
                        break;
                    case R.id.todoNav:
                        if (toDoFragment == null){
                            toDoFragment = new ToDoFragment();
                            fragmentTransaction.add(R.id.frame, toDoFragment, "ToDo");
                            fragmentTransaction.commit();
                            tags.add("ToDo");
                            fragmentsInit.add(new FragmentsInit(toDoFragment, "ToDo"));
                        }
                        else {
                            tags.remove("ToDo");
                            tags.add("ToDo");
                        }
                        setVisibility("ToDo");
                        break;
                    case R.id.settingsNav:
                        if (settingsFragment == null){
                            settingsFragment = new SettingsFragment();
                            fragmentTransaction.add(R.id.frame, settingsFragment, "Settings");
                            fragmentTransaction.commit();
                            tags.add("Settings");
                            fragmentsInit.add(new FragmentsInit(settingsFragment, "Settings"));
                        }
                        else {
                            tags.remove("Settings");
                            tags.add("Settings");
                        }
                        setVisibility("Settings");
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void setVisibility(String tag){
        if (tag.equals("Notes")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if(tag.equals("Edit")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        else if(tag.equals("Search")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        else if(tag.equals("New")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        else if(tag.equals("Settings")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if(tag.equals("ToDo")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if(tag.equals("ViewToDo")){
            showBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        for (int i = 0; i < fragmentsInit.size(); i++){
            if (tag.equals(fragmentsInit.get(i).getTag())){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(fragmentsInit.get(i).getFragment());
                fragmentTransaction.commit();
            }
            else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(fragmentsInit.get(i).getFragment());
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        int total = tags.size();
        if (total > 1){
            String top = tags.get(total - 1);
            String bot = tags.get(total - 2);
            setVisibility(bot);
            tags.remove(top);
            mCount = 0;
        }
        else if (total == 1){
            String top = tags.get(total - 1);
            if (top == "Notes"){
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                mCount++;
            }
            else {
                mCount++;
            }
        }
        if (mCount > 2){
            super.onBackPressed();
        }
    }

    public void showBottom(){
        if (bottomNavigationView != null){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }

    }
    public void hideBottom(){
        if (bottomNavigationView != null){
            bottomNavigationView.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getNotes(Notes notes) {
        if (editFragment != null){
            getSupportFragmentManager().beginTransaction().remove(editFragment).commitAllowingStateLoss();
        }
        editFragment = new EditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Note", notes);
        editFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, editFragment, "Edit");
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.commit();
        tags.add("Edit");
        fragmentsInit.add(new FragmentsInit(editFragment, "Edit"));
        setVisibility("Edit");
    }
    @Override
    public void addNote() {
        if (editFragment == null){
            editFragment = new EditFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame, editFragment, "Edit");
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.commit();
            tags.add("Edit");
            fragmentsInit.add(new FragmentsInit(editFragment, "Edit"));
        }
        else {
            tags.remove("Edit");
            tags.add("Edit");
        }
        setVisibility("Edit");
    }

    @Override
    public void performSearch() {
        if (searchFragment == null){
            searchFragment = new SearchFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame, searchFragment, "Search");
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.commit();
            tags.add("Search");
            fragmentsInit.add(new FragmentsInit(searchFragment, "Search"));
        }
        else {
            tags.remove("Search");
            tags.add("Search");
        }
        setVisibility("Search");
    }

    @Override
    public void newNote() {
        if (newNoteFragment == null){
            newNoteFragment = new NewNoteFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame, newNoteFragment, "New");
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.commit();
            tags.add("New");
            fragmentsInit.add(new FragmentsInit(newNoteFragment, "New"));
        }
        else {
            tags.remove("New");
            tags.add("New");
        }
        setVisibility("New");
    }

    @Override
    public void showToDoList() {
        if (viewToDoFragment == null){
            viewToDoFragment = new ViewToDoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame, viewToDoFragment, "ViewToDo");
            fragmentTransaction.commit();
            tags.add("ViewToDo");
            fragmentsInit.add(new FragmentsInit(viewToDoFragment, "ViewToDo"));
        }
        else {
            tags.remove("ViewToDo");
            tags.add("ViewToDo");
        }
        setVisibility("ViewToDo");
    }


}