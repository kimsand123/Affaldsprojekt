package erickkim.dtu.dk.affaldsprojekt.utilities;

import android.text.Html;

import erickkim.dtu.dk.affaldsprojekt.interfaces.I_GenerateFeedback;
import erickkim.dtu.dk.affaldsprojekt.model.Data_Controller;


public class MakeFeedbackForScreens {
    private I_GenerateFeedback feedback = new GenerateFeedback();
    int metPlaGlaAmount;
    int bioAmount;
    int papPapiAmount;
    int restAmount;
    int gold;
    String screen;
    String startText;
    String userType;

    public MakeFeedbackForScreens(int metPlaGlaAmount, int bioAmount, int papPapiAmount, int restAmount, int gold, String screen){
        this.metPlaGlaAmount = metPlaGlaAmount;
        this.bioAmount = bioAmount;
        this.papPapiAmount = papPapiAmount;
        this.restAmount = restAmount;
        this.gold = gold;
        this.screen = screen;

        feedback.setAmounts(this.metPlaGlaAmount, this.bioAmount, this.papPapiAmount, this.restAmount);
        userType = Data_Controller.getInstance().getUserType();

        if(userType.equals("Virksomhed")){
            startText = "I";
        } else {
            startText = "Du";
        }
    }

    public String createCO2FeedbackText(){
        String txt;

        float co2Sparet = Integer.parseInt(feedback.co2SaverCalc());
        if (screen.equals("screen3")) {
            if (co2Sparet > 1000.0) {
                txt = "Du har i dag sparet miljøet for " + co2Sparet / 1000.0 + "kg CO2 ";
            } else {
                txt = "Du har i dag sparet miljøet for " + co2Sparet + "g CO2 ";
            }
        } else {
            if(co2Sparet > 1000.0){
                txt = startText + " har i dag sparet miljøet for " + co2Sparet/1000.0 + "kg CO2 ";

            } else {
                txt = startText + " har i dag sparet miljøet for " + co2Sparet + "g CO2 ";
            }
        }
        if (userType.equals("borger")) {
            txt = txt + " \n Du har modtaget " + gold + " guld for din aflevering ";
        }



        return txt;
    }

    public String createTxtBoxFeedbackText(){

        String txt;
        if(screen.equals("screen3")) {

            if (userType.equals("virksomhed")) {
                txt = feedback.getAnalysis("<i><b>Din aflevering i dag har givet følgende indtjening</i></b>", "virksomhed");
            } else {
                txt = feedback.getAnalysis("<i><b>Din aflevering i dag har betydet</i></b>", "borger");
            }

        } else {

            if(userType.equals("virksomhed")){

                txt = feedback.getAnalysis("<i><b>Jeres kvartalsaflevering har givet følgende indtjening</i></b>","virksomhed");
            }else {
                txt = feedback.getAnalysis("<i><b>Din kvartalsaflevering har betydet</i></b>","borger");
            }
        }
        return txt;
    }
}
