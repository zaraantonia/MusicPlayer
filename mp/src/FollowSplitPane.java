import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class FollowSplitPane extends JSplitPane {

    private static DefaultListModel<String> followModel;

    FollowSplitPane(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0, 0, 150, 50);

        JList<String> followList = new JList<>();
        followModel = new DefaultListModel<>();
        for (Artist loopArtist : artistsUsers) {
            followModel.addElement(loopArtist.getUsername());
        }

        followList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = followList.getSelectedValue();
            boolean found = false;
            for (Artist loopArtist : artistsUsers) {
                if (loopArtist.getUsername() == selectedTitle) {
                    PeoplePanel.setSelectedArtist(loopArtist);
                }
            }
            Artist displayUser = PeoplePanel.getSelectedArtist();
            textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                    + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());

        });

        JPanel buySongPanel = new JPanel();
        followList.setModel(followModel);

        this.setLeftComponent(new JScrollPane(followList));
        buySongPanel.add(textArea);
        this.setRightComponent(buySongPanel);
        this.setBounds(420, 30, 350, 100);
        this.setVisible(true);
    }
}
