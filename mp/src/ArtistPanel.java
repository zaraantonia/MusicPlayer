import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class ArtistPanel extends JPanel {
    
    JButton deleteSongButton;
    private static Song selectedSong;

    ArtistPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<Song> allSongs, ArrayList<Album> allAlbums, ArrayList<User> allUsers, ArrayList<StandardUser> allStandard, ArrayList<PremiumUser>allPremium) {
        this.setBackground(Color.darkGray);
        this.setBounds(0, 0, 850, 400);
        this.setLayout(null);


        //delete songs area
        JLabel artistSongsLabel = new JLabel("Your songs:");
        artistSongsLabel.setLocation(20, 10);
        artistSongsLabel.setSize(150, 15);
        artistSongsLabel.setForeground(Color.white);
        artistSongsLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(artistSongsLabel);

        DeleteSongSplitPane deleteSongSplitPane = new DeleteSongSplitPane(currentStandard, currentPremium, currentArtist, currentUserType, allSongs);

        deleteSongButton = new JButton("Delete Song");
        deleteSongButton.setHorizontalTextPosition(JButton.CENTER);
        deleteSongButton.setVerticalTextPosition(JButton.CENTER);
        deleteSongButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        deleteSongButton.setBackground(new Color(0x83d4ec));
        deleteSongButton.setBounds(20, 140, 120, 30);
        deleteSongButton.setFocusable(false);
        deleteSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentUserType == UserType.ARTIST) {
                        allSongs.remove(selectedSong);
                        for(StandardUser standardLoop: allStandard){
                            if(standardLoop.getOwnedSongs().contains(selectedSong)){
                                standardLoop.getOwnedSongs().remove(selectedSong);
                            }
                        }
                        for(Album loopAlbum: allAlbums){
                            if(loopAlbum.getSongs().contains(selectedSong)){
                                loopAlbum.getSongs().remove(selectedSong);
                            }
                        }
                        BuySongSplitPane.refreshNotOwnedSongs(UserType.ARTIST,null,allSongs);
                        PlaySongSplitPane.refreshOwnedSongs(UserType.ARTIST,null,allSongs);
                        DeleteSongSplitPane.refreshYourSongs(UserType.ARTIST,currentArtist,allSongs);
                        Database.deleteSongAlbum(myConn,selectedSong.getId());
                        Database.deleteOwnedSong(myConn,selectedSong.getId());
                        Database.deleteSong(myConn,selectedSong.getId());
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"You can't delete a song!");
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });

        //delete albums area
        JLabel deleteAlbumLabel = new JLabel("Your albums");
        deleteAlbumLabel.setLocation(20, 200);
        deleteAlbumLabel.setSize(150, 15);
        deleteAlbumLabel.setForeground(Color.white);
        deleteAlbumLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(deleteAlbumLabel);

        DeleteAlbumSplitPane2 deleteAlbumSplitPane = new DeleteAlbumSplitPane2(currentStandard, currentPremium, currentArtist, currentUserType, allAlbums);

        /*deleteAlbumButton = new JButton("Add");
        deleteAlbumButton.setHorizontalTextPosition(JButton.CENTER);
        deleteAlbumButton.setVerticalTextPosition(JButton.CENTER);
        deleteAlbumButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        deleteAlbumButton.setBackground(new Color(0x83d4ec));
        deleteAlbumButton.setBounds(20, 330, 120, 30);
        deleteAlbumButton.setFocusable(false);
        deleteAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    } else {
                        JOptionPane.showMessageDialog(null, "A true artist's only friend is his art.");
                    }
                } catch (Exception exceptione) {
                    exceptione.printStackTrace();
                }
            }
        });*/

        //followers area
        JLabel followLabel = new JLabel("Your followers:");
        followLabel.setLocation(420, 10);
        followLabel.setSize(150, 15);
        followLabel.setForeground(Color.white);
        followLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        this.add(followLabel);

        FollowerSplitPane followerSplitPane = new FollowerSplitPane(myConn, currentStandard, currentPremium, currentArtist, currentUserType, allUsers);
        
        this.add(followerSplitPane);
        this.add(deleteAlbumSplitPane);
        this.add(deleteSongSplitPane);
        //this.add(deleteSongButton);
        this.setVisible(true);

    }

    public static Song getSelectedSong() {
        return selectedSong;
    }

    public static void setSelectedSong(Song selectedSong) {
        ArtistPanel.selectedSong = selectedSong;
    }
}
