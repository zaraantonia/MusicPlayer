import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class PremiumUser extends User {

    //add methods for rich people
    private ArrayList<Playlist> playlistsArray = new ArrayList<Playlist>();
    private ArrayList<StandardUser> Standardfriends = new ArrayList<StandardUser>();
    private ArrayList<PremiumUser> Premiumfriends = new ArrayList<PremiumUser>();
    private UserType userType = UserType.PREMIUM;
    private ArrayList<Artist> following = new ArrayList<Artist>();

    public PremiumUser(){};

    public PremiumUser(String username, String email, String password, String firstName, String lastName, Country country, int id, String status, Date birthday){
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

    public ArrayList<StandardUser> getStandardfriends() {
        return Standardfriends;
    }

    public void setStandardfriends(ArrayList<StandardUser> standardfriends) {
        Standardfriends = standardfriends;
    }

    public ArrayList<PremiumUser> getPremiumfriends() {
        return Premiumfriends;
    }

    public void setPremiumfriends(ArrayList<PremiumUser> premiumfriends) {
        Premiumfriends = premiumfriends;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ArrayList<Artist> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Artist> following) {
        this.following = following;
    }

    public void upgradeToArtist(Connection connection, ArrayList<Artist> artistsUsers, ArrayList<PremiumUser> premiumUsers) throws SQLException {
        Artist upgradedUser = new Artist(this.username,this.email,this.password,this.firstName,this.lastName,this.country,this.id,this.status,this.birthday);
        int id = upgradedUser.getId();
        Database.upgradeFromPremiumToArtist(connection,id);
        Database.deleteFollowing(connection,id);
        artistsUsers.add(upgradedUser);
        premiumUsers.remove(this);
    }

    public void deleteStandardFriend(StandardUser selectedStandardFriend) {
        this.getStandardfriends().remove(selectedStandardFriend);
    }

    public void deletePremiumFriend(PremiumUser selectedPremiumFriend) {
        this.getPremiumfriends().remove(selectedPremiumFriend);
    }

    public void addStandardFriend(StandardUser selectedStandardStranger) {
        this.getStandardfriends().add(selectedStandardStranger);
    }

    public void addPremiumFriend(PremiumUser selectedPremiumStranger){
        this.getPremiumfriends().add(selectedPremiumStranger);
    }

    public void follow(Artist selectedArtist) {
        this.getFollowing().add(selectedArtist);
    }
}