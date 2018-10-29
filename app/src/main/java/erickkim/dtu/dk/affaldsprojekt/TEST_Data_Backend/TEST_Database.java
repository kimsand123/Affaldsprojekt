package erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend;

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

    public static int getDeliveryCode() {
        int min = 1;
        int max = 9999;
        int range = (max - min) + 1;
        double newCode = (int) (Math.random() * range) + min;

        for (int i = 0; i < usedCodes.length; i++) {

        }


    }

}
