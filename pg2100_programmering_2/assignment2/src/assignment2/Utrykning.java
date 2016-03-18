package assignment2;

/**
 * @author Espen R&oslash;nning.
 */
public class Utrykning extends Tjeneste {

    private int gebyr;

    public Utrykning() {
        super();
    }

    public Utrykning(String registreringsNummer, String kontaktPerson, int pris, int gebyr) {
        super(registreringsNummer, kontaktPerson, pris);
        setGebyr(gebyr);
    }

    public int getGebyr() {
        return gebyr;
    }

    public void setGebyr(int gebyr) {
        this.gebyr = gebyr;
    }

    @Override
    public String toString() { // polymorfisme
        return super.toString() + "\n" +
                String.format("%-20s%20d", "Gebyr", gebyr);

    }
}
