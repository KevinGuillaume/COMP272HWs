import java.util.*;
import java.io.*;
public class Homework9
{
    static List<WeightedEdge> weightedEdgeSet;
    static int numVertex;
    static int numEdges;
    static int count;
    static int[] findSet;
    static int[] height;
    
    public static void readAndStoreGraph(String fileName1,String fileName2) {     
        try{
            Scanner sc =new Scanner(new File(fileName1));
            Scanner sc2 = new Scanner(new File(fileName2));
            weightedEdgeSet = new ArrayList<>();
            String s;
            String w;
            int i =0;
            int maxV=0;

            while (sc.hasNextLine() && sc2.hasNextLine()) {
                s = sc.nextLine();
                w = sc2.nextLine();
           
                //File 1 is split by tabs
                String[] line= s.split("\t");
           
                //verticies from file1
                int v1=  Integer.parseInt(line[0]);
                int v2=  Integer.parseInt(line[1]);
                //weight of line
                int weight = Integer.parseInt(w);
           
                int p= Math.max(v1,v2);
                if (p>maxV) maxV=p;
           
                weightedEdgeSet.add(new WeightedEdge(v1,v2,weight));
                i++;
           
            }
            numEdges = i;
            numVertex = maxV + 1;
            
            height = new int[numVertex];
            //To Fill findSet[i] = i 
            findSet = new int[numVertex];
            
            for(int j=0; j<numVertex; j++){
                findSet[j]=j;
                //System.out.println("Filled pos: " + j + "with: " + findSet[j]);
            }
        }
        catch (FileNotFoundException e) {
        }  
    }
     
    public static int find(int x) {
        int r = x;
        //get the set r is in
        //System.out.println("FOUND: " + findSet[r]);
        while(findSet[r] != r){  
            r = findSet[r];
        }
        
        int i = x;
        while(i != r){
            int j = findSet[i];
            findSet[i]=r;
            i = j;
        
        }
        return r;
    }
    
    public static void merge(int a, int b) {
        //Same size
        if(height[a] == height[b]){
            //Putting b into A's set and increasing the height at A's set
            height[a] = height[a] + 1;
            findSet[b] = a;   
        }
        //Not same size
        else{
            
            if(height[a] > height[b]) //If a is bigger make b apart of A
                findSet[b] = a;
            else //if b is bigger make 'a' a part of B
                findSet[a] = b;
        }
    }
          
    public static ArrayList<WeightedEdge> Kruskal(PriorityQueue<WeightedEdge> pq){
        PriorityQueue<WeightedEdge> q = pq;
        //MST
        ArrayList<WeightedEdge> MSTedges = new ArrayList<>();
        int size = numVertex;
        
        //To keep track of MSTedges size
        int treeEdges = 0;
        int counter = 0;
        //Since MST is n-1 edges
        while(treeEdges < size-1){
            //Get top of PQ
            WeightedEdge e = q.poll();
            //System.out.println(e);
            if(e != null){
                //System.out.println(e.v1 + " " + e.v2);
                
                int parent1 = find(e.v1);
                int parent2 = find(e.v2);
                //System.out.println(parent1 + " " + parent2);
                //not in the same set
                if(parent1 != parent2){
                    //Add edge to arraylist
                    MSTedges.add(e);
                    //now merge two vertexes into one set in findSet
                    merge(parent1,parent2);
                    //Now we're increasing count since we encountered an edge of the MST
                    treeEdges++;
                }
            }
            counter++;
        }
        count =counter;
        return MSTedges;
    }
       
    public static void main(String[] args){
        
        readAndStoreGraph("artist_edges.txt","Weights.txt");
        
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>(weightedEdgeSet);
        System.out.println("Max Vertex Label: " + numVertex);
        System.out.println("Vertex Set Size: " + numVertex);
        System.out.println("Number of Edges: " + numEdges);

        ArrayList<WeightedEdge> ans = Kruskal(pq);
        double sum = 0;
        for(WeightedEdge e: ans){
            sum += e.getWeight();
        }
        
        System.out.println("Number of Edges Considered For Finding MST: " + count);
      
        System.out.println("Minimum Weight of Spanning Tree: " + sum);
        
    }
}
