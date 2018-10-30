package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class screen2delivery extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //instantier variable.
    private String mParam1;
    private String mParam2;
    private View root;
    private Button doneButton;
    private Button newIdButton;
    private TextView txtIdBox;
    private TextView txtCoinBox2;

    private Data_DAO_deliveryCode getData;
    private Data_DTO_deliveryCode dataDTO;



    public screen2delivery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen2delivery.
     */
    // TODO: Rename and change types and number of parameters
    public static screen2delivery newInstance(String param1, String param2) {
        screen2delivery fragment = new screen2delivery();
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
        root = inflater.inflate(R.layout.fragment_screen2delivery, container, false);

        //initialiser views
        doneButton = root.findViewById(R.id.doneButton);
        newIdButton = root.findViewById(R.id.newIdButton);
        txtIdBox = root.findViewById(R.id.txtIdBox);
        txtCoinBox2 = root.findViewById(R.id.txtCoinBox2);

        //Hent data til textfelter.
        txtCoinBox2.setText("GarbageCoins: " + Data_Background.getInstance().getTrashCoins());
        txtIdBox.setText("ID: 2 3 4 5");//+ Data_Background.getInstance().getDeliveryCode());

        doneButton.setOnClickListener(this);
        newIdButton.setOnClickListener(this);
        return root;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.doneButton:
                //TODO: sæt hent dataflag i preferences, og skift til screen1main fragment
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen3afterdelivery())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.newIdButton:
                //TODO: Hent nyt id fra backend.
                //txtIdBox.setText("ID: "+ Data_Background.getInstance().getDeliveryCode());
                break;

        }

    }
}

