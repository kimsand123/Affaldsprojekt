package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class screen7sortingguide extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txtCoinBox5;
    private View root;
    private WebView web;

    public screen7sortingguide() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen7sortingguide.
     */
    // TODO: Rename and change types and number of parameters
    public static screen7sortingguide newInstance(String param1, String param2) {
        screen7sortingguide fragment = new screen7sortingguide();
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
        root = inflater.inflate(R.layout.fragment_screen7sortingguide, container, false);
        txtCoinBox5 = root.findViewById(R.id.txtCoinBox5);
        web = root.findViewById(R.id.sortguide_Webview);
        txtCoinBox5.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());
        web.loadUrl("http://www.naestved-affald.dk/min-ordning/dagrenovation/madaffald/");
        // Inflate the layout for this fragment
        return root;
    }

}
