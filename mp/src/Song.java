public class Song {

    private int id;
    private String title;
    private int length;
    private int price;
    private Artist artist;
    private Album album;
    private String musicpath;

    public Song(int id, String title, int length, int price, Artist artist, Album album){
        this.id = id;
        this.title = title;
        this.length = length;
        this.price = price;
        this.artist = artist;
        this.album = album;
    }

    public Song(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public void setMusicpath(String musicpath) {
        this.musicpath = musicpath;
    }
}
