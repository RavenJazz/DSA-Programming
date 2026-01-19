package Network;

class Router {
    String name;
    Map<String, Integer> neighbors = new HashMap<>();

    public Router(String name) {
        this.name = name;
    }

    public void addNeighbor(String neighborName, int cost) {
        neighbors.put(neighborName, cost);
    }
}
