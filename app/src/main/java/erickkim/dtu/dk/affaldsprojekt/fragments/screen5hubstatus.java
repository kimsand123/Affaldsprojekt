package erickkim.dtu.dk.affaldsprojekt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import erickkim.dtu.dk.affaldsprojekt.R;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;

public class screen5hubstatus extends Fragment {

    //variable instantiering
    private String [] hubStatus;
    private TextView biotxtBox;
    private TextView metalGlasTxtBox;
    private TextView restTxtBox;
    private TextView plastikTxtBox;
    private View root;

    public screen5hubstatus() {
        // Required empty public constructor
    }

    public static screen5hubstatus newInstance(String param1, String param2) {
        screen5hubstatus fragment = new screen5hubstatus();
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
        root = inflater.inflate(R.layout.fragment_screen5hubstatus, container, false);
        hubStatus = new String[4];
        hubStatus = Data_Controller.getInstance().getHubStatus();

        biotxtBox = root.findViewById(R.id.bioAffaldTxtBox);
        metalGlasTxtBox = root.findViewById(R.id.metalGlasAffaltTxtBox);
        plastikTxtBox = root.findViewById(R.id.plastikAffaldTxtBox);
        restTxtBox = root.findViewById(R.id.restAffaldTxtBox);

        biotxtBox.setText("Bio\ntårn:\n\n" + hubStatus[0]);
        metalGlasTxtBox.setText("Metal/glas\ntårn:\n\n" + hubStatus[1]);
        plastikTxtBox.setText("Plastik\ntårn:\n\n" + hubStatus[2]);
        restTxtBox.setText("Rest\ntårn:\n\n" + hubStatus[3]);

        return root;
    }
}
