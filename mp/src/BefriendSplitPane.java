import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class BefriendSplitPane extends JSplitPane {

    private static DefaultListModel<String> befriendModel;

    BefriendSplitPane(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0, 0, 150, 50);

        JList<String> befriendList = new JList<>();
        befriendModel = new DefaultListModel<>();
        if (currentUserType == UserType.STANDARD) {
            for (StandardUser loopStandard : currentStandard.getStandardfriends()) {
                befriendModel.addElement(loopStandard.getUsername());
            }
            for (PremiumUser loopPremium : currentStandard.getPremiumfriends()) {
                befriendModel.addElement(loopPremium.getUsername());
            }
        } else if (currentUserType == UserType.PREMIUM) {
            for (StandardUser loopStandard : currentPremium.getStandardfriends()) {
                befriendModel.addElement(loopStandard.getUsername());
            }
            for (PremiumUser loopPremium : currentPremium.getPremiumfriends()) {
                befriendModel.addElement(loopPremium.getUsername());
            }
        }

        befriendList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = befriendList.getSelectedValue();
            boolean found = false;
            for (User loopUser : allUsers) {
                if (loopUser.getUsername().equals(selectedTitle)) {
                    for (StandardUser loopStandard : standardUsers) {
                        if (loopStandard.getUsername() == selectedTitle) {
                            PeoplePanel.setSelectedStandardFriend(loopStandard);
                            PeoplePanel.setIsStandardSelectedFriend(true);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        for (PremiumUser loopPremium : premiumUsers) {
                            if (loopPremium.getUsername() == selectedTitle) {
                                PeoplePanel.setSelectedPremiumFriend(loopPremium);
                                PeoplePanel.setIsStandardSelectedFriend(false);
                            }
                        }
                    }
                }
            }

            if (PeoplePanel.isIsStandardSelectedFriend() == true) {
                StandardUser displayUser = PeoplePanel.getSelectedStandardFriend();
                textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                        + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());
            }
            else{
                PremiumUser displayUser = PeoplePanel.getSelectedPremiumFriend();
                textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                        + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());
            }
        });

        JPanel buySongPanel = new JPanel();
        befriendList.setModel(befriendModel);

        this.setLeftComponent(new JScrollPane(befriendList));
        buySongPanel.add(textArea);
        this.setRightComponent(buySongPanel);
        this.setBounds(20, 30, 350, 100);
        this.setVisible(true);
    }

    public static void refreshFriends(UserType currentUserType, StandardUser currentStandard, PremiumUser currentPremium) {
        befriendModel.setSize(0);
        if (currentUserType == UserType.STANDARD) {
            for (StandardUser loopStandard : currentStandard.getStandardfriends()) {
                befriendModel.addElement(loopStandard.getUsername());
            }
            for (PremiumUser loopPremium : currentStandard.getPremiumfriends()) {
                befriendModel.addElement(loopPremium.getUsername());
            }
        } else if (currentUserType == UserType.PREMIUM) {
            for (StandardUser loopStandard : currentPremium.getStandardfriends()) {
                befriendModel.addElement(loopStandard.getUsername());
            }
            for (PremiumUser loopPremium : currentPremium.getPremiumfriends()) {
                befriendModel.addElement(loopPremium.getUsername());
            }
        }
    }
}
