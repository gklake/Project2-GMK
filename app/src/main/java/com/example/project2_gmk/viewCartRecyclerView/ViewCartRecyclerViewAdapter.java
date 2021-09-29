package com.example.project2_gmk.viewCartRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project2_gmk.R;

import java.util.ArrayList;

public class ViewCartRecyclerViewAdapter extends RecyclerView.Adapter<ViewCartRecyclerViewAdapter.ItemViewHolder> {

    private ArrayList<ViewCartRecyclerView> itemArrayList;
    private ViewCartClickListener viewCartClickListener;

    public interface ViewCartClickListener{
        void onRemoveClick(int position);
    }

    public void setViewCartClickListener(ViewCartClickListener listener){
        viewCartClickListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewItemName;
        public TextView textViewItemDescription;
        public TextView textViewItemPrice;
        public ImageView deleteTrashCan;


        public ItemViewHolder(@NonNull View itemView, ViewCartClickListener listener) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewCartName);
            textViewItemDescription = itemView.findViewById(R.id.textViewCartDescription);
            textViewItemPrice = itemView.findViewById(R.id.textViewCartPrice);
            deleteTrashCan = itemView.findViewById(R.id.cartTrashCan);

            deleteTrashCan.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onRemoveClick(position);
                    }
                }
            });
        }
    }

    public ViewCartRecyclerViewAdapter(ArrayList<ViewCartRecyclerView> cartList){
        itemArrayList = cartList;
    }

    @NonNull
    @Override
    public ViewCartRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_change, parent, false);
        return new ItemViewHolder(v, viewCartClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartRecyclerViewAdapter.ItemViewHolder holder, int position) {
        ViewCartRecyclerView currentItem = itemArrayList.get(position);
        holder.textViewItemName.setText(currentItem.getName());
        holder.textViewItemDescription.setText(currentItem.getDescription());
        holder.textViewItemPrice.setText(Double.toString(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


}
