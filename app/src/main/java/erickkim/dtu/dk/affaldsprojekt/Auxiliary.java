package erickkim.dtu.dk.affaldsprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Auxiliary extends AppCompatActivity implements View.OnClickListener {

    private EditText deliveryCode;
    private EditText userId;
    private EditText amount;
    private Spinner type;
    private Button deliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deliveryCode = findViewById(R.id.text_deliverycode);
        userId = findViewById(R.id.text_userid);
        amount = findViewById(R.id.text_amount);
        type = findViewById(R.id.spinner_type);
        deliveryButton = findViewById(R.id.button_deliver);
    }

    @Override
    public void onClick(View v) {

    }
}
