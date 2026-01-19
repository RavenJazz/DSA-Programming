import java.util.*;

public class BellmanFord {
    static class Edge {
        int src, dest, weight;
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void bellmanFord(int V, List<Edge> edges, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Step 1: Relax all edges |V| - 1 times
        for (int i = 1; i < V; i++) {
            for (Edge e : edges) {
                if (dist[e.src] != Integer.MAX_VALUE && dist[e.src] + e.weight < dist[e.dest]) {
                    dist[e.dest] = dist[e.src] + e.weight;
                }
            }
        }

        // Step 2: Check for negative-weight cycles
        for (Edge e : edges) {
            if (dist[e.src] != Integer.MAX_VALUE && dist[e.src] + e.weight < dist[e.dest]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }

        // Output results
        System.out.println("Bellman-Ford - Vertex Distance from Source " + source);
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t\t" + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<Edge> edges = new ArrayList<>();

        // Adding edges (u, v, w) including negative weights
        edges.add(new Edge(0, 1, -1));
        edges.add(new Edge(0, 2, 4));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 3, 2));
        edges.add(new Edge(1, 4, 2));
        edges.add(new Edge(3, 2, 5));
        edges.add(new Edge(3, 1, 1));
        edges.add(new Edge(4, 3, -3));

        bellmanFord(V, edges, 0);
    }
}