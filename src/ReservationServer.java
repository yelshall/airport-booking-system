import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
/**
 *
 * Server Class
 *
 */
public class ReservationServer {

    public static void main(String[] argv) throws Exception {
        int connectionCount = 0;
        Random random = new Random();
        ServerSocket s = new ServerSocket(random.nextInt(5000));
        System.out.printf("<Now serving clients on port %d...>%n", s.getLocalPort());

        Gate gate = new Gate();
        Alaska alaska = new Alaska();
        Delta delta = new Delta();
        Southwest southwest = new Southwest();

        alaska.setGate(gate.makeNewGate());
        delta.setGate(gate.makeNewGate());
        southwest.setGate(gate.makeNewGate());

        while (true) {
            Socket t = s.accept();
            System.out.println("server connected");

            RequestHandler handler = new RequestHandler(t, alaska, delta, southwest);

            Thread handlerThread = new Thread(handler);

            handlerThread.start();

            System.out.printf("<Client %d connected...>%n", connectionCount);

            connectionCount++;
        }
    }
}
