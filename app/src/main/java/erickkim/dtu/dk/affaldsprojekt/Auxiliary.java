package erickkim.dtu.dk.affaldsprojekt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Auxiliary extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText deliveryCode;
    private EditText userId;
    private EditText amount;
    private Spinner typeSpinner;
    private String typeString;
    private Button deliveryButton;
    private Data_DTO_delivery deliveryObject;

    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deliveryCode = findViewById(R.id.text_deliverycode);
        userId = findViewById(R.id.text_userid);
        amount = findViewById(R.id.text_amount);
        typeSpinner = findViewById(R.id.spinner_type);
        deliveryButton = findViewById(R.id.button_deliver);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.garbage_types_array,
                android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeString = "";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        fireData = FirebaseDatabase.getInstance();
        dataRef = fireData.getReference("delivery");

    }

    @Override
    public void onClick(View v) {
        int deliveryCodeInt;
        deliveryCodeInt = Integer.parseInt(deliveryCode.getText().toString());
        if (deliveryCodeInt == 0) {
            System.err.print("Delivery code is 0.");
            return;
        }

        String userIdString;
        userIdString = userId.getText().toString();
        if (userIdString.equals("")) {
            System.err.print("User ID empty.");
            return;
        }

        int amountInt;
        amountInt = Integer.parseInt(userId.getText().toString());
        if (amountInt == 0) {
            System.err.print("Amount is 0");
            return;
        }

        if (typeString.equals("")) {
            System.err.print("Type is not selected");
            return;
        }

        /*deliveryObject = new Data_DTO_delivery();
        deliveryObject.setDate("22-Nov-18");
        deliveryObject.setDeliveryCode(deliveryCodeInt);
        deliveryObject.setAmount(amountInt);
        deliveryObject.setType(typeString);
        deliveryObject.setUserId(userIdString);

        asyncDeliver deliverTask = new asyncDeliver();
        deliverTask.execute();*/


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
            System.out.print("Let's go.");
            return null;
        }
    }
}
