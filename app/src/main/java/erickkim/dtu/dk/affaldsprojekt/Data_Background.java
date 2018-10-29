package erickkim.dtu.dk.affaldsprojekt;

public class Data_Background {

    private int trashCoins;
    private Data_DTO_deliveryCode deliveryCode;
    private int personId;

    public Data_Background() {
        Data_DAO_deliveryCode dao_deliveryCode = new Data_DAO_deliveryCode();
        Data_DAO_trashCoins dao_trashCoins = new Data_DAO_trashCoins();

        setDeliveryCode(dao_deliveryCode.getAvailableDeliveryCode());
    }

    public int getTrashCoins() {
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
