import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.Random;
/**
 *
 * Boarding Pass
 *
 */
public class BoardingPass implements Serializable {
    Random r = new Random();
    private String boardingPass;

    public BoardingPass(String firstName, String lastName, int age,
                        String airLine, String gate) {
        this.boardingPass = "<html>" + "-----------------------------" +
                "-------------------------" + "<br>" +
                "BOARDING PASS FOR FLIGHT " + r.nextInt(100000) + " WITH "
                + airLine + " AirLines" + "<br>" +
                "PASSENGER FIRST NAME: " + firstName.toUpperCase() + "<br>" +
                "PASSENGER LAST NAME: " + lastName.toUpperCase() + "<br>" +
                "PASSENGER AGE: " + age + "<br>" +
                "You can now begin at gate " + gate + "<br>" +
                "------------------------------------------------------" + "</html>";
    }

    public String getBoardingPass() {
        return boardingPass;
    }
}
