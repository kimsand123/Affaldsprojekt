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

import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_Notification;

// Create basic adapter extending from recyclerview.adapter, specifying our custom viewholder.
public class notificationRecycleViewAdapter extends RecyclerView.Adapter<notificationRecycleViewAdapter.ViewHolder> {

    private itemClickListener mClickListener;

    // Holds references to each view inside data.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView date;
        public TextView body;
        public ImageView status;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.notification_entry_title);
            date = (TextView) itemView.findViewById(R.id.notification_entry_date);
            body = (TextView) itemView.findViewById(R.id.notification_entry_body);
            status = (ImageView) itemView.findViewById(R.id.notification_entry_imageview);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(itemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Store notifications in a list
    private List<Data_DTO_Notification> notificationList;

    public notificationRecycleViewAdapter(List<Data_DTO_Notification> newList) {
        notificationList = newList;
    }

    @NonNull
    @Override
    public notificationRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notificationView = inflater.inflate(R.layout.notificationentrylayout, parent, false);

        ViewHolder viewHolder = new notificationRecycleViewAdapter.ViewHolder(notificationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Data_DTO_Notification notification = notificationList.get(position);

        viewHolder.title.setText(notification.getTitle());
        viewHolder.body.setText(notification.getText());
        if (notification.getStatus() == 1) {
            viewHolder.status.setVisibility(View.INVISIBLE);
        } else if (notification.getStatus() == 0){
            viewHolder.status.setVisibility(View.VISIBLE);
        }
        String date = notification.getFormatDate();
        viewHolder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

}
