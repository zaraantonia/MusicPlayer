import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class DeleteSongSplitPane extends JSplitPane {

    private static DefaultListModel<String> deleteSongModel;

    DeleteSongSplitPane(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs) {
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0, 0, 150, 50);

        JList<String> deleteSongList = new JList<>();
        deleteSongModel = new DefaultListModel<>();
        if (currentUserType == UserType.ARTIST) {
            for (Song loopSong : allSongs) {
                if (loopSong.getArtist() == currentArtist) {
                    deleteSongModel.addElement(loopSong.getTitle());
                }
            }
        }

        deleteSongList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = deleteSongList.getSelectedValue();
            Song selectedSong = null;
            for (Song loopSong : allSongs) {
                if (loopSong.getTitle().equals(selectedTitle)) {
                    selectedSong = loopSong;
                    ArtistPanel.setSelectedSong(selectedSong);
                    break;
                }
            }
            if (selectedSong != null) {
                textArea.setText("Title: " + selectedSong.getTitle()
                        + "\nAlbum: " + selectedSong.getAlbum().getTitle() + "\nPrice: " + selectedSong.getPrice() + " $");
            }
        });

        JPanel deleteSongPanel = new JPanel();
        deleteSongList.setModel(deleteSongModel);

        this.setLeftComponent(new JScrollPane(deleteSongList));
        deleteSongPanel.add(textArea);
        this.setRightComponent(deleteSongPanel);
        this.setBounds(20, 30, 350, 100);
        this.setVisible(true);
    }

    public static void refreshYourSongs(UserType currentUserType, Artist currentArtist, ArrayList<Song> allSongs) {
        deleteSongModel.setSize(0);
        if (currentUserType == UserType.ARTIST) {
            for (Song loopSong : allSongs) {
                if (loopSong.getArtist() == currentArtist) {
                    deleteSongModel.addElement(loopSong.getTitle());
                }
            }
        }
    }

}
