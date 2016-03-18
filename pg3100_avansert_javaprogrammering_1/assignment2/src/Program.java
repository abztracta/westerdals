import no.wact.pg3100.assignment2.DBHandler;
import no.wact.pg3100.assignment2.DBTable;

public class Program {

    public static void main(String[] args) {
        try {
            DBHandler dbHandler = new DBHandler("root", "root");
            dbHandler.copyFile("tekstfil.txt", "people");
            DBTable table = dbHandler.getTable("people");
            System.out.println(table.getTablename() + " har blitt opprettet.\n");

            dbHandler.showTable("people");
            dbHandler.close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }
}
