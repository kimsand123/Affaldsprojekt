package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class screen7sortingguide extends Fragment {

    private TextView txtGoldBox;
    private ImageView imgGoldBox;
    private View root;
    private WebView web;

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

        updateGoldBox();

        web.loadUrl("http://www.naestved-affald.dk/information/affaldsvejviser/");
        return root;
    }

    public void updateGoldBox() {
        mref = FirebaseDatabase.getInstance();
        txtGoldBox = root.findViewById(R.id.txtCoinBox1);
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        txtGoldBox.setText(String.valueOf(Data_Controller.getInstance().getGold()));
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
                txtGoldBox.setText(goldBoxContent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);
    }

}
