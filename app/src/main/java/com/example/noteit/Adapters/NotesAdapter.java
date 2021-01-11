package com.example.noteit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Notes> notesArrayList;
    private SelectedItemInterface selectedItemInterface;
    private Context context;
    private ArrayList<Notes> filteredNotes;

    public NotesAdapter(Context context) {
        this.context = context;
        notesArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.note_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.MyViewHolder holder, int position) {
        holder.title.setText(notesArrayList.get(position).getTitle());
        holder.note.setText(notesArrayList.get(position).getNote());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemInterface.getNotes(notesArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        selectedItemInterface = (SelectedItemInterface) context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Notes> notesArrayList1 = new ArrayList<>();
                if (constraint == null || constraint.length() == 0){
                    notesArrayList1.addAll(filteredNotes);
                }
                else {
                    String text = constraint.toString().toLowerCase().trim();
                    for (Notes notes : filteredNotes){
                        if (notes.getTitle().toLowerCase().contains(text)){
                            notesArrayList1.add(notes);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = notesArrayList1;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notesArrayList.clear();
                notesArrayList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView note;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleItem);
            note = (TextView) itemView.findViewById(R.id.noteItem);
            cardView = (CardView) itemView.findViewById(R.id.cardItem);
        }
    }

    public void clear(){
        notesArrayList.clear();
        notifyDataSetChanged();
    }
    public void addAll(ArrayList<Notes> notesArrayList1){
        for (Notes notes1 : notesArrayList1){
            notesArrayList.add(notes1);
            notifyDataSetChanged();
        }
        filteredNotes = new ArrayList<>(notesArrayList);
        notifyDataSetChanged();
    }
}
