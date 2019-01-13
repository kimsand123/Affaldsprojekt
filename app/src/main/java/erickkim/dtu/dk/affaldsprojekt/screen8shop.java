package erickkim.dtu.dk.affaldsprojekt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class screen8shop extends Fragment implements itemClickListener {

    shopRecycleViewAdapter adapter;
    private DialogInterface.OnClickListener dialogClickListener;
    private TextView txtCoinBox;
    private int lastPrice;
    private View root;

    public screen8shop() {
    }

    public static screen8shop newInstance(String param1, String param2) {
        screen8shop fragment = new screen8shop();
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
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_frag_screen8shop, container, false);

        txtCoinBox = root.findViewById(R.id.txtCoinBox1);
        txtCoinBox.setText("" + Data_Controller.getInstance().getTrashCoins());

        // TODO: Get these from firebase.
        ArrayList<Data_DTO_shopEntry> shopEntries = new ArrayList<Data_DTO_shopEntry>();
        shopEntries.add(new Data_DTO_shopEntry("2L Flaske Vin", "Den ideelle starter til enhver midaldrende kvinde med en svagtpræsterende mand.", R.drawable.alcoholicmother, 2000));
        shopEntries.add(new Data_DTO_shopEntry("5L Flaske Vin", "Er du ekstra desperat? Så er denne vidst lige noget for dig.", R.drawable.alcoholicmother, 4000));
        shopEntries.add(new Data_DTO_shopEntry("2L Flaske Vin", "Den ideelle starter til enhver midaldrende kvinde med en svagtpræsterende mand.", R.drawable.alcoholicmother, 2000));
        shopEntries.add(new Data_DTO_shopEntry("5L Flaske Vin", "Er du ekstra desperat? Så er denne vidst lige noget for dig.", R.drawable.alcoholicmother, 4000));
        shopEntries.add(new Data_DTO_shopEntry("2L Flaske Vin", "Den ideelle starter til enhver midaldrende kvinde med en svagtpræsterende mand.", R.drawable.alcoholicmother, 2000));
        shopEntries.add(new Data_DTO_shopEntry("5L Flaske Vin", "Er du ekstra desperat? Så er denne vidst lige noget for dig.", R.drawable.alcoholicmother, 4000));
        shopEntries.add(new Data_DTO_shopEntry("2L Flaske Vin", "Den ideelle starter til enhver midaldrende kvinde med en svagtpræsterende mand.", R.drawable.alcoholicmother, 2000));
        shopEntries.add(new Data_DTO_shopEntry("5L Flaske Vin", "Er du ekstra desperat? Så er denne vidst lige noget for dig.", R.drawable.alcoholicmother, 4000));

        // Set up the recyclerview
        RecyclerView recyclerView = root.findViewById(R.id.coinShop_RecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new shopRecycleViewAdapter(recyclerView.getContext(), shopEntries);
        adapter.setClickListener(this);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        // Setup the dialog to purchase items.
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Data_Controller.getInstance().addTrashCoins(-lastPrice);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        return root;
    }


    public void onItemClick(View view, int position) {
        // Ask if truly buy.
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        lastPrice = adapter.getItem(position).price;
        builder.setMessage("Vil du gerne købe " + adapter.getItem(position).title).setPositiveButton("Ja tak!", dialogClickListener)
                .setNegativeButton("Nej tak.", dialogClickListener).show();
    }
}
