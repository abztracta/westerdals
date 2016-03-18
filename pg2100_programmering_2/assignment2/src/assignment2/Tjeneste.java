package assignment2;

/**
 * @author Espen R&oslash;nning.
 */
public abstract class Tjeneste {

    private String registreringsNummer;
    private String kontaktPerson;
    private int pris;

    public Tjeneste() {
        setRegistreringsNummer("00000");
        setKontaktPerson("Ukjent");
    }

    public Tjeneste(String registreringsNummer, String kontaktPerson, int pris) {
        setRegistreringsNummer(registreringsNummer);
        setKontaktPerson(kontaktPerson);
        setPris(pris);
    }

    public String getRegistreringsNummer() {
        return registreringsNummer;
    }

    public void setRegistreringsNummer(String registreringsNummer) {
        this.registreringsNummer = registreringsNummer;
    }

    public String getKontaktPerson() {
        return kontaktPerson;
    }

    public void setKontaktPerson(String kontaktPerson) {
        this.kontaktPerson = kontaktPerson;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    @Override
    public boolean equals(Object object) { // polymorfisme
        if (!(object instanceof Tjeneste)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        Tjeneste tjeneste = (Tjeneste) object;
        return this.registreringsNummer.equals(tjeneste.registreringsNummer);
    }

    @Override
    public String toString() { // polymorfisme
        return String.format("%-20s%20s", "Reg. Nr", registreringsNummer) + "\n" +
                String.format("%-20s%20s", "Kontaktperson", kontaktPerson) + "\n" +
                String.format("%-20s%20d", "Pris", pris);
    }
}
