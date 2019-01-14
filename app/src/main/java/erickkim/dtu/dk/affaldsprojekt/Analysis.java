package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

// tal taget fra forskellige miljø/genbrugs/oplysnings hjemmesider, og skal betragtes som vejledende,
// for at illustrere eksemplet.

public class Analysis implements I_Analysis {
    int metalAmount = 0;
    int bioAmount = 0;
    int plastikAmount = 0;
    int restAmount = 0;

    @Override
    public String getAnalysis(String startText) {
        String text = startText;
        if ( metalAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Metal", metalAmount, false);
        }
        if ( bioAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Bio", bioAmount, true);
        }
        if ( plastikAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Plastik", plastikAmount, true);
        }
        text = text + "<br>";
        return text;
    }

    @Override
    public void setAmounts (int metalAmount, int bioAmount, int plastikAmount, int restAmount){
        this.metalAmount = metalAmount;
        this.bioAmount = bioAmount;
        this.plastikAmount = plastikAmount;
        this.restAmount = restAmount;
    }

    @Override
    public String co2SaverCalc (){

        String text="";
        double resultat;
        double co2plast = 2;                   //Der spares 2 kg. CO2 når 1 kg plastic genanvendes
        double co2metal = 2;                   //Der spares 2 ton CO2, når 1 ton jern genanvendes
        double co2bio = 37000/1000000;         //Der spares en CO2 emission på 37 kg CO2 pr ton bioAffald
        DecimalFormat format = new DecimalFormat("#.###");

        resultat = metalAmount * co2metal;
        resultat = resultat + co2plast * plastikAmount;
        resultat = resultat + bioAmount * co2bio;
        metalAmount=0;
        plastikAmount=0;
        bioAmount=0;
        return format.format(resultat);
    }

    @Override
    public String getFractionStory(String fraction, int fractionAmountInGrams, boolean multipleLines){

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
                        double telefon = 138.0*.25;
                        textEnding = "";
                        textStart = "Man kunne";

                        if (multipleLines){
                            textStart = " Derudover kan man";
                        }
                        resultat = fractionAmountInGrams/telefon;
                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        text = text + textStart + " lave " + format.format(resultat) + " mobiltelefon" + textEnding + " med det afleverede metal.";
                        break;
                    case 2:
                        //Cykel 200 dåser jern eller aluminium til et cykelstel
                        double daase = 16.0;
                        textEnding = "el.";
                        textStart = "Du har";
                        double antalDaaser = fractionAmountInGrams/daase;
                        resultat = antalDaaser/200.0;

                        if (resultat >= 2.0){
                            textEnding = "ler.";
                        }
                        if (multipleLines) {
                            textStart = " Du har også";
                        }
                        text = text + textStart + " afleveret jern nok til at lave " + format.format(resultat) + " cyk" + textEnding;
                        break;
                }
                break;

            case "Bio":
                switch(number){
                    case 1:
                        //Et ton bioaffald bliver til 84 normalkubikmeter ren metan.
                        //En gasbus kan køre 44 km på denne mængde gas.
                        double metanm3prgram = 84.00;
                        double km = 44.00;
                        resultat = (fractionAmountInGrams/1000.0);
                        text = text + "En gasbus kan køre  " + format.format(km/metanm3prgram*resultat) + "m på mængden af metan fra dit Bioaffald.";
                        break;
                    case 2:
                        //Hvis gassen bruges til at producere elektricitet af, kan der produceres ca. 230 kWh energi pr ton bioaffald
                        //(830 MJ/ton). Det svarer til, at man kan lade en mobiltelefon op i 19 år, eller spille Play Station 4 i 1 år og 4
                        //måneder.
                        double watthprgram = 230000.0/1000000.0;
                        double watthpramount = watthprgram * fractionAmountInGrams;
                        double watthprminut = 230000.0/9986400.0;
                        resultat = watthprminut*watthpramount;


                        text = text + textStart + " kan lade din telefon op i " + format.format(resultat) + " minutter med energien fra dit Bioaffald.";
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

                    case 2:
                        //2 liter olie til at lave 1 kg ren plast
                        double oliesSparet = 2.0;
                        resultat = oliesSparet * fractionAmountInGrams;
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
