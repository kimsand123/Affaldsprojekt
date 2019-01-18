package erickkim.dtu.dk.affaldsprojekt;

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

import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_deliveryCode;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class screen2delivery extends Fragment implements View.OnClickListener, View.OnTouchListener {

    //instantier variable.
    private View root;
    private ImageButton doneButton;
    private Button newIdButton;
    private TextView txtIdBox;
    private TextView goldBox;
    private ImageView imgGoldBox;
    private Button auxiliaryButton;
    private Button coinBoxButton;

    private FirebaseDatabase mref;

    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

    public screen2delivery() {
        // Required empty public constructor
    }

    public static screen2delivery newInstance(String param1, String param2) {
        screen2delivery fragment = new screen2delivery();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen2delivery, container, false);

        //initialiser views
        doneButton = root.findViewById(R.id.doneButton);
        newIdButton = root.findViewById(R.id.newIdButton);
        txtIdBox = root.findViewById(R.id.txtIdBox);
        auxiliaryButton = root.findViewById(R.id.button_auxiliary);
        coinBoxButton = root.findViewById(R.id.txtCoinButton2);

        PulsatorLayout pulsar = root.findViewById(R.id.pulsator);

        doneButton.setOnClickListener(this);
        doneButton.setOnTouchListener(this);
        newIdButton.setOnClickListener(this);
        newIdButton.setOnTouchListener(this);
        auxiliaryButton.setOnClickListener(this);
        coinBoxButton.setOnClickListener(this);
        coinBoxButton.setOnTouchListener(this);
        pulsar.start();

        updateGoldBox();
        setNewIdCode();
        return root;
    }

    public void setNewIdCode() {
        Data_DTO_deliveryCode ID = Data_Controller.getInstance().getNewDeliveryCode();
        String stringID = Integer.toString(ID.getCode());
        txtIdBox.setText("ID: " + ID);//+ Data_Controller.getInstance().getDeliveryCode());
        //Testdata
        Data_Controller.getInstance().setUsedDataDeliveryCode(ID.getCode());
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.doneButton:
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen3afterdelivery())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        //.addToBackStack(null)
                        .commit();
                break;
            case R.id.newIdButton:
                setNewIdCode();
                break;
            case R.id.txtCoinButton2:
                Intent intent = new Intent(screen2delivery.this.getActivity(), CoinShopActivity.class);
                startActivity(intent);
                break;
                // Test button to enter Auxiliary activity.
            case R.id.button_auxiliary:
                Intent auxIntent;
                auxIntent = new Intent(this.getContext(), Auxiliary.class);
                startActivity(auxIntent);

        }
    }

    public boolean onTouch(View v, MotionEvent me) {
        v.animate().setDuration(200);
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            v.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            v.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
        }
        return false;
    }

    public void updateGoldBox() {
        mref = FirebaseDatabase.getInstance();
        goldBox = root.findViewById(R.id.txtCoinButton2);
        imgGoldBox = root.findViewById(R.id.imgGoldBox);


        goldBox.setText(String.valueOf(Data_Controller.getInstance().getGold()));
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
                goldBox.setText(goldBoxContent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);
    }
}

