import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Controller {

    private static ArrayList<StandardUser> standardUsers = new ArrayList<StandardUser>();
    private static ArrayList<PremiumUser> premiumUsers = new ArrayList<PremiumUser>();
    private static ArrayList<Artist> artistsUsers = new ArrayList<Artist>();
    private static ArrayList<User> allUsers = new ArrayList<User>();
    private static ArrayList<Album> allAlbums = new ArrayList<Album>();
    private static ArrayList<Playlist> allPlaylist = new ArrayList<Playlist>();
    private static ArrayList<Song> allSongs = new ArrayList<Song>();
    private static ArrayList<Song> songQueue = new ArrayList<Song>();
    private static ArrayList<Country> allCountries = new ArrayList<Country>();
    private static ArrayList<Genre> allGenres = new ArrayList<Genre>();
    private static Song currentSongPlaying;
    private static PlayerStatus playerStatus;
    private static PlayerMode playerMode;
    private static int nrOfPlayedSongs;
    private static StandardUser currentStandard = new StandardUser();
    private static PremiumUser currentPremium = new PremiumUser();
    private static Artist currentArtist = new Artist();
    private static UserType currentUserType;
    private static String selectedUsername;
    private static LoginWindow loginWindow;
    private static int selectedId;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Controller().createAndShowGui();
            }
        });
    }

    public void createAndShowGui() {

        try {

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?verifyServerCertificate=false&useSSL=true", "root", "campului4");
            Database.getCountriesFromDatabase(myConn, allCountries);
            Database.getGenresFromDatabase(myConn,allGenres);
            Database.getStandardUserDataFromDataBase(myConn, standardUsers, allUsers, allCountries);
            Database.getPremiumUserDataFromDataBase(myConn, premiumUsers, allUsers,allCountries);
            Database.getArtistsDataFromDataBase(myConn, artistsUsers, allUsers,allCountries);
            Database.getFriendshipsFromDatabase(myConn, premiumUsers, standardUsers,artistsUsers);
            Database.getAlbumDataFromDatabase(myConn, allAlbums, allGenres);
            Database.getSongDataFromDatabase(myConn, allSongs, artistsUsers);
            Database.getAlbumSongsDataFromDatabase(myConn, allAlbums, allSongs);
            Database.getPlaylistDataFromDatabase(myConn, allPlaylist);
            Database.getArtistAlbumsDataFromDatabase(myConn, allAlbums, artistsUsers);
            Database.getPlaylistSongsDataFromDatabase(myConn, allPlaylist, allSongs);
            Database.getUserPlaylistsDataFromDatabase(myConn, allPlaylist, allUsers, standardUsers, premiumUsers);
            Database.getSongsOwnedByUserDataFromDatabase(myConn, allSongs, allUsers, standardUsers);
            Database.getFollowingsDataFromDatabase(myConn, standardUsers, premiumUsers, artistsUsers, allUsers);
            Database.getAlbumsOwnedByUserDataFromDatabase(myConn, standardUsers, allAlbums);

            loginWindow = new LoginWindow(allUsers,myConn);

        } catch (Exception databaseException) {
            databaseException.printStackTrace();
        }
    }

    public static void startMainApp(Connection myConn) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        loginWindow.dispose();

        selectedId = 0;
        findUserIdForGlobalUse();
        setUserForGlobalUse(selectedId);

        playerStatus = PlayerStatus.STOPPED;
        playerMode = PlayerMode.ORDERED;
        nrOfPlayedSongs = 0;

        MyFrame myFrame = new MyFrame();

        MusicPanel myMusicPanel = new MusicPanel();

        GeneralAccessPanel generalAccessPanel = new GeneralAccessPanel(myConn, currentStandard, currentPremium, currentArtist, currentUserType, premiumUsers, standardUsers, artistsUsers);

        BigPanel centerPanel = new BigPanel(myConn, currentStandard, currentPremium, currentArtist, currentUserType, standardUsers, premiumUsers, artistsUsers, allUsers, allSongs, allAlbums, allCountries);

        TopPanel topPanel = new TopPanel(currentStandard,currentPremium,currentArtist,currentUserType);

        myFrame.add(topPanel);
        myFrame.add(myMusicPanel);
        myFrame.add(generalAccessPanel);
        myFrame.add(centerPanel);

        myFrame.setVisible(true);
    }

    public static void findUserIdForGlobalUse(){
        for (User loopUser1 : allUsers) {
            if (loopUser1.getUsername() == selectedUsername) {
                selectedId = loopUser1.getId();
            }
        }
    }

    public static UserType findUserType(int id) {
        boolean found = false;
        UserType type = UserType.STANDARD;
        for (StandardUser loopStandard : standardUsers) {
            if (loopStandard.getId() == id) {
                found = true;
            }
        }
        if (found == false) {
            for (PremiumUser loopPremium : premiumUsers) {
                if (loopPremium.getId() == id) {
                    found = true;
                    type = UserType.PREMIUM;
                }
            }
        }
        if (found == false) {
            for (Artist loopArtist : artistsUsers) {
                if (loopArtist.getId() == id) {
                    found = true;
                    type = UserType.ARTIST;
                }
            }
        }
        return type;
    }

    public static void setUserForGlobalUse(int selectedId){
        for (User loopUser : allUsers) {
            if (loopUser.getId() == selectedId) {
                currentUserType = findUserType(selectedId);
            }
        }

        if (currentUserType == UserType.STANDARD) {
            for (StandardUser loopStandard : standardUsers) {
                if (loopStandard.getId() == selectedId) {
                    currentStandard = loopStandard;
                }
            }
        } else if (currentUserType == UserType.PREMIUM) {
            for (PremiumUser loopPremium : premiumUsers) {
                if (loopPremium.getId() == selectedId) {
                    currentPremium = loopPremium;
                }
            }
        } else if (currentUserType == UserType.ARTIST) {
            for (Artist loopArtist : artistsUsers) {
                if (loopArtist.getId() == selectedId) {
                    currentArtist = loopArtist;
                }
            }
        }
    }

    public static ArrayList<Song> getSongQueue() {
        return songQueue;
    }

    public static void setSongQueue(ArrayList<Song> songQueue) {
        Controller.songQueue = songQueue;
    }

    public static Song getCurrentSongPlaying() {
        return currentSongPlaying;
    }

    public static void setCurrentSongPlaying(Song currentSongPlaying) {
        Controller.currentSongPlaying = currentSongPlaying;
    }

    public static PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public static void setPlayerStatus(PlayerStatus playerStatus) {
        Controller.playerStatus = playerStatus;
    }

    public static PlayerMode getPlayerMode() {
        return playerMode;
    }

    public static void setPlayerMode(PlayerMode playerMode) {
        Controller.playerMode = playerMode;
    }

    public int getNrOfOrderedPlayedSongs() {
        return nrOfPlayedSongs;
    }

    public void setNrOfOrderedPlayedSongs(int nrOfOrderedPlayedSongs) {
        this.nrOfPlayedSongs = nrOfOrderedPlayedSongs;
    }

    public static String getSelectedUsername() {
        return selectedUsername;
    }

    public static void setSelectedUsername(String selectedUsername) {
        Controller.selectedUsername = selectedUsername;
    }

    public static boolean usernameAlreadyExists(String text) {
        boolean found = false;
        for(User loopUser: allUsers){
            if(loopUser.getUsername().equals(text)){
                found = true;
                break;
            }
        }
        return found;
    }
}
