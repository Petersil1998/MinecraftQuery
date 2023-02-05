public class Main {
    public static void main(String[] args) {
        MinecraftQuery query = new MinecraftQuery("localhost", 25565);
        BasicStats stats = query.getBasicStats();
        FullStats fullStats = query.getFullStats();
        System.out.println(stats);
        System.out.println(fullStats);
    }
}
