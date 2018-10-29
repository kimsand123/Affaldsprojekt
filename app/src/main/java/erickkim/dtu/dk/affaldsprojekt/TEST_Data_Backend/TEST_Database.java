package erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend;

import java.util.Date;

import erickkim.dtu.dk.affaldsprojekt.Data_DTO_deliveryCode;

public class TEST_Database {

    static int personCoins[][] = { {657, 323, 252, 623, 696, 111, 112, 113, 999, 986},
                            {15555, 55005, 252555, 236110, 0, 10, 623523, 161512, 696969696, 2336 }
    };

    public static int getSomeTrashCoins(int personId) {
        for (int i = 0; i < 10; i++) {
            if (personCoins[1][i] == personId) {
                return personCoins[1][i];
            }
        }
        return -1;
    }

    static Data_DTO_deliveryCode[] usedCodes = new Data_DTO_deliveryCode[1000];

    public static Data_DTO_deliveryCode getDeliveryCode() {
        Data_DTO_deliveryCode newTempCode = new Data_DTO_deliveryCode();
        Date dateSetter = new Date();
        do {
            newTempCode.setCode(fabricateNewCode());
            newTempCode.setDate((int) dateSetter.getTime());
        } while (!testCodeValid(newTempCode));

        return newTempCode;
    }

    public static boolean testCodeValid(Data_DTO_deliveryCode testCode) {
        for (int i = 0; i < usedCodes.length; i++) {
            if (usedCodes[i] != null)
                if (usedCodes[i].getCode() == testCode.getCode())
                    if (compareTimeStamps(usedCodes[i], testCode))
                        return true;
        }
        return true;
    }

    public static boolean compareTimeStamps(Data_DTO_deliveryCode code1, Data_DTO_deliveryCode code2) {
        if (code1.getDate() > 10 + code2.getDate())
            return true;
        else
            return false;
    }

    public static int fabricateNewCode() {
        int min = 1;
        int max = 9999;
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

}
