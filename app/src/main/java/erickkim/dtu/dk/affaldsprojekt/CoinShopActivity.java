package erickkim.dtu.dk.affaldsprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CoinShopActivity extends AppCompatActivity implements itemClickListener {

    shopRecycleViewAdapter adapter;

    private TextView txtCoinBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_shop);

        txtCoinBox = findViewById(R.id.coinShop_CoinsText);
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
        RecyclerView recyclerView = findViewById(R.id.coinShop_RecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new shopRecycleViewAdapter(this, shopEntries);
        adapter.setClickListener(this);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
