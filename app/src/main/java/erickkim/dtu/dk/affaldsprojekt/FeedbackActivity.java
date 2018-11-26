package erickkim.dtu.dk.affaldsprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemSelectedListener {
    private Spinner typeSpinner;
    private String feedbackString;
    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;
    
    private Button send;
    private EditText feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        typeSpinner = findViewById(R.id.feedbackDropdown);

        //populer feedback type spinner
        String[] feedbackArray = new String[]{"Send en go idé", "Fejlmeld affaldstårn", "en mere", "og en sidste"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, feedbackArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackString="";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendButton:
                //dataRef.child(userIdString).child(date).child(deliveryCodeString).child("amount").setValue("" + amountInt);
                //dataRef.child(userIdString).child(date).child(deliveryCodeString).child("type").setValue("" + typeString);
                //TODO kode som skriver til firebase
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        feedbackString = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        feedbackString = "";
    }
}
