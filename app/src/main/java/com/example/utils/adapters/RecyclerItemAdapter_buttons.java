package com.example.utils.adapters;

// RecyclerItemAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.utils.RecyclerItem;

import java.util.List;

public class RecyclerItemAdapter_buttons extends RecyclerView.Adapter<RecyclerItemAdapter_buttons.ViewHolder> {

    private List<RecyclerItem> recyclerItemList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerItemAdapter_buttons(List<RecyclerItem> recyclerItemList) {
        this.recyclerItemList = recyclerItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_buttons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerItem item = recyclerItemList.get(position);
        holder.textView1.setText(item.getText1());
        holder.textView2.setText(item.getText2());
        holder.imageCheck.setVisibility(View.VISIBLE);
        holder.imageCheck.setImageResource(R.drawable.check);
        holder.imageDeny.setVisibility(View.VISIBLE);
        holder.imageDeny.setImageResource(R.drawable.deny);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageCheck;
        ImageView imageDeny;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageCheck = itemView.findViewById(R.id.button_accept);
            imageDeny = itemView.findViewById(R.id.button_deny);
        }
    }
}
