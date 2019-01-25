package erickkim.dtu.dk.affaldsprojekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity implements Button.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        button = findViewById(R.id.aboutButton);
        button.setText("Denne app \"Affald er guld\" er blevet til som et projekt mellem udviklerne \n" +
                "Erick Lauridsen(s175199) \n Kim Sandberg(s163290) \n" +
                "og kunde\n" +
                "Næstved Kommune/Mærk Næstved \nv. Anette Moss \n\n"+
                "Den er et produkt af 3. semester 2018 samt 3ugers projekt 2019 \n" +
                "i faget \n Brugerinteraktion og udvikling af mobile applikationer.\n\n" +
                "Appen er til inspiration for Næstved Kommune. Hvis man vil \n" +
                "bruge appens kode, eller UI \n skal der aftales et honorar med udviklerne.");

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
