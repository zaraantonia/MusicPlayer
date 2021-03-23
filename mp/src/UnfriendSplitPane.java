import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class UnfriendSplitPane extends JSplitPane {

    private static DefaultListModel<String> unfriendModel;

    UnfriendSplitPane(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0, 0, 150, 50);

        JList<String> unfriendList = new JList<>();
        unfriendModel = new DefaultListModel<>();
        if (currentUserType == UserType.STANDARD) {
            int id1 = currentStandard.getId();
            for (User loopUser : allUsers) {
                boolean found = false;
                int id2 = loopUser.getId();
                if (id1 == id2) {
                    found = true;
                }
                if (!found) {
                    for (StandardUser loopStandard : currentStandard.getStandardfriends()) {
                        if (loopStandard.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    for (PremiumUser loopPremium : currentStandard.getPremiumfriends()) {
                        if (loopPremium.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    for (Artist loopArtist : artistsUsers) {
                        if (loopArtist.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    unfriendModel.addElement(loopUser.getUsername());
                }
            }
        } else if (currentUserType == UserType.PREMIUM) {
            int id1 = currentPremium.getId();
            for (User loopUser : allUsers) {
                boolean found = false;
                int id2 = loopUser.getId();
                if (id1 == id2) {
                    found = true;
                }
                if (!found) {
                    for (StandardUser loopStandard : currentPremium.getStandardfriends()) {
                        if (loopStandard.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    for (PremiumUser loopPremium : currentPremium.getPremiumfriends()) {
                        if (loopPremium.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    for (Artist loopArtist : artistsUsers) {
                        if (loopArtist.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    unfriendModel.addElement(loopUser.getUsername());
                }
            }
        }

        unfriendList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = unfriendList.getSelectedValue();
            boolean found = false;
            for (User loopUser : allUsers) {
                if (loopUser.getUsername().equals(selectedTitle)) {
                    for (StandardUser loopStandard : standardUsers) {
                        if (loopStandard.getUsername() == loopUser.getUsername()) {
                            PeoplePanel.setSelectedStandardStranger(loopStandard);
                            PeoplePanel.setIsStandardSelectedStranger(true);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        for (PremiumUser loopPremium : premiumUsers) {
                            if (loopPremium.getUsername() == loopUser.getUsername()) {
                                PeoplePanel.setSelectedPremiumStranger(loopPremium);
                                PeoplePanel.setIsStandardSelectedStranger(false);
                            }
                        }
                    }
                }
            }

            if (PeoplePanel.isIsStandardSelectedStranger() == true) {
                StandardUser displayUser = PeoplePanel.getSelectedStandardStranger();
                textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                        + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());
            } else {
                PremiumUser displayUser = PeoplePanel.getSelectedPremiumStranger();
                textArea.setText(displayUser.getFirstName() + " " + displayUser.getLastName() + "\n"
                        + displayUser.getCountry().toString() + "\n" + displayUser.getBirthday().toString());
            }
        });

        JPanel buySongPanel = new JPanel();
        unfriendList.setModel(unfriendModel);

        this.setLeftComponent(new JScrollPane(unfriendList));
        buySongPanel.add(textArea);
        this.setRightComponent(buySongPanel);
        this.setBounds(20, 220, 350, 100);
        this.setVisible(true);
    }


    public static void refreshStrangers(UserType currentUserType, StandardUser currentStandard, PremiumUser currentPremium, ArrayList<User> allUsers) {
        unfriendModel.setSize(0);
        if (currentUserType == UserType.STANDARD) {
            int id1 = currentStandard.getId();
            for (User loopUser : allUsers) {
                boolean found = false;
                int id2 = loopUser.getId();
                if (id1 == id2) {
                    found = true;
                }
                if (!found) {
                    for (StandardUser loopStandard : currentStandard.getStandardfriends()) {
                        if (loopStandard.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    for (PremiumUser loopPremium : currentStandard.getPremiumfriends()) {
                        if (loopPremium.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    unfriendModel.addElement(loopUser.getUsername());
                }
            }
        } else if (currentUserType == UserType.PREMIUM) {
            int id1 = currentPremium.getId();
            for (User loopUser : allUsers) {
                boolean found = false;
                int id2 = loopUser.getId();
                if (id1 == id2) {
                    found = true;
                }
                if (!found) {
                    for (StandardUser loopStandard : currentPremium.getStandardfriends()) {
                        if (loopStandard.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    for (PremiumUser loopPremium : currentPremium.getPremiumfriends()) {
                        if (loopPremium.getId() == id2) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    unfriendModel.addElement(loopUser.getUsername());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You can't unfriend the friends you can't have.");
        }
    }
}

