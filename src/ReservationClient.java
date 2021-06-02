import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
/**
 *
 * Client Class
 *
 */
public class ReservationClient implements ActionListener, KeyListener {
    JFrame connect;
    JFrame main;
    JFrame passengerListFrame;

    JButton cancelButton1;
    JButton okButton1;
    JButton cancelButton2;
    JButton okButton2;
    JButton exitButton1;
    JButton bookFlight1;
    JButton exitButton2;
    JButton confirmButton1;
    JButton exitButton3;
    JButton chooseFlightButton1;
    JButton exitButton4;
    JButton differentchoice1;
    JButton confirmFlight;
    JButton exitButton5;
    JButton nextButton1;
    JButton yesButton1;
    JButton noButton1;
    JButton exitButton6;
    JButton refreshButton;
    JButton listButton;

    JComboBox dropMenu;

    JPanel hostPanel;
    JPanel portPanel;
    JPanel welcomePanel;
    JPanel confirmBook;
    JPanel chooseFlight;
    JPanel confirmFlightBooking;
    JPanel enterInfo;
    JPanel confirmInfo;
    JPanel finalPanel;
    JPanel passengerListPanel;

    JLabel host;
    JLabel port;
    JLabel welcomeLabel;
    JLabel confirmTxt;
    JLabel dropLabel;
    JLabel confirmBookingLabel;
    JLabel enterInfopls;
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel ageLabel;
    JLabel confirmInfoLabel;
    JLabel finalLabel;
    JLabel flightsAd;
    JLabel passengerListLabel;
    JLabel image;
    JLabel thanks;
    JScrollPane list;
    JTextArea ja;

    JTextField hostTxt;
    JTextField portTxt;
    JTextField firstNameTxt;
    JTextField lastNameTxt;
    JTextField ageTxt;

    Socket s;
    ObjectOutputStream oos;

    String hostName;
    int portNo;
    String selected;
    String firstName;
    String lastName;
    int age;
    private static String[] passNames;
    private static BoardingPass boardingPass;
    private static String[] availableFlights;
    private static String passNumber;

    public ReservationClient() {
        connect = new JFrame("Connect to server ");
        connect.setBounds(500, 200, 400, 200);
        hostPanel = new JPanel();
        hostPanel.setLayout(null);
        hostPanel.setBounds(500, 200, 400, 200);

        host = new JLabel("What is the host name you'd like to connect to?");
        host.setBounds(100, 15, 300, 15);
        hostPanel.add(host);

        hostTxt = new JTextField(30);
        hostTxt.setBounds(100, 40, 250, 25);
        hostTxt.addKeyListener(this);
        hostPanel.add(hostTxt);

        cancelButton1 = new JButton("Cancel");
        okButton1 = new JButton("Ok");
        cancelButton1.setBounds(200, 120, 80, 25);
        okButton1.setBounds(300, 120, 80, 25);
        cancelButton1.addActionListener(this);
        okButton1.addActionListener(this);
        hostPanel.add(cancelButton1);
        hostPanel.add(okButton1);

        connect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hostPanel.setBackground(Color.WHITE);

        connect.add(hostPanel);
        connect.setVisible(true);
    }

    public static void setPassNo(String passNo) {
        passNumber = passNo;
    }

    public static void setNames(String[] names) {
        passNames = names;
    }

    public static void setBoardingPass(BoardingPass boarding) {
        boardingPass = boarding;
    }

