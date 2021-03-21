package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Deze klasse wordt gebruikt als een factory om de rekening te maken.

 * @version 1.0
 */
public class RekeningFactory {
    private static RekeningFactory ourInstance = new RekeningFactory();
    private Winkel winkel;

    /**
     * Deze methode haalt de enige instance op van de klasse.
     * @return de enige instance van de klasse.

     */
    public static RekeningFactory getInstance() {
        return ourInstance;
    }

    /**
     * Deze methode maakt een instance aan van de klasse.

     */
    private RekeningFactory() {
    }

    /**
     * Deze methode stelt de winkel in.
     * @param winkel de winkel.

     */
    public void setWinkel(Winkel winkel) {
        this.winkel = winkel;
    }

    /**
     * Deze methode maakt een rekening ahv de properties.
     * @param properties de properties.
     * @return de rekening.

     */
    public RekeningAbstract create(Properties properties) {
        RekeningAbstract rekening = new Rekening(winkel);
        try {
            if (!properties.get("headerCustom").equals("")) {
                rekening = new CustomHeader(rekening, (String) properties.get("headerCustom"));
            }
            if (!properties.get("headerTijd").equals("")) {
                rekening = new HeaderTijd(rekening);
            }
            if (!properties.get("footerKorting").equals("") && this.winkel.getKorting() != null) {
                rekening = new FooterZonderKorting(rekening, winkel.getKorting().berekenKorting(winkel.getCurrent().getAll()), winkel.getCurrent().getTotaal());
            }
            if (!properties.get("footerBTW").equals("") && winkel.getKorting() != null) {
                double totaal = winkel.getCurrent().getTotaal() - winkel.getKorting().berekenKorting(winkel.getCurrent().getAll());
                rekening = new FooterZonderBTW(rekening, totaal);
            } else if (!properties.get("footerBTW").equals("")) {
                rekening = new FooterZonderBTW(rekening, winkel.getCurrent().getTotaal());
            }
            if (!properties.get("footerAfsluitlijn").equals("")) {
                rekening = new FooterBericht(rekening);
            }
            return rekening;
        } catch (NumberFormatException ignored) {
            return rekening;
        }
    }
}
