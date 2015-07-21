package AudioLibs;

import java.util.ArrayList;

/**
 *
 * @author Pedro Tiago
 */
public class AudioProcess {

    public static ArrayList<Float> process(byte[] data, Types mode) {
        ArrayList<Float> aux = new ArrayList<Float>();
        float sum, max;
        float[] auxData = lowPass(data, 50);
        for (int i = 0; i < auxData.length - Types.WINDOW_SIZE.value; i++) {
            sum = 0;
            max = 0;
            for (int j = i; j < i + Types.WINDOW_SIZE.value; j++) {
                if (mode == Types.AVG) {
                    sum += auxData[j];
                } else if (mode == Types.MAX) {
                    if (auxData[j] > max) {
                        max = auxData[j];
                    }
                }
            }

            if (mode == Types.AVG) {
                aux.add(sum / Types.WINDOW_SIZE.value);
            } else if (mode == Types.MAX) {
                aux.add(max);
            }
        }
        return aux;
    }

    public static float[] lowPass(byte[] arr, int smooth) {
        float aux = arr[0];
        float[] ret = new float[arr.length];
        float curr;
        for (int i = 1; i < arr.length; i++) {
            curr = arr[i];
            aux += (curr - aux) / smooth;
            ret[i] = aux;
        }
        return ret;
    }

    public static ArrayList<Float> process(float[] data, Types mode) {
        ArrayList<Float> aux = new ArrayList<Float>();
        float sum, max;
        float[] auxData = lowPass(data, 50);
        for (int i = 0; i < auxData.length - Types.WINDOW_SIZE.value; i++) {
            sum = 0;
            max = 0;
            for (int j = i; j < i + Types.WINDOW_SIZE.value; j++) {
                if (mode == Types.AVG) {
                    sum += auxData[j];
                } else if (mode == Types.MAX) {
                    if (auxData[j] > max) {
                        max = auxData[j];
                    }
                }
            }

            if (mode == Types.AVG) {
                aux.add(sum / Types.WINDOW_SIZE.value);
            } else if (mode == Types.MAX) {
                aux.add(max);
            }
        }
        return aux;
    }

    public static float[] lowPass(float[] arr, int smooth) {
        float aux = arr[0];
        float[] ret = new float[arr.length];
        float curr;
        for (int i = 1; i < arr.length; i++) {
            curr = arr[i];
            aux += (curr - aux) / smooth;
            ret[i] = aux;
        }
        return ret;
    }

    public static byte[] cut(byte[] buffer, int startIndex) {
        byte[] ret = new byte[Types.FRAMES.value];
        int end = startIndex + Types.FRAMES.value;

        for (int i = startIndex; i < end; i++) {
            ret[i - startIndex] = buffer[i];
        }

        return ret;
    }

    public static float[] cut(float[] buffer, int startIndex) {
        float[] ret = new float[Types.FRAMES.value];
        int end = startIndex + Types.FRAMES.value;

        for (int i = startIndex; i < end; i++) {
            ret[i - startIndex] = buffer[i];
        }

        return ret;
    }

    public static float[] convertByte2Float(byte[] data) {
        float[] aux = new float[data.length];

        for (int i = 0; i < aux.length; i++) {
            aux[i] = (float) data[i];
        }

        return aux;
    }

    public static ArrayList<Float> convertByte2ArrayFloat(byte[] data) {
        ArrayList<Float> aux = new ArrayList<Float>();

        for (int i = 0; i < data.length; i++) {
            aux.add((float) data[i]);
        }

        return aux;
    }

    public static ArrayList<Byte> convertArray2List(byte[] data) {
        ArrayList<Byte> aux = new ArrayList<Byte>();

        for (int i = 0; i < data.length; i++) {
            aux.add(data[i]);
        }

        return aux;
    }

    public static byte[] convertList2Array(ArrayList<Byte> data) {
        byte[] aux = new byte[data.size()];

        for (int i = 0; i < data.size(); i++) {
            aux[i] = data.get(i);
        }

        return aux;
    }
}