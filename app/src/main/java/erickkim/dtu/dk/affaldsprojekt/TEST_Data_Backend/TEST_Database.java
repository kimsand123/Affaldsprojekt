package erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend;

import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_delivery;
import erickkim.dtu.dk.affaldsprojekt.model.Data_DTO_deliveryCode;

public class TEST_Database {
    static FirebaseDatabase mref;
    static DatabaseReference myref;
    public double coordinates[] = {0.0, 0.0};

    public static TEST_Database getInstance() {
        mref = FirebaseDatabase.getInstance();
        if (testDatabaseInstance == null)
            testDatabaseInstance = new TEST_Database();
        return testDatabaseInstance;
    }

    private static TEST_Database testDatabaseInstance = null;

    public static String tips[] = {"Danmark har EU-rekord i skrald - danskerne smider 802 " +
            "kg skrald ud per indbygger om året. \n \n kilde: Eurostat - tal fra 2010 ",

            "Ca. 30 plastflasker kan blive til en fleecetrøje og ca. 500 " +
            "dåser kan blive til at cykelstel",

            "Et kilo aviser bliver til 32 æggebakker og i to konservesdåser " +
            "er der materiale nok til at lave 1 ringeklokke til en cykel",

            "Stryhns laver 100.000 bakker leverpostej om dagen, i 106 foliebakker " +
            "Er der materiale nok til rammen til en bærbar computer",

            "Et kg aluminium udvundet af bauxit skaber ca. 85 kg affald. Det skaber " +
            "kun 3.5 kg affald at genbruge et kg aluminium",

            "Der bliver brugt cirka 1,5 tons råstoffer til at producere en computer og " +
            "cirka 75 kilo til en mobiltelefon. Det meste bliver brugt ved fremstillingen "+
            "og er ikke en del af det færdige produkt, men bliver til affald.",

            "Koncentrationen af ædelmetaller er ca. 3 gange så stor i elektronikskrot " +
            "pr. ton, sammenlignet med normal minedrift. Dermed spares der store mængder " +
            "energi og CO2 ved denne form for genvinding.",

            "hvis alle levede som danskerne, ville det kræve 4 jordkloder for at dække " +
            "vores forbrug?",

            "For at lave en cykel skal der ikke samles mere end 200 dåser aluminium eller jern.",

            "Et ton forbehandlet bioaffald kan gøde 466 m2 landbrugsjord. Det svarer omtrent til "+
                    "en parcelhusgrund. Samtidig undgås en CO2 emission på 37 kg CO2 pr ton " +
                    "forbehandlet bioaffald, fordi kulstoffet føres tilbage til jorden, hvor "+
                    "det lagres og forbedrer dyrkningsegenskaberne.",

    };



    public static int personCoins[][] = { {1111111111, 323, 252, 623, 555, 111, 112, 113, 999, 986},
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
        final ArrayList<PieEntry> liste = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("affaldsprojekt-ae236").child("delivery").child(userId).child(date)
                .addValueEventListener(new ValueEventListener() {
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
}
