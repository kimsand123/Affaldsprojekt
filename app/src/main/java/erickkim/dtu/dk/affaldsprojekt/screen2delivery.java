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
import android.widget.ImageView;
import android.widget.TextView;


public class screen2delivery extends Fragment implements View.OnClickListener, View.OnTouchListener {

    //instantier variable.
    private View root;
    private Button doneButton;
    private Button newIdButton;
    private TextView txtIdBox;
    private TextView txtGoldBox;
    private ImageView imgGoldBox;
    private Button auxiliaryButton;

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
        txtGoldBox = root.findViewById(R.id.txtCoinBox1);
        auxiliaryButton = root.findViewById(R.id.button_auxiliary);

        //Hent data til textfelter.
        txtGoldBox.setText(Data_Controller.getInstance().getGoldBoxContent());
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);

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

