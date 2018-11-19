package erickkim.dtu.dk.affaldsprojekt;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_Controller {

    private int trashCoins;
    private Data_DTO_deliveryCode deliveryCode;
    private int personId = 123; // TODO: implement a way to store a personalized person ID.
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
        hubStatus[1]=TEST_Database.getFraktion1DisposalStatus();
        hubStatus[2]=TEST_Database.getFraktion1DisposalStatus();
        hubStatus[3]=TEST_Database.getFraktion1DisposalStatus();
        return hubStatus;
    }

}
