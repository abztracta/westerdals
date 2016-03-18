package assignment2;

import java.util.ArrayList;

/**
 * @author Espen R&oslash;nning.
 */
public class Program {

    public static void main(String[] args) {
        ArrayList<Tjeneste> tjenester = new ArrayList<Tjeneste>();
        tjenester.add(new Utrykning("123", "Per Persen", 12000, 1000));
        tjenester.add(new Kontrakt("113", "Petter Pettersen", 14000, "Mandag"));
        tjenester.add(new Engasjement("103", "Pernille Pernillesen", 12000, 7));

        for (Tjeneste tjeneste : tjenester) {
            if (tjeneste instanceof Engasjement) {
                System.out.println("(Engasjement)" + "\n" +
                        skrivData(tjeneste) + "\n" +
                        "Varighet: " + ((Engasjement) tjeneste).getVarighet() + "\n");
            } else if (tjeneste instanceof Kontrakt) {
                System.out.println("(Kontrakt)" + "\n" +
                        skrivData(tjeneste) + "\n" +
                        "Dag: " + ((Kontrakt) tjeneste).getDag() + "\n");
            } else if (tjeneste instanceof Utrykning) {
                System.out.println("(Utrykning)" + "\n" +
                        skrivData(tjeneste) + "\n" +
                        "Gebyr: " + ((Utrykning) tjeneste).getGebyr() + "\n");
            }
        }
        likhetsSjekk(new Utrykning("123", "", 0, 0), new Utrykning("123", "Per Persen", 12000, 1000));
    }

    private static void likhetsSjekk(Tjeneste tjeneste1, Tjeneste tjeneste2) {
        System.out.println("Er:");
        System.out.println();
        System.out.println(skrivType(tjeneste1));
        System.out.println(tjeneste1.toString());
        System.out.println();
        System.out.println("lik:");
        System.out.println();
        System.out.println(skrivType(tjeneste2));
        System.out.println(tjeneste2.toString());
        System.out.println("?");
        System.out.println();
        System.out.println(tjeneste1.equals(tjeneste2));
    }

    private static String skrivType(Tjeneste tjeneste) {
        if (tjeneste instanceof Engasjement) {
            return "(Engasjement)";
        } else if (tjeneste instanceof Kontrakt) {
            return "(Kontrakt)";
        } else if (tjeneste instanceof Utrykning) {
            return "(Utrykning)";
        }
        return null;
    }

    private static String skrivData(Tjeneste tjeneste) {
        return tjeneste.toString() + "\n" +
        tjeneste.getRegistreringsNummer() + " (Reg.nr) " +
                tjeneste.getKontaktPerson() + " (Kontaktperson) "
                + tjeneste.getPris() + " (Pris)";
    }
}
