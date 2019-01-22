package erickkim.dtu.dk.affaldsprojekt.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ListIterator;

import erickkim.dtu.dk.affaldsprojekt.CoinShopActivity;
import erickkim.dtu.dk.affaldsprojekt.utilities.DimensionHandling;
import erickkim.dtu.dk.affaldsprojekt.utilities.GenerateFeedback;
import erickkim.dtu.dk.affaldsprojekt.interfaces.I_GenerateFeedback;
import erickkim.dtu.dk.affaldsprojekt.R;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_delivery;
import erickkim.dtu.dk.affaldsprojekt.utilities.MakeFeedbackScreen3;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class screen3afterdelivery extends Fragment implements View.OnClickListener, Button.OnTouchListener {

    //instantier variable
    private View root;
    private ImageButton statisticButton;
    private TextView goldBox;
    private TextView txtInfoBox3;
    private TextView tabForGoldImage3;
    private TextView co2TextBox;
    private PieChart chart;

    private ImageView imgGoldBox;
    private Button coinBoxButton;

    final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();
    final OvershootInterpolator sOvershooter = new OvershootInterpolator(5f);

    private FirebaseDatabase mref;

    public screen3afterdelivery() {
        // Required empty public constructor
    }

    public static screen3afterdelivery newInstance(String param1, String param2) {
        screen3afterdelivery fragment = new screen3afterdelivery();
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

        root = inflater.inflate(R.layout.fragment_screen3afterdelivery, container, false);
        chart = root.findViewById(R.id.pieChart);
        chart.setNoDataText(" ");
        chart.setNoDataTextColor(Color.BLACK);
        getDataForPieChart();
        PulsatorLayout pulsator =  root.findViewById(R.id.pulsator);
        //initialiser views
        statisticButton = root.findViewById(R.id.statisticButton);
        txtInfoBox3 = root.findViewById(R.id.txtInfoBox3);
        co2TextBox = root.findViewById(R.id.co2TextBox);
        coinBoxButton = root.findViewById(R.id.txtCoinButton3);
        tabForGoldImage3 = root.findViewById(R.id.tabForGoldImage3);
        tabForGoldImage3.setText(Data_Controller.getInstance().getGoldBoxContent());
        txtInfoBox3.setTextSize(1, DimensionHandling.getInstance().getTextSize());

        updateGoldBox();
        coinBoxButton.setOnClickListener(this);
        coinBoxButton.setOnTouchListener(this);
        statisticButton.setOnClickListener(this);
        statisticButton.setOnTouchListener(this);
        pulsator.start();
        return root;
    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        v.animate().setDuration(200);
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            v.animate().setInterpolator(sDecelerator).scaleX(.7f).scaleY(.7f);
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            v.animate().setInterpolator(sOvershooter).scaleX(1f).scaleY(1f);
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        //check view objektet, og skift til den tilhørende case
        switch(v.getId()){
            case R.id.statisticButton:
                //kør fragmentet for screen4statistic.
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen4statistic())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.txtCoinButton3:
                Intent intent = new Intent(screen3afterdelivery.this.getActivity(), CoinShopActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void getDataForPieChart(){

        Data_Controller.getInstance().setToday();
        String date = Data_Controller.getInstance().getLongToday();
        final String userId = Data_Controller.getInstance().getUserId();

        final ArrayList<PieEntry> values = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId).child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_DTO_delivery snapshotData;
                        int metPlaGlaAmount=0;
                        int bioAmount=0;
                        int papPapiAmount=0;
                        int restAmount=0;
                        int gold = 0;
                        ArrayList<Integer> colors = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshotData = snapshot.getValue(Data_DTO_delivery.class);

                            ListIterator<PieEntry> listElements = values.listIterator();

                            //algoritme for at samle 2 deposits af den samme type eks. 2 bio den samme dag
                            //til ér deposit i datastrukturen indeholdende PieEntries, før PieChart bliver tegnet.
                            while(listElements.hasNext()){
                                String label = listElements.next().getLabel();
                                String currentType = snapshotData.getType();
                                if (label.equals(currentType)) {
                                    listElements.previous();
                                    float value = listElements.next().getValue();
                                    listElements.remove();
                                    snapshotData.setAmount(Integer.toString(Integer.parseInt( snapshotData.getAmount()) + (int) value));
                                }
                            }
                            ((ArrayList) values).add(new PieEntry(Integer.parseInt(snapshotData.getAmount()), snapshotData.getType()));
                            gold = gold + Integer.parseInt(snapshotData.getGold());
                            switch(snapshotData.getType()){
                                case "Metal/Plastik/Glas":
                                    if (colors.contains(Color.LTGRAY)) {
                                        colors.remove(colors.indexOf(Color.LTGRAY));

                                    }
                                    colors.add(Color.LTGRAY);
                                    metPlaGlaAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Bio":
                                    if (colors.contains(Color.GREEN)) {
                                        colors.remove(colors.indexOf(Color.GREEN));
                                    }
                                    colors.add(Color.GREEN);
                                    bioAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Pap/Papir":
                                    if (colors.contains(Color.YELLOW)) {
                                        colors.remove(colors.indexOf(Color.YELLOW));
                                    }
                                    colors.add(Color.YELLOW);
                                    papPapiAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Rest":
                                    if (colors.contains(Color.RED)) {
                                        colors.remove(colors.indexOf(Color.RED));
                                    }
                                    colors.add(Color.RED);
                                    restAmount =Integer.parseInt(snapshotData.getAmount());
                                    break;
                            }
                        }
                        MakeFeedbackScreen3 feedback = new MakeFeedbackScreen3(metPlaGlaAmount,bioAmount,papPapiAmount,restAmount, gold, "screen3");
                        txtInfoBox3.setText(Html.fromHtml(feedback.createTxtBoxFeedbackText()));
                        co2TextBox.setText(feedback.createCO2FeedbackText());
                        drawPieChart(values, colors);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

    }
    public void drawPieChart(ArrayList<PieEntry> values, ArrayList<Integer> colors){

        //initialize dataset and pass the data
        PieDataSet dataSet = new PieDataSet(values, "" );

        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setSliceSpace(2f);
        dataSet.setColors(colors);

        //Initialize PieData
        PieData data = new PieData(dataSet);

        chart.setEntryLabelColor(Color.BLACK);

        chart.setUsePercentValues(true);
        chart.setDrawHoleEnabled(true);
        chart.setTransparentCircleRadius(30f);
        chart.setHoleRadius(10f);
        chart.getLegend().setEnabled(false);
        //Vi easer en lille smule mod slutningen af animationen.
        chart.animateXY(1400, 1400, Easing.EaseOutCubic);
        chart.setRotationEnabled(true);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }

    public void updateGoldBox() {
        mref = FirebaseDatabase.getInstance();
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        coinBoxButton.setText(String.valueOf(Data_Controller.getInstance().getGold()));
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
                coinBoxButton.setText(goldBoxContent);
                tabForGoldImage3.setText(goldBoxContent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);
    }
}

