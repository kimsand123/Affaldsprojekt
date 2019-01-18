package erickkim.dtu.dk.affaldsprojekt;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String userId = Data_Controller.getInstance().getUserId();
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
        mref.child("messages").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int numberOfMessages = 0;
                        Data_DTO_messages messageData;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            messageData = ds.getValue(Data_DTO_messages.class);
                            if (messageData.getState() == 0) {
                                numberOfMessages = numberOfMessages + 1;
                            }
                        }

                        if(ShortcutBadger.isBadgeCounterSupported(getApplicationContext())) {
                            //ShortcutBadger.applyCount(.getDrawable(getApplicationContext(), R.drawable.ic_launcher_foreground, numberOfMessages);
                            ShortcutBadger.applyCount(getApplicationContext(), numberOfMessages);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.d("Failed to read value.", error.getMessage());
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(getPackageName(), "NotificationService is created");
    }

    @Override
    public ComponentName startService(Intent service) {

        return super.startService(service);
    }
}
