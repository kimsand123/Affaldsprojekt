package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Random;

public class Analysis implements I_Analysis {


    @Override
    public String getHistoryAnalysis(ArrayList<Entry> metalData, ArrayList<Entry> bioData, ArrayList<Entry> plastikData, ArrayList<Entry> restData) {

        //Co2 Beregning

        //Rest Andel bevægelse

        //Hvor meget af en fraktion har man afleveret og hvad svarer det til





        return null;
    }

    @Override
    public String getDailyAnalysis(ArrayList<PieEntry> dailyData) {
        return null;
    }

    private String getFractionComparison (String fraction, int fractionAmountInGrams){
    // tal taget fra
    // https://affald.dk/da/7-10/affald-og-miljo/artikler/255-fremstilling-af-elektronik-7-10.html


        String text=null;

        int number = getRandom();
        switch (fraction){

            case "Metal":
                switch(number){
                    case 1:
                        //Mobiltelefon 25% metal
                        break;
                    case 2:
                        //Cykel 200 dåser jern eller aluminium
                        break;
                    case 3:
                        break;
                }
                break;

            case "Bio":
                switch(number){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;

            case "Plastik":
                switch(number){
                    case 1:
                        //Mobiltelefon 56% plastik
                        break;
                    case 2:
                        //Der går 10 saftevandsflasker til at fremstille et par fiber-handsker.
                        break;
                    case 3:
                        break;
                }
                break;

            case "Rest":
                switch(number){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;

        }


        return text;
    }

    private int getRandom(){
        Random rnd = new Random();
        int number = rnd.nextInt(3)+1
        return number;
    }
}
