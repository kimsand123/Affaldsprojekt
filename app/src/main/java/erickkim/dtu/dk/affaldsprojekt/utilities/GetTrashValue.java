package erickkim.dtu.dk.affaldsprojekt.utilities;

public class GetTrashValue {

    public double convertToValue(String fraction, int amountInGrams){
        double faktor=0.0;
        switch(fraction){
            case "Metal/Plastik/Glas":
                faktor=0.1;
                break;
            case "Bio":
                faktor=0.035;
                break;
            case "Pap/Papir":
                faktor=0.01;
                break;
            case "Rest":
                faktor=0.001;
                break;
        }
        return amountInGrams*faktor;
    }
}
