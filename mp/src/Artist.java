import java.sql.Date;
import java.util.ArrayList;

public class Artist extends User{
    //add methods for cool people
    private ArrayList<Playlist> playlistsArray = new ArrayList<Playlist>();
    private ArrayList<Album> myAlbums = new ArrayList<Album>();
    private ArrayList<User> followers = new ArrayList<User>();
    private UserType userType = UserType.ARTIST;

    public Artist(){};

    public Artist(String username, String email, String password, String firstName, String lastName, Country country, int id, String status, Date birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.id = id;
        this.status = status;
        this.birthday = birthday;
    }

    public ArrayList<Playlist> getPlaylistsArray() {
        return playlistsArray;
    }

    public void setPlaylistsArray(ArrayList<Playlist> playlistsArray) {
        this.playlistsArray = playlistsArray;
    }

    public ArrayList<Album> getMyAlbums() {
        return myAlbums;
    }

    public void setMyAlbums(ArrayList<Album> myAlbums) {
        this.myAlbums = myAlbums;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }
}
