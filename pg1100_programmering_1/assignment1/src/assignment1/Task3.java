package assignment1;

/**
 * @author Espen Rønning (RONESP13)
 */
public class Task3 {

    // constant variable declarations
    public static final int POINT_VICTORY = 3;
    public static final int POINT_DRAW = 1;

    public static void main(String[] args) {

        // variable declarations
        int fixtures = 14;
        int victory = 8;
        int draw = 4;
        int loss = 2;
        int adultTicketPrice = 75;
        int childTicketPrice = 25;
        int attendingAdults = 456;
        int attendingChildren = 45;

        // perform computations
        int points = (victory * POINT_VICTORY) + (draw * POINT_DRAW);
        int profit = (attendingAdults * adultTicketPrice) + (attendingChildren * childTicketPrice);

        // generate output
        System.out.println("Resultater hjemmekamper:");
        System.out.println("Ballkameratene har denne sesongen spilt " + fixtures + " kamper.");
        System.out.print("Det endte med " + victory + " seiere, ");
        System.out.println(draw + " uavgjort og " + loss + " tap.");
        System.out.print("Dette gir " + points + " poeng.\n\n");
        System.out.println("Billettinntekter hjemmekampene:");
        System.out.print("Til sammen har det vært " + attendingAdults + " voksne (kr " + adultTicketPrice + ") ");
        System.out.println("og " + attendingChildren + " barn (kr " + childTicketPrice + ") på hjemmekampene.");
        System.out.println("Dette ga kr " + profit + " i billettinntekter.");
    }
}
