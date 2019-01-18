package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;

import static android.view.View.GONE;


public class screen0login extends Fragment implements Button.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View root;
    private String mParam1;
    private String mParam2;
    private EditText loginIdText;
    private Button loginButton;
    private ProgressBar loadSpinner;
    private FirebaseDatabase mref;
    private boolean loggingIn;

    public screen0login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Frag_screen0login.
     */
    // TODO: Rename and change types and number of parameters
    public static screen0login newInstance(String param1, String param2) {
        screen0login fragment = new screen0login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_screen0login, container, false);

        loginIdText = root.findViewById(R.id.login_editText);

        loginButton = root.findViewById(R.id.login_loginButton);
        loadSpinner = root.findViewById(R.id.login_progressBar);

        loginButton.setOnClickListener(this);
        loggingIn = false;
        /*loginIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginButton.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!loggingIn)
                    loginButton.setEnabled(true);
            }
        });*/
        loginIdText.setText("1111111111");
        mref = FirebaseDatabase.getInstance();
        loadSpinner.setVisibility(GONE);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_loginButton:
                loginButton.setEnabled(false);
                loginIdText.setEnabled(false);
                loadSpinner.setVisibility(View.VISIBLE);
                Data_Controller.getInstance().hideSoftKeyboard(getActivity());
                loggingIn = true;
                String loginId = String.valueOf(loginIdText.getText());
                login(loginId);
                break;
            default:
                break;
        }
    }

    private void login(final String loginId) {
        mref.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (loginId.equals(snapshot.getKey())) {
                        Data_Controller.getInstance().setUserType(String.valueOf(snapshot.child("usertype").getValue()));
                        loginWithId(loginId);
                        return;
                    }
                }
                resetLoginFields();
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void loginWithId(String loginId) {
        Data_Controller.getInstance().setUserId(loginId);
        Data_Controller.getInstance().setDefaultLogin(getContext());
        Data_Controller.getInstance().getGold();
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragmentContent, new screen1main())
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
    }

    private void resetLoginFields() {
        Toast.makeText(getContext(), "Fejl. Dit ID findes ikke.", Toast.LENGTH_SHORT).show();
        loggingIn = false;
        loginIdText.setText("");
        loginIdText.setEnabled(true);
        loadSpinner.setVisibility(GONE);
    }

}
