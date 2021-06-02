import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * Southwest Airline
 *
 */
public class Southwest implements Airline {
    private int capacity;
    private int currentPassenger;
    private String southwest;
    private String passenger;
    private String[] passengerList;
    private String gate;

    public Southwest() {
        southwest = "SOUTHWEST";
        passenger = "";
        passengerList = new String[0];
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("res.txt"));
            String line = "";
            ArrayList<String> psgInfo = new ArrayList<>();
            while ((line = bfr.readLine()) != null) {
                psgInfo.add(line);
            }
            bfr.close();
            for (int i = 0; i < psgInfo.size(); i++) {
                if (psgInfo.get(i).contains(southwest)) {
                    if (psgInfo.get(i - 1) != null && psgInfo.get(i - 1).contains(".")) {
                        passenger += psgInfo.get(i - 1) + "/";
                    }
                    passengerList = passenger.split("/");
                    String line1 = psgInfo.get(psgInfo.indexOf(southwest) + 1);
                    String[] passger = line1.split("/");
                    currentPassenger = Integer.parseInt(passger[0]);
                    capacity = Integer.parseInt(passger[1]);
                    if (i == 0) {
                        continue;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getGate() {
        return gate;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public synchronized int currentPassenger() {
        southwest = "SOUTHWEST";
        passenger = "";
        passengerList = new String[0];
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("res.txt"));
            String line = "";
            ArrayList<String> psgInfo = new ArrayList<>();
            while ((line = bfr.readLine()) != null) {
                psgInfo.add(line);
            }
            bfr.close();
            for (int i = 0; i < psgInfo.size(); i++) {
                if (psgInfo.get(i).contains(southwest)) {
                    if (i != 0 && psgInfo.get(i - 1).contains(".") && psgInfo.get(i - 1) != null) {
                        passenger += psgInfo.get(i - 1) + "/";
                    }
                    passengerList = passenger.split("/");
                    String line1 = psgInfo.get(psgInfo.indexOf(southwest) + 1);
                    String[] passger = line1.split("/");
                    currentPassenger = Integer.parseInt(passger[0]);
                    capacity = Integer.parseInt(passger[1]);
                    if (i == 0) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentPassenger;
    }

    @Override
    public synchronized String[] passengerNames() {
        southwest = "SOUTHWEST";
        passenger = "";
        passengerList = new String[0];
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("res.txt"));
            String line = "";
            ArrayList<String> psgInfo = new ArrayList<>();
            while ((line = bfr.readLine()) != null) {
                psgInfo.add(line);
            }
            bfr.close();
            for (int i = 0; i < psgInfo.size(); i++) {
                if (psgInfo.get(i).contains(southwest)) {
                    if (i != 0 && psgInfo.get(i - 1).contains(".") && psgInfo.get(i - 1) != null) {
                        passenger += psgInfo.get(i - 1) + "/";
                    }
                    passengerList = passenger.split("/");
                    String line1 = psgInfo.get(psgInfo.indexOf(southwest) + 1);
                    String[] passger = line1.split("/");
                    currentPassenger = Integer.parseInt(passger[0]);
                    capacity = Integer.parseInt(passger[1]);
                    if (i == 0) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passengerList;
    }

    @Override
    public String airLineName() {
        return "Southwest AirLines";
    }

    public synchronized boolean checkFull() {
        southwest = "SOUTHWEST";
        passenger = "";
        passengerList = new String[0];
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("res.txt"));
            String line = "";
            ArrayList<String> psgInfo = new ArrayList<>();
            while ((line = bfr.readLine()) != null) {
                psgInfo.add(line);
            }
            bfr.close();
            for (int i = 0; i < psgInfo.size(); i++) {
                if (psgInfo.get(i).contains(southwest)) {
                    if (i != 0 && psgInfo.get(i - 1).contains(".") && psgInfo.get(i - 1) != null) {
                        passenger += psgInfo.get(i - 1) + "/";
                    }
                    passengerList = passenger.split("/");
                    String line1 = psgInfo.get(psgInfo.indexOf(southwest) + 1);
                    String[] passger = line1.split("/");
                    currentPassenger = Integer.parseInt(passger[0]);
                    capacity = Integer.parseInt(passger[1]);
                    if (i == 0) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (currentPassenger < capacity);
    }

    public synchronized void addPassenger(String name) throws IOException {
        String passengerNum = "0";
        String[] currentPas = new String[0];
        String[] na = name.split(" ");
        int position = 0;
        int numberPosition = 0;
        int currentPass = 0;
        String formattedName = na[0].toUpperCase().charAt(0) + ". " + na[1].toUpperCase() + na[2] + "\n" +
                "---------------------SOUTHWEST";
        try {

            BufferedReader bfr = new BufferedReader(new FileReader("res.txt"));
            String line = "";
            ArrayList<String> lines = new ArrayList<>();
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("Southwest")) {
                    position = i + 1;
                }
                numberPosition = lines.indexOf("SOUTHWEST") + 1;
                passengerNum = lines.get(lines.indexOf("SOUTHWEST") + 1);
                currentPas = passengerNum.split("/");
                currentPass = Integer.parseInt(currentPas[0]);
                currentPass++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = Paths.get("res.txt");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.add(position, formattedName);
        lines.remove(numberPosition);
        lines.add(numberPosition, currentPass + "/" + 200);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }
}
