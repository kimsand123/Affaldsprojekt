package erickkim.dtu.dk.affaldsprojekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        txtCoinBox.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());

        // TODO: Create a class object to instead populate the list with proper info and images.
        ArrayList<Data_DTO_shopEntry> names = new ArrayList<Data_DTO_shopEntry>();
        names.add(new Data_DTO_shopEntry("2L Flaske Vin", "Den ideelle starter til enhver midaldrende kvinde med en svagtpræsterende mand.", R.drawable.alcoholicmother));
        names.add(new Data_DTO_shopEntry("5L Flaske Vin", "Er du ekstra desperat? Så er denne vidst lige noget for dig.", R.drawable.alcoholicmother));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));
        names.add(new Data_DTO_shopEntry("Skraldesorteringsbutler", "Denne fine herre vil følge dig overalt for at minde dig om at sortere dit skrald ordentligt. Ja, overalt.", R.drawable.sorterskrald));

        // Set up the recyclerview
        RecyclerView recyclerView = findViewById(R.id.coinShop_RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new shopRecycleViewAdapter(this, names);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
