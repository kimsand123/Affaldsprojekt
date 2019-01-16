package erickkim.dtu.dk.affaldsprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);



        // ArrayAdapter<Data_DTO_Notification> adapter = new ArrayAdapter<Data_DTO_Notification>(this,
        //         android.R.layout.simple_list_item_1, myStringArray);
    }
}
