import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class PeoplePanel extends JPanel {

    private static PremiumUser selectedPremiumStranger;
    private static PremiumUser selectedPremiumFriend;
    private static StandardUser selectedStandardStranger;
    private static StandardUser selectedStandardFriend;
    private static Artist selectedArtist;
    private static boolean isStandardSelectedFriend;
    private static boolean isStandardSelectedStranger;
    JButton unfriendButton;
    JButton befriendButton;
    JButton followButton;

    PeoplePanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers) {
        this.setBackground(Color.darkGray);
        this.setBounds(0, 0, 850, 400);
        this.setLayout(null);


        //friends area
        JLabel befriendLabel = new JLabel("Your friends:");
        befriendLabel.setLocation(20, 10);
        befriendLabel.setSize(150, 15);
        befriendLabel.setForeground(Color.white);
        befriendLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(befriendLabel);

        BefriendSplitPane befriendSplitPane = new BefriendSplitPane(myConn, currentStandard, currentPremium, currentArtist, currentUserType, standardUsers, premiumUsers, artistsUsers, allUsers);

        befriendButton = new JButton("Unfriend");
        befriendButton.setHorizontalTextPosition(JButton.CENTER);
        befriendButton.setVerticalTextPosition(JButton.CENTER);
        befriendButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        befriendButton.setBackground(new Color(0x83d4ec));
        befriendButton.setBounds(20, 140, 120, 30);
        befriendButton.setFocusable(false);
        befriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentUserType == UserType.STANDARD) {
                        if (isStandardSelectedFriend == true) {
                            if(selectedStandardFriend == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentStandard.deleteStandardFriend(selectedStandardFriend);
                                int id1 = currentStandard.getId();
                                int id2 = selectedStandardFriend.getId();
                                Database.deleteFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        }
                        else {
                            if(selectedPremiumFriend == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentStandard.deletePremiumFriend(selectedPremiumFriend);
                                int id1 = currentStandard.getId();
                                int id2 = selectedPremiumFriend.getId();
                                Database.deleteFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        }
                    } else if (currentUserType == UserType.PREMIUM) {
                        if (isStandardSelectedFriend == true) {
                            if(selectedStandardFriend == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentPremium.deleteStandardFriend(selectedStandardFriend);
                                int id1 = currentPremium.getId();
                                int id2 = selectedStandardFriend.getId();
                                Database.deleteFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        } else {
                            if(selectedPremiumFriend == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentPremium.deletePremiumFriend(selectedPremiumFriend);
                                int id1 = currentPremium.getId();
                                int id2 = selectedPremiumFriend.getId();
                                Database.deleteFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "A true artist's only friend is his art.");
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //strangers area
        JLabel unfriendLabel = new JLabel("Add new people:");
        unfriendLabel.setLocation(20, 200);
        unfriendLabel.setSize(150, 15);
        unfriendLabel.setForeground(Color.white);
        unfriendLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(unfriendLabel);

        UnfriendSplitPane unfriendSplitPane = new UnfriendSplitPane(myConn, currentStandard, currentPremium, currentArtist, currentUserType, standardUsers, premiumUsers, artistsUsers, allUsers);

        unfriendButton = new JButton("Add");
        unfriendButton.setHorizontalTextPosition(JButton.CENTER);
        unfriendButton.setVerticalTextPosition(JButton.CENTER);
        unfriendButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        unfriendButton.setBackground(new Color(0x83d4ec));
        unfriendButton.setBounds(20, 330, 120, 30);
        unfriendButton.setFocusable(false);
        unfriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentUserType == UserType.STANDARD) {
                        if (isStandardSelectedStranger == true) {
                            if(selectedStandardStranger == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentStandard.addStandardFriend(selectedStandardStranger);
                                selectedStandardStranger.addStandardFriend(currentStandard);
                                int id1 = currentStandard.getId();
                                int id2 = selectedStandardStranger.getId();
                                Database.addFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        } else {
                            if(selectedPremiumStranger == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentStandard.addPremiumFriend(selectedPremiumStranger);
                                selectedPremiumStranger.addStandardFriend(currentStandard);
                                int id1 = currentStandard.getId();
                                int id2 = selectedPremiumStranger.getId();
                                Database.addFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        }
                    } else if (currentUserType == UserType.PREMIUM) {
                        if (isStandardSelectedStranger == true) {
                            if(selectedStandardStranger == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else{
                            currentPremium.addStandardFriend(selectedStandardStranger);
                            selectedStandardStranger.addPremiumFriend(currentPremium);
                            int id1 = currentPremium.getId();
                            int id2 = selectedStandardStranger.getId();
                            Database.addFriendship(myConn, id1, id2);
                            BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                            UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        } else {
                            if(selectedPremiumStranger == null){
                                JOptionPane.showMessageDialog(null, "First select a person!");
                            }
                            else {
                                currentPremium.addPremiumFriend(selectedPremiumStranger);
                                selectedPremiumStranger.addPremiumFriend(currentPremium);
                                int id1 = currentPremium.getId();
                                int id2 = selectedPremiumStranger.getId();
                                Database.addFriendship(myConn, id1, id2);
                                BefriendSplitPane.refreshFriends(currentUserType, currentStandard, currentPremium);
                                UnfriendSplitPane.refreshStrangers(currentUserType, currentStandard, currentPremium, allUsers);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "A true artist's only friend is his art.");
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //follow area
        //friends area
        JLabel followLabel = new JLabel("All artists:");
        followLabel.setLocation(420, 10);
        followLabel.setSize(150, 15);
        followLabel.setForeground(Color.white);
        followLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(followLabel);

        FollowSplitPane followSplitPane = new FollowSplitPane(myConn, currentStandard, currentPremium, currentArtist, currentUserType, standardUsers, premiumUsers, artistsUsers, allUsers);

        followButton = new JButton("Follow");
        followButton.setHorizontalTextPosition(JButton.CENTER);
        followButton.setVerticalTextPosition(JButton.CENTER);
        followButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        followButton.setBackground(new Color(0x83d4ec));
        followButton.setBounds(420, 140, 120, 30);
        followButton.setFocusable(false);
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(selectedArtist == null){
                        JOptionPane.showMessageDialog(null, "First select an artist!");
                    }
                    else {
                        if (currentUserType == UserType.STANDARD) {
                            boolean found = false;
                            for (Artist loopArtist : currentStandard.getFollowing()) {
                                if (loopArtist.getId() == selectedArtist.getId()) {
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                currentStandard.follow(selectedArtist);
                                selectedArtist.getFollowers().add(currentStandard);
                                int userId = currentStandard.getId();
                                int artistId = selectedArtist.getId();
                                Database.addFollowing(myConn, userId, artistId);
                                JOptionPane.showMessageDialog(null, "You followed " + selectedArtist.getUsername() + "!");
                            } else {
                                JOptionPane.showMessageDialog(null, "You already follow this artist!");
                            }
                        } else if (currentUserType == UserType.PREMIUM) {
                            boolean found = false;
                            for (Artist loopArtist : currentPremium.getFollowing()) {
                                if (loopArtist.getId() == selectedArtist.getId()) {
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                currentPremium.follow(selectedArtist);
                                selectedArtist.getFollowers().add(currentPremium);
                                int userId = currentPremium.getId();
                                int artistId = selectedArtist.getId();
                                Database.addFollowing(myConn, userId, artistId);
                                JOptionPane.showMessageDialog(null, "You followed " + selectedArtist.getUsername() + "!");
                            } else {
                                JOptionPane.showMessageDialog(null, "You already follow this artist!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "An artist shouldn't follow other artists!");
                        }
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        this.add(followButton);
        this.add(followSplitPane);
        this.add(unfriendButton);
        this.add(befriendButton);
        this.add(befriendSplitPane);
        this.add(unfriendSplitPane);
        this.setVisible(true);

    }

    public static PremiumUser getSelectedPremiumStranger() {
        return selectedPremiumStranger;
    }

    public static void setSelectedPremiumStranger(PremiumUser selectedPremiumStranger) {
        PeoplePanel.selectedPremiumStranger = selectedPremiumStranger;
    }

    public static PremiumUser getSelectedPremiumFriend() {
        return selectedPremiumFriend;
    }

    public static void setSelectedPremiumFriend(PremiumUser selectedPremiumFriend) {
        PeoplePanel.selectedPremiumFriend = selectedPremiumFriend;
    }

    public static StandardUser getSelectedStandardFriend() {
        return selectedStandardFriend;
    }

    public static void setSelectedStandardFriend(StandardUser selectedStandardFriend) {
        PeoplePanel.selectedStandardFriend = selectedStandardFriend;
    }

    public static Artist getSelectedArtist() {
        return selectedArtist;
    }

    public static void setSelectedArtist(Artist selectedArtist) {
        PeoplePanel.selectedArtist = selectedArtist;
    }

    public static boolean isIsStandardSelectedFriend() {
        return isStandardSelectedFriend;
    }

    public static void setIsStandardSelectedFriend(boolean isStandardSelectedFriend) {
        PeoplePanel.isStandardSelectedFriend = isStandardSelectedFriend;
    }

    public static boolean isIsStandardSelectedStranger() {
        return isStandardSelectedStranger;
    }

    public static void setIsStandardSelectedStranger(boolean isStandardSelectedStranger) {
        PeoplePanel.isStandardSelectedStranger = isStandardSelectedStranger;
    }

    public static StandardUser getSelectedStandardStranger() {
        return selectedStandardStranger;
    }

    public static void setSelectedStandardStranger(StandardUser selectedStandardStranger) {
        PeoplePanel.selectedStandardStranger = selectedStandardStranger;
    }
}
