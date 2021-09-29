package com.example.project2_gmk.deleteItemRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_gmk.R;

import java.util.ArrayList;

public class DeleteRecyclerViewAdapter extends RecyclerView.Adapter<DeleteRecyclerViewAdapter.ItemViewHolder>{

    private ArrayList<DeleteItemRecyclerView> itemArrayList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItemName;
        public TextView textViewItemDescription;
        public ImageView trashCan;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemDescription = itemView.findViewById(R.id.textViewItemDescription);
            trashCan = itemView.findViewById(R.id.imageTrashCan);

            trashCan.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public DeleteRecyclerViewAdapter(ArrayList<DeleteItemRecyclerView> itemList){
        itemArrayList = itemList;
    }

    @NonNull
    @Override
    public DeleteRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete, parent, false);
        return new ItemViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteRecyclerViewAdapter.ItemViewHolder holder, int position) {
        DeleteItemRecyclerView currentItem = itemArrayList.get(position);
        holder.textViewItemName.setText(currentItem.getItemName());
        holder.textViewItemDescription.setText(currentItem.getItemDescription());

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

}
