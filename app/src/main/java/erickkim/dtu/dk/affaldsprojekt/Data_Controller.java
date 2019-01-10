package erickkim.dtu.dk.affaldsprojekt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_Controller {


    public void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private String date;
    private long longDate;
    private long startdate;
    private int todaysDeliveryCounter;
    private long trashCoins;
    private Data_DTO_deliveryCode deliveryCode;
    private FirebaseDatabase mref;
    private int usedDataDeliveryCode=0;
    private String userId;
    private String deliveredDate;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private static Data_Controller dataBackgroundInstance = null;
    Data_DAO_deliveryCode dao_deliveryCode;
    Data_DAO_trashCoins dao_trashCoins;
    Data_DAO_tips dao_tips;

    private Data_Controller() {
        dao_deliveryCode = new Data_DAO_deliveryCode();
        dao_trashCoins = new Data_DAO_trashCoins();
        dao_tips = new Data_DAO_tips();
        mref = FirebaseDatabase.getInstance();
        setDeliveryCode(dao_deliveryCode.getAvailableDeliveryCode());
    }

    public static Data_Controller getInstance() {
        if (dataBackgroundInstance == null)
            dataBackgroundInstance = new Data_Controller();
        return dataBackgroundInstance;
    }

    public void removeDefaultLogin(Context context) {
        sharedPref = context.getSharedPreferences("defaultLogin", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.remove("userId");
        editor.apply();
    }

    public void setDefaultLogin(Context context) {
        sharedPref = context.getSharedPreferences("defaultLogin", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public boolean performDefaultLogin(Context context) {
        sharedPref = context.getSharedPreferences("defaultLogin", Context.MODE_PRIVATE);
        String defaultUserId = sharedPref.getString("userId", null);
        if (defaultUserId == null) {
            return false;
        } else {
            setUserId(defaultUserId);
            getTrashCoins();
            return true;
        }

    }

    public int getTrashCoins() {
        mref.getReference().child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals("coins")) {
                        trashCoins = (long) snapshot.getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return (int) trashCoins;
    }

    public Data_DTO_deliveryCode getDeliveryCode() {
        return deliveryCode;
    }

    public void setTrashCoins(int trashCoins) {
        this.trashCoins = trashCoins;
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
        pieData = TEST_Database.getInstance().getFraktionAmount(this.usedDataDeliveryCode, this.userId, ""+this.deliveredDate);
        return pieData;
    }

    public String getStringToday() {
        return this.date;
    }

    public String getLongToday(){
        return ""+this.longDate;
    }

    public void setToday() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date todayWithoutTime = null;
        try {
            todayWithoutTime = sdf.parse(sdf.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = sdf.format(today);
        this.longDate = todayWithoutTime.getTime();
        this.startdate = this.longDate - 7776000000l;

    }

    public int getTodaysDeliveryCounter() {
        return todaysDeliveryCounter;
    }

    public void setTodaysDeliveryCounter(int todaysDeliveryCounter) {
        this.todaysDeliveryCounter = todaysDeliveryCounter;
    }
}
