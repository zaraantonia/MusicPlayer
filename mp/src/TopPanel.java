import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private static JLabel loggedInAsLabel;

    TopPanel(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType) {
        this.setBackground(Color.BLACK);
        this.setBounds(0, 0, 1000, 27);
        this.setVisible(true);

        String displayUserDetails = null;
        if(currentUserType == UserType.STANDARD){
            displayUserDetails = currentStandard.getUsername() + " (" + UserType.STANDARD.toString() + ")";
        } else if (currentUserType == UserType.PREMIUM) {
            displayUserDetails = currentPremium.getUsername() + " (" + UserType.PREMIUM.toString() + ")";
        } else{
            displayUserDetails = currentArtist.getUsername() + " (" + UserType.ARTIST.toString() + ")";
        }

        loggedInAsLabel = new JLabel("Logged in as: " + displayUserDetails);
        loggedInAsLabel.setLocation(5, 5);
        loggedInAsLabel.setSize(150, 15);
        loggedInAsLabel.setForeground(Color.white);
        loggedInAsLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(loggedInAsLabel);
    }

    public static void usernameLabelRefresh(String newUsername, UserType currentUserType) {
        loggedInAsLabel.setText("Logged in as: " + newUsername + " (" + currentUserType.toString() + ")");
    }
}
