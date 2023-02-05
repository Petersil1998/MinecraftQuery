import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullStats {
    private final String motD;

    private final String gameType;

    private final String gameID;

    private final String version;

    private final String plugins;

    private final String map;

    private final int numPlayers;

    private final int maxPlayers;

    private final String hostIP;

    private final int hostPort;

    private final List<String> players;

    public FullStats(byte[][] data) {
        this.motD = new String(data[1]);
        this.gameType = new String(data[3]);
        this.gameID = new String(data[5]);
        this.version = new String(data[7]);
        this.plugins = new String(data[9]);
        this.map = new String(data[11]);
        this.numPlayers = Integer.parseInt(new String(data[13]));
        this.maxPlayers = Integer.parseInt(new String(data[15]));
        this.hostPort = Integer.parseInt(new String(data[17]));
        this.hostIP = new String(data[19]);
        this.players = new ArrayList<>();
        for(int i = 23; i < data.length; i++) {
            players.add(new String(data[i]));
        }
    }

    public String getMotD() {
        return motD;
    }

    public String getGameType() {
        return gameType;
    }

    public String getGameID() {
        return gameID;
    }

    public String getVersion() {
        return version;
    }

    public String getPlugins() {
        return plugins;
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

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "FullStats{" +
                "motD='" + motD + '\'' +
                ", gameType='" + gameType + '\'' +
                ", gameID='" + gameID + '\'' +
                ", version='" + version + '\'' +
                ", plugins='" + plugins + '\'' +
                ", map='" + map + '\'' +
                ", numPlayers=" + numPlayers +
                ", maxPlayers=" + maxPlayers +
                ", hostIP='" + hostIP + '\'' +
                ", hostPort=" + hostPort +
                ", players=" + players +
                '}';
    }
}
