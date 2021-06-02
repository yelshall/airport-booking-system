import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Objects;
/**
 *
 * Request Handler
 *
 */
public class RequestHandler extends Thread implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Delta delta;
    private Alaska alaska;
    private Southwest southwest;
    private String airlineSelected;

    public RequestHandler(Socket clientSocket, Alaska alaska, Delta delta, Southwest southwest) throws IOException {
        this.alaska = alaska;
        this.delta = delta;
        this.southwest = southwest;
        this.clientSocket = Objects.requireNonNull(clientSocket);
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            String s = ois.readUTF();
            if (s.equals("AVAILABLE")) {
                flightsAvailable();
            } else if (s.equals("ADD/LIST")) {
                addNewPassenger();
            } else if (s.equals("PASSENGERLIST")) {
                getPassengers();
            }
        } catch (EOFException e) {
            return;
        } catch (SocketException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flightsAvailable() {
        ArrayList<String> flights = new ArrayList<>();

        if (alaska.checkFull()) {
            flights.add("Alaska");
        }
        if (delta.checkFull()) {
            flights.add("Delta");
        }
        if (southwest.checkFull()) {
            flights.add("Southwest");
        }

        String[] flightsArray = flights.toArray(new String[flights.size()]);
        try {
            oos.writeObject(flightsArray);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewPassenger() {
        Passenger passenger;
        BoardingPass boardingPass = null;

        try {
            passenger = (Passenger) ois.readObject();
            String airline = ois.readUTF();
            if (airline.equals("Alaska")) {
                if (alaska.checkFull()) {
                    alaska.addPassenger(passenger.toString());
                    boardingPass = new BoardingPass(passenger.getFirstName(), passenger.getLastName(),
                            passenger.getAge(), "Alaska", alaska.getGate());
                    oos.writeUTF("SPACE");
                    oos.writeObject(boardingPass);
                    oos.writeObject(alaska.passengerNames());
                    oos.writeUTF(String.format("(%d/%d)", alaska.currentPassenger(), alaska.capacity()));
                    oos.flush();
                } else {
                    oos.writeUTF("FULL");
                    oos.flush();
                }
            } else if (airline.equals("Southwest")) {
                if (southwest.checkFull()) {
                    southwest.addPassenger(passenger.toString());
                    boardingPass = new BoardingPass(passenger.getFirstName(), passenger.getLastName(),
                            passenger.getAge(), "Southwest", southwest.getGate());
                    oos.writeUTF("SPACE");
                    oos.writeObject(boardingPass);
                    oos.writeObject(southwest.passengerNames());
                    oos.writeUTF(String.format("(%d/%d)", delta.currentPassenger(), delta.capacity()));
                    oos.flush();
                } else {
                    oos.writeUTF("FULL");
                    oos.flush();
                }
            } else if (airline.equals("Delta")) {
                if (delta.checkFull()) {
                    delta.addPassenger(passenger.toString());
                    boardingPass = new BoardingPass(passenger.getFirstName(), passenger.getLastName(),
                            passenger.getAge(), "Delta", delta.getGate());
                    oos.writeUTF("SPACE");
                    oos.writeObject(boardingPass);
                    oos.writeObject(delta.passengerNames());
                    oos.writeUTF(String.format("(%d/%d)", southwest.currentPassenger(), southwest.capacity()));
                    oos.flush();
                } else {
                    oos.writeUTF("FULL");
                    oos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getPassengers() {
        String[] names = null;
        String cap = "";
        String alaskaCap = String.format("(%d/%d)", alaska.currentPassenger(), alaska.capacity());
        String deltaCap = String.format("(%d/%d)", delta.currentPassenger(), delta.capacity());
        String southwestCap = String.format("(%d/%d)", southwest.currentPassenger(), southwest.capacity());

        try {
            String airline = ois.readUTF();
            if (airline.equals("Alaska")) {
                names = alaska.passengerNames();
                cap = alaskaCap;
            } else if (airline.equals("Southwest")) {
                names = southwest.passengerNames();
                cap = southwestCap;
            } else if (airline.equals("Delta")) {
                names = delta.passengerNames();
                cap = deltaCap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(names);
            oos.writeUTF(cap);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
