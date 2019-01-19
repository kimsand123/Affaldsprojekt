package erickkim.dtu.dk.affaldsprojekt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import erickkim.dtu.dk.affaldsprojekt.CoinShopActivity;
import erickkim.dtu.dk.affaldsprojekt.MainActivity;
import erickkim.dtu.dk.affaldsprojekt.MapsActivity;
import erickkim.dtu.dk.affaldsprojekt.R;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.services.NotificationService;

public class screen1main extends Fragment implements View.OnClickListener, Button.OnTouchListener{

    //variable instantiering
    private View root;
    private ImageButton garbageButton;
    private Button hubstatusButton;
    private ImageButton hubplacementButton;
    private Button depositButton;
    private ImageButton goldBox;
    private TextView txtInfoBox;
    private ImageView imgGoldBox;
    private Button coinBoxButton;

    private FirebaseDatabase mref;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().startService(new Intent(getActivity()   , NotificationService.class));
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen1main, container, false);

        // initialiser de forskellige views i fragmentet
        garbageButton = root.findViewById(R.id.garbageButton);
        hubplacementButton = root.findViewById(R.id.hubplacementButton);
        depositButton = root.findViewById(R.id.depositButton);
        txtInfoBox = root.findViewById(R.id.txtInfoBox1);
        coinBoxButton = root.findViewById(R.id.txtCoinButton1);

        // setonclick and ontouch listeners for alle knapper.
        garbageButton.setOnClickListener(this);
        garbageButton.setOnTouchListener(this);
        hubplacementButton.setOnClickListener(this);
        hubplacementButton.setOnTouchListener(this);
        depositButton.setOnClickListener(this);
        depositButton.setOnTouchListener(this);
        coinBoxButton.setOnClickListener(this);
        coinBoxButton.setOnTouchListener(this);

        // Hent data for TextViews
        txtInfoBox.setText(Data_Controller.getInstance().getTip());
        updateGoldBox();



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
           case R.id.txtCoinButton1:
                   Intent intent = new Intent(screen1main.this.getActivity(), CoinShopActivity.class);
                   startActivity(intent);
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


     // Method to update gold box contents, reflecting usertype and update when data is received.
     public void updateGoldBox() {
         mref = FirebaseDatabase.getInstance();

         coinBoxButton = root.findViewById(R.id.txtCoinButton1);
         imgGoldBox = root.findViewById(R.id.imgGoldBox);

         coinBoxButton.setText(String.valueOf(Data_Controller.getInstance().getGold()));
         mref.getReference().child("users").child(Data_Controller.getInstance().getUserId()).child("gold").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 long gold = (long) dataSnapshot.getValue();
                 int goldInt = (int) gold;
                 String goldBoxContent = "";
                 if (Data_Controller.getInstance().getUserType().equals("borger")) {
                     goldBoxContent = "" + goldInt;
                 } else if (Data_Controller.getInstance().getUserType().equals("virksomhed")) {
                     goldBoxContent = "Penge sparet: " + String.valueOf(gold) + " kr.";
                 }
                 Data_Controller.getInstance().setGold(goldInt);
                 coinBoxButton.setText(goldBoxContent);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
             }
         });
         if (Data_Controller.getInstance().getUserType().equals("virksomhed"))

             imgGoldBox.setVisibility(View.INVISIBLE);
     }
}
