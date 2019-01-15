package erickkim.dtu.dk.affaldsprojekt;

import java.text.DecimalFormat;
import java.util.Random;

// tal taget fra forskellige miljø/genbrugs/oplysnings hjemmesider, og skal betragtes som vejledende,
// for at illustrere eksemplet.

public class Analysis implements I_Analysis {
    int metPlaGlaAmount = 0;
    int bioAmount = 0;
    int papPapiAmount = 0;
    int restAmount = 0;

    @Override
    public String getAnalysis(String startText) {
        String text = startText;
        if ( metPlaGlaAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Metal/Plastik/Glas", metPlaGlaAmount, false);
        }
        if ( bioAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Bio", bioAmount, true);
        }
        if ( papPapiAmount !=0) {
            text = text + "<br><br>" + getFractionStory("Pap/Papir", papPapiAmount, true);
        }
        text = text + "<br>";
        return text;
    }

    @Override
    public void setAmounts (int metalAmount, int bioAmount, int plastikAmount, int restAmount){
        this.metPlaGlaAmount = metalAmount;
        this.bioAmount = bioAmount;
        this.papPapiAmount = plastikAmount;
        this.restAmount = restAmount;
    }

    @Override
    public String co2SaverCalc (){

        double resultat;
        double co2pappapi = 2;                   //Der spares 2 kg. CO2 når 1 kg plastic genanvendes
        double co2metplagla = 2;                   //Der spares 2 ton CO2, når 1 ton jern genanvendes
        double co2bio = 37000/1000000;         //Der spares en CO2 emission på 37 kg CO2 pr ton bioAffald
        DecimalFormat format = new DecimalFormat("#.###");

        resultat = metPlaGlaAmount * co2metplagla;
        resultat = resultat + co2pappapi * papPapiAmount;
        resultat = resultat + bioAmount * co2bio;
        metPlaGlaAmount =0;
        papPapiAmount =0;
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

            case "Metal/Plastik/Glas":
                switch(number){
                    case 1:
                        //Mobiltelefon 25% metal
                        double telefonMetal = 138.0*.25;
                        textEnding = "";
                        textStart = "Man kunne";

                        if (multipleLines){
                            textStart = " Derudover kan man";
                        }
                        resultat = fractionAmountInGrams/telefonMetal;
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
                    case 3:
                        //Mobiltelefon 56% plastik

                        double telefonPlastik = 138*.56;
                        textEnding = "";
                        textStart = "Du har";

                        if (multipleLines){
                            textStart = " Derudover har du";
                        }
                        resultat = fractionAmountInGrams/telefonPlastik;
                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        text = text + textStart + " afleveret en mængde plastik der svarer til hvad man skal bruge for at lave " + format.format(resultat) + " mobiltelefon" + textEnding;
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

            case "Pap/Papir":
                switch(number){
                    case 1:
                        break;

                    case 2:
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
