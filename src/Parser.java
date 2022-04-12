import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.nio.file.Paths;

public class Parser {
    public static Map<String, String> names;
    public static Map<String, Integer> keys;
    public static Map<Integer, String> values;
    public static Map<Integer, HashMap<Integer, Integer>> adj;
    
    public Parser() {
        // maps coded names to full names (eg. "lebronjames" - "Lebron James")
        Parser.names = new HashMap<String, String>();
        
        // maps coded names to player IDs (eg. "lebronjames" - 156)
        Parser.keys = new HashMap<String, Integer>();
        
        // maps player IDs to coded names (eg. 156 - "lebronjames")
        Parser.values = new HashMap<Integer, String>();
        
        // maps player IDs to a map of other player IDs and the number of years they player together
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
                    if (season.containsKey("TOT")) {
                        season.remove("TOT");
                    }
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
                data[2] = data[2].replaceAll("[^A-Za-z]", "");
                name = data[2].replaceAll("\\s", "").toLowerCase();
                nameKey = this.keys.getOrDefault(name, this.keys.size() + 1);
                this.names.put(name,  data[2]);
                this.keys.put(name, nameKey);
                this.values.put(nameKey, name);
                
                team = data[5];
                currPlayers = season.getOrDefault(team, new HashSet<Integer>());
                currPlayers.add(nameKey);
                season.put(team, currPlayers);
            }
        }
        br.close();
        
        // Verify that the results are correct.
        /*
         for (int k = 1; k < 11; k++) {
            this.adj.get(k).entrySet().forEach(entry -> {
                System.out.print(entry.getKey() + " : " + entry.getValue() + ", ");
            });
            System.out.println();
        }
        
        this.names.entrySet().forEach(entry -> {
            System.out.print(entry.getKey() + " : " + entry.getValue() + ", ");
        });
        this.keys.entrySet().forEach(entry -> {
            System.out.print(entry.getKey() + " : " + entry.getValue() + ", ");
        });
        this.values.entrySet().forEach(entry -> {
            System.out.print(entry.getKey() + " : " + entry.getValue() + ", ");
        });*/
        
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

    private static void findPaths(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> arr, ArrayList<ArrayList<Integer>> parent,
                      int n, int player2) {
        if (player2 == -1) {
            paths.add(new ArrayList<>(arr));
            return;
        }
        for (int p: parent.get(player2)) {
            arr.add(player2);
            findPaths(paths, arr, parent, n, p);
            arr.remove(arr.size()-1);
        }
    }
    
    public static String question1(String p1, String p2) {
        // check whether or not the input players are valid
        if (!keys.containsKey(p1) || !keys.containsKey(p2)) {
            return "Input player isn't valid!";
        }
        // initialize queue, distance array, parent array for bfs
        int player1ID = keys.get(p1);
        int player2ID = keys.get(p2);
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(player1ID);

        int[] dist = new int[4550];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[player1ID] = 0;

        ArrayList<ArrayList<Integer>> parent = new ArrayList<>();
        for (int i = 0; i < 4550; i++) {
            parent.add(new ArrayList<>());
        }

        parent.get(player1ID).clear();
        parent.get(player1ID).add(-1);
        
        // bfs to get all shortest paths
        while(!q.isEmpty()) {
            int node = q.remove();

            HashMap<Integer, Integer> adjMap = adj.get(node);
            for (Map.Entry<Integer, Integer> entry: adjMap.entrySet()) {
                int key = entry.getKey();
                if (dist[key] > dist[node] + 1) {
                    dist[key] = dist[node] + 1;
                    q.add(key);
                    parent.get(key).clear();
                    parent.get(key).add(node);
                } else if (dist[key] == dist[node] + 1) {
                    parent.get(key).add(node);
                }
            }
        }
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        
        // traverse parent array to find all shortest paths
        findPaths(paths, path, parent, 4550, player2ID);
        String res = "";
        
        // print out all shortest paths
        for (ArrayList<Integer> v: paths) {
            Collections.reverse(v);
            for (int u: v) {
                String codedName = values.get(u);
                String rawName = names.get(codedName);
                if (res.equals("") || res.endsWith("\n")) {
                    res += rawName;
                } else {
                    res += "-> " + rawName;
                }
                
            }
            res += "\n";
        }

        return res;
    }
    
    public static String question2(String p1, String p2) {
        // check whether or not the input players are valid
        return "Question 2 Called";
    }
    
    public static String question3(String p) {
        if (keys.get(p) != null) {
            String toReturn = "";
            int playerId = keys.get(p);
            HashMap<Integer, Integer> teammates = adj.get(playerId);

            // find number of teammates
            toReturn += "Number of teammates: " + teammates.size() + "\n"; 

            // find longest duration teammates
            String longestMate = "";
            int longestYears = 0; 
            for(Map.Entry<Integer, Integer> teammate: teammates.entrySet()) {
                int years = (int)teammate.getValue();
                if (years > longestYears) {
                    longestYears = years;
                    int currMate = (int)teammate.getKey();
                    longestMate = names.get(values.get(currMate));
                }
            }
            toReturn += "Longest-Duration teammate: " + longestMate + " (" + longestYears + ")\n";

            // find longest duration mutual teammates
            String mate1 = "";
            String mate2 = "";
            int matesYears = 0;
            for(Map.Entry<Integer, Integer> teammate: teammates.entrySet()) {
                int teammateId = (int)teammate.getKey();
                HashMap<Integer, Integer> teammates2 = adj.get(teammateId);
                for (Map.Entry<Integer, Integer> teammate2: teammates2.entrySet()) {
                    int teammate2Id = (int)teammate2.getKey();
                    int years = (int)teammate.getValue();
                    if (teammate2Id != playerId && years > matesYears) {
                        matesYears = years;
                        mate1 = values.get(teammateId);
                        mate2 = values.get(teammate2Id);
                    }
                }
            }
            toReturn += "Longest-Duration Mutual Teammates: " + mate1 + " & " + mate2 + " (" + matesYears + ")\n";

            // find clustering coefficient
            int mutualCounter = 0;
            for(Map.Entry<Integer, Integer> teammate: teammates.entrySet()) {
                int teammateId = (int)teammate.getKey();
                HashMap<Integer, Integer> teammates2 = adj.get(teammateId);
                for (Map.Entry<Integer, Integer> teammate2: teammates.entrySet()) {
                    if (teammates2.get((int)teammate2.getKey()) != null) {
                        mutualCounter++;
                    }
                }
            }
            int n = teammates.size();
            toReturn += "Clustering Coefficient: " + (0.5*mutualCounter/(n*((n-1)/2 - 1)));
            return toReturn;
        }
        return "That player is not in our database";
    }
    
    public static String question4() {
        // no checks necessary
        return "Question 4 Called";
    }
}
