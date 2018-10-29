package erickkim.dtu.dk.affaldsprojekt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class screen1main extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View root;
    private ImageButton garbageButton;
    private Button hubstatusButton;
    private Button hubplacementButton;
    private Button depositButton;
    private TextView txtCoinBox;
    private TextView txtInfoBox;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public screen1main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen1main.
     */
    // TODO: Rename and change types and number of parameters

    public static screen1main newInstance(String param1, String param2) {
        screen1main fragment = new screen1main();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen1main, container, false);

        garbageButton = root.findViewById(R.id.garbageButton);
        hubstatusButton = root.findViewById(R.id.hubstatusButton);
        hubplacementButton = root.findViewById(R.id.hubplacementButton);
        depositButton = root.findViewById(R.id.depositButton);
        txtCoinBox = root.findViewById(R.id.txtCoinBox);
        txtInfoBox = root.findViewById(R.id.txtInfoBox);

        garbageButton.setOnClickListener(this);
        hubstatusButton.setOnClickListener(this);
        hubplacementButton.setOnClickListener(this);
        depositButton.setOnClickListener(this);

        //TODO: hent data til de to felter. ligenu laver jeg bare noget tekst
        txtInfoBox.setText("Vidste du at blablablablabl ablas asdf asd efr r as da sdf  gasd asd fas df sad asdas df");
        txtCoinBox.setText("GarbageCoins: 2287392");

        return root;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.garbageButton:
                //TODO: vis pdf i en browser.
                //huske det nok skal foregår i en anden tråd
                break;
            case R.id.hubstatusButton:
                //TODO: vis et dummy billede af en hubstatus
                //huske det nok skal foregår i en anden tråd
                break;
            case R.id.hubplacementButton:
                //TODO: vis et googlemaps med et koordinat evt. med en fra til markeret.
                //huske det nok skal foregår i en anden tråd
                break;
            case R.id.depositButton:
                //TODO: Gå til deposit fragmentet.
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen2delivery())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
        }
        }
    }
