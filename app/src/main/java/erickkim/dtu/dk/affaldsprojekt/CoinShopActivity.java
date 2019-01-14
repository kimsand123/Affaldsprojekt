package erickkim.dtu.dk.affaldsprojekt;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.annotation.Nullable;
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
    private Observer goldObserver;
    private LiveData<Integer> goldLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_shop);

        v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        txtCoinBox = findViewById(R.id.txtCoinBox1);
        txtCoinBox.setText("" + Data_Controller.getInstance().getTrashCoins());

        // TODO: Get these from firebase.
        ArrayList<Data_DTO_shopEntry> shopEntries = new ArrayList<Data_DTO_shopEntry>();
        shopEntries.add(new Data_DTO_shopEntry("13 Stk Snapseglas", "Snapseglas fra Holmegaard med en flot messingekant.", R.drawable.shop_glas, 120));
        shopEntries.add(new Data_DTO_shopEntry("2 Stk Skaale", "Skaale fra Holmegaard lavet i et pænt drejet mønster", R.drawable.shop_skaale, 100));
        shopEntries.add(new Data_DTO_shopEntry("2 stk Lysestage", "Lysestager i messing. Perfekt til når jordens energiforsyning er sluppet op.", R.drawable.shop_lys, 80));
        shopEntries.add(new Data_DTO_shopEntry("Sanghæfter", "Gamle sanghæfter fra dengang børn ikke brugte 24 timer foran computeren.", R.drawable.shop_sanghaefte, 30));
        shopEntries.add(new Data_DTO_shopEntry("En 80mm AT-Kanon", "Ja du læste rigtigt. Brug dit guld på at købe en kanon. Mon naboen endeligt tier stille.", R.drawable.shop_kanon, 200));

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

        goldLiveData = new goldLiveData();
        goldObserver = new goldObserver();
        goldLiveData.observe(this, goldObserver);

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

    public class goldLiveData extends LiveData<Integer> implements Observer {
        @Override
        public void onChanged(@Nullable Object o) {

        }
    }

    private class goldObserver implements Observer {
        @Override
        public void onChanged(@Nullable Object o) {

        }
    }
}
