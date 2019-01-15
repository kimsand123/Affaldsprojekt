package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class screen7sortingguide extends Fragment {

    private TextView txtGoldBox;
    private ImageView imgGoldBox;
    private View root;
    private WebView web;

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
        txtGoldBox = root.findViewById(R.id.txtCoinBox1);
        web = root.findViewById(R.id.sortguide_Webview);

        txtGoldBox.setText(Data_Controller.getInstance().getGoldBoxContent());
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);

        web.loadUrl("http://www.naestved-affald.dk/information/affaldsvejviser/");
        return root;
    }

}
