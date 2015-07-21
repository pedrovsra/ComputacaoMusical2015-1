/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Pedro Tiago
 */
public class WAVEncoder {

    //private static final int TIMER_INTERVAL = 120;
    private RandomAccessFile randomAccessWriter;
    private String filePath;
    private short numberChannels;
    private int sampleRate;
    private short sampleSize_bits;
    //private byte[] outBuffer;
    //private int framePeriod;
    private int payloadSize;

    public WAVEncoder(String filePath, short numberChannels, short sampleSize_bits, int sampleRate) throws FileNotFoundException {
        this.filePath = filePath + ".wav";
        this.numberChannels = numberChannels;
        this.sampleSize_bits = sampleSize_bits;
        this.sampleRate = sampleRate;

        //this.framePeriod = sampleRate * TIMER_INTERVAL / 1000;
        this.payloadSize = 0;
    }

    public void prepare() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            //file.mkdirs();
            file.createNewFile();

        }

        this.randomAccessWriter = new RandomAccessFile(filePath, "rw");

        // Set file length to 0, to prevent unexpected behavior in case the file already existed
        this.randomAccessWriter.setLength(0);
        this.randomAccessWriter.writeBytes("RIFF");
        this.randomAccessWriter.writeInt(0); // Final file size not known yet, write 0
        this.randomAccessWriter.writeBytes("WAVE");
        this.randomAccessWriter.writeBytes("fmt ");
        this.randomAccessWriter.writeInt(Integer.reverseBytes(16)); // Sub-chunk size, 16 for PCM
        this.randomAccessWriter.writeShort(Short.reverseBytes((short) 1)); // AudioFormat, 1 for PCM
        // Number of channels, 1 for mono, 2 for stereo
        this.randomAccessWriter.writeShort(Short.reverseBytes(numberChannels));
        this.randomAccessWriter.writeInt(Integer.reverseBytes(sampleRate)); // Sample rate
        // Byte rate, SampleRate*NumberOfChannels*BitsPerSample/8
        this.randomAccessWriter.writeInt(Integer.reverseBytes(sampleRate * sampleSize_bits * numberChannels / 8));
        // Block align, NumberOfChannels*BitsPerSample/8
        this.randomAccessWriter.writeShort(Short.reverseBytes((short) (numberChannels * sampleSize_bits / 8)));
        this.randomAccessWriter.writeShort(Short.reverseBytes(sampleSize_bits)); // Bits per sample
        this.randomAccessWriter.writeBytes("data");
        this.randomAccessWriter.writeInt(0);

        //outBuffer = new byte[framePeriod * sampleSize_bits / 8 * numberChannels];
        //System.out.println("tamanho " + outBuffer.length);
    }

    public void setPayload(int payload) {
        this.payloadSize = payload;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath + ".wav";
    }

    public void write(byte[] buffer) throws IOException {
        this.randomAccessWriter.write(buffer);
    }

    public void save() throws IOException {

        this.randomAccessWriter.seek(4); // Write size to RIFF header
        this.randomAccessWriter.writeInt(Integer.reverseBytes(36 + payloadSize));

        this.randomAccessWriter.seek(40); // Write size to Subchunk2Size field
        this.randomAccessWriter.writeInt(Integer.reverseBytes(payloadSize));

        //this.randomAccessWriter.write(buffer);

        this.randomAccessWriter.close();
    }
}