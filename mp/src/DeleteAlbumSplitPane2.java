import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class DeleteAlbumSplitPane2 extends JSplitPane {

    DeleteAlbumSplitPane2(StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Album> allALbums){
        JTextArea textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        textArea.setBounds(0,0,150,50);

        JList<String> deleteAlbumList = new JList<>();
        DefaultListModel<String> deleteAlbumModel = new DefaultListModel<>();
        if(currentUserType == UserType.ARTIST){
            for(Album loopAlbum: currentArtist.getMyAlbums()){
                deleteAlbumModel.addElement(loopAlbum.getTitle());
            }
        }

        deleteAlbumList.getSelectionModel().addListSelectionListener(e -> {
            String selectedTitle = deleteAlbumList.getSelectedValue();
            Album selectedAlbum = null;
            for(Album loopAlbum: allALbums){
                if(loopAlbum.getTitle().equals(selectedTitle)){
                    selectedAlbum = loopAlbum;
                    break;
                }
            }
            textArea.setText("Title: " + selectedAlbum.getTitle()
                    + "\n" + selectedAlbum.getGenre().getName() + "\nPrice: " + selectedAlbum.getPrice() + " $");
        });

        JPanel deleteAlbumPanel = new JPanel();
        deleteAlbumList.setModel(deleteAlbumModel);

        this.setLeftComponent(new JScrollPane(deleteAlbumList));
        deleteAlbumPanel.add(textArea);
        this.setRightComponent(deleteAlbumPanel);
        this.setBounds(20,220, 350,100);
        this.setVisible(true);
    }


}
