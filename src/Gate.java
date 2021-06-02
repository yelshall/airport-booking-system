import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * Gate Class
 *
 */
public class Gate implements Serializable {
    String gate;
    ArrayList<String> gatesMade;

    public Gate() {
        gatesMade = new ArrayList<>();
    }

    public String makeNewGate() {
        Random rand = new Random();
        char letter = '0';
        int rand1 = rand.nextInt(18) + 1;
        int rand2 = rand.nextInt(3);

        switch (rand2) {
            case 0:
                letter = 'A';
                break;
            case 1:
                letter = 'B';
                break;
            case 2:
                letter = 'C';
                break;
            default:
                break;
        }


        gate = String.format("%s%d", letter, rand1);
        if (gatesMade != null) {
            for (int i = 0; i < gatesMade.size(); i++) {
                if (gate.equals(gatesMade.get(i))) {
                    makeNewGate();
                } else {
                    gatesMade.add(gate);
                }
            }
        }

        return gate;
    }
}
