import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.nio.file.Paths;

public class Parser {
    public static Map<String, Integer> keys;
    public static Map<Integer, HashMap<Integer, Integer>> adj;
    
    public Parser() {
        Parser.keys = new HashMap<String, Integer>();
        Parser.adj = new HashMap<Integer, HashMap<Integer, Integer>>();
    }
    
    public void readData() throws IOException {
        Map<String, HashSet<Integer>> season = new HashMap<String, HashSet<Integer>>();
        BufferedReader br = new BufferedReader(new FileReader("data/season-data.csv"));
        int year = 1950;
        String row;
        String name;
        int nameKey;
        String team;
        HashSet<Integer> currPlayers;
        
        row = br.readLine();
        while ((row = br.readLine()) != null) {
            String[] data = row.split(",");
            if (data.length > 2) {
                if (Integer.parseInt(data[1]) != year) {
                    for (Map.Entry<String, HashSet<Integer>> e : season.entrySet()) {
                        List<Integer> players = new ArrayList<Integer>(e.getValue());
                        for (int i = 0; i < players.size(); i++) {
                            for (int j = i + 1; j < players.size(); j++) {
                                if (i != j) {
                                    incrementEdge(players.get(i), players.get(j));
                                    incrementEdge(players.get(j), players.get(i));
                                }
                            }
                        }
                    }
                    year = Integer.parseInt(data[1]);
                    season = new HashMap<String, HashSet<Integer>>();
                }
                name = data[2].replaceAll("\\s", "").toLowerCase();
                nameKey = this.keys.getOrDefault(name, this.keys.size() + 1);
                this.keys.put(name, nameKey);
                
                team = data[5];
                currPlayers = season.getOrDefault(team, new HashSet<Integer>());
                currPlayers.add(nameKey);
                season.put(team, currPlayers);
            }
        }
        br.close();
        
        // Verify that the results are correct.
        for (int k = 1; k < 11; k++) {
            this.adj.get(k).entrySet().forEach(entry -> {
                System.out.print(entry.getKey() + " : " + entry.getValue() + ", ");
            });
            System.out.println();
        }
    }
    
    public void incrementEdge(int p1, int p2) {
        HashMap<Integer, Integer> p1Edges = this.adj.getOrDefault(p1, new HashMap<Integer, Integer>());
        if (p1Edges.containsKey(p2)) {
            p1Edges.put(p2, p1Edges.get(p2) + 1);
        } else {
            p1Edges.put(p2, 1);
        }
        this.adj.put(p1, p1Edges);
    }
    
    public static String question1(String p1, String p2) {
        return "Question 1";
    }
    
    public static String question2(String p1, String p2) {
        return "Question 2";
    }
    
    public static String question3(String p) {
        return "Question 3";
    }
}
