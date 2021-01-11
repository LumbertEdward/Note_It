package com.example.noteit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteit.Classes.AllConstants;
import com.example.noteit.DatabaseClasses.ToDoDatabase;
import com.example.noteit.ModelClasses.ToDo;
import com.example.noteit.R;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> implements Filterable {
    private ArrayList<ToDo> toDoArrayList;
    private Context context;
    private ArrayList<ToDo> filteredToDo;
    private ToDoDatabase toDoDatabase;

    public ToDoAdapter(Context context) {
        this.context = context;
        toDoArrayList = new ArrayList<>();
        toDoDatabase = new ToDoDatabase(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.todoitem_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(toDoArrayList.get(position).getTitle());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radioButton.setChecked(true);
                toDoDatabase.updateData(toDoArrayList.get(position).getId(), AllConstants.CLOSE);
                holder.imageView.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
        holder.imageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return toDoArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<ToDo> arrayList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0){
                    arrayList.addAll(filteredToDo);
                }
                String val = constraint.toString().toLowerCase().trim();
                for (ToDo toDo : filteredToDo){
                    if (toDo.getTitle().toLowerCase().equals(val)){
                        arrayList.add(toDo);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                toDoArrayList.clear();
                toDoArrayList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RadioButton radioButton;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nameToDo);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioToDo);
            imageView = (ImageView) itemView.findViewById(R.id.imgDone);
        }
    }

    public void clear(){
        toDoArrayList.clear();
        notifyDataSetChanged();
    }
    public void addAll(ArrayList<ToDo> toDos){
        for (ToDo toDo : toDos){
            toDoArrayList.add(toDo);
            notifyDataSetChanged();
        }
        filteredToDo = new ArrayList<>(toDos);
        notifyDataSetChanged();
    }
}
