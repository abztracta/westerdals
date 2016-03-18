package assignment2;

/**
 * @author Espen R&oslash;nning.
 */
public class Kontrakt extends Tjeneste {

    private String dag;

    public Kontrakt() {
        super();
        setDag("Ukjent");
    }

    public Kontrakt(String registreringsNummer, String kontaktPerson, int pris, String dag) {
        super(registreringsNummer, kontaktPerson, pris);
        setDag(dag);
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    @Override
    public String toString() { // polymorfisme
        return super.toString() + "\n" +
                String.format("%-20s%20s", "Dag", dag);

    }
}
