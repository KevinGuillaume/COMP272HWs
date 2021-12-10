import java.security.*;
import java.util.*;
import java.io.*;
public class DirectedGraph  {
   ArrayList<DirectedNodeList> dGraph;
   HashMap<Integer,ArrayList<Integer>> hm;
   int numVertex;
   boolean [] marked;
   int[] finishing;
   int number = 0;
   static int edges = 0;
   static int reducedEdges = 0;
   ArrayList<Integer> temp; 
   //create an arraylist to store each compoenent coming out of recursive DFT
    
    
    public DirectedGraph() {
        dGraph = new ArrayList<>();
        numVertex=0;
        
    }
    
    public void fillGraph(){
        BufferedReader reader;
        
        try{
            reader = new BufferedReader(new FileReader("Slashdot0902.txt"));
            //ignore first 4 lines
            String line = reader.readLine(); //line 0
            line = reader.readLine(); //line1
            line = reader.readLine(); //line 2
            line = reader.readLine(); //line 3
            line = reader.readLine(); //Start of vertecies
            
            while (line != null) {
                String[] xy = line.split("\t");
                
                int v1 = Integer.valueOf(xy[0]);
                int v2 = Integer.valueOf(xy[1]);
                
               this.addEdge(v1,v2);
               edges++;
                // read next line
               line = reader.readLine();
            }
            reader.close();     
        }
        catch(IOException e){
            System.out.println(e);
        }
    
    }

    public DirectedGraph(int n) {
      numVertex =n;
      hm = new HashMap<>();
      dGraph = new ArrayList<>(n);
      temp = new ArrayList<>();
      marked= new boolean[n];
      finishing = new int[n];  
    
      for (int i=0;i<numVertex;i++)
       dGraph.add(new DirectedNodeList());
    }
    
    public void addEdge(int u, int v) {
       // assume all vertices are created
       // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.
       
       if (u>=0 && u<numVertex && v>=0 && v<numVertex) { 
          if (u!=v) {
           getNeighborList(u).addToOutList(v);
           getNeighborList(v).addToInList(u);
          }
        }
        else throw new IndexOutOfBoundsException();
    }
    
    
    public DirectedNodeList getNeighborList(int u) {
        return dGraph.get(u);
    }
    
    public void printAdjacency(int u) {
       DirectedNodeList dnl = getNeighborList(u);
       System.out.println ("vertices going into "+u+"  "+dnl.getInList());
       System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
       System.out.println();
    }
    
    public void postOrderDepthFirstTraversal() {
       for (int i=0;i<numVertex;i++) 
       if (!marked[i])
           postOrderDFT (i);
       
       //resetting array for DFT
       marked = new boolean[numVertex];
    }
    
    //Question 1 --> Change to inList
    public void postOrderDFT(int v){
        
        marked[v]=true;
        //1. Change to getInList because this does the reverse
        for (Integer u:dGraph.get(v).getInList())
        if (!marked[u]) postOrderDFT(u);
        System.out.println(v);
        //number starts at 0 then increases after each recursive call
        finishing[number] = v; 
        number++;
    }
    
    //For question 3 --> Finds largest size of SCC
    public void findMaxSize(){
        int max = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : hm.entrySet()) {
            max = Math.max(entry.getValue().size(), max);
        }
        System.out.println("Biggest: " + max);
    }
    
    //Question 2: --> Using finishing numbers as label for loop
    public void depthFirstTraversal() {
       for (int i=number-1;i>=0;i--){ 
       if (!marked[i]){
           //leaders come out of here : namely i
           //initialize arraylist (empty) --> empty out 'temp'
           temp.clear();
           dFT (finishing[i]); //will recursively add v to the arraylist
           //put the arraylist in the hasmap at key i
           hm.put(i,temp);
           
        }
        }
    }
    public void dFT(int v){
        System.out.println(v);
        //add V to arrayList
        temp.add(v);
        marked[v]=true;
        
        for (Integer u:dGraph.get(v).getOutList())
        if (!marked[u]) dFT(u);
       
    }
    
    //question 4 --> Creating a reduced graph based on results from Q3
    //Loops through the HashMap and gets the list vals from different verticies and compares
    public void createReduced(HashMap<Integer,ArrayList<Integer>> hash){
        HashSet<Integer> sameEdges = new HashSet<>(); //set for comparison later on
        DirectedGraph reduced = new DirectedGraph(hash.size());//Should be same size as hashmap

        for (Map.Entry<Integer, ArrayList<Integer>> entry : hash.entrySet()) {
            ArrayList<Integer> val = entry.getValue(); //returns array in the key
            int vertex = entry.getKey();
            //loop to initially add to hashSet
            for(Integer ver: val){ 
                sameEdges.add(ver);
            }
            for (Map.Entry<Integer, ArrayList<Integer>> entry2 : hash.entrySet()){
            int vertex2 = entry2.getKey();
            ArrayList<Integer> val2 = entry2.getValue(); //returns the array in the key
            if(vertex != vertex2){ //if not the same vertex
              for(Integer v: val2){ //comparing w values already in HashSet
                if(!sameEdges.add(v)){
                    //Means this is an edge we have to create in reduced(G)
                    reduced.addEdge(v,vertex2);
                    reducedEdges++;
                 }
              }
            }
            }
        }  
    }
    
    public static void main(String[] args) {
        int n = 82168;
        int sum = 0;
        DirectedGraph dg = new DirectedGraph(n);
        dg.fillGraph();
        System.out.println("Initial Verticies & Edges (Respectively) ---------");
        System.out.println(dg.dGraph.size());
        System.out.println(edges);
        
        System.out.println("Q1:-----------------------------------");
        dg.postOrderDepthFirstTraversal();
        System.out.println("Q2:-----------------------------------");
        dg.depthFirstTraversal();
        System.out.println("Q3: -----------------------------");
        System.out.println("Size: "+ dg.hm.size());
        System.out.print("Largest SCC: ");dg.findMaxSize();
        System.out.println("Q4: ----------------------------");
        dg.createReduced(dg.hm);
        System.out.println(reducedEdges);
       
        
      
        
    }
    

   
}
