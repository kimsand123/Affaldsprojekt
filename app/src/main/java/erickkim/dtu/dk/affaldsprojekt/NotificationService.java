package erickkim.dtu.dk.affaldsprojekt;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;

import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;

import androidx.annotation.Nullable;
import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.Data_DTO_messages;

public class NotificationService extends Service {
    final String TAG = getClass().getName();
    FirebaseDatabase mref = FirebaseDatabase.getInstance();


    @Override
    public ComponentName startService(Intent service) {

        String userId=Data_Controller.getInstance().getUserId();

        FirebaseDatabase.getInstance().getReference().child("message").child(userId)
                .addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numberOfMessages=0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Data_DTO_messages messageData = ds.getValue(Data_DTO_messages.class);
                    if(messageData.getState().equals("0")){
                        numberOfMessages=numberOfMessages+1;
                    }
                }

                try {
                    Badges.setBadge(getApplicationContext(), numberOfMessages);
                } catch (BadgesNotSupportedException badgesNotSupportedException) {
                    Log.d(TAG, badgesNotSupportedException.getMessage());
                }


                try {
                    Badges.removeBadge(getApplicationContext());
                } catch (BadgesNotSupportedException badgesNotSupportedException) {
                    Log.d(TAG, badgesNotSupportedException.getMessage());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Failed to read value.", error.getMessage());
            }
        });


        return super.startService(service);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){

    }

    @Override
    public void onDestroy(){

    }

    public int onStartCommand(Intent intent, int flags, int startId){

        //Check for on datachange

        return START_STICKY;
    }
}
