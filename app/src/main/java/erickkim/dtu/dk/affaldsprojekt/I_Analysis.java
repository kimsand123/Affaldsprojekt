package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public interface I_Analysis {

    public String getAnalysis(String startText);
    public String getFractionStory(String fraction, int fractionAmountInGrams, boolean multipleLines);
    public String co2SaverCalc();
    public void setAmounts (int metPlaGlaAmount, int bioAmount, int papPapiAmount, int restAmount);


}
