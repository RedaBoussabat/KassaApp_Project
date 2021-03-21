package model;

import model.SoortKorting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Deze klasse is een singleton die instaat voor het aanmaken van de kortingen.

 * @version 1.0
 */
public class KortingFactory {
    private static KortingFactory ourInstance = new KortingFactory();

    /**
     * Deze methode haalt de enige instance van deze klasse op.
     * @return de enige instance.

     */
    public static KortingFactory getInstance() {
        if (ourInstance == null) {
            ourInstance = new KortingFactory();
        }
        return ourInstance;
    }

    /**
     * Deze methode maakt een contructor aan voor deze klasse.

     */
    private KortingFactory() {
    }

    /**
     * Deze methode maakt een korting aan en, stelt deze in.
     * @param input de input om een kortingsobject aan te maken.
     * @return de korting net aangemaakt.

     */
    public Korting create(String input) {
        Korting korting = null;
        SoortKorting soortKorting = SoortKorting.valueOf(input);
        try {
            Class<?> clazz = Class.forName(soortKorting.getKlasseNaam());
            Constructor<?> constructor = clazz.getConstructor();
            korting = (Korting)constructor.newInstance();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return korting;
    }
}