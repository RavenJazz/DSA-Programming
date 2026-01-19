import java.util.*;

public class Dijkstra {
    // Node class to represent graph vertices and weights
    static class Node implements Comparable<Node> {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        // Compare nodes by weight for the PriorityQueue
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public static void dijkstra(int V, List<List<Node>> adj, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Min-Heap to store {vertex, distance}
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            // Optimization: Skip if we found a shorter path to u already
            if (current.weight > dist[u]) continue;

            for (Node neighbor : adj.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                // Relaxation step
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        // Output results
        System.out.println("Dijkstra - Vertex Distance from Source " + source);
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t\t" + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Node>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        // Adding edges (u, v, w)
        adj.get(0).add(new Node(1, 9));
        adj.get(0).add(new Node(2, 6));
        adj.get(0).add(new Node(3, 5));
        adj.get(0).add(new Node(4, 3));
        adj.get(2).add(new Node(1, 2));
        adj.get(2).add(new Node(3, 4));

        dijkstra(V, adj, 0);
    }
}
