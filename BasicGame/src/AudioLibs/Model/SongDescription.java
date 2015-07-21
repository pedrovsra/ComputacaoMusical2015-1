package AudioLibs.Model;

/**
 *
 * @author Pedro Tiago
 */
public class SongDescription {

    // song info
    private String id;
    private String Artist;
    private String Album;
    private String Song;
    // song files
    private String configFileName;
    private String syncFileName;
    private String audioFileName;
    // song properties
    private int totalNotes;

    public SongDescription() {
    }

    public SongDescription(String id, String Artist, String Album, String Song, String configFileName, String syncFileName, String audioFileName) {
        this.id = id;
        this.Artist = Artist;
        this.Album = Album;
        this.Song = Song;
        this.configFileName = configFileName;
        this.syncFileName = syncFileName;
        this.audioFileName = audioFileName;
    }

    public SongDescription(String id, String Artist, String Album, String Song) {
        this.id = id;
        this.Artist = Artist;
        this.Album = Album;
        this.Song = Song;
    }

    public SongDescription(String configFileName, String syncFileName, String audioFileName) {
        this.configFileName = configFileName;
        this.syncFileName = syncFileName;
        this.audioFileName = audioFileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String Artist) {
        this.Artist = Artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String Album) {
        this.Album = Album;
    }

    public String getSong() {
        return Song;
    }

    public void setSong(String Song) {
        this.Song = Song;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public String getSyncFileName() {
        return syncFileName;
    }

    public void setSyncFileName(String syncFileName) {
        this.syncFileName = syncFileName;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public int getTotalNotes() {
        return this.totalNotes;
    }

    public void setTotalNotes(int totalNotes) {
        this.totalNotes = totalNotes;
    }
}