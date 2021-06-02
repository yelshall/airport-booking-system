/**
 *
 * Airline Interface
 *
 */
public interface Airline {
    int capacity();

    int currentPassenger();

    String[] passengerNames();

    String airLineName();

    boolean checkFull();

    static void addPassenger(String name) {
    }
}
