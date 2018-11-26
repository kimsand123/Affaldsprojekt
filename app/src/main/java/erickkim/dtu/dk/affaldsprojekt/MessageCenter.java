package erickkim.dtu.dk.affaldsprojekt;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;


class MessageCenter {
    private static final MessageCenter ourInstance = new MessageCenter();
    private Context context;

    static MessageCenter getInstance() {
        return ourInstance;
    }

    private MessageCenter() {
    }

    public void showMessage(String message){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getInstance().context, message, duration);
        toast.show();
    }
}
