import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class BigPanel extends JPanel {

    BigPanel(Connection myConn, StandardUser currentStandard, PremiumUser currentPremium, Artist currentArtist, UserType currentUserType, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist>artistsUsers, ArrayList<User> allUsers, ArrayList<Song>allSongs,ArrayList<Album>allAlbums, ArrayList<Country> allCountries) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        this.setBackground(Color.darkGray);
        this.setBounds(150, 27, 850, 573);
        this.setLayout(null);
        this.setVisible(true);

        JTabbedPane tp = new JTabbedPane();

        BuyPanel buyPanel = new BuyPanel(myConn,currentStandard,currentPremium,currentArtist,currentUserType,premiumUsers,standardUsers,allUsers,allSongs,allAlbums);
        PeoplePanel peoplePanel = new PeoplePanel(myConn,currentStandard,currentPremium,currentArtist,currentUserType,standardUsers,premiumUsers,artistsUsers,allUsers);
        PlayPanel playPanel = new PlayPanel(myConn,currentStandard,currentPremium,currentArtist,currentUserType,allSongs,allAlbums);
        ArtistPanel artistPanel = new ArtistPanel(myConn,currentStandard,currentPremium,currentArtist,currentUserType,allSongs,allAlbums,allUsers,standardUsers,premiumUsers);
        UserPanel userPanel = new UserPanel(myConn,currentStandard,currentPremium,currentArtist,currentUserType, allCountries, allUsers);

        tp.add("Play something",playPanel);
        tp.add("Buy something",buyPanel);
        tp.add("People",peoplePanel);
        tp.add("Artist Lounge", artistPanel);
        tp.add("Your details", userPanel);
        tp.setBounds(0, 0, 850, 420);
        tp.setBackground(Color.darkGray);
        tp.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
        tp.setForeground(Color.white);
        tp.setBorder(null);
        tp.setVisible(true);
        this.add(tp);

    }


}
