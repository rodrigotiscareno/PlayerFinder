// Rodrigo Tiscareno
// MSCI 240 Final Project
// December 10-16, 2021
// The PlayerGraph class is an object class that serves to 
// 1. Contruct the graph from players to clubs and nationalities. 
// 2. Conduct a breadth-first search to search and establish parent nodes of a node from the source node.
// 3. Having a target node, backtrack towards the source node and form a sequence (path) of vertices connecting the two players together. 

// The input to the PlayerGraph class is the constructor and the calling of different methods when constructing the graph or conducting the BFS.

// The output of the PlayerGraph class is the construction of a new PlayerGraph object, and the essential list of vertices that connect source to destination nodes.
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;



public class PlayerGraph {

    // For each vertex that exists, there is adjacent vertices that are all included in the adjacency list of the vertex.
    // The key represents the String value of the vertex and the LinkedList represents the list of nodes that are adjacent to the specified vertex.
    private HashMap <String, LinkedList<String>> adjacencyList;

    // Constructing a PlayerGraph means constructing a new hashmap to track vertices (as keys) and their adjacent vertices (as LinkedList values).
    public PlayerGraph() {
        adjacencyList = new HashMap<>();
    }

    // Adds a vertex to the graph. The vertex can be either player. club, or nationality.
    // The HashMap will add a new key - the string value - and define a new LinkedList which will be added once edges to the current vertex are defined.
    public void addVertex(String s){
        adjacencyList.putIfAbsent(s, new LinkedList<>());
    }

    // To both vertices specified, the value of the other vertex will be added to the adjacency list of that vertex.
    public void addEdge(String first, String second) {
        // Getting the vertex from the HashMap, then adding the other vertex to the LinkedList. 
        adjacencyList.get(first).add(second);
        // We also need to keep track of the "other" nodes adjacency list and add the other connecting node to the adjacency list. 
        adjacencyList.get(second).add(first);
    }

    // Useful method to ensure duplicate clubs or nationalities are not added to the graph. 
    public boolean contains(String s) {
        // Keys in adjacent list represent vertices. Therefore checking the keys for the value is sufficient to check whether vertex is already established.
        return adjacencyList.containsKey(s);
    }

    // Used for testing purposes. Returns the adjacent vertices of a specified vertex.

    // public void getAdjacencyList(String s) {
    //     System.out.println(adjacencyList.get(s));
    // }

    // The BFS will traverse through the entire graph and visit all vertices included. 
    // The BFS will start at the InitialPlayer node and reach every unvisited node that is connected to the starting node or connected to the adjacent nodes connected to the starting node.
    public ArrayList<String> breadthFirstSearch(String initialPlayer, String targetPlayer) {
        // The distances HashMap keeps track of each vertex's distance from the starting vertex. 
        // Key -> Vertex, Value -> Distance from starting node.
        Map<String, Integer> distancesFromStart = new HashMap<String, Integer>();
        // The parents hashmap will keep track of a vertex's parent vertex from the starting node. 
        // Key -> Vertex, Value -> Parent node of vertex as defined by starting vertex.
        Map<String, String> parents = new HashMap<String, String>();

        // For every vertex in the graph, set the distance value to -1 (represents unvisited status) and set parent value to null (not parented by any vertex yet.)
        for (String str : adjacencyList.keySet()) {
            distancesFromStart.put(str, -1);
            parents.put(str, null);
        }
        
        // Queue will assist in queuing each node to be visited.
        Queue<String> q = new LinkedList<String>();

        // Defining the distance of the initalPlayer vertex to 0. (First to be visited)
        distancesFromStart.put(initialPlayer, 0);
        // Adding the initial vertex to the Queue - it will be the first node to be visited.
        q.add(initialPlayer);

        // Until the queue is exhausted and all possible nodes have been visited, loop executes.
        while(!q.isEmpty()){
            
            // The current vertex being explored.
            String current = q.remove(); 

            // For every node that is connected to the current node, loop executes.
            for (String v : adjacencyList.get(current)) {
                // If the node is unvisited, if statement executes. If node is previously visited, the next adjacent node to current is visited.
                // If there are no more adjacent nodes to the current node, the queue is revisted and the loop is repeated for the new vertex adjacent vertices. 
                if (distancesFromStart.get(v)==-1) {
                    // Define the unvisited adjacent node and set its distance to the current nodes distance plus 1.
                    // The additon of 1 is included because it is 1 additional vertex away from the inital node.
                    distancesFromStart.put(v, distancesFromStart.get(current)+1);
                    // The unvisited node is included as a key and it's value is the parent that connected it from the inital player. 
                    parents.put(v, current);
                    // Planning to visit the univisited node by adding to the queue. 
                    q.add(v);
                
                }
            }
        }

        return backtrack(parents, targetPlayer, initialPlayer);


        // System.out.println(parents);
        
        // int targetDistance = distances.get(targetPlayer);
        // System.out.println(targetDistance);
    }

    // The backtrack method will return the list of vertices from the destination vertex to the source vertex using the parent HashMap defined in the BFS.
    public ArrayList<String> backtrack(Map<String, String> parents, String targetPlayer, String initialPlayer) {
        // list will reprent sequence of nodes. 
        ArrayList<String> list = new ArrayList<String>();
        // Adding the destination node to the list.
        list.add(targetPlayer);
        // While the parents of the "current" vertex is not the initial player vertex, loop executes.
        while (parents.get(targetPlayer) != initialPlayer) {
            // Adding the parents of the current node to the list.
            list.add(parents.get(targetPlayer));
            // Re-assigning the "current" node to be the parent node of the previous current node. 
            targetPlayer = parents.get(targetPlayer);
            // The loop will execute, finding the parent of each node and re-assigning the node in question until the initial player node is found. 
        }
        // Adding initial player vertex to list. 
        list.add(initialPlayer);
        // Since the sequence started from destination to source, reversing the list gets the path in-order. 
        Collections.reverse(list);
        return list;
    }
}
