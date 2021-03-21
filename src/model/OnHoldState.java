package model;

public class OnHoldState extends VerkoopState {

    private Verkoop verkoop;

    public OnHoldState(Verkoop verkoop) {
        super(verkoop);
        this.verkoop = verkoop;
    }

    @Override
    public void setToActive() {
        verkoop.setCurrent(verkoop.getActiveState());
    }

    @Override
    public void sold() {
        verkoop.setCurrent(verkoop.getActiveState());
    }
}
