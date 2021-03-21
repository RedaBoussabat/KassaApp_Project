package model;

public abstract class VerkoopState {

    private Verkoop verkoop;

    public VerkoopState(Verkoop verkoop) {
         this.verkoop = verkoop;
    }

    public void setToActive(){
        throw new ModelException("Verkoop kan niet op actief gezet worrden");
    };

    public void cancelVerkoop(){
        throw new ModelException("Verkoop kan niet gecancelled worden");
    };

    public void setToOnHold(){
        throw new ModelException("Verkoop kan niet op hold gezet worden");
    };

    public void sold(){
        throw new ModelException("Verkoop kan niet op verkocht worden gezet");
    };
}
