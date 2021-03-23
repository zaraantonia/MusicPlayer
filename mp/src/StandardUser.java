import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class StandardUser extends User {

    private ArrayList<Playlist> playlistsArray = new ArrayList<Playlist>();
    private ArrayList<StandardUser> Standardfriends = new ArrayList<StandardUser>();
    private ArrayList<PremiumUser> Premiumfriends = new ArrayList<PremiumUser>();
    private ArrayList<Song> ownedSongs = new ArrayList<Song>();
    private ArrayList<Album> ownedAlbums = new ArrayList<Album>();
    private UserType userType = UserType.STANDARD;
    private ArrayList<Artist> following = new ArrayList<Artist>();

    private int songsAlreadyPlayed = 0;

    public StandardUser(){};

    public ArrayList<Playlist> getPlaylistsArray() {
        return playlistsArray;
    }

    public void setPlaylistsArray(ArrayList<Playlist> playlistsArray) {
        this.playlistsArray = playlistsArray;
    }

    public ArrayList<Song> getOwnedSongs() {
        return ownedSongs;
    }

    public void setOwnedSongs(ArrayList<Song> ownedSongs) {
        this.ownedSongs = ownedSongs;
    }

    public int getSongsAlreadyPlayed() {
        return songsAlreadyPlayed;
    }

    public void setSongsAlreadyPlayed(int songsAlreadyPlayed) {
        this.songsAlreadyPlayed = songsAlreadyPlayed;
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

    public ArrayList<Album> getOwnedAlbums() {
        return ownedAlbums;
    }

    public void setOwnedAlbums(ArrayList<Album> ownedAlbums) {
        this.ownedAlbums = ownedAlbums;
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

    @Override
    public void upgradeToPremium(Connection connection, ArrayList<PremiumUser> premiumUsers, ArrayList<StandardUser> standardUsers) throws SQLException {
        PremiumUser upgradedUser = new PremiumUser(this.username,this.email,this.password,this.firstName,this.lastName,this.country,this.id,this.status,this.birthday);
        int id = upgradedUser.getId();
        Database.upgradeFromStandardToPremium(connection,id);
        premiumUsers.add(upgradedUser);
        standardUsers.remove(this);
    }

    public boolean userOwnsSong(Song wantedSong) {
        boolean result = false;
        for(Song loopSong: this.getOwnedSongs()){
            if(loopSong.getId() == wantedSong.getId()){
                result = true;
                break;
            }
        }
        return result;
    }

    public void buySong(Song selectedSong) {
        this.getOwnedSongs().add(selectedSong);
    }

    public boolean userOwnsAlbum(Album wantedAlbum) {
        boolean result = false;
        for(Album loopAlbum: this.getOwnedAlbums()){
            if(loopAlbum.getId() == wantedAlbum.getId()){
                result = true;
                break;
            }
        }
        return result;
    }

    public void buyAlbum(Album selectedAlbum) {
        this.getOwnedAlbums().add(selectedAlbum);
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
