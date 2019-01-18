package erickkim.dtu.dk.affaldsprojekt.model;

public class Data_DTO_deliveryCode {
    private int code;
    private long date;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public java.lang.String toString() {
        String output = "";
        String number = String.valueOf(code);
        for(int i = 0; i < number.length(); i++) {
            int j = Character.digit(number.charAt(i), 10);
            output += " " + j;
        }
        return output;
    }
}
