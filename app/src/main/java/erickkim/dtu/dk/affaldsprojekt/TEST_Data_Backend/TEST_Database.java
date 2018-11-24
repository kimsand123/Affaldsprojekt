package erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend;

import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import erickkim.dtu.dk.affaldsprojekt.Data_DTO_delivery;
import erickkim.dtu.dk.affaldsprojekt.Data_DTO_deliveryCode;

public class TEST_Database {
    static FirebaseDatabase mref;
    static DatabaseReference myref;

    public static TEST_Database getInstance() {
        mref = FirebaseDatabase.getInstance();
        if (testDatabaseInstance == null)
            testDatabaseInstance = new TEST_Database();
        return testDatabaseInstance;
    }

    private static TEST_Database testDatabaseInstance = null;

    public static String tips[] = {"Leverpostejen skal opdeles.", "Smid nu for filen din skrald ordentligt ud.", "Det er noget af en weekend du har haft!",
            "Vi kan se at det blev en god første date igår!", "Bananer går i bio, plast går i plast. Lær det nu."};

    public static int personCoins[][] = { {123, 323, 252, 623, 555, 111, 112, 113, 999, 986},
                            {15555, 55005, 252555, 236110, 0, 10, 623523, 161512, 696969696, 2336 }
    };

    public static int getSomeTrashCoins(int personId) {
        for (int i = 0; i < 10; i++) {
            if (personCoins[0][i] == personId) {
                return personCoins[1][i];
            }
        }
        return -1;
    }

    public static Data_DTO_deliveryCode[] usedCodes = new Data_DTO_deliveryCode[1000];

    public static Data_DTO_deliveryCode getDeliveryCode() {
        Data_DTO_deliveryCode newTempCode = new Data_DTO_deliveryCode();
        /* Simplify the code process.
        Date dateSetter = new Date();
        do {
            newTempCode.setCode(fabricateNewCode());
            newTempCode.setDate(dateSetter.getTime());
        } while (!testCodeValid(newTempCode));

        insertCodeIntoData(newTempCode);
        */
        newTempCode.setDate(new Date().getTime());
        newTempCode.setCode(fabricateNewCode());
        return newTempCode;
    }

    private static void insertCodeIntoData(Data_DTO_deliveryCode newTempCode) {
        for (int i = 0; i < usedCodes.length; i++) {
            if (usedCodes[i] != null) {
                if (!tooCloseDates(usedCodes[i], newTempCode)) {
                    usedCodes[i] = newTempCode;
                    break;
                }
            } else {
                usedCodes[i] = newTempCode;
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

    public static int getRandomNumber() {
        int min = 1000;
        int max = 9999;
        int range = (max - min) + 1;
        int result = (int) (Math.random() * range) + min;
        return result;
    }

    public static int fabricateNewCode() {

        int output = getRandomNumber();
        return output;
    }

    public static String getTip() {
        Random r = new Random();
        int tipToGet = r.nextInt(tips.length);
        return tips[tipToGet];
    }

    public static String getFraktion1DisposalStatus(){
        String status="";
        int statcode;
        Random statrnd = new Random();
        statcode = statrnd.nextInt(10);
        if (statcode <= 8){
            status = "ok";
        } else {
            status = "Fejl, virker ikke";
        }
        return status;
    }

    public static String getFraktion2DisposalStatus(){
        String status="";
        int statcode;
        Random statrnd = new Random();
        statcode = statrnd.nextInt(10);
        if (statcode <= 8){
            status = "ok";
        } else {
            status = "Fejl, virker ikke";
        }
        return status;
    }

    public static String getFraktion3DisposalStatus(){
        String status="";
        int statcode;
        Random statrnd = new Random();
        statcode = statrnd.nextInt(10);
        if (statcode <= 8){
            status = "ok";
        } else {
            status = "Fejl, virker ikke";
        }
        return status;
    }

    public static String getFraktion4DisposalStatus(){
        String status="";
        int statcode;
        Random statrnd = new Random();
        statcode = statrnd.nextInt(10);
        if (statcode <= 8){
            status = "ok";
        } else {
            status = "Fejl, virker ikke";
        }
        return status;
    }


    public ArrayList<PieEntry> getFraktionAmount(final int usedDataDeliveryCode, final String userId, final String date) {
        int result;
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("delivery").child(userId).child(date);

        final ArrayList<PieEntry> liste = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("delivery").child(userId).child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Data_DTO_delivery data = snapshot.getValue(Data_DTO_delivery.class);
                            ((ArrayList) liste).add(data.getAmount());
                            ((ArrayList) liste).add(data.getType());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return liste;
        }



    public class asyncTestFirebase extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            return null;
        }
    }
}
