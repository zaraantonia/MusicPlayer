import java.sql.*;
import java.util.ArrayList;

public class Database {

    public static void getCountriesFromDatabase(Connection connection, ArrayList<Country> allCountries) throws SQLException {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from countries order by name asc");
        while(myResult.next()){
            Country temporaryCountry = new Country();
            temporaryCountry.setName(myResult.getString("name"));
            temporaryCountry.setId(myResult.getInt("country_id"));
            allCountries.add(temporaryCountry);
        }
    }

    public static void getGenresFromDatabase(Connection connection, ArrayList<Genre> allGenres) throws SQLException {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from genres order by name asc");
        while(myResult.next()){
            Genre temporaryGenre = new Genre();
            temporaryGenre.setName(myResult.getString("name"));
            temporaryGenre.setId(myResult.getInt("genre_id"));
            allGenres.add(temporaryGenre);
        }
    }

    public static void getStandardUserDataFromDataBase(Connection connection, ArrayList<StandardUser> standardUsers, ArrayList<User> allUsers, ArrayList<Country> allCountries) {
        try {
            Statement myStatement = connection.createStatement();
            ResultSet myResult = myStatement.executeQuery("select * from user u join standard_user su on u.id = su.id");
            int index = 0;
            while (myResult.next()) {
                StandardUser temporaryUser = new StandardUser();
                temporaryUser.setId(myResult.getInt("id"));
                temporaryUser.setBirthday(myResult.getDate("birthday"));
                temporaryUser.setUsername(myResult.getString("username"));
                temporaryUser.setEmail(myResult.getString("email"));
                temporaryUser.setPassword(myResult.getString("password"));
                temporaryUser.setFirstName(myResult.getString("first_name"));
                temporaryUser.setLastName(myResult.getString("last_name"));
                temporaryUser.setStatus(myResult.getString("status"));
                int countryId = myResult.getInt("country_id");
                Country userCountry = new Country();
                for(Country loopCountry: allCountries){
                    if(countryId == loopCountry.getId()){
                        temporaryUser.setCountry(loopCountry);
                        break;
                    }
                }
                standardUsers.add(temporaryUser);
                allUsers.add(temporaryUser);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPremiumUserDataFromDataBase(Connection connection, ArrayList<PremiumUser> premiumUsers, ArrayList<User> allUsers, ArrayList<Country> allCountries) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from user u join premium_user pu on u.id = pu.id");
        int index = 0;
        while (myResult.next()) {
            PremiumUser temporaryUser = new PremiumUser();
            temporaryUser.setId(myResult.getInt("id"));
            temporaryUser.setBirthday(myResult.getDate("birthday"));
            temporaryUser.setUsername(myResult.getString("username"));
            temporaryUser.setEmail(myResult.getString("email"));
            temporaryUser.setPassword(myResult.getString("password"));
            temporaryUser.setFirstName(myResult.getString("first_name"));
            temporaryUser.setLastName(myResult.getString("last_name"));
            temporaryUser.setStatus(myResult.getString("status"));
            int countryId = myResult.getInt("country_id");
            for(Country loopCountry: allCountries){
                if(countryId == loopCountry.getId()){
                    temporaryUser.setCountry(loopCountry);
                    break;
                }
            }
            premiumUsers.add(temporaryUser);
            allUsers.add(temporaryUser);
            index++;
        }
    }

    public static void getArtistsDataFromDataBase(Connection connection, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers, ArrayList<Country> allCountries) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from user u join artist a on u.id = a.id");
        int index = 0;
        while (myResult.next()) {
            Artist temporaryUser = new Artist();
            User temp = new User();
            temporaryUser.setId(myResult.getInt("id"));
            temporaryUser.setBirthday(myResult.getDate("birthday"));
            temporaryUser.setUsername(myResult.getString("username"));
            temporaryUser.setEmail(myResult.getString("email"));
            temporaryUser.setPassword(myResult.getString("password"));
            temporaryUser.setFirstName(myResult.getString("first_name"));
            temporaryUser.setLastName(myResult.getString("last_name"));
            temporaryUser.setStatus(myResult.getString("status"));
            int countryId = myResult.getInt("country_id");
            for(Country loopCountry: allCountries){
                if(countryId == loopCountry.getId()){
                    temporaryUser.setCountry(loopCountry);
                    break;
                }
            }
            artistsUsers.add(temporaryUser);
            allUsers.add(temporaryUser);
            index++;
        }
    }

    public static void getFriendshipsFromDatabase(Connection connection,ArrayList<PremiumUser> premiumUsers,ArrayList<StandardUser> standardUsers, ArrayList<Artist> artistsUsers) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select user1_id,user2_id from friendships");
        while (myResult.next()) {
            boolean foundAnArtist = false;
            int firstUserId = myResult.getInt("user1_id");
            int secondUserId = myResult.getInt("user2_id");
            int isFirstUserPremium = 1;
            int isSecondUserPremium = 1;
            int standardFirstUserIndex = -1;
            int standardSecondUserIndex = -1;
            int premiumFirstUserIndex = -1;
            int premiumSecondUserIndex = -1;
            for (int i = 0; i < standardUsers.size(); i++) {
                if (standardUsers.get(i).getId() == firstUserId) {
                    isFirstUserPremium = 0;
                    standardFirstUserIndex = i;
                }
                if (standardUsers.get(i).getId() == secondUserId) {
                    isSecondUserPremium = 0;
                    standardSecondUserIndex = i;
                }
            }

            for (int i = 0; i < premiumUsers.size(); i++) {
                if (premiumUsers.get(i).getId() == firstUserId) {
                    isFirstUserPremium = 1;
                    premiumFirstUserIndex = i;
                }
                if (premiumUsers.get(i).getId() == secondUserId) {
                    isSecondUserPremium = 1;
                    premiumSecondUserIndex = i;
                }
            }

            for (int i = 0; i < artistsUsers.size(); i++) {
                if (artistsUsers.get(i).getId() == firstUserId) {
                    foundAnArtist = true;
                    break;
                }
                if (artistsUsers.get(i).getId() == secondUserId) {
                    foundAnArtist = true;
                    break;
                }
            }

            if(!foundAnArtist) {
                if (isFirstUserPremium == 1 && isSecondUserPremium == 1) {
                    PremiumUser firstUser = premiumUsers.get(premiumFirstUserIndex);
                    PremiumUser secondUser = premiumUsers.get(premiumSecondUserIndex);
                    firstUser.getPremiumfriends().add(secondUser);
                } else if (isFirstUserPremium == 1 && isSecondUserPremium == 0) {
                    PremiumUser firstUser = premiumUsers.get(premiumFirstUserIndex);
                    StandardUser secondUser = standardUsers.get(standardSecondUserIndex);
                    firstUser.getStandardfriends().add(secondUser);
                } else if (isFirstUserPremium == 0 && isSecondUserPremium == 1) {
                    StandardUser firstUser = standardUsers.get(standardFirstUserIndex);
                    PremiumUser secondUser = premiumUsers.get(premiumSecondUserIndex);
                    firstUser.getPremiumfriends().add(secondUser);
                } else if (isFirstUserPremium == 0 && isSecondUserPremium == 0) {
                    StandardUser firstUser = standardUsers.get(standardFirstUserIndex);
                    StandardUser secondUser = standardUsers.get(standardSecondUserIndex);
                    firstUser.getStandardfriends().add(secondUser);
                }
            }
        }
    }

