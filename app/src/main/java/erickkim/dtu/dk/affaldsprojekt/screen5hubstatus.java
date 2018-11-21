package erickkim.dtu.dk.affaldsprojekt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class screen5hubstatus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //variable instantiering
    private String [] hubStatus;
    private TextView biotxtBox;
    private TextView metalGlasTxtBox;
    private TextView restTxtBox;
    private TextView plastikTxtBox;
    private View root;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public screen5hubstatus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen5hubstatus.
     */
    // TODO: Rename and change types and number of parameters
    public static screen5hubstatus newInstance(String param1, String param2) {
        screen5hubstatus fragment = new screen5hubstatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
