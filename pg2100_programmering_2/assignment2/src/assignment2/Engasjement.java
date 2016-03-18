package assignment2;

/**
 * @author Espen R&oslash;nning.
 */
public class Engasjement extends Tjeneste {

    private int varighet;

    public Engasjement() {
        super();
    }

    public Engasjement(String registreringsNummer, String kontaktPerson, int pris, int varighet) {
        super(registreringsNummer, kontaktPerson, pris);
        setVarighet(varighet);
    }

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }

    @Override
    public String toString() { // polymorfisme
        return super.toString() + "\n" +
                String.format("%-20s%20d", "Varighet", varighet);

    }
}
