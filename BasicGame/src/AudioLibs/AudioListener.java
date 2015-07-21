package AudioLibs;

import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Pedro Tiago
 */
public class AudioListener {

    private static final int DATA_SIZE = 1024;
    private int samplingRate;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private DataLine.Info dataLineInfo;
    private boolean stopped;
    private boolean audioDeviceInitialized;
    private byte[] data;
    private int numBytesRead;
    private short channels;
    private short sampleSize;

    public AudioListener(int samplingRate, short sampleSize, short channels) throws LineUnavailableException {
        this.stopped = true;
        this.audioDeviceInitialized = false;
        this.data = new byte[DATA_SIZE];
        this.numBytesRead = 0;

        this.samplingRate = samplingRate;
        this.sampleSize = sampleSize;
        this.channels = channels;

        this.audioFormat = new AudioFormat(samplingRate, sampleSize, channels, true, true); //big-endian
        //this.audioFormat = new AudioFormat(samplingRate, sampleSize, channels, true, false); //little-endian
        this.targetDataLine = AudioSystem.getTargetDataLine(this.audioFormat);
        this.dataLineInfo = new DataLine.Info(TargetDataLine.class, this.audioFormat);

        this.targetDataLine = (TargetDataLine) AudioSystem.getLine(this.dataLineInfo);
        this.targetDataLine.open(this.audioFormat);
    }

    public void start() {
        this.stopped = false;
        this.targetDataLine.start();
    }

    public void stop() {
        this.stopped = true;
        this.targetDataLine.stop();
    }

    public void open() throws LineUnavailableException {
        this.targetDataLine.open(this.audioFormat);
    }

    public void close() {
        this.targetDataLine.close();
    }

    public void flush() {
        this.targetDataLine.flush();
    }

    public byte[] read() {
        this.numBytesRead = this.targetDataLine.read(this.data, 0, this.data.length);
        return this.data;
    }

    public boolean isOpen() {
        return this.targetDataLine.isOpen();
    }

    public boolean isActive() {
        return this.targetDataLine.isActive();
    }

    public ArrayList<String> getAvailableInputDevices() {
        ArrayList<String> arr = new ArrayList<String>();
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        Line.Info targetDLInfo = new Line.Info(TargetDataLine.class);

        for (int i = 0; i < mixerInfos.length; i++) {
            Mixer curr = AudioSystem.getMixer(mixerInfos[i]);

            if (curr.isLineSupported(targetDLInfo)) {
                arr.add(mixerInfos[i].getName());
            }
        }
        return arr;
    }

    public void startInputDevice(String deviceName) throws LineUnavailableException {

        Mixer.Info[] mixerInfo;
        mixerInfo = AudioSystem.getMixerInfo();
        Line.Info targetDLInfo = new Line.Info(TargetDataLine.class);

        for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
            Mixer currentMixer = AudioSystem.getMixer(mixerInfo[cnt]);

            if (mixerInfo[cnt].getName().equals(deviceName)) {
                this.targetDataLine.close();
                this.dataLineInfo = new DataLine.Info(TargetDataLine.class, this.audioFormat);

                this.targetDataLine = (TargetDataLine) currentMixer.getLine(this.dataLineInfo);
                this.targetDataLine.open(this.audioFormat);
                this.targetDataLine.start();
            }
        }
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    public void setTargetDataLine(TargetDataLine targetDataLine) {
        this.targetDataLine = targetDataLine;
    }

    public DataLine.Info getDataLineInfo() {
        return dataLineInfo;
    }

    public void setDataLineInfo(DataLine.Info dataLineInfo) {
        this.dataLineInfo = dataLineInfo;
    }

    public int getNumBytesRead() {
        return numBytesRead;
    }

    public void setNumBytesRead(int numBytesRead) {
        this.numBytesRead = numBytesRead;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    public short getChannels() {
        return channels;
    }

    public void setChannels(short channels) {
        this.channels = channels;
    }

    public short getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(short sampleSize) {
        this.sampleSize = sampleSize;
    }
}