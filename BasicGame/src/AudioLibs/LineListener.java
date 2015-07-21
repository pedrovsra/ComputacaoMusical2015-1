/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Pedro Tiago
 */
public class LineListener {

    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    AudioFormat format;
    DataLine.Info info;
    AudioInputStream ais;

    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    public LineListener() throws LineUnavailableException {
        this.format = getAudioFormat();
        this.info = new DataLine.Info(TargetDataLine.class, format);

        if (!checkLine()) {
            throw new LineUnavailableException();
        }

        this.line = (TargetDataLine) AudioSystem.getLine(info);
        this.ais = new AudioInputStream(line);
    }

    public void open() throws LineUnavailableException {
        this.line.open();
    }

    public void close() {
        this.line.close();
    }

    public void flush() {
        this.line.flush();
    }

    public void stop() {
        this.line.stop();
    }

    public void start() {
        this.line.start();
    }

    public void record(String filePath) throws IOException, InterruptedException {
        start();
        AudioSystem.write(ais, fileType, new File(filePath + ".wav"));
    }

    private boolean checkLine() {
        return AudioSystem.isLineSupported(info);
    }
}
