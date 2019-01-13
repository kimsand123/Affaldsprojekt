package erickkim.dtu.dk.affaldsprojekt;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
    private TextView txtCoinBox;
    private LineChart statisticChart;
    private Data_DTO_ChartBundle[] dataBundle;
    private I_Analysis analysis = new Analysis();

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
        txtCoinBox = root.findViewById(R.id.txtCoinBox1);
        txtCoinBox.setText("" + Data_Controller.getInstance().getTrashCoins());
        statisticChart = root.findViewById(R.id.lineChart);
        statisticChart.setOnChartGestureListener(this);
        statisticChart.setOnChartValueSelectedListener(this);
        createLineChart();

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

    //from https://www.studytutorial.in/android-line-chart-or-line-graph-using-mpandroid-library-tutorial
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

    private void createLineChart(){
        String userId = Data_Controller.getInstance().getUserId();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //ArrayList<String> xDataSet = new ArrayList<>();
                        ArrayList<Entry> yDataSetMetal = new ArrayList<>();
                        ArrayList<Entry> yDataSetBio = new ArrayList<>();
                        ArrayList<Entry> yDataSetPlastik = new ArrayList<>();
                        ArrayList<Entry> yDataSetRest = new ArrayList<>();

                        int taller=0;
                        String lastdate="";
                        float metalAmount=0, plastikAmount=0, restAmount=0, bioAmount=0;
                        //For hvert barn i datasnapshot.
                        for (DataSnapshot bigSnapShot : dataSnapshot.getChildren()) {
                            String date = bigSnapShot.getKey();
                            for (DataSnapshot dateSnapShot : bigSnapShot.getChildren()) {
                                Data_DTO_ChartBundle dataBundle =  dateSnapShot.getValue(Data_DTO_ChartBundle.class);
                                if (Long.parseLong(date) >= Long.parseLong(Data_Controller.getInstance().getLongToday()) - 7776000000L) {
                                        if (!(lastdate.equals(date)||lastdate.equals(""))) {
                                            yDataSetBio.add(new Entry(taller, bioAmount));
                                            yDataSetMetal.add(new Entry(taller, metalAmount));
                                            yDataSetPlastik.add(new Entry(taller, plastikAmount));
                                            yDataSetRest.add(new Entry(taller, restAmount));
                                            /*metalAmount=0;
                                            bioAmount=0;
                                            plastikAmount=0;
                                            restAmount=0;*/
                                        }
                                String type = dataBundle.type;
                                switch (type){
                                    case "Metal" :
                                        metalAmount++;
                                        break;
                                    case "Bio":
                                        bioAmount++;
                                        break;
                                    case "Plastik":
                                        plastikAmount++;
                                        break;
                                    case "Rest":
                                        restAmount++;
                                        break;
                                }
                                lastdate = date.toString();
                                }
                                taller++;
                            }
                        }

                        String analysisText = analysis.getHistoryAnalysis(yDataSetBio, yDataSetMetal, yDataSetPlastik, yDataSetRest);
                        //write text to view.
                        drawChart(yDataSetMetal, yDataSetBio, yDataSetPlastik, yDataSetRest);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    }

    private void drawChart(ArrayList<Entry> yDataSetMetal, ArrayList<Entry> yDataSetBio, ArrayList<Entry>yDataSetPlastik, ArrayList<Entry>yDataSetRest) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        if(yDataSetMetal.size()!=0) {
            LineDataSet lineDataSetMetal = new LineDataSet(yDataSetMetal, "Metal");

            lineDataSetMetal.setColor(Color.LTGRAY);
            lineDataSetMetal.setCircleColor(Color.LTGRAY);
            lineDataSetMetal.setLineWidth(1f);
            lineDataSetMetal.setCircleRadius(3f);
            lineDataSetMetal.setDrawCircleHole(false);
            lineDataSetMetal.setValueTextSize(9f);
            lineDataSetMetal.setDrawFilled(false);

            dataSets.add(lineDataSetMetal);
        }
        if(yDataSetRest.size()!=0) {
            LineDataSet lineDataSetRest = new LineDataSet(yDataSetRest, "Reest");

            lineDataSetRest.setColor(Color.RED);
            lineDataSetRest.setCircleColor(Color.RED);
            lineDataSetRest.setLineWidth(1f);
            lineDataSetRest.setCircleRadius(3f);
            lineDataSetRest.setDrawCircleHole(false);
            lineDataSetRest.setValueTextSize(9f);
            lineDataSetRest.setDrawFilled(false);

            dataSets.add(lineDataSetRest);

        } if(yDataSetBio.size()!=0) {
            LineDataSet lineDataSetBio = new LineDataSet(yDataSetBio, "Bio");

            lineDataSetBio.setColor(Color.GREEN);
            lineDataSetBio.setCircleColor(Color.GREEN);
            lineDataSetBio.setLineWidth(1f);
            lineDataSetBio.setCircleRadius(3f);
            lineDataSetBio.setDrawCircleHole(false);
            lineDataSetBio.setValueTextSize(9f);
            lineDataSetBio.setDrawFilled(false);

            dataSets.add(lineDataSetBio);

        } if(yDataSetPlastik.size()!=0) {
            LineDataSet lineDataSetPlastik = new LineDataSet(yDataSetPlastik, "Plastik");

            lineDataSetPlastik.setColor(Color.YELLOW);
            lineDataSetPlastik.setCircleColor(Color.YELLOW);
            lineDataSetPlastik.setLineWidth(1f);
            lineDataSetPlastik.setCircleRadius(3f);
            lineDataSetPlastik.setDrawCircleHole(false);
            lineDataSetPlastik.setValueTextSize(9f);
            lineDataSetPlastik.setDrawFilled(false);

            dataSets.add(lineDataSetPlastik);
        }

        LineData data = new LineData(dataSets);

        statisticChart.setData(data);
        statisticChart.invalidate();

    }
}
