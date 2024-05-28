package com.example.utils;

// RecyclerItemAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.RecyclerItemViewHolder> {

    private List<RecyclerItem> recyclerItemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerItemAdapter(List<RecyclerItem> recyclerItemList) {
        this.recyclerItemList = recyclerItemList;
    }

    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new RecyclerItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder holder, int position) {
        RecyclerItem currentRecyclerItem = recyclerItemList.get(position);
        holder.textView1.setText(currentRecyclerItem.getText1());
        holder.textView2.setText(currentRecyclerItem.getText2());
    }

    @Override
    public int getItemCount() {
        return recyclerItemList.size();
    }

    public static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;

        public RecyclerItemViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

