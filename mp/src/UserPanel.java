import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserPanel extends JPanel {

    private static Song selectedSong;
    private static Album selectedAlbum;
    JButton buySongButton;
    JButton buyAlbumButton;

    UserPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Country> allCountries, ArrayList<User> allUsers) {
        this.setBackground(Color.darkGray);
        this.setBounds(0, 0, 850, 400);
        this.setLayout(null);


        //buy song area
        JLabel changeDetailsLabel = new JLabel("Change your details");
        changeDetailsLabel.setLocation(20, 10);
        changeDetailsLabel.setSize(150, 15);
        changeDetailsLabel.setForeground(Color.white);
        changeDetailsLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
        this.add(changeDetailsLabel);

        JLabel insertUsernameLabel = new JLabel("Username");
        insertUsernameLabel.setLocation(20, 40);
        insertUsernameLabel.setSize(150, 15);
        insertUsernameLabel.setForeground(Color.white);
        insertUsernameLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertUsernameLabel);

        JTextField insertUsernameField = new JTextField();
        insertUsernameField.setBounds(130,40,150,20);
        insertUsernameField.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertUsernameField);

        JButton insertUsernameButton = new JButton("Modify");
        insertUsernameButton.setHorizontalTextPosition(JButton.CENTER);
        insertUsernameButton.setVerticalTextPosition(JButton.CENTER);
        insertUsernameButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        insertUsernameButton.setBackground(new Color(0x83d4ec));
        insertUsernameButton.setBounds(300, 40, 80, 20);
        insertUsernameButton.setFocusable(false);
        insertUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertUsernameField.getText() == null){
                    JOptionPane.showMessageDialog(null,"First write something!");
                }
                else if(insertUsernameField.getText().length() >= 30){
                    JOptionPane.showMessageDialog(null,"Username is too long, write a shorter one!");
                }
                else if(Controller.usernameAlreadyExists(insertUsernameField.getText())){
                    JOptionPane.showMessageDialog(null,"Username already exists, try another one!");
                }
                else{
                    int id = 0;
                    String newUsername = insertUsernameField.getText();
                    if(currentUserType == UserType.STANDARD){
                        id = currentStandard.getId();
                        currentStandard.setUsername(newUsername);
                    } else if (currentUserType == UserType.PREMIUM){
                        id = currentPremium.getId();
                        currentPremium.setUsername(newUsername);
                    } else if(currentUserType == UserType.ARTIST){
                        id = currentArtist.getId();
                        currentArtist.setUsername(newUsername);
                    }
                    try {
                        Database.modifyUsername(myConn,id,newUsername);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    GeneralAccessPanel.usernameLabelRefresh(newUsername);
                    TopPanel.usernameLabelRefresh(newUsername,currentUserType);
                }
            }
        });
        this.add(insertUsernameButton);

        JLabel insertFirstNameLabel = new JLabel("First Name");
        insertFirstNameLabel.setLocation(20, 80);
        insertFirstNameLabel.setSize(150, 15);
        insertFirstNameLabel.setForeground(Color.white);
        insertFirstNameLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertFirstNameLabel);

        JTextField insertFirstNameField = new JTextField();
        insertFirstNameField.setBounds(130,80,150,20);
        insertFirstNameField.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertFirstNameField);

        JButton insertFirstNameButton = new JButton("Modify");
        insertFirstNameButton.setHorizontalTextPosition(JButton.CENTER);
        insertFirstNameButton.setVerticalTextPosition(JButton.CENTER);
        insertFirstNameButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        insertFirstNameButton.setBackground(new Color(0x83d4ec));
        insertFirstNameButton.setBounds(300, 80, 80, 20);
        insertFirstNameButton.setFocusable(false);
        insertFirstNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertFirstNameField.getText() == null){
                    JOptionPane.showMessageDialog(null,"First write something!");
                }
                else if(insertFirstNameField.getText().length() >= 30){
                    JOptionPane.showMessageDialog(null,"First name is too long, write a shorter one!");
                }
                else{
                    int id = 0;
                    String newFirstName = insertFirstNameField.getText();
                    if(currentUserType == UserType.STANDARD){
                        id = currentStandard.getId();
                        currentStandard.setFirstName(newFirstName);
                        GeneralAccessPanel.fullNameLabelRefresh(newFirstName,currentStandard.getLastName());
                    } else if (currentUserType == UserType.PREMIUM){
                        id = currentPremium.getId();
                        currentPremium.setFirstName(newFirstName);
                        GeneralAccessPanel.fullNameLabelRefresh(newFirstName,currentPremium.getLastName());
                    } else if(currentUserType == UserType.ARTIST){
                        id = currentArtist.getId();
                        currentArtist.setFirstName(newFirstName);
                        GeneralAccessPanel.fullNameLabelRefresh(newFirstName,currentArtist.getLastName());
                    }
                    try {
                        Database.modifyFirstName(myConn,id,newFirstName);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        this.add(insertFirstNameButton);

        JLabel insertlastNameLabel = new JLabel("Last Name");
        insertlastNameLabel.setLocation(20, 120);
        insertlastNameLabel.setSize(150, 15);
        insertlastNameLabel.setForeground(Color.white);
        insertlastNameLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertlastNameLabel);

        JTextField insertLastNameField = new JTextField();
        insertLastNameField.setBounds(130,120,150,20);
        insertLastNameField.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertLastNameField);

        JButton insertlastNameButton = new JButton("Modify");
        insertlastNameButton.setHorizontalTextPosition(JButton.CENTER);
        insertlastNameButton.setVerticalTextPosition(JButton.CENTER);
        insertlastNameButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        insertlastNameButton.setBackground(new Color(0x83d4ec));
        insertlastNameButton.setBounds(300, 120, 80, 20);
        insertlastNameButton.setFocusable(false);
        insertlastNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertLastNameField.getText() == null){
                    JOptionPane.showMessageDialog(null,"First write something!");
                }
                else if(insertLastNameField.getText().length() >= 30){
                    JOptionPane.showMessageDialog(null,"Last name is too long, write a shorter one!");
                }
                else{
                    int id = 0;
                    String newLastName = insertLastNameField.getText();
                    if(currentUserType == UserType.STANDARD){
                        id = currentStandard.getId();
                        currentStandard.setLastName(newLastName);
                        GeneralAccessPanel.fullNameLabelRefresh(currentStandard.getFirstName(),newLastName);
                    } else if (currentUserType == UserType.PREMIUM){
                        id = currentPremium.getId();
                        currentPremium.setLastName(newLastName);
                        GeneralAccessPanel.fullNameLabelRefresh(currentPremium.getFirstName(),newLastName);
                    } else if(currentUserType == UserType.ARTIST){
                        id = currentArtist.getId();
                        currentArtist.setLastName(newLastName);
                        GeneralAccessPanel.fullNameLabelRefresh(currentArtist.getFirstName(),newLastName);
                    }
                    try {
                        Database.modifyLastName(myConn,id,newLastName);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        this.add(insertlastNameButton);

        JLabel insertEmailLabel = new JLabel("Email");
        insertEmailLabel.setLocation(20, 160);
        insertEmailLabel.setSize(150, 15);
        insertEmailLabel.setForeground(Color.white);
        insertEmailLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertEmailLabel);

        JTextField insertEmailField = new JTextField();
        insertEmailField.setBounds(130,160,150,20);
        insertEmailField.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertEmailField);

        JButton insertEmailButton = new JButton("Modify");
        insertEmailButton.setHorizontalTextPosition(JButton.CENTER);
        insertEmailButton.setVerticalTextPosition(JButton.CENTER);
        insertEmailButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        insertEmailButton.setBackground(new Color(0x83d4ec));
        insertEmailButton.setBounds(300, 160, 80, 20);
        insertEmailButton.setFocusable(false);
        insertEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertEmailField.getText() == null){
                    JOptionPane.showMessageDialog(null,"First write something!");
                }
                else if(insertEmailField.getText().length() >= 30){
                    JOptionPane.showMessageDialog(null,"Email is too long, write a shorter one!");
                }
                else if(!isValidEmail(insertEmailField.getText())){
                    JOptionPane.showMessageDialog(null,"Your email is not valid!");
                }
                else{
                    int id = 0;
                    String newEmail = insertEmailField.getText();
                    if(currentUserType == UserType.STANDARD){
                        id = currentStandard.getId();
                        currentStandard.setEmail(newEmail);
                        GeneralAccessPanel.emailLabelRefresh(newEmail);
                    } else if (currentUserType == UserType.PREMIUM){
                        id = currentPremium.getId();
                        currentPremium.setEmail(newEmail);
                        GeneralAccessPanel.emailLabelRefresh(newEmail);
                    } else if(currentUserType == UserType.ARTIST){
                        id = currentArtist.getId();
                        currentArtist.setEmail(newEmail);
                        GeneralAccessPanel.emailLabelRefresh(newEmail);
                    }
                    try {
                        Database.modifyEmail(myConn,id,newEmail);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        this.add(insertEmailButton);

        JLabel insertCountryLabel = new JLabel("Country");
        insertCountryLabel.setLocation(20, 200);
        insertCountryLabel.setSize(150, 15);
        insertCountryLabel.setForeground(Color.white);
        insertCountryLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertCountryLabel);

        String[] allCountriesNames = new String[1000];
        int index = 0;
        for(Country loopCountry: allCountries){
            allCountriesNames[index] =  loopCountry.getName();
            index++;
        }
        JComboBox insertCountryComboBox = new JComboBox(allCountriesNames);
        insertCountryComboBox.setBounds(130,200,150,20);
        insertCountryComboBox.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(insertCountryComboBox);

        JButton insertCountryButton = new JButton("Modify");
        insertCountryButton.setHorizontalTextPosition(JButton.CENTER);
        insertCountryButton.setVerticalTextPosition(JButton.CENTER);
        insertCountryButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        insertCountryButton.setBackground(new Color(0x83d4ec));
        insertCountryButton.setBounds(300, 200, 80, 20);
        insertCountryButton.setFocusable(false);
        insertCountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Country countryChanged = new Country();
                if(insertCountryComboBox.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null,"First select a country!");
                }
                else {
                    for (Country loopCountry : allCountries) {
                        if (loopCountry.getName() == insertCountryComboBox.getSelectedItem().toString()) {
                            countryChanged = loopCountry;
                            break;
                        }
                    }
                    int id = 0;
                    if(currentUserType == UserType.STANDARD){
                        id = currentStandard.getId();
                        currentStandard.setCountry(countryChanged);
                        GeneralAccessPanel.countryLabelRefresh(countryChanged.getName());
                    } else if (currentUserType == UserType.PREMIUM){
                        id = currentPremium.getId();
                        currentPremium.setCountry(countryChanged);
                        GeneralAccessPanel.countryLabelRefresh(countryChanged.getName());
                    } else if(currentUserType == UserType.ARTIST){
                        id = currentArtist.getId();
                        currentArtist.setCountry(countryChanged);
                        GeneralAccessPanel.countryLabelRefresh(countryChanged.getName());
                    }
                    try {
                        Database.modifyCountry(myConn,id,countryChanged.getId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        this.add(insertCountryButton);
        
        this.setVisible(true);

    }

    private boolean isValidEmail(String text) {
        boolean valid = false;
        if(text.contains("@") && text.contains(".")){
            valid = true;
        }
        return valid;
    }
}
