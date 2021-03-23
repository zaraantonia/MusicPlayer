import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class BuyAlbumSplitPane extends JSplitPane {

    BuyAlbumSplitPane(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs, ArrayList<Album> allALbums){
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0,0,150,50);

        JList<String> buyAlbumList = new JList<>();
        DefaultListModel<String> buyAlbumModel = new DefaultListModel<>();
        for(Album loopAlbum: allALbums){
            if(currentUserType == UserType.STANDARD){
                if(!currentStandard.userOwnsAlbum(loopAlbum)){
                    buyAlbumModel.addElement(loopAlbum.getTitle());
                }
            }
        }

        buyAlbumList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = buyAlbumList.getSelectedValue();
            Album selectedAlbum = null;
            for(Album loopAlbum: allALbums){
                if(loopAlbum.getTitle().equals(selectedTitle)){
                    selectedAlbum = loopAlbum;
                    BuyPanel.setSelectedAlbum(loopAlbum);
                    break;
                }
            }
            textArea.setText("Title: " + selectedAlbum.getTitle() + "\nArtist: " + selectedAlbum.getArtist().getFirstName() + " " + selectedAlbum.getArtist().getLastName()
                    + "\n" + selectedAlbum.getGenre().getName() + "\nPrice: " + selectedAlbum.getPrice() + " $");
        });

        JPanel buyAlbumPanel = new JPanel();
        buyAlbumList.setModel(buyAlbumModel);

        this.setLeftComponent(new JScrollPane(buyAlbumList));
        buyAlbumPanel.add(textArea);
        this.setRightComponent(buyAlbumPanel);
        this.setBounds(20,220, 350,100);
        this.setVisible(true);
    }


}
