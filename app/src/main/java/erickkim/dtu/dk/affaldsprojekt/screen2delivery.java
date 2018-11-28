package erickkim.dtu.dk.affaldsprojekt;

import android.content.Intent;
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
import android.widget.TextView;


public class screen2delivery extends Fragment implements View.OnClickListener, View.OnTouchListener {
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
    private Button auxiliaryButton;

    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen2delivery, container, false);

        //initialiser views
        doneButton = root.findViewById(R.id.doneButton);
        newIdButton = root.findViewById(R.id.newIdButton);
        txtIdBox = root.findViewById(R.id.txtIdBox);
        txtCoinBox2 = root.findViewById(R.id.txtCoinBox2);
        auxiliaryButton = root.findViewById(R.id.button_auxiliary);

        //Hent data til textfelter.
        txtCoinBox2.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());
        setNewIdCode();

        doneButton.setOnClickListener(this);
        doneButton.setOnTouchListener(this);
        newIdButton.setOnClickListener(this);
        newIdButton.setOnTouchListener(this);
        auxiliaryButton.setOnClickListener(this);
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
                //DUMMY SET deliveryId
                //Data_Controller.getInstance().setUsedDataDeliveryCode(4411);
                //Data_Controller.getInstance().setDeliveredDate("31-08-2018");

                //TODO: sæt hent dataflag i preferences, og skift til screen1main fragment
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
}

