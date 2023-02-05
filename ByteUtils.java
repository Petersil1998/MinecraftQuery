import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ByteUtils {

    public static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)(value)
        };
    }

    public static byte[][] split(byte[] array) {
        List<byte[]> byteArrays = new LinkedList<>();
        int begin = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                byteArrays.add(Arrays.copyOfRange(array, begin, i));
                begin = i + 1;
            }
        }

        // delimiter at the very end with no data following?
        if (begin != array.length) {
            byteArrays.add(Arrays.copyOfRange(array, begin, array.length));
        }

        return byteArrays.toArray(new byte[0][0]);
    }
}
