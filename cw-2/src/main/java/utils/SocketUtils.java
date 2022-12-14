package utils;

import Task1.Packet;

import java.io.IOException;
import java.io.InputStream;

public class SocketUtils {

    public static byte[] readInput(InputStream stream) throws IOException {
        int bytes;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((bytes = stream.read()) > -1) {
            buffer[counter++] = (byte) bytes;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && Packet.compare(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }

    public static byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }
}