    public static void getAlbumDataFromDatabase(Connection connection, ArrayList<Album> allAlbums, ArrayList<Genre> allGenres) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet myAlbumsResult = myStatement.executeQuery("select * from album");
        while(myAlbumsResult.next()){
            Album temporaryAlbum = new Album();
            temporaryAlbum.setId(myAlbumsResult.getInt("id")) ;
            temporaryAlbum.setPrice(myAlbumsResult.getInt("price"));
            temporaryAlbum.setTitle(myAlbumsResult.getString("title"));
            temporaryAlbum.setYear(myAlbumsResult.getInt("year"));
            for(Genre loopGenre: allGenres){
                if(loopGenre.getId() == myAlbumsResult.getInt("genre_id")){
                    temporaryAlbum.setGenre(loopGenre);
                }
            }
            allAlbums.add(temporaryAlbum);
        }
    }

    public static void getSongDataFromDatabase(Connection connection, ArrayList<Song> allSongs, ArrayList<Artist> artistsUsers) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet mySongResult = myStatement.executeQuery("select * from song");
        while(mySongResult.next()){
            Song temporarySong = new Song();
            temporarySong.setId(mySongResult.getInt("id"));
            temporarySong.setTitle(mySongResult.getString("title"));
            temporarySong.setLength(mySongResult.getInt("length"));
            temporarySong.setPrice(mySongResult.getInt("price"));
            temporarySong.setMusicpath(mySongResult.getString("filepath"));
            int artistId = mySongResult.getInt("artist_id");
            for(Artist loopArtist: artistsUsers){
                if(loopArtist.getId() == artistId){
                    temporarySong.setArtist(loopArtist);
                    break;
                }
            }
            allSongs.add(temporarySong);
        }
    }

    public static void getPlaylistDataFromDatabase(Connection connection,ArrayList<Playlist> allPlaylist) throws Exception{
        Statement myStatement = connection.createStatement();
        ResultSet myPlaylistResult = myStatement.executeQuery("select * from playlist");
        while(myPlaylistResult.next()){
            Playlist temporaryPlaylist = new Playlist();
            temporaryPlaylist.setId(myPlaylistResult.getInt("id"));
            temporaryPlaylist.setTitle(myPlaylistResult.getString("title"));
            allPlaylist.add(temporaryPlaylist);
        }
    }

    public static void getAlbumSongsDataFromDatabase(Connection connection, ArrayList<Album> allAlbums, ArrayList<Song> allSongs) throws Exception {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from album_songs");
        while (myResult.next()) {
            int albumId = myResult.getInt("album_id");
            int songId = myResult.getInt("song_id");
            Album temporaryAlbum = new Album();
            Song temporarySong = new Song();
            for (Album loopAlbum : allAlbums) {
                if (loopAlbum.getId() == albumId) {
                    temporaryAlbum = loopAlbum;
                    break;
                }
            }
            for (Song loopSong : allSongs) {
                if (loopSong.getId() == songId) {
                    temporarySong = loopSong;
                    break;
                }
            }
            temporaryAlbum.getSongs().add(temporarySong);
            temporarySong.setAlbum(temporaryAlbum);
        }
    }

    public static void getArtistAlbumsDataFromDatabase(Connection connection,ArrayList<Album> allAlbums, ArrayList<Artist> artistsUsers) throws Exception{
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from artist_albums");
        while(myResult.next()){
            int albumId = myResult.getInt("album_id");
            int artistId = myResult.getInt("artist_id");
            Album temporaryAlbum = new Album();
            Artist temporaryArtist = new Artist();
            for (Album loopAlbum : allAlbums) {
                if (loopAlbum.getId() == albumId) {
                    temporaryAlbum = loopAlbum;
                    break;
                }
            }
            for (Artist loopArtist: artistsUsers) {
                if(loopArtist.getId() == artistId){
                    temporaryArtist = loopArtist;
                    break;
                }
            }
            temporaryAlbum.setArtist(temporaryArtist);
            temporaryArtist.getMyAlbums().add(temporaryAlbum);
        }
    }

    public static void getPlaylistSongsDataFromDatabase(Connection connection,ArrayList<Playlist> allPlaylist, ArrayList<Song> allSongs) throws Exception{
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from playlist_songs");
        while(myResult.next()){
            int playlistId = myResult.getInt("playlist_id");
            int songId = myResult.getInt("song_id");
            Playlist temporaryPlaylist = new Playlist();
            Song temporarySong = new Song();
            for(Playlist loopPlaylist: allPlaylist){
                if(loopPlaylist.getId() == playlistId){
                    temporaryPlaylist = loopPlaylist;
                    break;
                }
            }
            for(Song loopSong: allSongs){
                if(loopSong.getId() == songId){
                    temporarySong = loopSong;
                    break;
                }
            }
            temporaryPlaylist.getSongs().add(temporarySong);
        }
    }

    public static void getUserPlaylistsDataFromDatabase(Connection connection, ArrayList<Playlist> allPlaylist, ArrayList<User> allUsers, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers) throws Exception{
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from user_playlists");
        while(myResult.next()){
            int playlistId = myResult.getInt("playlist_id");
            int userId = myResult.getInt("user_id");
            Playlist temporaryPlaylist = new Playlist();
            User temporaryUser = new User();
            for(Playlist loopPlaylist: allPlaylist){
                if(loopPlaylist.getId() == playlistId){
                    temporaryPlaylist = loopPlaylist;
                    break;
                }
            }
            for(User loopUser: allUsers){
                if(loopUser.getId() == userId){
                    temporaryUser = loopUser;
                    break;
                }
            }
            temporaryPlaylist.setCreator(temporaryUser);

            boolean found = false;
            for(StandardUser loopStandardUser: standardUsers){
                if(loopStandardUser.getId() == userId){
                    found = true;
                    loopStandardUser.getPlaylistsArray().add(temporaryPlaylist);
                    break;
                }
            }
            if(found == false){
                for(PremiumUser loopPremiumUser: premiumUsers){
                    if(loopPremiumUser.getId() == userId){
                        found = true;
                        loopPremiumUser.getPlaylistsArray().add(temporaryPlaylist);
                        break;
                    }
                }
            }
        }
    }

    public static void getSongsOwnedByUserDataFromDatabase(Connection connection,ArrayList<Song> allSongs, ArrayList<User> allUsers, ArrayList<StandardUser> standardUsers) throws Exception{
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from songs_owned_by_user");
        while(myResult.next()){
            int songId = myResult.getInt("song_id");
            int userId = myResult.getInt("user_id");
            Song temporarySong = new Song();
            User temporaryUser = new User();
            for(Song loopSong: allSongs){
                if(loopSong.getId() == songId){
                    temporarySong = loopSong;
                    break;
                }
            }
            for(User loopUser: allUsers){
                if(loopUser.getId() == userId){
                    temporaryUser = loopUser;
                    break;
                }
            }

            boolean found = false;
            for(StandardUser loopStandardUser: standardUsers){
                if(loopStandardUser.getId() == userId){
                    found = true;
                    loopStandardUser.getOwnedSongs().add(temporarySong);
                    break;
                }
            }
        }
    }

    public static void getFollowingsDataFromDatabase(Connection connection, ArrayList<StandardUser> standardUsers, ArrayList<PremiumUser> premiumUsers, ArrayList<Artist> artistsUsers, ArrayList<User> allUsers) throws SQLException {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from followings");
        while(myResult.next()){
            int userId = myResult.getInt("user_id");
            int artistId = myResult.getInt("artist_id");
            Artist artistToFollow = null;
            StandardUser followerStandard = null;
            PremiumUser followerPremium = null;
            boolean isFollowerStandard = true;
            for(Artist loopArtist: artistsUsers){
                if(loopArtist.getId() == artistId){
                    artistToFollow = loopArtist;
                }
            }

            boolean found = false;
            for(StandardUser loopStandard: standardUsers){
                if(loopStandard.getId() == userId){
                    found = true;
                    followerStandard = loopStandard;
                    break;
                }
            }

            if(!found){
                for(PremiumUser loopPremium: premiumUsers){
                    if(loopPremium.getId() == userId){
                        found = true;
                        isFollowerStandard = false;
                        followerPremium = loopPremium;
                    }
                }
            }

            if(isFollowerStandard){
                followerStandard.getFollowing().add(artistToFollow);
                artistToFollow.getFollowers().add(followerStandard);
            }
            else{
                followerPremium.getFollowing().add(artistToFollow);
                artistToFollow.getFollowers().add(followerPremium);
            }
        }
    }

    public static void getAlbumsOwnedByUserDataFromDatabase(Connection connection, ArrayList<StandardUser> standardUsers, ArrayList<Album> allAlbums) throws SQLException {
        Statement myStatement = connection.createStatement();
        ResultSet myResult = myStatement.executeQuery("select * from albums_owned_by_user");
        while(myResult.next()){
            int userId = myResult.getInt("user_id");
            int albumId = myResult.getInt("album_id");
            for(StandardUser loopStandard: standardUsers){
                if(loopStandard.getId() == userId){
                    for(Album loopAlbum: allAlbums){
                        if(loopAlbum.getId() == albumId){
                            loopStandard.getOwnedAlbums().add(loopAlbum);
                        }
                    }
                }
            }
        }
    }

    public static void insertNewStatusInDatabase(Connection connection, String string, User currentUser) throws Exception {
        String query = "update user set status = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, currentUser.getStatus());
        preparedStatement.setInt(2, currentUser.getId());
        preparedStatement.execute();
    }

    public static void deleteFriendship(Connection connection, int id1, int id2) throws SQLException {
        String query1 = "delete from friendships where user1_id = ? and user2_id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1,id1);
        preparedStatement1.setInt(2,id2);
        preparedStatement1.execute();

        String query2 = "delete from friendships where user1_id = ? and user2_id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1,id2);
        preparedStatement2.setInt(2,id1);
        preparedStatement2.execute();
    }

    public static void upgradeFromStandardToPremium(Connection connection, int id) throws SQLException {
        String query = "insert into premium_user (id, monthly_fee)" + "values(?, ?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query);
        preparedStatement1.setInt(1, id);
        preparedStatement1.setInt(2, 100);
        preparedStatement1.execute();

        String query2 = "delete from standard_user where id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, id);
        preparedStatement2.execute();
    }

    public static void upgradeFromPremiumToArtist(Connection connection, int id) throws SQLException {
        String query = "insert into artist (id)" + "values(?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query);
        preparedStatement1.setInt(1, id);
        preparedStatement1.execute();

        String query2 = "delete from premium_user where id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, id);
        preparedStatement2.execute();
    }

    public static void addFriendship(Connection connection, int id1, int id2) throws SQLException {
        String query1 = "insert into friendships (user1_id,user2_id)" + "values(?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, id1);
        preparedStatement1.setInt(2, id2);
        preparedStatement1.execute();

        String query2 = "insert into friendships (user1_id,user2_id)" + "values(?,?)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, id2);
        preparedStatement2.setInt(2, id1);
        preparedStatement2.execute();
    }

    public static void addFollowing(Connection connection, int userId, int artistId) throws SQLException {
        String query1 = "insert into followings (user_id,artist_id)" + "values(?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, userId);
        preparedStatement1.setInt(2, artistId);
        preparedStatement1.execute();
    }

    public static void addNewOwnedSong(Connection connection, int userId, int songId) throws SQLException {
        String query1 = "insert into songs_owned_by_user (song_id, user_id)" + "values(?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, songId);
        preparedStatement1.setInt(2, userId);
        preparedStatement1.execute();
    }

    public static void addNewOwnedAlbum(Connection connection, int userId, int albumId) throws SQLException {
        String query1 = "insert into albums_owned_by_user (album_id, user_id)" + "values(?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, albumId);
        preparedStatement1.setInt(2, userId);
        preparedStatement1.execute();
    }

    public static void deleteFollowing(Connection connection, int id) throws SQLException {
        String query2 = "delete from followings where user_id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, id);
        preparedStatement2.execute();
    }

    public static void modifyUsername(Connection connection, int id, String newUsername) throws SQLException {
        String query = "update user set username = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newUsername);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public static void modifyFirstName(Connection myConn, int id, String newFirstName) throws SQLException {
        String query = "update user set first_name = ? where id = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query);
        preparedStatement.setString(1, newFirstName);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public static void modifyLastName(Connection myConn, int id, String newLastName) throws SQLException {
        String query = "update user set last_name = ? where id = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query);
        preparedStatement.setString(1, newLastName);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public static void modifyEmail(Connection myConn, int id, String newEmail) throws SQLException {
        String query = "update user set email = ? where id = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query);
        preparedStatement.setString(1, newEmail);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public static void modifyCountry(Connection myConn, int id, int countryId) throws SQLException {
        String query = "update user set country_id = ? where id = ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query);
        preparedStatement.setInt(1, countryId);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public static void deleteOwnedSong(Connection myConn, int song_id) throws SQLException {
        String query2 = "delete from songs_owned_by_user where song_id = ?";
        PreparedStatement preparedStatement2 = myConn.prepareStatement(query2);
        preparedStatement2.setInt(1, song_id);
        preparedStatement2.execute();
    }

    public static void deleteSongAlbum(Connection myConn, int song_id) throws SQLException {
        String query2 = "delete from album_songs where song_id = ?";
        PreparedStatement preparedStatement2 = myConn.prepareStatement(query2);
        preparedStatement2.setInt(1, song_id);
        preparedStatement2.execute();
    }

    public static void deleteSong(Connection myConn, int id) throws SQLException {
        String query2 = "delete from song where id = ?";
        PreparedStatement preparedStatement2 = myConn.prepareStatement(query2);
        preparedStatement2.setInt(1, id);
        preparedStatement2.execute();
    }
    
}
