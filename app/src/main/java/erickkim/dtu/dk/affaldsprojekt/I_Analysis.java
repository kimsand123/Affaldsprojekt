package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public interface I_Analysis {

    public String getHistoryAnalysis(ArrayList<Entry> metalData, ArrayList<Entry> bioData, ArrayList<Entry> plastikData, ArrayList<Entry> restData );
    public String getDailyAnalysis(ArrayList<PieEntry> dailyData);

}
