import java.io.Serializable;
/**
 *
 * Passenger Class
 *
 */
public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private BoardingPass boardingPass;

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Passenger(String firstName, String lastName, int age, BoardingPass boardingPass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.boardingPass = boardingPass;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public String toString() {
        return String.format("%s. %s, %d", firstName.charAt(0), lastName, age);
    }
}
