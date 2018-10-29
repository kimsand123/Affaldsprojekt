package erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend;

import java.util.Date;

import erickkim.dtu.dk.affaldsprojekt.Data_DTO_deliveryCode;

public class TEST_Database {

    private static TEST_Database testDatabaseInstance = null;

    public static TEST_Database getInstance() {
        if (testDatabaseInstance == null)
            testDatabaseInstance = new TEST_Database();
        return testDatabaseInstance;
    }

    public static int personCoins[][] = { {657, 323, 252, 623, 696, 111, 112, 113, 999, 986},
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

    public static Data_DTO_deliveryCode[] usedCodes = new Data_DTO_deliveryCode[1000];

    public static Data_DTO_deliveryCode getDeliveryCode() {
        Data_DTO_deliveryCode newTempCode = new Data_DTO_deliveryCode();
        Date dateSetter = new Date();
        do {
            newTempCode.setCode(fabricateNewCode());
            newTempCode.setDate((int) dateSetter.getTime());
        } while (!testCodeValid(newTempCode));

        insertCodeIntoData(newTempCode);

        return newTempCode;
    }

    private static void insertCodeIntoData(Data_DTO_deliveryCode newTempCode) {
        for (int i = 0; i < usedCodes.length; i++) {
            if (!tooCloseDates(usedCodes[i], newTempCode)) {
                usedCodes[i] = newTempCode;
                break;
            }
        }
    }

    public static boolean testCodeValid(Data_DTO_deliveryCode testCode) {
        for (int i = 0; i < usedCodes.length; i++) {
            if (usedCodes[i] != null) {
                if (usedCodes[i].getCode() == testCode.getCode())
                    if (tooCloseDates(usedCodes[i], testCode))
                        return false;
            }
        }
        return true;
    }

    public static boolean tooCloseDates(Data_DTO_deliveryCode code1, Data_DTO_deliveryCode code2) {
        if (code1.getDate() - code2.getDate() < 600000)
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
