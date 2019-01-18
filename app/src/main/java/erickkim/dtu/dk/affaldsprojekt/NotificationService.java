package erickkim.dtu.dk.affaldsprojekt;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_messages;
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
                            if (messageData.status == 0) {
                                numberOfMessages = numberOfMessages + 1;
                            }
                        }

                        if(ShortcutBadger.isBadgeCounterSupported(getApplicationContext())) {
                            ShortcutBadger.applyCount(getApplicationContext(), numberOfMessages);
                            makeToast(Integer.toString(numberOfMessages));
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
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    public void makeToast(String toastString){
        Context context = getApplicationContext();
        CharSequence text = toastString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
