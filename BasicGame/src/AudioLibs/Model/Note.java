package AudioLibs.Model;

/**
 *
 * @author Pedro Tiago
 */
public class Note {

    private int noteType;
    private float[] samples;
    private String filePath;
    private long time;

    public Note(float[] samples, long time) {
        this.samples = samples;
        this.time = time;

        this.noteType = -1;
    }

    public Note(String filePath) {
        this.filePath = filePath;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public float[] getSamples() {
        return samples;
    }

    public void setSamples(float[] samples) {
        this.samples = samples;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}