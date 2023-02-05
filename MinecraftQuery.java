import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;

public class MinecraftQuery {

    private DatagramSocket socket = null;

    private final String hostname;
    private final int port;

    public MinecraftQuery(String hostname, int port) {
        this.createSocket();
        this.hostname = hostname;
        this.port = port;
    }

    public BasicStats getBasicStats() {
        byte[] sessionID = this.createSessionID();
        int token = this.doHandshake(sessionID);


        byte[] metadata = {(byte) 0xFE, (byte) 0xFD, 0};
        byte[] tokenArray = ByteUtils.intToByteArray(token);
        byte[] data = Arrays.copyOf(metadata, metadata.length + sessionID.length + tokenArray.length);
        System.arraycopy(sessionID, 0, data, metadata.length, sessionID.length);
        System.arraycopy(tokenArray, 0, data, metadata.length + sessionID.length, tokenArray.length);

        DatagramPacket response = doUDPRequest(data);
        return new BasicStats(ByteUtils.split(Arrays.copyOfRange(response.getData(), 5, response.getLength() - 1)));
    }

    public FullStats getFullStats() {
        byte[] sessionID = this.createSessionID();
        int token = this.doHandshake(sessionID);

        byte[] metadata = {(byte) 0xFE, (byte) 0xFD, 0};
        byte[] tokenArray = ByteUtils.intToByteArray(token);
        byte[] data = Arrays.copyOf(metadata, metadata.length + sessionID.length + tokenArray.length + 4);
        System.arraycopy(sessionID, 0, data, metadata.length, sessionID.length);
        System.arraycopy(tokenArray, 0, data, metadata.length + sessionID.length, tokenArray.length);
        System.arraycopy(new byte[4], 0, data, metadata.length + sessionID.length + tokenArray.length, 4);

        DatagramPacket response = doUDPRequest(data);
        System.out.println(Arrays.toString(Arrays.copyOfRange(response.getData(), 16, response.getLength() - 1)));
        return new FullStats(ByteUtils.split(Arrays.copyOfRange(response.getData(), 16, response.getLength() - 1)));
    }

    private byte[] createSessionID() {
        int tmp = new Random().nextInt();
        return new byte[]{
                (byte) (tmp >>> 24 & 0b01111111),
                (byte) (tmp >>> 16 & 0b01111111),
                (byte) (tmp >>> 8 & 0b01111111),
                (byte) (tmp & 0b01111111)};
    }

    private int doHandshake(byte[] sessionID) {
        byte[] metadata = {(byte) 0xFE, (byte) 0xFD, 9};
        byte[] data = Arrays.copyOf(metadata, metadata.length + sessionID.length);
        System.arraycopy(sessionID, 0, data, metadata.length, sessionID.length);

        DatagramPacket response = doUDPRequest(data);
        return Integer.parseInt(new String(Arrays.copyOfRange(response.getData(), 5, response.getLength())).trim());
    }

    private DatagramPacket doUDPRequest(byte[] data) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(this.hostname), this.port);
            socket.send(packet);

            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(500);
            socket.receive(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private void createSocket() {
        try {
            int srcPort = 25565;
            while(socket == null)
            {
                try {
                    socket = new DatagramSocket(srcPort);
                } catch (BindException e) {
                    ++srcPort;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
