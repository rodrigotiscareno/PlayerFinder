// Rodrigo Tiscareno
// MSCI 240 Final Project
// December 10-16, 2021
// The PlayerFinder is an algorithm to detect the path/distance from one player to another through connecting player, club, and nationality nodes searched through a BFS algorithm.

// The input to the PlayerFinder is the "FullData.csv" file which contains the record of different player names, clubs, nationalities, and skill ratings.
// From the user perspective, the input is also the defined source player and defined destination player. 

// The output of the program is the defined sequence of nodes corresponding to a player, club, or nationality until it arrives at the destination node specified.
// The program will also specify the degree of similarily between the two nodes, which represents the nodes needed to arrive at the destination node.

// To demonstrate knowledge of abstraction, object-orientated programming, and encapsulation, a trivial Player class was created. This can be found in the PlayerClass.txt file. 
// The intention is to build on this algorithm and include the Player class to store player rating scores and compute further algorithms. 

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PlayerFinder {

    // The following method will perform the following:
    // 1. Ask the user for their source player name and the destination player name.
    // 2. Construct the graph given an iterating player name, their club, and their nationality. (Connect player to club and nationality with edges)
    // 3. Return the sequence of players, clubs, and nationalities that link the source player to the destination player. 
    public static void main(String[] args) throws IOException {
        
        // Reading "FullData.csv" file from src folder.
        Scanner scanner = new Scanner(new FileReader("data/FullData.csv"));
        
        // Initializing graph object.
        PlayerGraph pG = new PlayerGraph();

        // The idea of the collections Map is to classify strings and determine whether they are clubs, players, or countries.
        // Ouput includes saying which node is a country, player, or club, so classifying them from the start is key.  
        Map<String, ArrayList<String>> collections = new HashMap<String, ArrayList<String>>();
        collections.put("clubs", new ArrayList<String>());
        collections.put("nationalities", new ArrayList<String>());
        collections.put("names", new ArrayList<String>());
        
        // We want to skip a line to skip the headers of the CSV file. 
        scanner.nextLine();
        
        // While the csv file has a new player entry, we want to repeat the following steps.
        while (scanner.hasNextLine()) {

            // Create an array from the line, splitting each token through their comma. 
            // Properties such as name are easily accessible through the index of the array. 
            String[] playerProperties = scanner.nextLine().split(",");
            // e.g. Player name can be found in current line's array at index 0 (first column).
            String name = playerProperties[0];
            String club = playerProperties[4];
            String nationality = playerProperties[1];

            // Add the entries to the collections HashMap to classify them as players, clubs, or countries.
            // Accessing the ArrayList value inside the HashMap to add the player/club/country string value. 
            collections.get("names").add(name);
            collections.get("clubs").add(club);
            collections.get("nationalities").add(nationality);

            
            // Add a vertex to the graph with the player name.
            pG.addVertex(name);
            // If the club corresponding to the player already exists, we do not need to create a new vertex representing such club.
            // If the current club vertex exists, we simply need to connect the player and the club through an edge. 
            // If the club doesn't exist yet, a vertex representing the club string is connected to the current player name. 
            if (pG.contains(club)) {
                pG.addEdge(name, club);
            } else {
                pG.addVertex(club);
                pG.addEdge(name, club);
            }

            // Same idea with the nationality, if it exists, simply connect player and nationality vertixes.
            // If the nationality doesn't exist, create a new nationality node and then connect with the player name vertex. 
            if (pG.contains(nationality)) {
                pG.addEdge(name, nationality);
            } else {
                pG.addVertex(nationality);
                pG.addEdge(name, nationality);
            }

            // At this point, the current player should be connected to their current club and country nodes. 
        }

        // Asking the user for input. 
        System.out.println();
        System.out.println("Welcome to the PlayerFinder");
        System.out.println();

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a source player name: ");
        String source = input.nextLine();
        System.out.println("Please enter a target player name: ");
        String target = input.nextLine();
        if (!collections.get("names").contains(source) || !collections.get("names").contains(target)) {
            System.out.println("Please enter a valid name.");
            input.close();
            return;
        }
        

        input.close();
        // Storing the resulting list from the BFS search algorithm.
        // The list is a sequence of vertices starting from the source player and ending with the destination player.
        ArrayList <String> list = pG.breadthFirstSearch(source, target);
        
        // To display the distance number between source vertex and destination vertex, the elements excluding the source and target are considered.
        // We can achieve this number by subtracting 2 from the size of the list. (1 - source player, 2 - destination player)
        String s = String.valueOf(list.size()-2);
        System.out.println();
        // Presenting distance or "degrees of similarity."
        System.out.println(source + " has " + s + " degrees of similarity with " + target + ".");
        System.out.println();
        // Presenting the starting vertex player and removing from the list before iteration. 
        System.out.print("We start with " + list.remove(0));
        
        // Iterating through the path sequence.
        // If-statements classify current vertex as a club, nationality, or player by checking if ArrayList embedded in two-element HashMap contains either value.
        // If nor a club or nationality value, the current vertex is a player. 
        for (String str : list) { 
            if(collections.get("clubs").contains(str)) {
                System.out.print(" who plays for " + str + " which is the same club as ");
            }else if(collections.get("nationalities").contains(str)) {
                System.out.print(" who's nationality is " + str + " which is the same nationality as ");
            } else {
                System.out.print(str);
            }
        }
        // Always finish with a player node as destination node and finishing statement. 
        System.out.print(" who is the target player.");
        
    }
    
}
