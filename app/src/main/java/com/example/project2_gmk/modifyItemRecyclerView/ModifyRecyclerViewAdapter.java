package com.example.project2_gmk.modifyItemRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_gmk.R;

import java.util.ArrayList;

public class ModifyRecyclerViewAdapter extends RecyclerView.Adapter<ModifyRecyclerViewAdapter.ItemViewHolder> {

    private ArrayList<ModifyItemRecyclerView> itemArrayList;
    private ModifyItemClickListener modifyItemClickListener;

    public interface ModifyItemClickListener{
        void onModifyClick(int position);
    }

    public void setModifyItemClickListener(ModifyItemClickListener listener){
        modifyItemClickListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewOldItemName;
        public TextView textViewOldItemDescription;
        public ImageView pencil;

        public ItemViewHolder(@NonNull View itemView, ModifyItemClickListener listener) {
            super(itemView);
            textViewOldItemName = itemView.findViewById(R.id.textViewOldItemName);
            textViewOldItemDescription = itemView.findViewById(R.id.textViewOldItemDescription);
            pencil = itemView.findViewById(R.id.imagePencil);

            pencil.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onModifyClick(position);
                    }
                }
            });
        }
    }

    public ModifyRecyclerViewAdapter(ArrayList<ModifyItemRecyclerView> itemList){
        itemArrayList = itemList;
    }

    @NonNull
    @Override
    public ModifyRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modify, parent, false);
        return new ItemViewHolder(v,modifyItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ModifyRecyclerViewAdapter.ItemViewHolder holder, int position) {
        ModifyItemRecyclerView currentItem = itemArrayList.get(position);
        holder.textViewOldItemName.setText(currentItem.getItemName());
        holder.textViewOldItemDescription.setText(currentItem.getItemDescription());

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


}
