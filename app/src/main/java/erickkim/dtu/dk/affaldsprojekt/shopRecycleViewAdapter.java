package erickkim.dtu.dk.affaldsprojekt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import erickkim.dtu.dk.affaldsprojekt.interfaces.itemClickListener;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_shopEntry;

public class shopRecycleViewAdapter extends RecyclerView.Adapter<shopRecycleViewAdapter.ViewHolder> {

    private List<Data_DTO_shopEntry> mData;
    private LayoutInflater mInflater;
    private itemClickListener mClickListener;

    // Data is passed into constructor.
    public shopRecycleViewAdapter(Context context, List<Data_DTO_shopEntry> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflates the row layout from XML
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.coinshoplayout, parent, false);
        return new ViewHolder(view);
    }

    // Data is binded to the views in each entry.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = mData.get(position).title;
        String description = mData.get(position).description;
        int image = mData.get(position).image;
        String price = String.valueOf(mData.get(position).price);
        holder.shopEntryTitle.setText(title);
        holder.shopEntryDescription.setText(description);
        holder.shopEntryImage.setImageResource(image);
        holder.shopEntryPrice.setText(price);
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Stores and recycles views as they are scrolled off in the list.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shopEntryTitle;
        TextView shopEntryDescription;
        ImageView shopEntryImage;
        TextView shopEntryPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopEntryTitle = itemView.findViewById(R.id.shop_entry_title);
            shopEntryDescription = itemView.findViewById(R.id.shop_entry_description);
            shopEntryImage = itemView.findViewById(R.id.shop_entry_image);
            shopEntryPrice = itemView.findViewById(R.id.shop_entry_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Gets item at specific location
    Data_DTO_shopEntry getItem (int id) {
        return mData.get(id);
    }

    // Catch click events
    void setClickListener(itemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
