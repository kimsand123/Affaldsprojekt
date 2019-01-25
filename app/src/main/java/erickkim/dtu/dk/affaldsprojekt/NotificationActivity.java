package erickkim.dtu.dk.affaldsprojekt;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

import erickkim.dtu.dk.affaldsprojekt.interfaces.itemClickListener;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_Notification;

public class NotificationActivity extends AppCompatActivity implements itemClickListener {

    private RecyclerView recyclerViewNotifications;
    private ProgressBar progressBar;
    private ArrayList<Data_DTO_Notification> notifications;
    private FirebaseDatabase mref;
    private Vibrator v;
    private notificationRecycleViewAdapter notificationAdapter;
    private LinearLayoutManager layoutManagerNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerViewNotifications = (RecyclerView) findViewById(R.id.notifications_recycleview);
        progressBar = findViewById(R.id.notification_progressbar);

        notifications = new ArrayList<Data_DTO_Notification>();
        Data_DTO_Notification tempData = new Data_DTO_Notification();
        tempData.setStatus(0);
        tempData.setText("");
        tempData.setTitle("");
        tempData.setDate(new Date(111110));
        notifications.add(tempData);
        mref = FirebaseDatabase.getInstance();
        v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        // Initialize RecView
        notificationAdapter = new notificationRecycleViewAdapter(notifications);
        notificationAdapter.setClickListener(this);
        layoutManagerNotifications = new LinearLayoutManager(this);
        recyclerViewNotifications.setLayoutManager(layoutManagerNotifications);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewNotifications.getContext(),
                layoutManagerNotifications.getOrientation());
        recyclerViewNotifications.addItemDecoration(dividerItemDecoration);
        recyclerViewNotifications.setAdapter(notificationAdapter);

        progressBar.setVisibility(View.VISIBLE);
        mref.getReference().child("messages").child(Data_Controller.getInstance().getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notifications.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Data_DTO_Notification notification = new Data_DTO_Notification();
                    notification.setTitle(snapshot.child("title").getValue().toString());
                    notification.setText(snapshot.child("body").getValue().toString());
                    Date newDate = new Date(Long.parseLong(snapshot.getKey()));
                    notification.setDate(newDate);
                    int status = Integer.valueOf(String.valueOf(snapshot.child("status").getValue()));
                    notification.setStatus(status);
                    notifications.add(notification);
                }
                Collections.sort(notifications);
                Collections.reverse(notifications);
                progressBar.setVisibility(View.INVISIBLE);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        if (notifications.get(position).getStatus() == 0) {
            if (Build.VERSION.SDK_INT>=26)
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            else
                v.vibrate(500);
            long date = notifications.get(position).getDate().getTime();
            progressBar.setVisibility(View.VISIBLE);
            mref.getReference().child("messages").child(Data_Controller.getInstance().getUserId()).child(String.valueOf(date)).child("status").setValue(1);
        }
    }
}
