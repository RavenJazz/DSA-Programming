package Network;

public class Main {
    public static void main(String[] args) {
        NetworkSimulator net = new NetworkSimulator();

        // Create Routers
        Router rA = new Router("A");
        Router rB = new Router("B");
        Router rC = new Router("C");
        Router rD = new Router("D");

        // Define Connections (Cost/Weight)
        rA.addNeighbor("B", 1); // Fast link
        rA.addNeighbor("C", 5); // Slow link
        rB.addNeighbor("D", 1);
        rC.addNeighbor("D", 1);

        net.addRouter(rA);
        net.addRouter(rB);
        net.addRouter(rC);
        net.addRouter(rD);

        // Simulate Packet
        Packet myData = new Packet("Hello World", "A", "D");
        net.transmitPacket(myData);
    }
}
