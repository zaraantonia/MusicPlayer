import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class PlayAlbumSplitPane extends JSplitPane {

    private static DefaultListModel<String> playAlbumModel;

    PlayAlbumSplitPane(StandardUser currentStandard, UserType currentUserType,ArrayList<Album> allAlbums){
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0,0,150,50);

        JList<String> playAlbumList = new JList<>();
        playAlbumModel = new DefaultListModel<>();
        for(Album loopAlbum: allAlbums){
            if(currentUserType == UserType.STANDARD){
                if(currentStandard.userOwnsAlbum(loopAlbum)){
                    playAlbumModel.addElement(loopAlbum.getTitle());
                }
            }
            else{
                playAlbumModel.addElement(loopAlbum.getTitle());
            }
        }

        playAlbumList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = playAlbumList.getSelectedValue();
            Album selectedAlbum = null;
            for(Album loopAlbum: allAlbums){
                if(loopAlbum.getTitle().equals(selectedTitle)){
                    selectedAlbum = loopAlbum;
                    PlayPanel.setSelectedAlbum(loopAlbum);
                    break;
                }
            }
            textArea.setText("Title: " + selectedAlbum.getTitle() + "\nArtist: " + selectedAlbum.getArtist().getFirstName() + " " + selectedAlbum.getArtist().getLastName()
                    + "\n" + selectedAlbum.getGenre().getName() );
        });

        JPanel playAlbumPanel = new JPanel();
        playAlbumList.setModel(playAlbumModel);

        this.setLeftComponent(new JScrollPane(playAlbumList));
        playAlbumPanel.add(textArea);
        this.setRightComponent(playAlbumPanel);
        this.setBounds(20,220, 350,100);
        this.setVisible(true);
    }


    public static void refreshOwnedAlbums(UserType currentUserType, StandardUser currentStandard, ArrayList<Album> allAlbums) {
        playAlbumModel.setSize(0);
        for(Album loopAlbum: allAlbums){
            if(currentUserType == UserType.STANDARD){
                if(currentStandard.userOwnsAlbum(loopAlbum)){
                    playAlbumModel.addElement(loopAlbum.getTitle());
                }
            }
            else{
                playAlbumModel.addElement(loopAlbum.getTitle());
            }
        }
    }
}
