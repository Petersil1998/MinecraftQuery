import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class BasicStats {

    private final String motD;

    private final String gameType;

    private final String map;

    private final int numPlayers;

    private final int maxPlayers;

    private final String hostIP;

    private final int hostPort;

    public BasicStats(byte[][] data) {
        this.motD = new String(data[0]);
        this.gameType = new String(data[1]);
        this.map = new String(data[2]);
        this.numPlayers = Integer.parseInt(new String(data[3]));
        this.maxPlayers = Integer.parseInt(new String(data[4]));
        this.hostPort = ByteBuffer.wrap(Arrays.copyOfRange(data[5], 0, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        this.hostIP = new String(Arrays.copyOfRange(data[5], 2, data[5].length));
    }

    public String getMotD() {
        return motD;
    }

    public String getGameType() {
        return gameType;
    }

    public String getMap() {
        return map;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getHostIP() {
        return hostIP;
    }

    public int getHostPort() {
        return hostPort;
    }

    @Override
    public String toString() {
        return "BasicStats{" +
                "motD='" + motD + '\'' +
                ", gameType='" + gameType + '\'' +
                ", map='" + map + '\'' +
                ", numPlayers=" + numPlayers +
                ", maxPlayers=" + maxPlayers +
                ", hostIP='" + hostIP + '\'' +
                ", hostPort=" + hostPort +
                '}';
    }
}
