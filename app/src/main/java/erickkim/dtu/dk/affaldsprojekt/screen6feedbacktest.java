package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class screen6feedbacktest extends Fragment implements View.OnClickListener,  AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner typeSpinner;
    private String feedbackString;
    private FirebaseDatabase fireData;
    private DatabaseReference dataRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private Button send;
    private EditText feedback;


    public screen6feedbacktest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen6feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static screen6feedbacktest newInstance(String param1, String param2) {
        screen6feedbacktest fragment = new screen6feedbacktest();
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
        root = inflater.inflate(R.layout.fragment_screen6feedback, container, false);
        typeSpinner = root.findViewById(R.id.feedbackDropdown);

        //populer feedback type spinner
        String[] feedbackArray = new String[]{"Send en go idé", "Fejlmeld affaldstårn", "en mere", "og en sidste"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, feedbackArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackString="";
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendButton:
                //dataRef.child(userIdString).child(date).child(deliveryCodeString).child("amount").setValue("" + amountInt);
                //dataRef.child(userIdString).child(date).child(deliveryCodeString).child("type").setValue("" + typeString);
                //TODO kode som skriver til firebase
                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        feedbackString = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        feedbackString = "";
    }
}
