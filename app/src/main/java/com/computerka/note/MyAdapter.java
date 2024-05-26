package com.computerka.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    List<NoteModel> notes;
    List<NoteModel> filteredList;


    public MyAdapter() {

    }

    public void setFilteredList(List<NoteModel> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }


    public MyAdapter(Context context, List<NoteModel> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        NoteModel model = notes.get(position);
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewTitle.setText(model.getTitle());


        holder.note_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                  Toast.makeText(context, "You clicked "+model.getId(), Toast.LENGTH_SHORT).show();
                int id = model.getId();
                String title = model.getTitle();
                String description = model.getDescription();

                Intent intent = new Intent(context, ViewNote.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("description", description);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;
        CardView note_item_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDescription = itemView.findViewById(R.id.textview_description);

            note_item_card = itemView.findViewById(R.id.note_item_card);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,ViewNote.class));
                }
            }); */
        }
    }


}
