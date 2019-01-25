package erickkim.dtu.dk.affaldsprojekt.utilities;

import java.text.DecimalFormat;
import java.util.Random;

import erickkim.dtu.dk.affaldsprojekt.interfaces.I_GenerateFeedback;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;

// tal taget fra forskellige miljø/genbrugs/oplysnings hjemmesider, og skal betragtes som vejledende,
// for at illustrere eksemplet.

public class GenerateFeedback implements I_GenerateFeedback {
    int metPlaGlaAmount = 0;
    int bioAmount = 0;
    int papPapiAmount = 0;
    int restAmount = 0;


    @Override
    public String getAnalysis(String startText, String userID) {
        String text = startText;
        int choseStory = getRandom(3);
        switch (userID){
            case "virksomhed":
                if (metPlaGlaAmount !=0 && choseStory==1){
                    text = text + "<br><br>" + getFractionStoryCompany("Metal/Plastik/Glas", metPlaGlaAmount) + " for Metal/Plastik/Glas";
                }
                if ( bioAmount !=0 && choseStory==2) {
                    text = text + "<br><br>" + getFractionStoryCompany("Bio", bioAmount)+ " for Bio";
                }
                if ( papPapiAmount !=0 && choseStory==3) {
                    text = text + "<br><br>" + getFractionStoryCompany("Pap/Papir", papPapiAmount) + " for Pap/Papir";
                }
                break;

            case "borger":
                if ( metPlaGlaAmount !=0 && choseStory==1) {
                    text = text + "<br><br>" + getFractionStoryCitizen("Metal/Plastik/Glas", metPlaGlaAmount, false);
                }
                if ( bioAmount !=0 && choseStory==2) {
                    text = text + "<br><br>" + getFractionStoryCitizen("Bio", bioAmount, true);
                }
                if ( papPapiAmount !=0 && choseStory==3) {
                    text = text + "<br><br>" + getFractionStoryCitizen("Pap/Papir", papPapiAmount, true);
                }
                break;

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
        return format.format(resultat);
    }

    @Override
    public String getFractionStoryCompany(String fraction, int FractionAmountInGrams){
        DecimalFormat format = new DecimalFormat("#.####");
        GetTrashValue getValue = new GetTrashValue();
        String text = "Virksomheden har indtjent " + format.format(Math.round(getValue.convertToValue(fraction, FractionAmountInGrams))) + " kr.";
        return text;
    }

    @Override
    public String getFractionStoryCitizen(String fraction, int fractionAmountInGrams, boolean multipleLines){

        String text="";
        int number = getRandom(3);
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
                        textStart = "Hvis det var metal, kunne man";

                        if (multipleLines){
                            textStart = " Derudover kan man, hvis det er metal, ";
                        }
                        resultat = fractionAmountInGrams/telefonMetal;
                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        text = text + textStart + " lave " + format.format(resultat) + " mobiltelefon" + textEnding + " med det afleverede metal.";
                        break;
                    case 2:
                        //Cykel 200 dåser jern eller aluminium til et cykelstel
                        double flaske = 150.0;
                        textEnding = "e.";
                        textStart = "Hvis det er glas har du";
                        resultat = fractionAmountInGrams/flaske;

                        if (resultat >= 2.0){
                            textEnding = "er.";
                        }
                        if (multipleLines) {
                            textStart = " hvis det er glas har du også";
                        }
                        text = text + textStart + " afleveret nok til at lave " + format.format(resultat) + " flask" + textEnding;
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
                        text = text + "Du kan lade din telefon op i " + format.format(resultat) + " minutter med energien fra dit Bioaffald.";
                        break;
                    case 3:
                        //DETTE ER IKKE RIGTIGT. FRI FANTASI. Har ikke tid til at lede efter flere eksempler
                        double faktor = 1.0/600.0;
                        resultat = fractionAmountInGrams*faktor;
                        text = text + textStart + " Du kan varme dit hus op i " + format.format(resultat) + " minutter med energien fra dit Bioaffald.";
                        break;

                }
                break;

            case "Pap/Papir":
                switch(number){
                    case 1:
                        //DETTE ER IKKE RIGTIGT. FRI FANTASI. Har ikke tid til at lede efter flere eksempler
                        double papirVaegt = 10.0;
                        double svind = 0.80;
                        resultat = fractionAmountInGrams / papirVaegt * svind;
                        text = text + "Man kan producere " + format.format(resultat) + " stk A4 papir af mængden af dit afleverede papir/pap";

                        break;

                    case 2:
                        //DETTE ER IKKE RIGTIGT. FRI FANTASI. Har ikke tid til at lede efter flere eksempler
                        papirVaegt = 1.0;
                        svind = 0.80;
                        double laengde = 10;
                        resultat = fractionAmountInGrams / papirVaegt * svind * laengde;
                        text = text + "Du kan producere " + format.format(resultat) + " cm. toiletpapir af dit afleverede papir/pap";
                        break;

                    case 3:
                        //DETTE ER IKKE RIGTIGT. FRI FANTASI. Har ikke tid til at lede efter flere eksempler
                        double soendaegsberlingerVaegt = 2500;
                        svind = 0.8;
                        resultat = fractionAmountInGrams/soendaegsberlingerVaegt*svind;
                        text = text + "Man kunne lave " + format.format(resultat) + " Søndags Berlingere af dit afleverede papir/pap";
                        break;
                }
                break;
        }
        return text;
    }

    public String getTip(){
        int tipnr = getRandom(10);
        return Data_Controller.getInstance().getTip(tipnr-1);
    }

    public int getRandom(int between1and){
        Random rnd = new Random();
        int number = rnd.nextInt(between1and)+1;
        return number;
    }
}
