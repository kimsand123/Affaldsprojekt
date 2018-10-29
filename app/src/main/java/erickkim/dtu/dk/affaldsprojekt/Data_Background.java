package erickkim.dtu.dk.affaldsprojekt;

public class Data_Background {

    private int trashCoins;
    private Data_DTO_deliveryCode deliveryCode;
    private int personId = 123; // TODO: implement a way to store a personalized person ID.
    private static Data_Background dataBackgroundInstance = null;
    Data_DAO_deliveryCode dao_deliveryCode;
    Data_DAO_trashCoins dao_trashCoins;
    Data_DAO_tips dao_tips;

    private Data_Background() {
        dao_deliveryCode = new Data_DAO_deliveryCode();
        dao_trashCoins = new Data_DAO_trashCoins();
        dao_tips = new Data_DAO_tips();
        setDeliveryCode(dao_deliveryCode.getAvailableDeliveryCode());
    }

    public static Data_Background getInstance() {
        if (dataBackgroundInstance == null)
            dataBackgroundInstance = new Data_Background();
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
}
