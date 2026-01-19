package Network;

class NetworkSimulator {
    Map<String, Router> nodes = new HashMap<>();

    public void addRouter(Router r) {
        nodes.put(r.name, r);
    }

    // Link State Routing Implementation (Dijkstra)
    public List<String> calculateRoute(String start, String end) {
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        
        for (String nodeName : nodes.keySet()) {
            distances.put(nodeName, Integer.MAX_VALUE);
        }
        
        distances.put(start, 0);
        pq.add(new NodeDistance(start, 0));

        while (!pq.isEmpty()) {
            String current = pq.poll().name;

            if (current.equals(end)) break;

            for (Map.Entry<String, Integer> neighbor : nodes.get(current).neighbors.entrySet()) {
                int newDist = distances.get(current) + neighbor.getValue();
                if (newDist < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDist);
                    previous.put(neighbor.getKey(), current);
                    pq.add(new NodeDistance(neighbor.getKey(), newDist));
                }
            }
        }

        // Reconstruct Path
        List<String> path = new LinkedList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public void transmitPacket(Packet p) {
        System.out.println("Starting transmission of: " + p.data);
        List<String> path = calculateRoute(p.source, p.destination);
        
        if (path.size() <= 1 && !p.source.equals(p.destination)) {
            System.out.println("Error: Destination unreachable.");
            return;
        }

        for (String hop : path) {
            p.hopHistory.add(hop);
            System.out.println(" > Packet arrived at Router: " + hop);
        }
        System.out.println("Transmission Complete. Hops: " + String.join(" -> ", p.hopHistory));
    }

    // Helper class for Dijkstra PQ
    private static class NodeDistance {
        String name;
        int distance;
        NodeDistance(String name, int distance) { this.name = name; this.distance = distance; }
    }
}