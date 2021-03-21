package model;

public class CancelledState extends VerkoopState {

    private Verkoop verkoop;

    public CancelledState(Verkoop verkoop) {
        super(verkoop);
        this.verkoop = verkoop;
    }

}
