import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class PlaySongSplitPane extends JSplitPane {

    private static DefaultListModel<String> playSongModel;

    PlaySongSplitPane(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs){
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0,0,150,50);

        JList<String> playSongList = new JList<>();
        playSongModel = new DefaultListModel<>();
        if(currentUserType == UserType.STANDARD){
            for(Album loopAlbum: currentStandard.getOwnedAlbums()){
                for(Song loopSong: loopAlbum.getSongs()){
                    playSongModel.addElement(loopSong.getTitle());
                }
            }
            for(Song loopSong: currentStandard.getOwnedSongs()){
                boolean found = false;
                for(Album loopAlbum: currentStandard.getOwnedAlbums()){
                    if(loopSong.getAlbum() == loopAlbum){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    playSongModel.addElement(loopSong.getTitle());
                }
            }
        }
        else{
            for(Song loopSong: allSongs){
                playSongModel.addElement(loopSong.getTitle());
            }
        }

        playSongList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = playSongList.getSelectedValue();
            Song selectedSong = null;
            for(Song loopSong: allSongs){
                if(loopSong.getTitle().equals(selectedTitle)){
                    selectedSong = loopSong;
                    PlayPanel.setSelectedSong(loopSong);
                    break;
                }
            }
            if(selectedSong != null) {
                textArea.setText("Title: " + selectedSong.getTitle() + "\nArtist: " + selectedSong.getArtist().getFirstName() + " " + selectedSong.getArtist().getLastName()
                        + "\nAlbum: " + selectedSong.getAlbum().getTitle());
            }
        });

        JPanel playSongPanel = new JPanel();
        playSongList.setModel(playSongModel);

        this.setLeftComponent(new JScrollPane(playSongList));
        playSongPanel.add(textArea);
        this.setRightComponent(playSongPanel);
        this.setBounds(20,30, 350,100);
        this.setVisible(true);
    }

    public static void refreshOwnedSongs(UserType currentUserType, StandardUser currentStandard, ArrayList<Song> allSongs){
        playSongModel.setSize(0);
        if(currentUserType == UserType.STANDARD){
            for(Album loopAlbum: currentStandard.getOwnedAlbums()){
                for(Song loopSong: loopAlbum.getSongs()){
                    playSongModel.addElement(loopSong.getTitle());
                }
            }
            for(Song loopSong: currentStandard.getOwnedSongs()){
                boolean found = false;
                for(Album loopAlbum: currentStandard.getOwnedAlbums()){
                    if(loopSong.getAlbum() == loopAlbum){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    playSongModel.addElement(loopSong.getTitle());
                }
            }
        }
        else{
            for(Song loopSong: allSongs){
                playSongModel.addElement(loopSong.getTitle());
            }
        }
    }
}
