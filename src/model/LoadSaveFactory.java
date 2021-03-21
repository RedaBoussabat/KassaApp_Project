package model;

import database.LoadSave;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Deze klasse beschrijft de factory om een loadsave inlezer / wegschrijver aan te maken ahv de properties.
 * Deze klasse is een Singleton.

 * @version 1.0
 */
public class LoadSaveFactory {
    private static LoadSaveFactory ourInstance = new LoadSaveFactory();

    /**
     * Deze methode haalt de instance van de klasse op of maakt er een aan.
     * @return de unique instance van deze klasse.
     */
    public static LoadSaveFactory getInstance() {
        if (ourInstance == null) ourInstance = new LoadSaveFactory();
        return ourInstance;
    }

    /**
     * private constructor voor deze klasse.

     */
    private LoadSaveFactory() {
    }

    /**
     * Deze methode stelt in, waar het programma de stock opslaat ahv de properties.
     * Deze klasse gebruikt de SoortOpslag enum om de klassen te instantieren.
     * @param properties de properties.
     * @return de instantie van de opslag.

     */
    public LoadSave createLoadSave(Properties properties) {
        LoadSave loadSave = null;
        Object inputObj = properties.get("input");
        String input = (String) inputObj;
        SoortOplslag soortOplslag = SoortOplslag.valueOf(input);
        try{
            Class<?> clazz = Class.forName(soortOplslag.getKlasseNaam());
            Constructor<?> constructor = clazz.getConstructor();
            loadSave = (LoadSave) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return loadSave;
    }
}
