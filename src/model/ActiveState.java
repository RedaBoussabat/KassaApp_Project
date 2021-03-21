package model;

public class ActiveState extends VerkoopState{

    private Verkoop verkoop;

    public ActiveState(Verkoop verkoop) {
        super(verkoop);
        this.verkoop = verkoop;
    }

    @Override
    public void cancelVerkoop() {
        verkoop.setCurrent(verkoop.getCancelledState());
    }

    @Override
    public void setToOnHold() {
        verkoop.setCurrent(verkoop.getOnHoldState());
    }

    @Override
    public void sold() {
    }
}
