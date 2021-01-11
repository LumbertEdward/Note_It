package com.example.noteit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteit.Interfaces.SelectedItemInterface;
import com.example.noteit.ModelClasses.Notes;
import com.example.noteit.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Notes> notesArrayList;
    private Context context;
    private SelectedItemInterface selectedItemInterface;
    private ArrayList<Notes> notesFiltered;

    public SearchAdapter(ArrayList<Notes> notesArrayList, Context context) {
        this.notesArrayList = notesArrayList;
        this.context = context;
        notesFiltered = new ArrayList<>(notesArrayList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(notesArrayList.get(position).getTitle());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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
                    notesArrayList1.addAll(notesFiltered);
                }
                else {
                    String text = constraint.toString().toLowerCase().trim();
                    for (Notes notes : notesFiltered){
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
        private RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_Search);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rel_search);
        }
    }
}
