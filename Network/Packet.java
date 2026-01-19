package Network;

import java.util.*;

// Represents a data packet moving through the network
class Packet {
    String data;
    String source;
    String destination;
    List<String> hopHistory = new ArrayList<>();

    public Packet(String data, String source, String destination) {
        this.data = data;
        this.source = source;
        this.destination = destination;
    }
}

// Represents a connection between two routers with a "cost" (latency/bandwidth)
class Link {
    String targetNode;
    int weight;

    public Link(String targetNode, int weight) {
        this.targetNode = targetNode;
        this.weight = weight;
    }
}
