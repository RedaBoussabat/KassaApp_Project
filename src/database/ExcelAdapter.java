package database;

import model.Artikel;

import java.util.ArrayList;

/**
 * Deze klasse is een adapter voor Excel inpput files.

 * @version 1.0
 */
public class ExcelAdapter implements LoadSave {

    private ExcelLoadSaveStrategy excel = new ExcelLoadSaveStrategy();

    /**
     * Deze mehode haalt data op uit een excel file.
     * @param filepath het pad naar de excel file.
     * @return een ArrayList van artikels

     */
    @Override
    public ArrayList<Artikel> load(String filepath) {
        return excel.load(filepath);
    }

    /**
     * Deze klasse schrijft data weg naar naar een Excel file.
     * @param artikels de ArrayList van artikels om weg te schrijven.
     * @param filepath het pad naar de Excel file.

     */
    @Override
    public void save(ArrayList<Artikel> artikels, String filepath) {
        excel.save(artikels, filepath);
    }
}
