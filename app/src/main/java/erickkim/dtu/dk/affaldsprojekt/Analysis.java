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
                        //Der spares 2 ton CO2, når 1 ton jern genanvendes
                        break;
                }
                break;

            case "Bio":
                switch(number){
                    case 1:
                        // Der spares en CO2 emission på 37 kg CO2 pr ton bioAffald
                        break;
                    case 2:
                        //Et ton bioaffald bliver til 84 normalkubikmeter ren metan.
                        //En gasbus kan køre 44 km på denne mængde gas.
                        break;
                    case 3:
                        //Hvis gassen bruges til at producere elektricitet af, kan der produceres ca. 230 kWh energi pr ton bioaffald
                        //(830 MJ/ton). Det svarer til, at man kan lade en mobiltelefon op i 19 år, eller spille Play Station 4 i 1 år og 4
                        //måneder.
                        break;
                }
                break;

            case "Plastik":
                switch(number){
                    case 1:
                        //Mobiltelefon 56% plastik
                        break;
                    case 2:
                        //1 kg genanvendt plast sparer miljøet for 2 kg CO2
                        break;
                    case 3:
                        //2 liter olie til at lave 1 kg ren plast

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
        int number = rnd.nextInt(3)+1;
        return number;
    }
}
