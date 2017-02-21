/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */



import java.util.*;
import java.lang.*;
import java.io.*;
 
public class Prims
{
	public static int count=0;
	private static int V =0 ;
	public Prims(int e){
		count = e;
		V = e;
		System.out.println("count is  jhysadsajk "+count);
	}
	
	public Prims(){}
    // Number of vertices in the graph
    
    int face[][] = new int[100][100];
 
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    // A utility function to print the constructed MST stored in
    // parent[]
    int [][] printMST(int parent[], int n, int graph[][])
    {
        for (int i = 1; i < V; i++){
        int x = parent[i];
        int y = i;
        face[x][y] = 1;
        face[y][x] = 1;
        }
       //tuval8 tu = new tuval8(face);
       //tu.amay();
       return face;
    }
 
    // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    int [][] primMST(int graph[][])
    {
        // Array to store constructed MST
        int parent[] = new int[V];
 
        // Key values used to pick minimum weight edge in cut
        int key[] = new int [V];
 
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[V];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
 
        // The MST will have V vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v]!=0 && mstSet[v] == false &&
                    graph[u][v] <  key[v])
                {
                    parent[v]  = u;
                    key[v] = graph[u][v];
                }
        }
 
        // print the constructed MST
        int a[][] = printMST(parent, V, graph);
        return a;
    }
 
    int [][] check (int [][] graph)
    {
        /* Let us create the following graph
           2    3
        (0)--(1)--(2)
        |    / \   |
        6| 8/   \5 |7
        | /      \ |
        (3)-------(4)
             9          */
        Prims t = new Prims();        // Print the solution
        int z[][] = t.primMST(graph);
        System.out.println("count is jsbdchjbsdjmn "+count);
        for(int i=0;i<count;i++){
     	   for(int j=0;j<count;j++){
     		   System.out.print(z[i][j]+" ");
     	   }
     	   System.out.println();
        }
        return z;
    }
}