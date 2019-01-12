package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Random;

public class Analysis implements I_Analysis {
    int metalAmount, bioAmount, plastikAmount, restAmount;

    //Co2 Beregning  DONE UDEN REST affald

    //Rest Andel bevægelse

    //Hvor meget af en fraktion har man afleveret og hvad svarer det til  DONE UDEN REST affald


    @Override
    public String getHistoryAnalysis(ArrayList<Entry> metalData, ArrayList<Entry> bioData, ArrayList<Entry> plastikData, ArrayList<Entry> restData) {






        return null;
    }
    @Override
    public void recordDataForDailyAnalysis(int amount, String type){
        switch(type){
            case "Metal":
                metalAmount= amount;
                break;
            case "Bio":
                bioAmount= amount;
                break;
            case "Plastik":
                plastikAmount= amount;
                break;
            case "Rest":
                restAmount=amount;
                break;
        }
    }

    @Override
    public String getDailyAnalysis() {
        String text = "Din aflevering i dag har betydet: \n";
        if ( metalAmount!=0) {
            text = text + getFractionStory("Metal", metalAmount, false);
        }
        if ( bioAmount !=0) {
            text = text + "\n" + getFractionStory("Bio", bioAmount, true);
        }
        if ( plastikAmount !=0) {
            text = text + "\n" + getFractionStory("Plastik", plastikAmount, true);
        }
        text = text + "\n";
        text = text + co2SaverCalc( metalAmount, plastikAmount, bioAmount);

        //Dit restaffald var højere i dag end dit gennemsnit.
        return text;
    }

    @Override
    public String co2SaverCalc (int metalFractionAmountInGrams, int plastikFractionAmountInGrams, int bioFractionAmountInGrams){
        String text="";
        double resultat;
        double co2 = 1000000;
        double co2sparet = 1000/2000;         //Der spares 2 ton CO2, når 1 ton jern genanvendes
        double co2prgram = 37000/1000000;        // Der spares en CO2 emission på 37 kg CO2 pr ton bioAffald
        DecimalFormat format = new DecimalFormat("#.######");

        resultat = metalFractionAmountInGrams/co2;
        resultat = resultat + co2sparet * plastikFractionAmountInGrams;
        resultat = resultat + bioFractionAmountInGrams * co2prgram;

        return "Du har i dag sparet miljøet for " + format.format(resultat) + "g CO2";
    }

    @Override
    public String getFractionStory(String fraction, int fractionAmountInGrams, boolean multipleLines){
    // tal taget fra forskellige miljø/genbrugs/oplysnings hjemmesider, og skal betragtes som vejledende, for at illustrere
    // eksemplet.
        String text="";
        int number = getRandom();
        double resultat;
        String textEnding="";
        String textStart="";
        DecimalFormat format = new DecimalFormat("#.####");

        switch (fraction){

            case "Metal":
                switch(number){
                    case 1:
                        //Mobiltelefon 25% metal
                        double telefon = 138*.25;
                        textEnding = "";
                        textStart = "Man kunne";

                        if (multipleLines){
                            textStart = " Derudover kan man";
                        }
                        resultat = fractionAmountInGrams/telefon;
                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        text = text + textStart + " lave " + format.format(resultat) + " mobiltelefon" + textEnding + " med den mængde metal du har afleveret.";
                        break;
                    case 2:
                        //Cykel 200 dåser jern eller aluminium
                        int dåse = 16;
                        textEnding = "el.";
                        textStart = "Du har";

                        double antalDaaser = fractionAmountInGrams/dåse;
                        resultat = 200/antalDaaser;
                        if (resultat >= 2.0){
                            textEnding = "ler.";
                        }
                        if (multipleLines) {
                            textStart = " Du har også";
                        }
                        text = text + textStart + " afleveret jern nok til at lave " + format.format(resultat) + " cyk" + textEnding;
                        break;
                    /*case 3:
                        //text = text + co2SaverCalc(fraction, fractionAmountInGrams, multipleLines);
                        break;*/
                }
                break;

            case "Bio":
                switch(number){
                   /* case 1:
                        text = text + co2SaverCalc(fraction, fractionAmountInGrams, multipleLines);
                        break;*/
                    case 1:
                        //Et ton bioaffald bliver til 84 normalkubikmeter ren metan.
                        //En gasbus kan køre 44 km på denne mængde gas.
                        int metanm3prgram = 84/1000000;
                        resultat = fractionAmountInGrams * metanm3prgram;

                        textStart = "Vidste du at";
                        if (multipleLines) {
                            textStart = " Vidste du også at";
                        }
                        text = text + textStart + " en gasbus kan køre  " + format.format(44/84*resultat) + "km på den mængde " + format.format(resultat) + "m3 rene metan du har genereret med dit Bioaffald.";

                        break;
                    case 2:
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

                        double telefon = 138*.56;
                        textEnding = "";
                        textStart = "Du har";

                        if (multipleLines){
                            textStart = " Derudover har du";
                        }
                        resultat = fractionAmountInGrams/telefon;
                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        text = text + textStart + " afleveret en mængde plastik der svarer til hvad man skal bruge for at lave " + format.format(resultat) + " mobiltelefon" + textEnding;
                        break;
                   /* case 2:
                        //1 kg genanvendt plast sparer miljøet for 2 kg CO2
                        text = text + co2SaverCalc(fraction, fractionAmountInGrams, multipleLines);
                        break;*/
                    case 2:
                        //2 liter olie til at lave 1 kg ren plast
                        double oliesSparet = 2000/1000;
                        resultat = oliesSparet * (fractionAmountInGrams/1000);
                        textStart = "Du har";
                        text = text + textStart + " sparet " + format.format(resultat) + "g olie ved at aflevere " + fractionAmountInGrams + "g plastik";
                        break;
                }
                break;
        }
        return text;
    }

    private int getRandom(){
        Random rnd = new Random();
        int number = rnd.nextInt(2)+1;
        return number;
    }
}
