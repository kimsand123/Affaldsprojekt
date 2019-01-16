package erickkim.dtu.dk.affaldsprojekt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class screen4statistic extends Fragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {

    private View root;
    private TextView txtGoldBox;
    private TextView textAnalyseBox;
    private TextView co2TextBox2;
    private LineChart statisticChart;
    private I_GenerateFeedback feedback = new GenerateFeedback();
    private ImageView imgGoldBox;

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

        // statistic = root.findViewById(R.id.statistic);
        txtGoldBox = root.findViewById(R.id.txtCoinBox1);
        txtGoldBox.setText(Data_Controller.getInstance().getGoldBoxContent());
        imgGoldBox = root.findViewById(R.id.imgGoldBox);
        if (Data_Controller.getInstance().getUserType().equals("virksomhed"))
            imgGoldBox.setVisibility(View.INVISIBLE);

        co2TextBox2 = root.findViewById(R.id.co2TextBox2);
        textAnalyseBox = root.findViewById(R.id.textAnalyseBox);
        statisticChart = root.findViewById(R.id.lineChart);
        statisticChart.setOnChartGestureListener(this);
        statisticChart.setOnChartValueSelectedListener(this);
        createLineChart();

        return root;
    }

    @Override
    public void onClick(View v) {

    }

    //Alt omkring Chart i denne klasse er inspireret fra
    //https://www.studytutorial.in/android-line-chart-or-line-graph-using-mpandroid-library-tutorial
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

    private void createLineChart(){
        String userId = Data_Controller.getInstance().getUserId();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //ArrayList<String> xDataSet = new ArrayList<>();
                        ArrayList<Entry> yDataSetMetPlaGla = new ArrayList<>();
                        yDataSetMetPlaGla.add(new Entry(0,0));
                        ArrayList<Entry> yDataSetBio = new ArrayList<>();
                        yDataSetBio.add(new Entry(0,0));
                        ArrayList<Entry> yDataSetPapPapi = new ArrayList<>();
                        yDataSetPapPapi.add(new Entry(0,0));
                        ArrayList<Entry> yDataSetRest = new ArrayList<>();
                        yDataSetRest.add(new Entry(0,0));

                        int taller=1;
                        String lastdate="";
                        int metPlaGlaAmountDaily=0, papPapiAmountDaily=0, restAmountDaily=0, bioAmountDaily=0;
                        Long nintyDaysAgo = Long.parseLong(Data_Controller.getInstance().getLongToday())-7776000000L;
                        int totalBio=0, totalPlaGla=0, totalMetPapPapi=0, totalRest=0;
                        //Hent Alle data for konkret bruger
                        for (DataSnapshot bigSnapShot : dataSnapshot.getChildren()) {
                            //næste dato
                            String date = bigSnapShot.getKey();
                            // snapshot for hver deposit
                            for (DataSnapshot depositSnapShot : bigSnapShot.getChildren()) {
                                //hent deposit data
                                Data_DTO_delivery dataBundle =  depositSnapShot.getValue(Data_DTO_delivery.class);

                                //Hvis datoen er større end for 90 dage siden.
                                if (Long.parseLong(date) >= nintyDaysAgo) {
                                        // Hvis det er samme dato og der er flere af samme type deposit eller 1. gang
                                        // Så skal de deposits lægges sammen inden de registreres i datastrukturen.
                                    if (!(lastdate.equals(date)||lastdate.equals(""))) {
                                        metPlaGlaAmountDaily=0;
                                        bioAmountDaily=0;
                                        papPapiAmountDaily=0;
                                        restAmountDaily=0;
                                    }
                                    switch (dataBundle.getType()) {
                                        case "Bio":
                                            bioAmountDaily = bioAmountDaily + Integer.parseInt(dataBundle.getAmount());
                                            break;
                                        case "Metal/Plastik/Glas":
                                            metPlaGlaAmountDaily = metPlaGlaAmountDaily + Integer.parseInt((dataBundle.getAmount()));
                                            break;
                                        case "Pap/Papir":
                                            papPapiAmountDaily = papPapiAmountDaily + Integer.parseInt(dataBundle.getAmount());
                                            break;
                                        case "Rest":
                                            restAmountDaily = Integer.parseInt(dataBundle.getAmount());
                                            break;
                                    }


                                lastdate = date.toString();
                                }
                            }
                            totalBio = totalBio + bioAmountDaily;
                            totalPlaGla = totalPlaGla + metPlaGlaAmountDaily;
                            totalMetPapPapi = totalMetPapPapi + papPapiAmountDaily;
                            totalRest = totalRest + restAmountDaily;

                            // tilføj opsummeret data til datasets
                            yDataSetBio.add(new Entry(taller, totalBio));
                            yDataSetMetPlaGla.add(new Entry(taller, totalPlaGla));
                            yDataSetPapPapi.add(new Entry(taller, totalMetPapPapi));
                            yDataSetRest.add(new Entry(taller, totalRest));

                            //When this is commented out, it is the accumulated amount, otherwise it is the day to day
                                        /*metPlaGlaAmount=0;
                                          bioAmount=0;
                                          papPapiAmount=0;
                                          restAmountDaily=0;*/
                            taller++;
                        }

                        //Send det akkumulerede total tal for hver fraction til feedback
                        feedback.setAmounts((int)yDataSetMetPlaGla.get(yDataSetMetPlaGla.size()-1).getY(),
                                            (int)yDataSetBio.get(yDataSetBio.size()-1).getY(),
                                            (int)yDataSetPapPapi.get(yDataSetPapPapi.size()-1).getY(),
                                            (int)yDataSetRest.get(yDataSetRest.size()-1).getY());
                        //Hent user type
                        String userType = Data_Controller.getInstance().getUserType();
                        //Hent co2 besparelse
                        float co2Sparet = Integer.parseInt(feedback.co2SaverCalc());

                        if(userType.equals("virksomhed")){
                            textAnalyseBox.setText((Html.fromHtml(feedback.getAnalysis("<i><b>Jeres kvartalsaflevering har givet følgende indtjening</i></b>","virksomhed"))));
                        }else {
                            textAnalyseBox.setText(Html.fromHtml(feedback.getAnalysis("<i><b>Din kvartalsaflevering har betydet</i></b>","borger")));
                        }
                        String txt;

                        if(co2Sparet > 1000.0){
                            txt = "I har i dag sparet miljøet for " + co2Sparet/1000.0 + "kg CO2";

                        } else {
                            txt = "I har i dag sparet miljøet for " + co2Sparet + "g CO2";
                        }
                        co2TextBox2.setText(txt);
                        drawChart(yDataSetMetPlaGla, yDataSetBio, yDataSetPapPapi, yDataSetRest);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
    }

    private void drawChart(ArrayList<Entry> yDataSetMetPlaGla, ArrayList<Entry> yDataSetBio, ArrayList<Entry>yDataSetPapPapi, ArrayList<Entry>yDataSetRest) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        if(yDataSetMetPlaGla.size()!=0) {
            LineDataSet lineDataSetMetPlaGla = new LineDataSet(yDataSetMetPlaGla, "Metal/Plastik/Glas");

            lineDataSetMetPlaGla.setColor(Color.LTGRAY);
            lineDataSetMetPlaGla.setCircleColor(Color.LTGRAY);
            lineDataSetMetPlaGla.setLineWidth(1f);
            lineDataSetMetPlaGla.setCircleRadius(2f);
            lineDataSetMetPlaGla.setDrawCircleHole(false);
            lineDataSetMetPlaGla.setValueTextSize(9f);
            lineDataSetMetPlaGla.setDrawFilled(false);
            lineDataSetMetPlaGla.setValueTextColor(Color.LTGRAY);

            dataSets.add(lineDataSetMetPlaGla);
        }
        if(yDataSetRest.size()!=0) {
            LineDataSet lineDataSetRest = new LineDataSet(yDataSetRest, "Rest");

            lineDataSetRest.setColor(Color.RED);
            lineDataSetRest.setCircleColor(Color.RED);
            lineDataSetRest.setLineWidth(1f);
            lineDataSetRest.setCircleRadius(2f);
            lineDataSetRest.setDrawCircleHole(false);
            lineDataSetRest.setValueTextSize(9f);
            lineDataSetRest.setDrawFilled(false);
            lineDataSetRest.setValueTextColor(Color.RED);

            dataSets.add(lineDataSetRest);

        } if(yDataSetBio.size()!=0) {
            LineDataSet lineDataSetBio = new LineDataSet(yDataSetBio, "Bio");

            lineDataSetBio.setColor(Color.GREEN);
            lineDataSetBio.setCircleColor(Color.GREEN);
            lineDataSetBio.setLineWidth(1f);
            lineDataSetBio.setCircleRadius(2f);
            lineDataSetBio.setDrawCircleHole(false);
            lineDataSetBio.setValueTextSize(9f);
            lineDataSetBio.setDrawFilled(false);
            lineDataSetBio.setValueTextColor(Color.GREEN);

            dataSets.add(lineDataSetBio);

        } if(yDataSetPapPapi.size()!=0) {
            LineDataSet lineDataSetPapPapi = new LineDataSet(yDataSetPapPapi, "Pap/Papir");

            lineDataSetPapPapi.setColor(Color.YELLOW);
            lineDataSetPapPapi.setCircleColor(Color.YELLOW);
            lineDataSetPapPapi.setLineWidth(1f);
            lineDataSetPapPapi.setCircleRadius(2f);
            lineDataSetPapPapi.setDrawCircleHole(false);
            lineDataSetPapPapi.setValueTextSize(9f);
            lineDataSetPapPapi.setDrawFilled(false);
            lineDataSetPapPapi.setValueTextColor(Color.YELLOW);

            dataSets.add(lineDataSetPapPapi);
        }

        LineData data = new LineData(dataSets);

        statisticChart.setData(data);
        statisticChart.invalidate();

    }
}
