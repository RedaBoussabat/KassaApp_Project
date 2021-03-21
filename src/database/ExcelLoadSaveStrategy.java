package database;

import excel.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Artikel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deze klasse is gebruikt om excel files in te lezen en weg te schrijven.

 * @version 1.0
 */
public class ExcelLoadSaveStrategy {

    private ExcelPlugin excelPlugin = new ExcelPlugin();

    /**
     * Deze methode leest artikels uit uit een excel file.
     * @param filepath het pad naar de excel file.
     * @return een ArrayList van artikels.

     */
    public ArrayList<Artikel> load(String filepath) {
        File file  = new File(filepath);
        ArrayList<Artikel> res = new ArrayList<>();
        try {
            ArrayList<ArrayList<String>> data = excelPlugin.read(file);
            for (ArrayList<String> list : data) {
                String code = list.get(0);
                String beschrijving = list.get(1);
                String groep = list.get(2);
                double prijs = Double.parseDouble(list.get(3));
                int voorraad = Integer.parseInt(list.get(4));
                Artikel a = new Artikel(code, beschrijving, groep, prijs, voorraad);
                res.add(a);
            }
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Artikel list : res) {
        }
        return res;
    }

    /**
     * Deze methode schrijft artikels weg naar een excel file.
     * @param artikels de artikels om weg te schrijven.
     * @param filepath het pad naar de outputfile.

     */
    public void save(ArrayList<Artikel> artikels, String filepath) {
        File file  = new File(filepath);
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (Artikel artikel : artikels) {
            ArrayList<String> resInner = new ArrayList<>();
            String code = artikel.getCode();
            String beschrijving = artikel.getOmschrijving();
            String artikelgroep = artikel.getArtikelGroep();
            String prijs = String.valueOf(artikel.getVerkoopprijs());
            String voorraad = String.valueOf(artikel.getActueleVoorraad());
            resInner.add(code);
            resInner.add(beschrijving);
            resInner.add(artikelgroep);
            resInner.add(prijs);
            resInner.add(voorraad);
            res.add(resInner);
        }
        try {
            excelPlugin.write(file, res);
        } catch (IOException | WriteException | BiffException e) {
            e.printStackTrace();
        }
    }
}
