package erickkim.dtu.dk.affaldsprojekt;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import erickkim.dtu.dk.affaldsprojekt.interfaces.itemClickListener;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_shopEntry;

public class CoinShopActivity extends AppCompatActivity implements itemClickListener {

    // MediaPlayer for Sound:
    // final MediaPlayer mp = MediaPlayer.create(this, R.raw.goldbuyshopsound);

    shopRecycleViewAdapter adapter;
    private DialogInterface.OnClickListener dialogClickListener;
    private TextView txtGoldBox;
    private ImageView imgGoldBox;
    private int lastPrice;
    private Vibrator v;

    private FirebaseDatabase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_shop);

        v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        updateGoldBox();

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

        // Setup the dialog to purchase items.
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // mp.start();
                        Data_Controller.getInstance().addGold(-lastPrice);
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
        if (lastPrice<=Data_Controller.getInstance().getGold()) {
            builder.setMessage("Vil du gerne købe " + adapter.getItem(position).title).setPositiveButton("Ja tak!", dialogClickListener)
                    .setNegativeButton("Nej tak.", dialogClickListener).show();
            updateGoldBox();
        }else{
            Toast toast = Toast.makeText(this, "Du har desværre ikke råd", Toast.LENGTH_LONG);
            toast.show();
        }
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

    public void updateGoldBox() {
        mref = FirebaseDatabase.getInstance();
        txtGoldBox = findViewById(R.id.txtCoinBox1);
        imgGoldBox = findViewById(R.id.imgGoldBox);
        txtGoldBox.setText(String.valueOf(Data_Controller.getInstance().getGold()));
        mref.getReference().child("users").child(Data_Controller.getInstance().getUserId()).child("gold").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long gold = (long) dataSnapshot.getValue();
                int goldInt = (int) gold;
                String goldBoxContent = "";
                if (Data_Controller.getInstance().getUserType().equals("borger")) {
                    goldBoxContent = "" + goldInt;
                } else if (Data_Controller.getInstance().getUserType().equals("virksomhed")) {
                    goldBoxContent = "Penge sparet: " + String.valueOf(gold) + " kr.";
                }
                Data_Controller.getInstance().setGold(goldInt);
                txtGoldBox.setText(goldBoxContent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);
    }
}
