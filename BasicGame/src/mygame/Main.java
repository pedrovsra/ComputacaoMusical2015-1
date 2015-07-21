package mygame;

public class Main {

    public static void main(String[] args) throws Exception {
        
        StartScreenApp app = new StartScreenApp();
        app.start();
        
        /*
         AudioListener listener = new AudioListener(8000, (short)16, (short)2);
        
         AudioFormat outputFormat = new AudioFormat(44100.0f, 16, 2, true, true);
         SourceDataLine output = AudioSystem.getSourceDataLine(outputFormat);
         DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, outputFormat);

         output = (SourceDataLine) AudioSystem.getLine(outInfo);
         output.open(listener.getAudioFormat());
         output.flush();
         output.start();
        
         listener.flush();
         listener.start();
        
        
         byte[] data = new byte[1025];
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         int numBytesRead;
         boolean stopped = false;

         while (!stopped) {
         data = listener.read();
         numBytesRead = listener.getNumBytesRead();
         out.write(data, 0, numBytesRead);
         output.write(data, 0, numBytesRead);
         }
         */
    }
}
