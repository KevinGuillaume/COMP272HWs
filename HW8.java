import java.util.*;
import java.security.*;
import java.io.*;
public class HW8
{
    Graph g;
    ArrayList<Integer> vertexSet;
    public HW8(String fileName){
    
        g= fillGraph(fileName);
        vertexSet = new ArrayList<Integer>(g.numVertex);
    
    }
    
    //Constructor helper to fill the data accordingly
    public static Graph fillGraph(String file){
        
        if(file.equals("Email-Enron.txt")){
        Graph g = new Graph(36692);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] xy = line.split("\t");
                
                int v1 = Integer.valueOf(xy[0]);
                int v2 = Integer.valueOf(xy[1]);
                
                g.addEdge(v1,v2);
                
                // read next line
               
               line = reader.readLine();
                
            }
            reader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }  
    
        return g;
        }
        else if(file.equals("Graph.txt")){
            Graph g = new Graph(1000000);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] xy = line.split(",");
                
                int v1 = Integer.valueOf(xy[0]);
                int v2 = Integer.valueOf(xy[1]);
                
                g.addEdge(v1,v2);
                
                // read next line
               
               line = reader.readLine();
                
            }
            reader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }  
    
        return g;
        }
        else{
            System.out.println("File Not Found");
            return null;
        }
    }
    //Question 1 -- Algorithm DFS
    public int dfs(){
        int connectedComponents = 0;
        
        boolean[] checked = new boolean[g.numVertex];
        for(int i = 0; i <checked.length;i++){
            checked[i]=false; //initialize spots as false
        }
        
        
        for(int i =0;i <g.numVertex; i++){
            if(checked[i] == false){ //so if not visited do recursion
                dfsHelper(i,checked);
                connectedComponents++;
            }
        }
        
        return connectedComponents;
    }
    public void dfsHelper(int v,boolean[] arr){
        arr[v] = true;
        for(int j = 0; j < g.getNeighbors(v).size();j++){
            if(arr[g.getNeighbors(v).get(j)])
                dfsHelper(g.getNeighbors(v).get(j),arr);
        
        }
    
    
    }
    
   
    
    //Question 1 -- Algorithm BFS
    public int bfs(){
        int connectedComponents = 0;
        boolean[] checked = new boolean[g.numVertex];
        
        
        Deque<Integer> q = new ArrayDeque<Integer>();
        for(int i = 0; i <checked.length;i++){
            checked[i]=false; //initialize spots as false
        }
        
        for(int j =0;j < g.numVertex;j++){
            if(checked[j] == false){
                q.add(j);
                checked[j] = true;
                //means we have a new connected component
                connectedComponents++;
                
            
            }
            while(q.size() !=0){
            int vertex = q.poll();
            
                for(int i = 0; i < g.getNeighbors(vertex).size();i++){
                    int temp = g.getNeighbors(vertex).get(i);
                    if(checked[temp] == false){
                        checked[temp] = true;
                        q.add(temp);
                        
                    }
                
            
                }

            }
        
        }
        
        return connectedComponents;
    }
    
    //Question 1 -- Algorithm 2
    public int countComponents(){
        int n = g.numVertex;
        int[] ids = new int[n];
        
        for(int i=0; i < ids.length;i++){
            ids[i] = i;
        
        }
        
        for(int j =0;j<g.numVertex;j++){
        ArrayList<Integer> neighbors = g.getNeighbors(j);
        for(int vertex: neighbors){
            union(j,vertex,ids);
        
        
        }
        }
        HashSet<Integer> set = new HashSet<>();
        for(int i =0;i<ids.length;i++){
            set.add(find(i,ids));
            
            
        }
    
        return set.size();
    }
    public void union(int u, int v, int[] ids){
        int parent1 = find(u,ids);
        int parent2 = find(v,ids);
        ids[parent1] = parent2;
    
    }
    
    public int find(int v, int[] ids){
        if(ids[v] != v){
            ids[v] = find(ids[v],ids);
        }
        
        return ids[v];
    }
    
    //Question 2
    public int bfsMax(){
        int connectedComponents = 0;
        boolean[] checked = new boolean[g.numVertex];
        int max = 0;
        int tempMax = 0;
        
        Deque<Integer> q = new ArrayDeque<Integer>();
        for(int i = 0; i <checked.length;i++){
            checked[i]=false; //initialize spots as false
        }
        
        for(int j =0;j < g.numVertex;j++){
            if(checked[j] == false){
                q.add(j);
                checked[j] = true;
                
                if(tempMax >= max){
                    max = tempMax;
                }
                tempMax = 1;
                
            
            }
            while(q.size() !=0){
                int vertex = q.poll();
                for(int i = 0; i < g.getNeighbors(vertex).size();i++){
                    int temp = g.getNeighbors(vertex).get(i);
                    if(checked[temp] == false){
                        checked[temp] = true;
                        q.add(temp);  
                        tempMax++; //increasing the size of the connected components connection
                    }
                }
                
            }
        
        }
        
        return max;
    }
    
    //Question 3
    public int findTrees(){
        int treeCount = 0;
        boolean isTree = false;
        HashSet<Integer> passed = new HashSet<Integer>();
        boolean[] checked = new boolean[g.numVertex];
        for(int i = 0; i <checked.length;i++){
            checked[i]=false; //initialize spots as false
        }
        Deque<Integer> q = new ArrayDeque<Integer>();
        
        for(int i = 0;i< g.numVertex;i++){
            if(checked[i] == false){
                q.add(i);
                checked[i] = true;
                //Need to see if a cycle is found
                if(isTree) treeCount++;
                //empty passed verticies of the cycle, then start the new one
                isTree = true;
                passed.clear();
                passed.add(i);
            }
            while(q.size() !=0){
                int vertex = q.poll();
                
                for(int j = 0; j < g.getNeighbors(vertex).size();j++){
                    int subVertex = g.getNeighbors(vertex).get(j);
                    if(passed.contains(subVertex)) isTree = false;
                    
                    if(checked[subVertex] == false){
                        
                        passed.add(subVertex);
                        checked[subVertex] = true;
                        q.add(subVertex);
                    }

                }
            }
        }
        
        return treeCount;
    }
    
    public static void main(String[] args){
        HW8 test = new HW8("Email-Enron.txt");
        HW8 test2 = new HW8("Graph.txt");
        //System.out.println(test.dfs());
        
        
        System.out.println("Email-Enron.txt");
        System.out.println("Components Using Union/Find: " + test.countComponents());
        System.out.println("Components Using BFS: " + test.bfs());
        System.out.println("Largest Component: " + test.bfsMax());
        System.out.println("# of Trees: " + test.findTrees());
        System.out.println("----------");
        System.out.println("Graph.txt");
        System.out.println("Components Using Union/Find: " + test2.countComponents());
        System.out.println("Components Using BFS: " + test2.bfs());
        System.out.println("Largest Component: " + test2.bfsMax());
        System.out.println("# of Trees: " + test2.findTrees());
        
    
    }
}
