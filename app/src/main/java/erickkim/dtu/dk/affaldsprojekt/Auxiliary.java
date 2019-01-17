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
    private EditText gold;
    private Spinner typeSpinner;
    private String typeString;
    private String userIdString;
    private Button deliveryButton;
    private int amountInt;
    private int goldInt;
    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;
    private String deliveryCodeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxiliary);

        Data_Controller.getInstance().setToday();
        deliveryCode = findViewById(R.id.text_deliverycode);
        int ID = Data_Controller.getInstance().getUsedDataDeliveryCode();
        deliveryCode.setText(Data_Controller.getInstance().getTodaysDeliveryCounter() + "_" + Integer.toString(ID));

        userId = findViewById(R.id.text_userid);
        userId.setText(Data_Controller.getInstance().getUserId());

        gold = findViewById(R.id.text_newCoins);
        if(Data_Controller.getInstance().getUserType().equals("virksomhed")){
            gold.setVisibility(View.INVISIBLE);
        }else{
            gold.setVisibility(View.VISIBLE);
        }

        amount = findViewById(R.id.text_amount);

        deliveryButton = findViewById(R.id.button_deliver);

        String[] typeArray = new String[]{"Metal/Plastik/Glas", "Pap/Papir", "Bio", "Rest"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner = findViewById(R.id.spinner_type);
        typeString = "";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        fireData = FirebaseDatabase.getInstance();
        dataRef = fireData.getReference();

        deliveryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        deliveryCodeString = deliveryCode.getText().toString();
        if (deliveryCodeString.equals("")) {
            //MessageCenter.getInstance().showMessage("Delivery code string missing");
            makeToast("Delivery code string missing");
            return;
        }

        userIdString = userId.getText().toString();
        if (userIdString.equals("")) {
            //MessageCenter.getInstance().showMessage("UserIDString missing");
           makeToast("UserIDString missing");
            return;
        }

        amountInt = Integer.parseInt(amount.getText().toString());
        if (amountInt == 0) {
            //MessageCenter.getInstance().showMessage("Amount missing");
            makeToast("Amount missing");
            return;
        }

        if(Data_Controller.getInstance().getUserType().equals("borger")){
            goldInt = Integer.parseInt(gold.getText().toString());
            if (goldInt < 0) {
                //MessageCenter.getInstance().showMessage("Amount missing");
                makeToast("Coins missing");
                return;
            }
        }

        if (typeString.equals("")) {
            //MessageCenter.getInstance().showMessage("Type Missing");
            makeToast("Type Missing");
            return;
        }


        asyncDeliver deliverTask = new asyncDeliver();
        deliverTask.execute();



    }

    public void makeToast(String toastString){
        Context context = getApplicationContext();
        CharSequence text = toastString;
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
            getTrashValue gettrashvalue = new getTrashValue();
            int newGold = 0;
            newGold = Data_Controller.getInstance().getGold();
            Data_DTO_delivery dataBundle= new Data_DTO_delivery();
            dataBundle.setAmount(Integer.toString(amountInt));
            dataBundle.setType(typeString);

            //Hvis usertypen er virksomhed er det kronerne der skal gemmes i guld, ellers er det guld.
            if (Data_Controller.getInstance().getUserType().equals("virksomhed")){
                dataBundle.setGold(Integer.toString((int)Math.round(gettrashvalue.convertToValue(typeString, amountInt))));
                newGold=newGold+amountInt;
            } else {
                dataBundle.setGold(Integer.toString(goldInt));
                newGold=newGold+goldInt;
            }
            dataRef.child("delivery").child(userIdString).child(Data_Controller.getInstance().getLongToday()).child(deliveryCodeString).setValue(dataBundle);
            dataRef.child("users").child(userIdString).child("gold").setValue(newGold);
            //dataRef.child(userIdString).child(Data_Controller.getInstance().getLongToday()).child(deliveryCodeString).child("amount").setValue("" + amountInt);
            //dataRef.child(userIdString).child(Data_Controller.getInstance().getLongToday()).child(deliveryCodeString).child("type").setValue("" + typeString);
            Data_Controller.getInstance().setDeliveredDate(Data_Controller.getInstance().getLongToday());
            Data_Controller.getInstance().setTodaysDeliveryCounter(Data_Controller.getInstance().getTodaysDeliveryCounter()+1);

            return null;
        }

        @Override
        protected void onPostExecute (Object o){
            super.onPostExecute(o);
            makeToast("Executed!");
            //MessageCenter.getInstance().showMessage("Data er skrevet");
            int ID = Data_Controller.getInstance().getUsedDataDeliveryCode();
            deliveryCode.setText(Data_Controller.getInstance().getTodaysDeliveryCounter() + "_" + Integer.toString(ID));
        }
    }


}
