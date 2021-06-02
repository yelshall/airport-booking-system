import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
/**
 *
 * Response Listener
 *
 */
public class ResponseListener implements Runnable {
    private String function;
    private Socket s;

    public ResponseListener(Socket s, String function) {
        this.s = s;
        this.function = function;
    }

    @Override
    public void run() {
        if (function.equals("PASSENGERLIST")) {
            readPassengerList();
        } else if (function.equals("ADD/LIST")) {
            addAndList();
        } else if (function.equals("AVAILABLE")) {
            availableFlights();
        }
    }

    public void readPassengerList() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            String[] names = (String[]) ois.readObject();
            String noPass = ois.readUTF();
            ois.close();

            ReservationClient.setPassNo(noPass);
            ReservationClient.setNames(names);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addAndList() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            String x = ois.readUTF();
            if (x.equals("FULL")) {
                ReservationClient.setBoardingPass(null);
            } else if (x.equals("SPACE")) {
                BoardingPass boardingPass = (BoardingPass) ois.readObject();
                String[] names = (String[]) ois.readObject();
                String noPass = ois.readUTF();
                ois.close();
                ReservationClient.setPassNo(noPass);
                ReservationClient.setNames(names);
                ReservationClient.setBoardingPass(boardingPass);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void availableFlights() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            String[] flights = (String[]) ois.readObject();
            ois.close();

            ReservationClient.setAvailableFlights(flights);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
