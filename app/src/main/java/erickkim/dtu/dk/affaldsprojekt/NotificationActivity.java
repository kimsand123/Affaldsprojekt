package erickkim.dtu.dk.affaldsprojekt;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerViewNotifications;
    ProgressBar progressBar;
    ArrayList<Data_DTO_Notification> notifications;
    FirebaseDatabase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerViewNotifications = (RecyclerView) findViewById(R.id.notifications_recycleview);
        progressBar = findViewById(R.id.notification_progressbar);

        notifications = new ArrayList<Data_DTO_Notification>();
        mref = FirebaseDatabase.getInstance();

        final LinearLayoutManager layoutManagerNotifications = new LinearLayoutManager(this);

        mref.getReference().child("messages").child(Data_Controller.getInstance().getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                notifications.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Data_DTO_Notification notification = new Data_DTO_Notification();
                    notification.setTitle(snapshot.child("title").getValue().toString());
                    notification.setText(snapshot.child("body").getValue().toString());
                    Date newDate = new Date(Long.parseLong(snapshot.getKey()));
                    notification.setDate(newDate);
                    notification.setStatus(Integer.parseInt(String.valueOf(snapshot.child("status").getValue())));

                    notifications.add(notification);
                }
                notificationRecycleViewAdapter notificationAdapter = new notificationRecycleViewAdapter(notifications);
                recyclerViewNotifications.setLayoutManager(layoutManagerNotifications);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewNotifications.getContext(),
                        layoutManagerNotifications.getOrientation());
                recyclerViewNotifications.addItemDecoration(dividerItemDecoration);
                recyclerViewNotifications.setAdapter(notificationAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        // ArrayAdapter<Data_DTO_Notification> adapter = new ArrayAdapter<Data_DTO_Notification>(this,
        //         android.R.layout.simple_list_item_1, myStringArray);
    }
}
