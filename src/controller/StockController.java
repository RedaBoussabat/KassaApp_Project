

package controller;

import model.Artikel;
import model.ComparatorByOmschrijving;
import model.Observer;
import model.Winkel;
import view.panels.ProductOverviewPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static model.SoortObserver.STOCK;

/**
 * Deze klasse fungeert als controller tussen tab2 van de kassa (Stock) en de winkel.
 *

 * @version 1.0
 */
public class StockController extends Observer {

    private Winkel subject;
    private ProductOverviewPane stock;
    private ArrayList<Artikel> artikels;

    /**
     * Deze methode maakt een instantie van deze klasse aan ahv een winkel.
     *
     * @param winkel de winkel

     */
    public StockController(Winkel winkel) {
        super(winkel);
        this.setSubject(winkel);
        subject.registerObserver(this, STOCK);
    }

    /**
     * Deze methode stelt een winkel in.
     *
     * @param subject de winkel.

     */
    public void setSubject(Winkel subject) {
        this.subject = subject;
    }

    /**
     * Deze methode stelt de stock in.
     *
     * @param stock de stock

     */
    public void setStock(ProductOverviewPane stock) {
        this.stock = stock;
    }

    /**
     * Deze methode updatet de stock bij de view. Deze artikelen zijn gesorteerd op beschrijving.
     *

     */
    @Override
    public void update(ArrayList<Artikel> dummy, Enum dummyToo) {
        artikels = subject.getDb().load();
        Collections.sort(artikels, new ComparatorByOmschrijving());
        stock.updateStockView(artikels);
    }

    /**
     * Deze methode toont de artikelen van de stock.
     */
    public void toonArtikelen() {
        subject.toonStock();
    }

    public void pasStockAan(){
        ArrayList<Artikel> test = subject.getCurrent().getAll();
        for(Artikel a : test){
            for (Artikel b : artikels){
                if (a.getCode().equalsIgnoreCase(b.getCode())) {
                    b.setActueleVoorraad(b.getActueleVoorraad()-1);
                }
            }
        }
    }
}
