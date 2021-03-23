import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class FollowerSplitPane extends JSplitPane {

    private static DefaultListModel<String> followerModel;

    FollowerSplitPane(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<User> allUsers) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0, 0, 150, 50);

        JList<String> followerList = new JList<>();
        followerModel = new DefaultListModel<>();
        if(currentUserType == UserType.ARTIST) {
            for (User loopUser : currentArtist.getFollowers()) {
                followerModel.addElement(loopUser.getUsername());
            }
        }

        followerList.getSelectionModel().addListSelectionListener(e -> {
            String selectedFollower = followerList.getSelectedValue();
            User displayUser = null;
            for (User loopUser: allUsers) {
                if (loopUser.getUsername() == selectedFollower) {
                    displayUser = loopUser;
                    break;
                }
            }
            textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                    + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());

        });

        JPanel buySongPanel = new JPanel();
        followerList.setModel(followerModel);

        this.setLeftComponent(new JScrollPane(followerList));
        buySongPanel.add(textArea);
        this.setRightComponent(buySongPanel);
        this.setBounds(420, 30, 350, 100);
        this.setVisible(true);
    }
}
