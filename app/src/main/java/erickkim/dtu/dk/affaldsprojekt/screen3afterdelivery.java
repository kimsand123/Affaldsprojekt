package erickkim.dtu.dk.affaldsprojekt;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class screen3afterdelivery extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //instantier variable
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View root;
    private Button statisticButton;
    private TextView txtCoinBox3 ;
    private TextView txtInfoBox3;
    private ArrayList<PieEntry> piedata;
    private PieChart chart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public screen3afterdelivery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment screen3afterdelivery.
     */
    // TODO: Rename and change types and number of parameters
    public static screen3afterdelivery newInstance(String param1, String param2) {
        screen3afterdelivery fragment = new screen3afterdelivery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_screen3afterdelivery, container, false);
        chart = root.findViewById(R.id.pieChart);
        getDataForPieChart();


        //initialiser views
        statisticButton = root.findViewById(R.id.statisticButton);
        txtCoinBox3 = root.findViewById(R.id.txtCoinBox3);
        txtInfoBox3 = root.findViewById(R.id.txtInfoBox3);

        //Hent data til TextViews.
        txtInfoBox3.setText(Data_Controller.getInstance().getTip());
        txtCoinBox3.setText("GarbageCoins: " + Data_Controller.getInstance().getTrashCoins());

        //TODO: Hent data til piechart.

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
        //Kode taget fra Galgeleg III
        LoadHandler lh = new LoadHandler();
        if (lh.getStatus() != AsyncTask.Status.FINISHED) {
            lh.execute();
        }
    }

    public class LoadHandler extends AsyncTask {

        @Override
        protected Object doInBackground (Object[]objects){
            piedata = Data_Controller.getInstance().getPieData();
            return null;
        }

        @Override
        protected void onPostExecute (Object o){
            super.onPostExecute(o);
            ArrayList<String> labels = new ArrayList<String>();
            ArrayList<PieEntry> values = new ArrayList<>();

            for (int counter = 0; counter<piedata.size();counter++){
                labels.add(new String(piedata.get(counter).getLabel()));
                values.add(new PieEntry((float)piedata.get(counter).getValue(), counter));
            }

            PieDataSet dataSet = new PieDataSet(values, "gram" );
            dataSet.setValueFormatter(new PercentFormatter());
            dataSet.setValueTextSize(11f);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            PieData data = new PieData(dataSet);

            chart.setData(data);
            chart.highlightValues(null);
            chart.invalidate();

            //load pieview with data.
            //finish();
        }
    }
}

