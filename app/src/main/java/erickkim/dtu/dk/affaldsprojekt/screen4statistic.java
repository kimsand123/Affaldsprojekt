package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.InternalTokenResult;
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.ListIterator;


public class screen4statistic extends Fragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {

    private View root;
    private Button becomeBetterButton;
    private TextView txtCoinBox4;
    private LineChart statisticChart;
    private Data_DTO_ChartBundle[] dataBundle;


    public screen4statistic() {
        // Required empty public constructor
    }

    public static screen4statistic newInstance(String param1, String param2) {
        screen4statistic fragment = new screen4statistic();
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
      root = inflater.inflate(R.layout.fragment_screen4statistic, container, false);

        becomeBetterButton = root.findViewById(R.id.hvordanBliverJegBedreButton);
        becomeBetterButton.setOnClickListener(this);

        // statistic = root.findViewById(R.id.statistic);
        txtCoinBox4 = root.findViewById(R.id.txtCoinBox4);
        txtCoinBox4.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());
        statisticChart = root.findViewById(R.id.lineChart);
        statisticChart.setOnChartGestureListener(this);
        statisticChart.setOnChartValueSelectedListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        //check view objektet og skift til den tilhørende case.
        switch(v.getId()) {
              case R.id.hvordanBliverJegBedreButton:
                //kør fragmentet for Screen2delivery.
               /*getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentContent, new screen5becomebetter())
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();*/
                break;
        }
    }

    //Idea from https://www.studytutorial.in/android-line-chart-or-line-graph-using-mpandroid-library-tutorial
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d("Gesture","Start, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d("Gesture", "End, lastGesture: " + lastPerformedGesture);
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP){
            statisticChart.highlightValue(null);
        }

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.d ("LongPress","Chart longpressed.");

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.d("DoubleTap", "Chart double-tapped");

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.d("SingleTap","Chart single-tapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.d("Scale / Zoom","ScaleX: " + scaleX + ", ScaleY: " + scaleX);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.d("Entry selected", e.toString());
        Log.d("LowHigh", "low: " + statisticChart.getLowestVisibleX()+
                                    ", high: " + statisticChart.getHighestVisibleX());
        Log.d("MIN MAX", "xmin: " + statisticChart.getXChartMax()+
                                    ", xmax: " + statisticChart.getXChartMax()+
                                    ", ymin: " + statisticChart.getYChartMin()+
                                    ", ymax: " + statisticChart.getYChartMax());

    }

    @Override
    public void onNothingSelected() {
        Log.d("Nothing selected","Nothing selected");
    }

    private void buildChart(Data_DTO_ChartBundle[] dataBundle){
        LineDataSet metal;
        LineDataSet bio;
        LineDataSet plastik;
        LineDataSet rest;


    }

   /* private LineData populateDataSet(Data_DTO_ChartBundle[] dataBundle, String type){
        LineData dataset;
        String date = Data_Controller.getInstance().getToday()-30;
        String userId = Data_Controller.getInstance().getUserId();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId).child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_DTO_ChartBundle snapshotData;
                        //For hvert barn i datasnapshot.
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshotData = snapshot.getValue(Data_DTO_ChartBundle.class);

                            ListIterator<Entry> listElements = values.listIterator();
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
                        }
                        drawPieChart(values, labels);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });


        return dataset;

    }*/
}
