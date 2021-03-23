import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class BuySongSplitPane extends JSplitPane {

    private static DefaultListModel<String> buySongModel;
    
    BuySongSplitPane(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs){
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0,0,150,50);

        JList<String> buySongList = new JList<>();
        buySongModel = new DefaultListModel<>();
        for(Song loopSong: allSongs){
            if(currentUserType == UserType.STANDARD){
                if(currentStandard.userOwnsSong(loopSong) == false){
                    buySongModel.addElement(loopSong.getTitle());
                }
            }
        }

        buySongList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = buySongList.getSelectedValue();
            Song selectedSong = null;
            for(Song loopSong: allSongs){
                if(loopSong.getTitle().equals(selectedTitle)){
                    selectedSong = loopSong;
                    BuyPanel.setSelectedSong(loopSong);
                    break;
                }
            }
            textArea.setText("Title: " + selectedSong.getTitle() + "\nArtist: " + selectedSong.getArtist().getFirstName() + " " + selectedSong.getArtist().getLastName()
                                + "\nAlbum: " + selectedSong.getAlbum().getTitle() + "\nPrice: " + selectedSong.getPrice() + " $");
        });

        JPanel buySongPanel = new JPanel();
        buySongList.setModel(buySongModel);

        this.setLeftComponent(new JScrollPane(buySongList));
        buySongPanel.add(textArea);
        this.setRightComponent(buySongPanel);
        this.setBounds(20,30, 350,100);
        this.setVisible(true);
    }

    public static void refreshNotOwnedSongs(UserType currentUserType, StandardUser currentStandard, ArrayList<Song> allSongs){
        buySongModel.setSize(0);
        for(Song loopSong: allSongs){
            if(currentUserType == UserType.STANDARD){
                if(currentStandard.userOwnsSong(loopSong) == false){
                    buySongModel.addElement(loopSong.getTitle());
                }
            }
        }
    }

}
