package erickkim.dtu.dk.affaldsprojekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class screen1main extends Fragment implements View.OnClickListener, Button.OnTouchListener{

    //variable instantiering
    private View root;
    private ImageButton garbageButton;
    private Button hubstatusButton;
    private Button hubplacementButton;
    private Button depositButton;
    private TextView txtCoinBox;
    private TextView txtInfoBox;

    private FirebaseDatabase mref;
    private DatabaseReference myref;

    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

       public screen1main() {
        // Required empty public constructor
    }

    public static screen1main newInstance(String param1, String param2) {
        screen1main fragment = new screen1main();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Testdata.
        Data_Controller.getInstance().setUserId("1111111111");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen1main, container, false);

        // initialiser de forskellige views i fragmentet
        garbageButton = root.findViewById(R.id.garbageButton);
        hubstatusButton = root.findViewById(R.id.sendButton);
        hubplacementButton = root.findViewById(R.id.hubplacementButton);
        depositButton = root.findViewById(R.id.depositButton);
        txtCoinBox = root.findViewById(R.id.txtCoinBox1);
        txtInfoBox = root.findViewById(R.id.txtInfoBox1);

        // setonclicklisteners for alle knapper.
        garbageButton.setOnClickListener(this);
        garbageButton.setOnTouchListener(this);
        hubstatusButton.setOnClickListener(this);
        hubstatusButton.setOnTouchListener(this);
        hubplacementButton.setOnClickListener(this);
        hubplacementButton.setOnTouchListener(this);
        depositButton.setOnClickListener(this);
        depositButton.setOnTouchListener(this);

        // Create firebase link
        mref = FirebaseDatabase.getInstance();

        // Hent data for TextViews
        txtInfoBox.setText(Data_Controller.getInstance().getTip());
        txtCoinBox.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());
        return root;
    }


    @Override
    public void onClick(View v) {
        //animateButton(v, v.getId());
        //check view objektet og skift til den tilhørende case.
        switch(v.getId()) {
            case R.id.garbageButton:
                // Kør fragmentet for screen7sortingguide
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen7sortingguide())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.sendButton:
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen5hubstatus())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.hubplacementButton:
                Intent mapIntent;
                mapIntent = new Intent(this.getContext(), MapsActivity.class);
                startActivity(mapIntent);
                break;
            case R.id.depositButton:
                //kør fragmentet for Screen2delivery.
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen2delivery())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    //Kode er fra Lektion 7 fragmenter. Animate_frag.  AndroidElementer Jacob Nordfalk


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

     public class asyncTestFirebase extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            myref.child("message1").setValue("Hello 1");
            myref.child("message2").setValue("Hello 2");
            return null;
        }
    }
}
