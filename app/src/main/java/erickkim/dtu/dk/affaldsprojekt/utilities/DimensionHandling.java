package erickkim.dtu.dk.affaldsprojekt.utilities;

public class DimensionHandling {
    private static final DimensionHandling ourInstance = new DimensionHandling();
    private int paddingSizeHubButton;
    private int paddingSizeGarbageHandlingButton;
    private float textSize;
    public static DimensionHandling getInstance() {
        return ourInstance;
    }

    private DimensionHandling() {
    }

    public void setSmall(){
        setTextSize(16f);
        setIconPaddingHubButton(55);
        setIconPaddingGarbageButton(20);
    }

    public void setLarge(){
        setTextSize(20f);
        setIconPaddingHubButton(170);
        setIconPaddingGarbageButton(50);
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }
    public float getTextSize (){
        return this.textSize;
    }

    public void setIconPaddingHubButton(int paddingSize){this.paddingSizeHubButton = paddingSize;}
    public int getIconPaddingHub(){ return this.paddingSizeHubButton; }

    public void setIconPaddingGarbageButton(int paddingSizeGarbageHandlingButton){this.paddingSizeGarbageHandlingButton = paddingSizeGarbageHandlingButton;}
    public int getPaddingSizeGarbageHandlingButton(){return this.paddingSizeGarbageHandlingButton;}

}
