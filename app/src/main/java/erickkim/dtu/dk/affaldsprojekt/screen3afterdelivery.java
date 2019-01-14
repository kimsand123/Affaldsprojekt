package erickkim.dtu.dk.affaldsprojekt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ListIterator;

public class screen3afterdelivery extends Fragment implements View.OnClickListener {

    //instantier variable
    private View root;
    private Button statisticButton;
    private TextView txtCoinBox;
    private TextView txtInfoBox3;
    private TextView co2TextBox;
    private ArrayList<PieEntry> piedata;
    private PieChart chart;
    private I_Analysis analysis = new Analysis();

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

        //initialiser views
        statisticButton = root.findViewById(R.id.statisticButton);
        txtCoinBox = root.findViewById(R.id.txtCoinBox1);
        txtInfoBox3 = root.findViewById(R.id.txtInfoBox3);
        co2TextBox = root.findViewById(R.id.co2TextBox);

        //Hent data til TextViews.
        txtCoinBox.setText("" + Data_Controller.getInstance().getTrashCoins());
        statisticButton.setOnClickListener(this);
        return root;
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
        }
    }

    public void getDataForPieChart(){

        Data_Controller.getInstance().setToday();
        String date = Data_Controller.getInstance().getLongToday();
        String userId = Data_Controller.getInstance().getUserId();

        final ArrayList<PieEntry> values = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId).child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_DTO_delivery snapshotData;
                        int metalAmount=0;
                        int bioAmount=0;
                        int plastikAmount=0;
                        int restAmount=0;
                        ArrayList<Integer> colors = new ArrayList<>();
                        //For hvert barn i datasnapshot.
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshotData = snapshot.getValue(Data_DTO_delivery.class);

                            ListIterator<PieEntry> listElements = values.listIterator();
                            //algoritme for at samle 2 deposits af den samme type eks. bio den samme dag
                            //til et deposit i datastrukturen indeholdende PieEntries, før PieChart bliver tegnet.
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
                            switch(snapshotData.getType()){
                                case "Metal":
                                    if (colors.contains(Color.LTGRAY)) {
                                        colors.remove(colors.indexOf(Color.LTGRAY));

                                    }
                                    colors.add(Color.LTGRAY);
                                    metalAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Bio":
                                    if (colors.contains(Color.GREEN)) {
                                        colors.remove(colors.indexOf(Color.GREEN));
                                    }
                                    colors.add(Color.GREEN);
                                    bioAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Plastik":
                                    if (colors.contains(Color.YELLOW)) {
                                        colors.remove(colors.indexOf(Color.YELLOW));
                                    }
                                    colors.add(Color.YELLOW);
                                    plastikAmount = Integer.parseInt(snapshotData.getAmount());
                                    break;
                                case "Rest":
                                    if (colors.contains(Color.RED)) {
                                        colors.remove(colors.indexOf(Color.RED));
                                    }
                                    colors.add(Color.RED);
                                    restAmount =Integer.parseInt(snapshotData.getAmount());
                                    break;
                            }

                            //analysis.recordDataForAnalysis(Integer.parseInt(snapshotData.getAmount()), snapshotData.getType());


                        }
                        //make analysis and write txt to view.
                        analysis.setAmounts(metalAmount, bioAmount, plastikAmount, restAmount);
                        txtInfoBox3.setText(Html.fromHtml(analysis.getAnalysis("<i><b>Din aflevering i dag har betydet</i></b>")));
                        //txtInfoBox3.setText(analysis.getAnalysis());
                        String txt = "";
                        float co2Sparet = Integer.parseInt(analysis.co2SaverCalc());
                        if(co2Sparet > 1000.0){
                            txt = "Du har i dag sparet miljøet for " + co2Sparet/1000.0 + "kg CO2 ";
                        } else {
                            txt = "Du har i dag sparet miljøet for " + co2Sparet + "g CO2 ";
                        }
                        co2TextBox.setText(txt);
                        drawPieChart(values, labels, colors);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

    }
    public void drawPieChart(ArrayList<PieEntry> values, ArrayList<String> labels, ArrayList<Integer> colors){

        //initialize dataset and pass the data
        PieDataSet dataSet = new PieDataSet(values, "Dagens aflevering" );

        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLACK);

        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setSliceSpace(2f);
        dataSet.setColors(colors);

        //Initialize PieData
        PieData data = new PieData(dataSet);

        chart.setEntryLabelColor(Color.BLACK);

        chart.setUsePercentValues(true);
        chart.setDrawHoleEnabled(true);
        chart.setTransparentCircleRadius(30f);
        chart.setHoleRadius(30f);
        chart.getLegend().setEnabled(false);
        //Vi easer en lille smule mod slutningen af animationen.
        chart.animateXY(1400, 1400, Easing.EaseOutCubic);
        chart.setRotationEnabled(true);
        chart.setData(data);

        chart.highlightValues(null);
        chart.invalidate();
    }
}

