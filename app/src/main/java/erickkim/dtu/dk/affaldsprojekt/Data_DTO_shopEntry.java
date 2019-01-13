package erickkim.dtu.dk.affaldsprojekt;

import android.widget.ImageView;

public class Data_DTO_shopEntry {
    public String title;
    public String description;
    public int image;
    public int price;

    public Data_DTO_shopEntry(String title, String description, int image, int price) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}