    public static void setAvailableFlights(String[] flights) {
        availableFlights = flights;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReservationClient();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(okButton1)) {
            hostName = hostTxt.getText();
            portNoPanel();
        } else if (e.getSource().equals(okButton2)) {
            portNo = Integer.parseInt(portTxt.getText());
            int t = 0;
            try {
                s = new Socket(hostName, portNo);
            } catch (IOException q) {
                JOptionPane.showMessageDialog(null,
                        "The server you are trying to connect to doesn't exist!",
                        "Connection Error!", JOptionPane.ERROR_MESSAGE);
                connect.dispose();
                new ReservationClient();
                t = 1;
            }
            if (t == 0) {
                connect.dispose();
                welcomePanel();
            }
        } else if (e.getSource().equals(bookFlight1)) {
            confirmBooking();
        } else if (e.getSource().equals(confirmButton1)) {
            confirmBook.setVisible(false);
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("AVAILABLE");
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "AVAILABLE");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();
                if (availableFlights.length == 0 || availableFlights == null) {
                    main.dispose();
                    JOptionPane.showMessageDialog(null,
                            "Sorry there are no flights available currently.",
                            "Flights Full", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    chooseFlightPanel();
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(chooseFlightButton1)) {
            chooseFlight.setVisible(false);
            confirmFlightPanel();
        } else if (e.getSource().equals(confirmFlight)) {
            confirmFlightBooking.setVisible(false);
            personalInfoPanel();
        } else if (e.getSource().equals(differentchoice1)) {
            confirmFlightBooking.setVisible(false);
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("AVAILABLE");
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "AVAILABLE");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();

                if (availableFlights.length == 0 || availableFlights == null) {
                    main.dispose();
                    JOptionPane.showMessageDialog(null,
                            "Sorry there are no flights available currently.",
                            "Flights Full", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    chooseFlightPanel();
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(nextButton1)) {
            int y = 0;
            firstName = firstNameTxt.getText();
            Pattern p = Pattern.compile("[^-a-z0-9]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(firstName);
            if (m.find()) {
                y = 1;
                JOptionPane.showMessageDialog(null,
                        "Your first name must not contain special characters in it!",
                        "Name error", JOptionPane.ERROR_MESSAGE);
            }

            lastName = lastNameTxt.getText();
            Pattern p1 = Pattern.compile("[^-a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m1 = p.matcher(lastName);
            if (m1.find()) {
                y = 1;
                JOptionPane.showMessageDialog(null,
                        "Your last name must not contain special characters in it!",
                        "Name error", JOptionPane.ERROR_MESSAGE);
            }

            try {
                age = Integer.parseInt(ageTxt.getText());
            } catch (NumberFormatException e1) {
                y = 1;
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number for age!", "Age error", JOptionPane.ERROR_MESSAGE);
            }
            if (y == 0) {
                enterInfo.setVisible(false);
                confirmInfoPanel();
            }
        } else if (e.getSource().equals(yesButton1)) {
            int h = 0;
            confirmInfo.setVisible(false);
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("ADD/LIST");
                Passenger passenger = new Passenger(firstName, lastName, age);
                oos.writeObject(passenger);
                selected = (String) dropMenu.getSelectedItem();
                oos.writeUTF(selected);
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "ADD/LIST");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();

                if (boardingPass == null) {
                    h = 1;
                    JOptionPane.showMessageDialog(null, "The flight you selected is full. Go " +
                            "back to choose another flight.", "Flight Full", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        s = new Socket(hostName, portNo);
                        oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeUTF("AVAILABLE");
                        oos.flush();
                        ResponseListener rl1 = new ResponseListener(s, "AVAILABLE");
                        Thread thread1 = new Thread(rl1);
                        thread1.start();
                        thread1.join();
                        if (availableFlights.length == 0 || availableFlights == null) {
                            main.dispose();
                            JOptionPane.showMessageDialog(null,
                                    "Sorry there are no flights available currently.",
                                    "Flights Full", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            chooseFlightPanel();
                        }
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    confirmInfo.setVisible(false);
                    chooseFlightPanel();
                }
                if (h == 0) {
                    finalFrame();
                }
            } catch (IOException | InterruptedException q) {
                q.printStackTrace();
            }
        } else if (e.getSource().equals(refreshButton)) {
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("PASSENGERLIST");
                oos.writeUTF(selected);
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "PASSENGERLIST");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();

                ja.setText(null);
                ja.append(passNumber);
                ja.append("\n");
                for (int i = 0; i < passNames.length; i++) {
                    ja.append(passNames[i]);
                    ja.append("\n");
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(listButton)) {
            passengerListFrame.dispose();
        } else if (e.getSource().equals(cancelButton1) || e.getSource().equals(cancelButton2)) {
            connect.dispose();
        } else if (e.getID() == 1) {
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("PASSENGERLIST");
                selected = (String) dropMenu.getSelectedItem();
                oos.writeUTF(selected);
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "PASSENGERLIST");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();
                flightPassengerList();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(listButton)) {
            passengerListFrame.dispose();
        } else if (e.getSource().equals(noButton1)) {
            confirmInfo.setVisible(false);
            personalInfoPanel();
        } else if (e.getSource().equals(exitButton1) || e.getSource().equals(exitButton2)
                || e.getSource().equals(exitButton3) || e.getSource().equals(exitButton4)
                || e.getSource().equals(exitButton5) || e.getSource().equals(exitButton6)) {
            main.dispose();
        } else if (dropMenu.getSelectedItem().toString().equals("Southwest")) {
            flightsAd.setText("<html>" + "Southwest airlines offers a wide range of" +
                    " in-flights amenities, " + "<br>" +
                    "such as, WIFI, 2 Main Course meals, and over 400 mov" +
                    "ies to pick from." + "</html>");
        } else if (dropMenu.getSelectedItem().toString().equals("Delta")) {
            flightsAd.setText("<html>" + "Delta airlines offers In-Air duty-free shopping where" + "<br>" +
                    "you can buy a wide variety of designer brand items. Delta also" + "<br>" +
                    "offers complimentary free WIFI." + "</html>");
        } else if (dropMenu.getSelectedItem().toString().equals("Alaska")) {
            flightsAd.setText("<html>" + "Alaska airlines offers more leg room" + "<br>" +
                    ", complimentary free WIFI, and complimentary free toys for little" + "<br>" +
                    "flyers." + "</html>");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == hostTxt) {
                hostPanel.setVisible(false);
                hostName = hostTxt.getText();
                portNoPanel();
            } else if (e.getSource() == portTxt) {
                portNo = Integer.parseInt(portTxt.getText());
                int t = 0;
                try {
                    s = new Socket(hostName, portNo);
                } catch (IOException q) {
                    JOptionPane.showMessageDialog(null,
                            "The server you are trying to connect to doesn't exist!",
                            "Connection Error!", JOptionPane.ERROR_MESSAGE);
                    connect.dispose();
                    new ReservationClient();
                    t = 1;
                }
                if (t == 0) {
                    connect.dispose();
                    welcomePanel();
                }
            } else if (e.getSource() == firstNameTxt) {
                lastNameTxt.requestFocus();
            } else if (e.getSource() == lastNameTxt) {
                ageTxt.requestFocus();
            } else if (e.getSource() == ageTxt) {
                int y = 0;
                firstName = firstNameTxt.getText();
                Pattern p = Pattern.compile("[^-a-z0-9]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(firstName);
                if (m.find()) {
                    y = 1;
                    JOptionPane.showMessageDialog(null,
                            "Your first name must not contain special characters in it!",
                            "Name error", JOptionPane.ERROR_MESSAGE);
                }

                lastName = lastNameTxt.getText();
                Pattern p1 = Pattern.compile("[^-a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m1 = p.matcher(lastName);
                if (m1.find()) {
                    y = 1;
                    JOptionPane.showMessageDialog(null,
                            "Your last name must not contain special characters in it!",
                            "Name error", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    age = Integer.parseInt(ageTxt.getText());
                } catch (NumberFormatException e1) {
                    y = 1;
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid number for age!", "Age error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (y == 0) {
                    enterInfo.setVisible(false);
                    confirmInfoPanel();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
            try {
                s = new Socket(hostName, portNo);
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeUTF("PASSENGERLIST");
                selected = (String) dropMenu.getSelectedItem();
                oos.writeUTF(selected);
                oos.flush();
                ResponseListener rl = new ResponseListener(s, "PASSENGERLIST");
                Thread thread = new Thread(rl);
                thread.start();
                thread.join();
                flightPassengerList();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            passengerListFrame.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void flightPassengerList() {
        passengerListFrame = new JFrame("Passenger List");
        passengerListFrame.setBounds(300, 300, 400, 400);
        passengerListPanel = new JPanel();
        passengerListPanel.setLayout(null);
        passengerListLabel = new JLabel(selected + " Airlines: " + passNumber);
        passengerListLabel.setBounds(150, 50, 160, 50);
        passengerListPanel.add(passengerListLabel);
        ja = new JTextArea();
        ja.setEditable(false);
        ja.addKeyListener(this);
        list = new JScrollPane(ja, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ja.setText(null);
        for (int i = 0; i < passNames.length; i++) {
            ja.append(passNames[i]);
            ja.append("\n");
        }
        list.setBounds(50, 100, 300, 200);
        passengerListPanel.add(list);
        listButton = new JButton("Exit");
        listButton.setBounds(150, 300, 80, 20);
        listButton.addActionListener(this);
        passengerListPanel.add(listButton);
        passengerListFrame.add(passengerListPanel);
        passengerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        passengerListFrame.setVisible(true);
    }

    public void portNoPanel() {
        hostPanel.setVisible(false);
        portPanel = new JPanel();
        portPanel.setLayout(null);
        portPanel.setBounds(500, 200, 400, 200);

        port = new JLabel("What is the port you'd like to connect to?");
        port.setBounds(100, 15, 300, 15);
        portPanel.add(port);

        portTxt = new JTextField(30);
        portTxt.setBounds(100, 40, 250, 25);
        portTxt.addKeyListener(this);
        portPanel.add(portTxt);

        cancelButton2 = new JButton("Cancel");
        okButton2 = new JButton("Ok");
        cancelButton2.setBounds(200, 120, 80, 25);
        okButton2.setBounds(300, 120, 80, 25);
        cancelButton2.addActionListener(this);
        okButton2.addActionListener(this);
        portPanel.add(cancelButton2);
        portPanel.add(okButton2);

        connect.add(portPanel);
        portTxt.requestFocus();
    }

    public void welcomePanel() {
        main = new JFrame("Purdue University Flight Reservation System");
        main.setVisible(true);
        main.setBounds(500, 200, 800, 400);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        welcomePanel = new JPanel();
        welcomePanel.setLayout(null);
        main.setBounds(500, 200, 800, 400);

        welcomeLabel = new JLabel("<html>" + "Welcome to the Purdue University Airline Reservation" +
                "<br>" + "Management System! " + "</html>");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        welcomeLabel.setBounds(100, 10, 600, 60);
        welcomePanel.add(welcomeLabel);

        image = new JLabel(new ImageIcon("src/image.png"));
        image.setBounds(100, 30, 500, 300);
        welcomePanel.add(image);

        exitButton1 = new JButton("Exit");
        bookFlight1 = new JButton("Book a Flight");
        exitButton1.setBounds(280, 320, 80, 25);
        bookFlight1.setBounds(400, 320, 160, 25);
        exitButton1.addActionListener(this);
        bookFlight1.addActionListener(this);
        welcomePanel.add(exitButton1);
        welcomePanel.add(bookFlight1);
        welcomePanel.setBackground(Color.WHITE);

        main.add(welcomePanel);
    }

    public void confirmBooking() {
        welcomePanel.setVisible(false);

        confirmBook = new JPanel();
        confirmBook.setLayout(null);
        confirmTxt = new JLabel("Do you want to book a flight today?");
        confirmTxt.setFont(new Font("Times New Roman", Font.BOLD, 25));
        confirmTxt.setHorizontalAlignment(SwingConstants.CENTER);
        confirmTxt.setBounds(100, 10, 600, 30);
        confirmBook.add(confirmTxt);
        exitButton2 = new JButton("Exit");
        confirmButton1 = new JButton("Yes, I want to book a flight.");
        exitButton2.setBounds(280, 320, 80, 25);
        confirmButton1.setBounds(400, 320, 200, 25);
        exitButton2.addActionListener(this);
        confirmButton1.addActionListener(this);
        confirmBook.add(exitButton2);
        confirmBook.add(confirmButton1);

        confirmBook.setBackground(Color.WHITE);

        main.add(confirmBook);
    }

    public void chooseFlightPanel() {
        main.setBounds(500, 200, 800, 400);
        chooseFlight = new JPanel();
        chooseFlight.setLayout(null);
        chooseFlight.setSize(400, 200);

        dropMenu = new JComboBox<String>();
        for (int i = 0; i < availableFlights.length; i++) {
            dropMenu.addItem(availableFlights[i]);
        }
        dropMenu.setBounds(340, 60, 100, 20);
        dropMenu.setSelectedItem(availableFlights[0]);
        dropMenu.addActionListener(this);

        flightsAd = new JLabel("");
        flightsAd.setBounds(70, 20, 600, 250);
        flightsAd.setFont(new Font ("Times New Roman", Font.BOLD, 18));
        chooseFlight.add(flightsAd);

        if (selected == null) {
            selected = availableFlights[0];
        }
        dropMenu.setSelectedItem(selected);
        dropMenu.addKeyListener(this);
        chooseFlight.add(dropMenu);

        dropLabel = new JLabel("Choose a flight from the drop the drop down menu.");
        dropLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        dropLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dropLabel.setBounds(100, 10, 600, 30);
        chooseFlight.add(dropLabel);

        exitButton3 = new JButton("Exit");
        chooseFlightButton1 = new JButton("Choose this flight");
        exitButton3.setBounds(280, 320, 80, 25);
        chooseFlightButton1.setBounds(400, 320, 200, 25);
        exitButton3.addActionListener(this);
        chooseFlightButton1.addActionListener(this);
        chooseFlight.add(exitButton3);
        chooseFlight.add(chooseFlightButton1);

        chooseFlight.setBackground(Color.WHITE);

        main.add(chooseFlight);
    }

    public void confirmFlightPanel() {
        confirmFlightBooking = new JPanel();
        confirmFlightBooking.setLayout(null);
        main.setBounds(500, 200, 800, 400);

        InputMap im = confirmFlightBooking.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = confirmFlightBooking.getActionMap();
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, 0, true);
        im.put(ks, 1);
        AbstractAction abstractAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    s = new Socket(hostName, portNo);
                    oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeUTF("PASSENGERLIST");
                    selected = (String) dropMenu.getSelectedItem();
                    oos.writeUTF(selected);
                    oos.flush();
                    ResponseListener rl = new ResponseListener(s, "PASSENGERLIST");
                    Thread thread = new Thread(rl);
                    thread.start();
                    thread.join();
                    flightPassengerList();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        actionMap.put(1, abstractAction);

        confirmBookingLabel = new JLabel("Are you sure you want to book a flight on " + selected + " Airlines?");
        confirmBookingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmBookingLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        confirmBookingLabel.setBounds(100, 10, 650, 60);
        confirmFlightBooking.add(confirmBookingLabel);

        exitButton4 = new JButton("Exit");
        differentchoice1 = new JButton("No, I want a different flight.");
        confirmFlight = new JButton("Yes, I want this flight");
        exitButton4.setBounds(120, 320, 80, 25);
        differentchoice1.setBounds(200, 320, 200, 25);
        confirmFlight.setBounds(400, 320, 160, 25);
        exitButton4.addActionListener(this);
        differentchoice1.addActionListener(this);
        confirmFlight.addActionListener(this);
        confirmFlight.addKeyListener(this);
        confirmFlightBooking.add(exitButton4);
        confirmFlightBooking.add(differentchoice1);
        confirmFlightBooking.add(confirmFlight);

        confirmFlightBooking.setBackground(Color.WHITE);

        main.add(confirmFlightBooking);
    }

    public void personalInfoPanel() {
        enterInfo = new JPanel();
        enterInfo.setLayout(null);

        InputMap im = enterInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = enterInfo.getActionMap();
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, 0, true);
        im.put(ks, 1);
        AbstractAction abstractAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    s = new Socket(hostName, portNo);
                    oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeUTF("PASSENGERLIST");
                    selected = (String) dropMenu.getSelectedItem();
                    oos.writeUTF(selected);
                    oos.flush();
                    ResponseListener rl = new ResponseListener(s, "PASSENGERLIST");
                    Thread thread = new Thread(rl);
                    thread.start();
                    thread.join();
                    flightPassengerList();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        actionMap.put(1, abstractAction);

        enterInfopls = new JLabel("Please input your information below");
        firstNameLabel = new JLabel("What is your first name?");
        firstNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        firstNameLabel.setBounds(50, 80, 650, 30);
        lastNameLabel = new JLabel("What is your last name?");
        lastNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lastNameLabel.setBounds(50, 160, 650, 30);
        ageLabel = new JLabel("What is your age?");
        ageLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        ageLabel.setBounds(50, 240, 650, 30);
        enterInfopls.setHorizontalAlignment(SwingConstants.CENTER);
        enterInfopls.setFont(new Font("Times New Roman", Font.BOLD, 25));
        enterInfopls.setBounds(100, 10, 650, 60);
        enterInfo.add(enterInfopls);
        enterInfo.add(firstNameLabel);
        enterInfo.add(lastNameLabel);
        enterInfo.add(ageLabel);

        firstNameTxt = new JTextField(50);
        lastNameTxt = new JTextField(50);
        ageTxt = new JTextField(50);
        firstNameTxt.setBounds(50, 120, 650, 30);
        lastNameTxt.setBounds(50, 200, 650, 30);
        ageTxt.setBounds(50, 280, 650, 30);
        firstNameTxt.addKeyListener(this);
        lastNameTxt.addKeyListener(this);
        ageTxt.addKeyListener(this);
        enterInfo.add(firstNameTxt);
        enterInfo.add(lastNameTxt);
        enterInfo.add(ageTxt);

        exitButton5 = new JButton("Exit");
        nextButton1 = new JButton("Next");
        exitButton5.setBounds(120, 320, 80, 25);
        nextButton1.setBounds(200, 320, 200, 25);
        exitButton5.addActionListener(this);
        nextButton1.addActionListener(this);
        exitButton5.addKeyListener(this);
        nextButton1.addKeyListener(this);
        enterInfo.add(exitButton5);
        enterInfo.add(nextButton1);
        enterInfo.setBackground(Color.WHITE);

        main.add(enterInfo);
    }

    public void confirmInfoPanel() {
        confirmInfo = new JPanel();
        confirmInfo.setLayout(null);
        confirmInfoLabel = new JLabel("<html>" + "Are all the details entered correct?" + "<br>" +
                "The passenger's name is " + firstName + " " + lastName + " and their age is " + age + "." + "<br>" +
                "If all the information shown is correct, select Yes. Otherwise select No. " + "</html>");
        confirmInfoLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        confirmInfoLabel.setBounds(50, 20, 300, 100);
        confirmInfo.add(confirmInfoLabel);

        yesButton1 = new JButton("Yes");
        noButton1 = new JButton("No");
        yesButton1.addActionListener(this);
        noButton1.addActionListener(this);
        yesButton1.setBounds(250, 100, 60, 25);
        noButton1.setBounds(320, 100, 50, 25);
        confirmInfo.add(yesButton1);
        confirmInfo.add(noButton1);

        main.add(confirmInfo);
    }

    public void finalFrame() {
        main.setBounds(300, 200, 500, 600);
        thanks = new JLabel("Thanks for using " + selected + " Airlines!");
        thanks.setFont(new Font("Times New Roman", Font.BOLD, 20));
        thanks.setBounds(30, 0, 400, 100);
        ja = new JTextArea();
        ja.setEditable(false);
        list = new JScrollPane(ja, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        list.setBounds(5, 60, 475, 350);
        ja.setText(null);
        ja.append(passNumber);
        ja.append("\n");
        for (int i = 0; i < passNames.length; i++) {
            ja.append(passNames[i]);
            ja.append("\n");
        }
        finalPanel = new JPanel();
        finalPanel.setLayout(null);
        finalLabel = new JLabel(boardingPass.getBoardingPass());
        finalLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        finalLabel.setBounds(0, 345, 400, 250);
        finalPanel.add(finalLabel);
        exitButton6 = new JButton("Exit");
        refreshButton = new JButton("Refresh flight status");
        exitButton6.setBounds(150, 530, 80, 25);
        refreshButton.setBounds(250, 530, 200, 25);
        exitButton6.addActionListener(this);
        refreshButton.addActionListener(this);
        finalPanel.add(exitButton6);
        finalPanel.add(refreshButton);
        finalPanel.add(thanks);
        finalPanel.add(list);
        finalPanel.setBackground(Color.WHITE);

        main.add(finalPanel);
    }
}