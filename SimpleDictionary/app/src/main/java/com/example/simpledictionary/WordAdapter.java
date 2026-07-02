package com.example.simpledictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private ArrayList<WordItem> wordList;

    // interface للضغط على العنصر (تعديل)
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public WordAdapter(ArrayList<WordItem> wordList, OnItemClickListener listener) {
        this.wordList = wordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        WordItem item = wordList.get(position);

        holder.txtWord.setText(item.getWord());
        holder.txtMeaning.setText(item.getMeaning());

        // click = تعديل
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(position);
        });

        // long click = حذف
        holder.itemView.setOnLongClickListener(v -> {

            wordList.remove(position);
            notifyDataSetChanged();

            android.widget.Toast.makeText(
                    v.getContext(),
                    "Word Deleted",
                    android.widget.Toast.LENGTH_SHORT
            ).show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {

        TextView txtWord, txtMeaning;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            txtWord = itemView.findViewById(R.id.txtWord);
            txtMeaning = itemView.findViewById(R.id.txtMeaning);
        }
    }
}