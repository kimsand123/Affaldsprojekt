package erickkim.dtu.dk.affaldsprojekt;

import android.graphics.Color;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
import java.util.Objects;


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
        // createLineChart();

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

    /* private void createLineChart(){

        String userId = Data_Controller.getInstance().getUserId();

        final ArrayList<Entry> values = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<String> xDataSet = new ArrayList<>();
                        ArrayList<Entry> yDataSet = new ArrayList<>();

                        int taller=0;
                        TYPE currentType=TYPE.Metal;

                        //For hvert barn i datasnapshot.
                        for (DataSnapshot bigSnapShot : dataSnapshot.getChildren()) {
                            String date = bigSnapShot.getKey();
                            for (DataSnapshot dateSnapShot : bigSnapShot.getChildren()) {

                                if (Long.parseLong(date) >= Long.parseLong(Data_Controller.getInstance().getLongToday()) - 7776000) {
                                   if(dateSnapShot.child("amount").getValue().equals("Metal")){
                                       Float amount = Float.parseFloat(dateSnapShot.child("amount").getValue());
                                       yDataSet.add(new Entry(amount, taller));
                                   }


                                    for (DataSnapshot dropSnapShot : dateSnapShot.getChildren()) {

                                        if (dropSnapShot.getKey().equals("Metal")) {
                                            xDataSet.add("test "+taller);

                                            yDataSet.add(new Entry(Float.parseFloat(dateSnapShot.child("amount").getValue()), taller));
                                        }
                                    }
                                }
                                taller++;
                            }
                        }
                        drawChart(yDataSet);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    } */

    private void drawChart(ArrayList<Entry> yDataSet) {
        LineDataSet lineDataSet = new LineDataSet(yDataSet, "OVERSKRIFT");

        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);

    }
}
