package erickkim.dtu.dk.affaldsprojekt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_Controller {

    private int trashCoins;
    private Data_DTO_deliveryCode deliveryCode;
    private int personId = 123; // TODO: implement a way to store a personalized person ID locally.
    private int usedDataDeliveryCode=0;
    private String userId;
    private String deliveredDate;
    private static Data_Controller dataBackgroundInstance = null;
    Data_DAO_deliveryCode dao_deliveryCode;
    Data_DAO_trashCoins dao_trashCoins;
    Data_DAO_tips dao_tips;

    private Data_Controller() {
        dao_deliveryCode = new Data_DAO_deliveryCode();
        dao_trashCoins = new Data_DAO_trashCoins();
        dao_tips = new Data_DAO_tips();
        setDeliveryCode(dao_deliveryCode.getAvailableDeliveryCode());
    }

    public static Data_Controller getInstance() {
        if (dataBackgroundInstance == null)
            dataBackgroundInstance = new Data_Controller();
        return dataBackgroundInstance;
    }

    public int getTrashCoins() {
        trashCoins = dao_trashCoins.getTrashCoins(personId);
        return trashCoins;
    }

    public void setTrashCoins(int trashCoins) {
        this.trashCoins = trashCoins;
    }

    public Data_DTO_deliveryCode getDeliveryCode() {
        return deliveryCode;
    }

    public void setUsedDataDeliveryCode(int code){
        this.usedDataDeliveryCode = code;
    }

    public int getUsedDataDeliveryCode(){
        return usedDataDeliveryCode;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setDeliveredDate(String deliveredDate){
        this.deliveredDate=deliveredDate;
    }

    public String getDeliveredDate(){
        return this.deliveredDate;
    }

    public void setDeliveryCode(Data_DTO_deliveryCode deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getTip() {
        return dao_tips.getTip();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Data_DTO_deliveryCode getNewDeliveryCode() {
        setDeliveryCode(dao_deliveryCode.getAvailableDeliveryCode());
        return deliveryCode;
    }

    public String[] getHubStatus(){
        String[] hubStatus = new String[4];
        hubStatus[0]=TEST_Database.getFraktion1DisposalStatus();
        hubStatus[1]=TEST_Database.getFraktion2DisposalStatus();
        hubStatus[2]=TEST_Database.getFraktion3DisposalStatus();
        hubStatus[3]=TEST_Database.getFraktion4DisposalStatus();
        return hubStatus;
    }

    public ArrayList<PieEntry> getPieData(){
        ArrayList<PieEntry> pieData = new ArrayList<>();
        pieData = TEST_Database.getInstance().getFraktionAmount(this.usedDataDeliveryCode, this.userId, this.deliveredDate);
        return pieData;
    }

}
