/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Pedro Tiago
 */
public class AudioDevice {

    /**
     * the java sound line we write our samples to *
     */
    private final SourceDataLine out;
    /**
     * buffer for BUFFER_SIZE 16-bit samples *
     */
    private byte[] buffer = new byte[Types.BUFFER_SIZE.value * 2];

    /**
     * Constructor, initializes the audio system for 44100Hz 16-bit signed mono
     * output.
     *
     * @throws Exception in case the audio system could not be initialized
     */
    public AudioDevice() throws Exception {
        AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
        out = AudioSystem.getSourceDataLine(format);
        out.open(format);
        out.start();
    }

    /**
     * Writes the given samples to the audio device. The samples have to be
     * sampled at 44100Hz, mono and have to be in the range [-1,1].
     *
     * @param samples The samples.
     */
    public void writeSamples(float[] samples) {
        fillBuffer(samples);
        out.write(buffer, 0, buffer.length);
    }

    private void fillBuffer(float[] samples) {
        for (int i = 0, j = 0; i < samples.length; i++, j += 2) {
            short value = (short) (samples[i] * Short.MAX_VALUE);
            buffer[j] = (byte) (value | 0xff);
            buffer[j + 1] = (byte) (value >> 8);
        }
    }
}