package erickkim.dtu.dk.affaldsprojekt;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.os.Vibrator;

import java.util.ArrayList;

public class CoinShopActivity extends AppCompatActivity implements itemClickListener {


    shopRecycleViewAdapter adapter;
    private DialogInterface.OnClickListener dialogClickListener;
    private TextView txtCoinBox;
    private int lastPrice;
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_shop);

        v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        txtCoinBox = findViewById(R.id.txtCoinBox1);
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

        // Setup the dialog to purchase items.
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Data_Controller.getInstance().addTrashCoins(-lastPrice);
                        if (Build.VERSION.SDK_INT>=26)
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        else
                            v.vibrate(500);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

    }


    @Override
    public void onItemClick(View view, int position) {
        // Ask if truly buy.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        lastPrice = adapter.getItem(position).price;
        builder.setMessage("Vil du gerne købe " + adapter.getItem(position).title).setPositiveButton("Ja tak!", dialogClickListener)
                .setNegativeButton("Nej tak.", dialogClickListener).show();
    }
}
