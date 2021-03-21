package model;

public class SoldState extends VerkoopState{

    private Verkoop verkoop;

    public SoldState(Verkoop verkoop) {
        super(verkoop);
        this.verkoop = verkoop;
    }

}
