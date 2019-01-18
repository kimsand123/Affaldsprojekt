package erickkim.dtu.dk.affaldsprojekt;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;


public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemSelectedListener, View.OnTouchListener {
    private Spinner typeSpinner;
    private String feedbackString;
    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;
    private Button sendButton;

    private Button send;
    private EditText feedback;

    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        typeSpinner = findViewById(R.id.feedbackDropdown);
        feedback = findViewById(R.id.txtFeedbackBox);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        sendButton.setOnTouchListener(this);


        //populer feedback type spinner
        String[] feedbackArray = new String[]{"Send en go idé", "Fejlmeld affaldstårn", "en mere", "og en sidste"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, feedbackArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackString="";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        fireData = FirebaseDatabase.getInstance();
        dataRef = fireData.getReference("ideas");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendButton:
                AsyncDeliver deliverTask = new AsyncDeliver();
                deliverTask.execute();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        feedbackString = parent.getItemAtPosition(position).toString();
        ((TextView) view).setTextColor(Color.WHITE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        feedbackString = "";
    }

    public void makeToast(String toastString){
        Context context = getApplicationContext();
        CharSequence text = toastString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        v.animate().setDuration(200);
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            v.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            v.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
        }
        return false;
    }

    public class AsyncDeliver extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            //Getting the next key
            String key = dataRef.push().getKey();

            //writing data
            dataRef.child(key).child("idea").setValue("" + feedback.getText().toString());
            dataRef.child(key).child("userId").setValue("" + Data_Controller.getInstance().getUserId());
            return null;
        }

        @Override
        protected void onPostExecute (Object o){
            super.onPostExecute(o);
            makeToast("Tak for din idé. :)");
            finish();
            //MessageCenter.getInstance().showMessage("Tak for din idé. :)");
        }

    }
}
