package AudioLibs;

/**
 *
 * @author Pedro Tiago
 */
public enum Types {

    AVG(1), MAX(2), WINDOW_SIZE(128), FRAMES(3000), BUFFER_SIZE(1024);
    public int value;
    public String sValue;

    Types(int value) {
        this.value = value;
    }

    Types(String value) {
        this.sValue = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.sValue;
    }
}