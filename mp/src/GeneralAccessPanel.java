import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralAccessPanel extends JPanel {

    JButton changeStatusButton;
    JButton upgradeButton;
    private static JLabel usernameLabel;
    private static JLabel countryLabel;
    private static JLabel emailLabel;
    private static JLabel fullNameLabel;

    GeneralAccessPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<PremiumUser> premiumUsers, ArrayList<StandardUser> standardUsers, ArrayList<Artist> artistsUsers) {
        this.setLayout(null);
        this.setBackground((new Color(0x222222)));
        this.setBounds(0, 27, 150, 450);
        this.setVisible(true);

        /*if(currentUserType == UserType.STANDARD){

        }
        else if(currentUserType == UserType.PREMIUM){

        }
        else if(currentUserType == UserType.ARTIST){

        }*/

        usernameLabel = null;
        if(currentUserType == UserType.STANDARD){
            usernameLabel = new JLabel("Username: " + currentStandard.getUsername());
        }
        else if(currentUserType == UserType.PREMIUM){
            usernameLabel = new JLabel("Username: " + currentPremium.getUsername());
        }
        else if(currentUserType == UserType.ARTIST){
            usernameLabel = new JLabel("Username: " + currentArtist.getUsername());
        }
        usernameLabel.setLocation(5, 100);
        usernameLabel.setSize(150, 15);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(usernameLabel);

        fullNameLabel = null;
        if(currentUserType == UserType.STANDARD){
            fullNameLabel = new JLabel(currentStandard.getFirstName() + " " + currentStandard.getLastName());
        }
        else if(currentUserType == UserType.PREMIUM){
            fullNameLabel = new JLabel(currentPremium.getFirstName() + " " + currentPremium.getLastName());
        }
        else if(currentUserType == UserType.ARTIST){
            fullNameLabel = new JLabel(currentArtist.getFirstName() + " " + currentArtist.getLastName());
        }
        fullNameLabel.setLocation(5, 120);
        fullNameLabel.setSize(150, 15);
        fullNameLabel.setForeground(Color.white);
        fullNameLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(fullNameLabel);

        emailLabel = null;
        if(currentUserType == UserType.STANDARD){
            emailLabel = new JLabel(currentStandard.getEmail());
        }
        else if(currentUserType == UserType.PREMIUM){
            emailLabel = new JLabel(currentPremium.getEmail());
        }
        else if(currentUserType == UserType.ARTIST){
            emailLabel = new JLabel(currentArtist.getEmail());
        }
        emailLabel.setLocation(5, 140);
        emailLabel.setSize(150, 15);
        emailLabel.setForeground(Color.white);
        emailLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(emailLabel);

        countryLabel = null;
        if(currentUserType == UserType.STANDARD){
            countryLabel = new JLabel(currentStandard.getCountry().getName());
        }
        else if(currentUserType == UserType.PREMIUM){
            countryLabel = new JLabel(currentPremium.getCountry().getName());
        }
        else if(currentUserType == UserType.ARTIST){
            countryLabel = new JLabel(currentArtist.getCountry().getName());
        }
        countryLabel.setLocation(5, 160);
        countryLabel.setSize(150, 15);
        countryLabel.setForeground(Color.white);
        countryLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(countryLabel);

        JLabel birthdayLabel = null;
        if(currentUserType == UserType.STANDARD){
            birthdayLabel = new JLabel(currentStandard.getBirthday().toString());
        }
        else if(currentUserType == UserType.PREMIUM){
            birthdayLabel = new JLabel(currentPremium.getBirthday().toString());
        }
        else if(currentUserType == UserType.ARTIST){
            birthdayLabel = new JLabel(currentArtist.getBirthday().toString());
        }
        birthdayLabel.setLocation(5, 180);
        birthdayLabel.setSize(150, 15);
        birthdayLabel.setForeground(Color.white);
        birthdayLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(birthdayLabel);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setLocation(5, 220);
        statusLabel.setSize(150, 15);
        statusLabel.setForeground(Color.white);
        statusLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(statusLabel);

        JLabel statusTextLabel = new JLabel();
        if(currentUserType == UserType.STANDARD){
            statusTextLabel.setText(currentStandard.getStatus());
        }
        else if(currentUserType == UserType.PREMIUM){
            statusTextLabel.setText(currentPremium.getStatus());
        }
        else if(currentUserType == UserType.ARTIST){
            statusTextLabel.setText(currentArtist.getStatus());
        }
        statusTextLabel.setLocation(5, 240);
        statusTextLabel.setSize(150, 15);
        statusTextLabel.setForeground(new Color(0x83d4ec));
        statusTextLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(statusTextLabel);

        JLabel profilePictureLabel = new JLabel();
        ImageIcon profilePicture = new ImageIcon(new ImageIcon("profilepicture.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        profilePictureLabel.setIcon(profilePicture);
        profilePictureLabel.setLocation((150 - 80) / 2, 10);
        profilePictureLabel.setSize(150, 80);
        profilePictureLabel.setForeground(Color.white);
        this.add(profilePictureLabel);

        JLabel changeStatusLabel = new JLabel("Change status: ");
        changeStatusLabel.setLocation(5, 275);
        changeStatusLabel.setSize(150, 15);
        changeStatusLabel.setForeground(Color.white);
        changeStatusLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(changeStatusLabel);

        JTextField statusTextField = new JTextField();
        statusTextField.setBounds(5, 295, 100, 20);
        this.add(statusTextField);

        changeStatusButton = new JButton("Change status");
        changeStatusButton.setHorizontalTextPosition(JButton.CENTER);
        changeStatusButton.setVerticalTextPosition(JButton.CENTER);
        changeStatusButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        changeStatusButton.setBackground(new Color(0x83d4ec));
        changeStatusButton.setBounds((150 - 60) / 2 - 30, 325, 120, 30);
        changeStatusButton.setFocusable(false);
        changeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentUserType == UserType.STANDARD) {
                        currentStandard.setStatus(statusTextField.getText());
                            Database.insertNewStatusInDatabase(myConn, currentStandard.getStatus(), currentStandard);
                        statusTextLabel.setText(currentStandard.getStatus());
                    } else if (currentUserType == UserType.PREMIUM) {
                        currentPremium.setStatus(statusTextField.getText());
                            Database.insertNewStatusInDatabase(myConn, currentPremium.getStatus(), currentPremium);
                        statusTextLabel.setText(currentPremium.getStatus());
                    } else if (currentUserType == UserType.ARTIST) {
                        currentArtist.setStatus(statusTextField.getText());
                        Database.insertNewStatusInDatabase(myConn, currentArtist.getStatus(), currentArtist);
                        statusTextLabel.setText(currentArtist.getStatus());
                    }
                }
                catch(Exception exceptione){
                    exceptione.printStackTrace();
                }
            }
        });
        this.add(changeStatusButton);

        upgradeButton = new JButton("Upgrade");
        upgradeButton.setHorizontalTextPosition(JButton.CENTER);
        upgradeButton.setVerticalTextPosition(JButton.CENTER);
        upgradeButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        upgradeButton.setBackground(new Color(0x83d4ec));
        upgradeButton.setBounds((150 - 60) / 2 - 30, 385, 120, 30);
        upgradeButton.setFocusable(false);

        upgradeButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentUserType == UserType.STANDARD) {
                        currentStandard.upgradeToPremium(myConn, premiumUsers, standardUsers);
                        JOptionPane.showMessageDialog(null,"You just upgraded to premium! Restart the app to see the changes!");
                    } else if (currentUserType == UserType.PREMIUM) {
                        currentPremium.upgradeToArtist(myConn, artistsUsers, premiumUsers);
                        JOptionPane.showMessageDialog(null,"You just upgraded to artist! Restart the app to see the changes!");
                    } else if (currentUserType == UserType.ARTIST) {
                        JOptionPane.showMessageDialog(null, "You are an artist. You can't upgrade anymore!");
                    }
                }
                catch(Exception exceptione){
                    exceptione.printStackTrace();
                }
            }
        });
        this.add(upgradeButton);
    }

    public static void usernameLabelRefresh(String newUsername) {
        usernameLabel.setText("Username: " + newUsername);
    }

    public static void fullNameLabelRefresh(String firstName, String lastName) {
        fullNameLabel.setText(firstName + " " + lastName);
    }

    public static void emailLabelRefresh(String text){
        emailLabel.setText(text);
    }

    public static void countryLabelRefresh(String text){
        countryLabel.setText(text);
    }
}
