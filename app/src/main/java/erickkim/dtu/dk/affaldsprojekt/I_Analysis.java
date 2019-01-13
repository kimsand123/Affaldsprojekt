package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public interface I_Analysis {

    public String getHistoryAnalysis(ArrayList<Entry> metalHist, ArrayList<Entry> bioHist, ArrayList<Entry> plastikHist, ArrayList<Entry> restAmountHist);
    public String getDailyAnalysis();
    public String getFractionStory(String fraction, int fractionAmountInGrams, boolean multipleLines);
    public String co2SaverCalc();
    public void recordDataForDailyAnalysis(int amount, String type);


}
