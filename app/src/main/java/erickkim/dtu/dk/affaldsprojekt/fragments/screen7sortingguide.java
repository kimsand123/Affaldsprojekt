package erickkim.dtu.dk.affaldsprojekt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import erickkim.dtu.dk.affaldsprojekt.CoinShopActivity;
import erickkim.dtu.dk.affaldsprojekt.R;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;

public class screen7sortingguide extends Fragment implements Button.OnClickListener, Button.OnTouchListener{

    private ImageView imgGoldBox;
    private View root;
    private WebView web;
    private TextView tabForGoldImage7;
    private Button coinBoxButton7;
    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);


    private FirebaseDatabase mref;

    public screen7sortingguide() {
        // Required empty public constructor
    }

    public static screen7sortingguide newInstance(String param1, String param2) {
        screen7sortingguide fragment = new screen7sortingguide();
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
        root = inflater.inflate(R.layout.fragment_screen7sortingguide, container, false);
        web = root.findViewById(R.id.sortguide_Webview);
        tabForGoldImage7 = root.findViewById(R.id.tabForGoldImage7);
        tabForGoldImage7.setText(Data_Controller.getInstance().getGoldBoxContent());
        coinBoxButton7 = root.findViewById(R.id.txtCoinButton7);
        coinBoxButton7.setOnClickListener(this);
        coinBoxButton7.setOnTouchListener(this);


        updateGoldBox();

        web.loadUrl("http://www.naestved-affald.dk/information/affaldsvejviser/");
        return root;
    }

    @Override
    public void onClick(View v) {

        //check view objektet, og skift til den tilhørende case
        switch(v.getId()){
            case R.id.txtCoinButton7:
                Intent intent = new Intent(screen7sortingguide.this.getActivity(), CoinShopActivity.class);
                startActivity(intent);
                break;
        }

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
    public void updateGoldBox() {
        mref = FirebaseDatabase.getInstance();
        coinBoxButton7 = root.findViewById(R.id.txtCoinButton7);
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        coinBoxButton7.setText(String.valueOf(Data_Controller.getInstance().getGold()));

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
                coinBoxButton7.setText(goldBoxContent);
                tabForGoldImage7.setText(goldBoxContent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);
    }

}
