package model;

import controller.KassaController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

/**
 * Deze klasse wordt gebruikt om een header met de datum en tijd van de bon te schrijven.

 * @version 1.0
 */
public class HeaderTijd extends RekeningDecorator {

    private RekeningAbstract rekening;


    /**
     * Deze methode maakt een instance aan van de klasse.
     * @param rekening de originele rekening.

     */
    public HeaderTijd(RekeningAbstract rekening) {
        this.rekening = rekening;
    }

    /**
     * Deze methode haalt de rekening op en voegt de header toe.
     * @return de nieuwe rekening.

     */
    @Override
    public String getDescription() {
        return "Bon afgedrukt op:\n" + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + "\nOmstreeks: "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" + rekening.getDescription();
    }
}
