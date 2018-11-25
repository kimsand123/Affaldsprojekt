package erickkim.dtu.dk.affaldsprojekt;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Auxiliary extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText deliveryCode;
    private EditText userId;
    private EditText amount;
    private Spinner typeSpinner;
    private String typeString;
    private String userIdString;
    private int deliveryCodeInt;
    private Button deliveryButton;
    private int amountInt;
    private String date = "25-11-2018";
    private Data_DTO_delivery deliveryObject;

    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxiliary);

        deliveryCode = findViewById(R.id.text_deliverycode);
        userId = findViewById(R.id.text_userid);
        amount = findViewById(R.id.text_amount);
        typeSpinner = findViewById(R.id.spinner_type);
        deliveryButton = findViewById(R.id.button_deliver);

        String[] typeArray = new String[]{"Metal", "Plastik", "Bio", "Rest"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeString = "";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        fireData = FirebaseDatabase.getInstance();
        dataRef = fireData.getReference("delivery");

        deliveryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        deliveryCodeInt = Integer.parseInt(deliveryCode.getText().toString());
        if (deliveryCodeInt == 0) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "Delivery Code Missing", duration);
            toast.show();
            return;
        }

        userIdString = userId.getText().toString();
        if (userIdString.equals("")) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "User ID missing", duration);
            toast.show();
            return;
        }

        amountInt = Integer.parseInt(userId.getText().toString());
        if (amountInt == 0) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "Amount missing", duration);
            toast.show();
            return;
        }

        if (typeString.equals("")) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, "Type missing", duration);
            toast.show();
            return;
        }

        /*deliveryObject = new Data_DTO_delivery();
        deliveryObject.setDate("22-Nov-18");
        deliveryObject.setDeliveryCode(deliveryCodeInt);
        deliveryObject.setAmount(amountInt);
        deliveryObject.setType(typeString);
        deliveryObject.setUserId(userIdString);
        */

        asyncDeliver deliverTask = new asyncDeliver();
        deliverTask.execute();

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeString = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        typeString = "";
    }

    public class asyncDeliver extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            /* int deliveryDataCount = 1;
            String last = dataRef.child(userIdString).child(date).getKey(); */
            dataRef.child(userIdString).child(date).child("1_" + deliveryCodeInt).child("amount").setValue(amountInt);
            dataRef.child(userIdString).child(date).child("1_" + deliveryCodeInt).child("type").setValue("" + typeString);
            return null;
        }
    }
}
