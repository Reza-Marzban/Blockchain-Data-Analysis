/*
Reza Marzban   
*/

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.math.BigDecimal;

class Graph// Directed Graph
{
	HashMap<String, Node> nodeList = new HashMap<String, Node>();//matches the public key to a node
	public Graph(HashSet<String> nodesId)
	{
		for(String id : nodesId)
		{
			Node t =new Node(id);
			nodeList.put(id,t);
		}
	}
	public static class Node
	{
		public String id;
		//adjacent keep track of the node's edges and their weights
		HashMap<String, BigDecimal> adjacent = new HashMap<String, BigDecimal>();
		public Node(String publicKey)
		{id=publicKey;}
		public String parentId=null;
	}
	public Node getNode(String id)
	{
		return nodeList.get(id);
	}
	public void addEdge(String source,String destination, BigDecimal Weight)
	{
		Node s=getNode(source);
		Node d1=getNode(destination);
		if(s.adjacent.containsKey(d1))
		{
			BigDecimal oldWeight=s.adjacent.get(d1);
			BigDecimal newWeight=oldWeight.add(Weight);
			s.adjacent.replace(d1.id,newWeight);
		}
		else{s.adjacent.put(d1.id,Weight);}
	}
	//for DFS usage:
	public HashMap<String, Integer> color = new HashMap<String, Integer>();//0->White(not Discovered), 1->gray(discovered), 2->Black(Finished), 3 & 4 reserved for SCC computation:3 is Blue, 4 is Red.
	public HashMap<Integer, String> discoveryTime = new HashMap<Integer, String>();
	public HashMap<Integer, String[]> FinishingTime = new HashMap<Integer, String[]>();
	// for SCC computation
	public HashMap<String, Integer> SCC = new HashMap<String, Integer>();
	//for Dijikstra usage:
	public HashMap<String, BigDecimal> d = new HashMap<String, BigDecimal>();//the shortest path cost, Default=null means inaccessible
	public HashMap<String, String> pi = new HashMap<String, String>();//predecessor in shortest path, Default="NIL"
}

class Graph2//Undirected Graph
{
	HashMap<String, Node> nodeList = new HashMap<String, Node>();//matches the public key to a node
	public Graph2(HashSet<String> nodesId)
	{
		for(String id : nodesId)
		{
			Node t =new Node(id);
			nodeList.put(id,t);
		}
	}
	public static class Node
	{
		public String id;
		//adjacent keep track of the node's edges and their weights
		HashMap<String, BigDecimal> adjacent = new HashMap<String, BigDecimal>();
		public Node(String publicKey)
		{id=publicKey;}
		public String parentId=null;
	}
	public Node getNode(String id)
	{
		return nodeList.get(id);
	}
	public void addEdge(String source,String destination, BigDecimal Weight)
	{
		Node s=getNode(source);
		Node d1=getNode(destination);
		if(s.adjacent.containsKey(d1))
		{
			BigDecimal oldWeight=s.adjacent.get(d1);
			BigDecimal newWeight=oldWeight.add(Weight);
			s.adjacent.replace(d1.id,newWeight);
		}
		else{s.adjacent.put(d1.id,Weight);}
		
		if(d1.adjacent.containsKey(s))
		{
			BigDecimal Two=new BigDecimal("2");
			BigDecimal oldWeight=d1.adjacent.get(s);
			BigDecimal newWeight=oldWeight.add(Weight);
			newWeight.divide(Two,0);
			s.adjacent.replace(s.id,newWeight);
		}
		else{d1.adjacent.put(s.id,Weight);}
	}
	
	//for Prim
	public HashMap<String, BigDecimal> key = new HashMap<String, BigDecimal>();//the cost in Minimum Spanning Tree (MST), Default=inf
	public HashMap<String, String> pi2 = new HashMap<String, String>();//predecessor in Minimum Spanning Tree (MST), Default="NIL"
}

public class Pgm2
{
	//read CSV contents from file
	private static ArrayList<String[]> csvreader(String fileName) 
	{ 
		int ci=0;
		ArrayList<String[]> contents = new ArrayList<String[]>();
		try (FileReader file = new FileReader(fileName)) {
		BufferedReader br = new BufferedReader(file);
		String line = br.readLine();
		while (line != null) { 
		if(line == "true"){break;}
		String[] columns = line.split(" ");
		line = br.readLine();
		String[] FTgas = new String[3];
		if(columns.length<2){continue;}
		if(!columns[5].chars().allMatch( Character::isDigit )){continue;}
		FTgas[0]= columns[2];
		FTgas[1]= columns[3];
		FTgas[2]= columns[5];
		contents.add(ci,FTgas);
		ci++;} } 
		catch (IOException ioe) { ioe.printStackTrace(); } 
		return contents; 
	}
	// print (num) rows from the contents of table, if (num) is equal to zero, it will print all of the data.
	private static ArrayList<String[]> transactionsfrom(ArrayList<String[]> data, String from) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=2){continue;}
			String temp= data.get(i)[0];
			if(from.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	private static ArrayList<String[]> transactionsto(ArrayList<String[]> data, String to) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=3){continue;}
			String temp= data.get(i)[1];
			if(to.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	//#1
	//Initiate the graph with just nodes.
	private static Graph createGraphnodes(ArrayList<String[]> transactions) 
	{
		HashSet<String> nodes =new HashSet<String>();
		int rows=transactions.size();
		for(int i=0; i<rows; i++)
		{
			if(transactions.get(i).length<2){continue;}
			String from= transactions.get(i)[0];
			String to= transactions.get(i)[1];
			if(!from.contains("null")){
				if(!nodes.contains(from))
					nodes.add(from);}
			if(!to.contains("null")){
				if(!nodes.contains(to))
					nodes.add(to);}
		}
		Graph g =new Graph(nodes);
		return g;
	}
	//Add edges to the initiated graph.
	private static Graph addEdgesToGraph(Graph g, ArrayList<String[]> transactions) 
	{
		int rows=transactions.size();
			for(int i=0; i<rows; i++)
			{
				if(transactions.get(i).length<3){continue;}
				String from=transactions.get(i)[0];
				String to=transactions.get(i)[1];
				if(to.contains("null")){continue;}
				if(from.contains("null")){continue;}
				BigDecimal weight  = new BigDecimal(transactions.get(i)[2]);
				Graph.Node N=g.getNode(from);
				if(N.adjacent.containsKey(to))
				{
					BigDecimal oldWeight=N.adjacent.get(to);
					BigDecimal newWeight=oldWeight.add(weight);
					N.adjacent.replace(to,oldWeight,newWeight);
				}
				else{N.adjacent.put(to,weight);}
				
			}	
		return g;
	}
	//print the Final Graph as an adjacency list
	//two inputs, the graph and the number of nodes to be printed
	private static void printGraph(Graph g, int i) 
	{
		int counter =0;
		System.out.println(" ");
		System.out.println("Printing the Graph:");
		System.out.println("Template:");
		System.out.println("Counter. Node Public Key  -->  connected node (weight), connected node (weight), ...");
		System.out.println("");
		for (Graph.Node node : g.nodeList.values()) {
			counter++;
			if(counter>i)
				break;
			String id= node.id;
			if(id=="")
			{
				counter--;
				continue;
			}
			if(node.adjacent.size()==0)
			{
				counter--;
				continue;
			}
			System.out.print(counter);
			System.out.print(". ");
			System.out.print(id);
			System.out.print(" --> ");
			if(node.adjacent.size()==0)
				System.out.print("NONE");
			for (Map.Entry<String, BigDecimal> entry : node.adjacent.entrySet()) {
			if(entry.getKey()==null||entry.getKey().contains("null")||entry.getKey()==null){continue;}
			System.out.print(entry.getKey() + " (" + entry.getValue()+"), ");
			}		
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	//Depth_first Search - has one input, the graph.
	public static HashMap<Integer, String[]> DFS(Graph g) 
	{
		int time=0;
		g.color = new HashMap<String, Integer>();//0->White(not Discovered), 1->gray(discovered), 2->Black(Finished)
		g.discoveryTime = new HashMap<Integer, String>();
		g.FinishingTime = new HashMap<Integer, String[]>();
		for (String nodeId : g.nodeList.keySet()) {
			g.color.put(nodeId,0);//white (undiscovered)
		}
		for (String nodeId : g.nodeList.keySet()) {
			if(g.color.get(nodeId)==0)
				{g.getNode(nodeId).parentId=null;time=DFSVisit(nodeId,time,g);}
		}
		return g.FinishingTime;
	}
	public static int DFSVisit(String nodeId,int time, Graph g) 
	{
		g.color.put(nodeId,1);//gray (discovered)
		time++;
		g.discoveryTime.put(time,nodeId);
		Graph.Node active=g.getNode(nodeId);
		for (String NID : active.adjacent.keySet()) {
			Graph.Node neighbornode=g.getNode(NID);
			if(g.color.get(neighbornode.id)==0)
				{neighbornode.parentId=nodeId;time=DFSVisit(neighbornode.id,time,g);}
		}
		g.color.put(nodeId,2);//black (finished)
		time++;
		String[] t=new String[2];t[0]=nodeId;t[1]=g.getNode(nodeId).parentId;
		g.FinishingTime.put(time,t);
		return time;
	}
	
	//#2
	//SCC
	//Compute and return the Transpose of Graph g.
	private static Graph graphTranspose(Graph g) 
	{
		HashSet<String> nodes =new HashSet<String>(g.nodeList.keySet());
		Graph gT =new Graph(nodes);
		for (Graph.Node node : g.nodeList.values()) {
			for (Map.Entry<String, BigDecimal>  entry : node.adjacent.entrySet()) {
				String adjacentnodeId = entry.getKey();
				BigDecimal weight = entry.getValue();
				Graph.Node adjacentNode=g.getNode(adjacentnodeId);
				gT.addEdge(adjacentnodeId,node.id,weight);
			}
		}
		return gT;
	}
	//Strong Connected Component - has one input, the graph. L is the list of node id sorted on DFS finishing time decreasing.
	public static HashMap<String, Integer> scc(Graph g,ArrayList<String> L) 
	{
		g.SCC = new HashMap<String, Integer>();
		for (String nodeId : g.nodeList.keySet()) {
			g.SCC.put(nodeId,0);//SCC[v] is the SCC identifier for v.
			g.color.put(nodeId,2);//black
		}
		int c=0; //c is the identifier for the current strongly connected component
		
		for (String nodeId : L) {
			if(g.color.get(nodeId)==2)
				{c++;c=SCCVisit(nodeId,c,g);}
		}
		return g.SCC;
	}
	public static int SCCVisit(String nodeId,int c, Graph g) 
	{
		g.color.put(nodeId,3);//Blue
		g.SCC.put(nodeId,c);
		Graph.Node active=g.getNode(nodeId);
		for (String NID : active.adjacent.keySet()) {
			Graph.Node neighbornode=g.getNode(NID);
			if(g.color.get(neighbornode.id)==2)
				{c=SCCVisit(neighbornode.id,c,g);}
		}
		g.color.put(nodeId,4);//Red
		return c;
	}
	//MST
	//convert a Directed Graph g to an undirected one and return it. if there are two edges between two nodes, it will save the average of two.
	private static Graph2 UnidirectionGraphConversion(Graph g) 
	{ 
		HashSet<String> nodesID = new HashSet<String>(g.nodeList.keySet());
		Graph2 g2= new Graph2(nodesID);
		for (Graph.Node node : g.nodeList.values()) {
			if(node.adjacent.size()==0)
				continue;
			for (Map.Entry<String, BigDecimal> entry : node.adjacent.entrySet()) {
				String s=node.id;
				String d=entry.getKey();
				BigDecimal w=entry.getValue();
				BigDecimal finalw=new BigDecimal(w.toString());
				Graph.Node destnode=g.getNode(d);
				if(destnode.adjacent.containsKey(s)){
					BigDecimal w2=destnode.adjacent.get(s);
					BigDecimal Two=new BigDecimal("2");
					finalw.add(w2);
					finalw.divide(Two,0);
				}
				g2.addEdge(s,d,finalw);
			}
		}
		return g2;
	}
	//Create the DFS Forest (undirected) according to DFS finishing time. return an arraylist of undirected graphs.
	private static ArrayList<Graph2> DFSForestCreation(ArrayList<String[]> L2, Graph g) 
	{ 
		Collections.reverse(L2);
		ArrayList<Graph2> DFSForest = new ArrayList<Graph2>();//keep DFS Trees
		HashSet<String> Nodes = new HashSet<String>();
		for (String[] t : L2) {
			String nodeid=t[0];
			String parentid=t[1];
			if(parentid==null){
				if(Nodes.size()>0){
					if(Nodes.size()>354000){Nodes.clear();continue;}
					Graph currentTree= new Graph(Nodes);
					//add all of the edges that the destination is inside the tree. //check , Only tree edges?
					for (String nodeId : Nodes) {
						Graph.Node node =g.getNode(nodeId);
						if(node.adjacent.size()==0){continue;}
						for (Map.Entry<String, BigDecimal> entry : node.adjacent.entrySet()) {
							String neighbornodeID=entry.getKey();
							if(Nodes.contains(neighbornodeID)){
							BigDecimal weight=entry.getValue();
							currentTree.addEdge(nodeId,neighbornodeID,weight);
							}
						}
					}
					DFSForest.add(UnidirectionGraphConversion(currentTree));
				}
				Nodes.clear();continue;
			}
			Nodes.add(nodeid);Nodes.add(parentid);
		}
		return DFSForest;
	}	
	//PRIM's Algorithm. Take two inputs: a undirected graph and the root node id.
	private static HashMap<String, String> PRIM(Graph2 g, String s) 
	{
		g.key.clear();
		g.pi2.clear();
		HashSet<String> Q =new HashSet<String>();
		BigDecimal inf=new BigDecimal("9900000000000000000000000000000000000000000000000000000000000000000000");
		for ( String nodeID : g.nodeList.keySet() ) {
			g.key.put(nodeID,inf);
			g.pi2.put(nodeID,"NIL");
			Q.add(nodeID);
		}
		BigDecimal Zero= new BigDecimal("0");
		g.key.put(s,Zero);
		
		
		BigDecimal min=new BigDecimal("90000000000000000000000000000000000000000000000000000000000000000000");
		String minID=null;
		while(Q.size()>0){
			minID=null;
			min=new BigDecimal("90000000000000000000000000000000000000000000000000000000000000000000");
			for (String NID : Q) {
				BigDecimal temp=g.key.get(NID);
				if(temp==inf){continue;}
				if(temp.compareTo(min) < 0){
					min=temp;minID=NID;}
			}
			Q.remove(minID);
			Graph2.Node u=g.getNode(minID);
			if(u.adjacent.size()==0){continue;}
			for (Map.Entry<String, BigDecimal> entry : u.adjacent.entrySet()) {
				String v=entry.getKey();
				BigDecimal w=entry.getValue();
				if(Q.contains(v)){
					if(w.compareTo(g.key.get(v)) < 0){
						g.key.put(v,w);
						g.pi2.put(v,u.id);
					}
				}
			}
		}
		return g.pi2;
	}
	
	//PRIM's Algorithm. Take two inputs: the graph and the root node id.
	private static void MSTOutput(ArrayList<String[]> L2, Graph g) 
	{
		ArrayList<Graph2> DFSForest= DFSForestCreation(L2, g);
		Collections.sort(DFSForest, new Comparator<Graph2>() {
			@Override
			public int compare(Graph2 o1, Graph2 o2) {
				return Double.compare(o1.nodeList.size(), o2.nodeList.size());
			}
		});
		System.out.println("Printing the MST on each Tree from lowest node count to the highest:");
		System.out.println("Format:\tNode - Predecessor");
		for(Graph2 g2: DFSForest){
			System.out.println("**********");
			System.out.print("Node Count: ");
			System.out.println(g2.nodeList.size());
			String s= g2.nodeList.keySet().stream().findFirst().get();			
			HashMap<String, String> MST=PRIM(g2, s);
			for (Map.Entry<String, String> entry : MST.entrySet()) {
				String v=entry.getKey();
				String u=entry.getValue();
				System.out.print("\t");
				System.out.print(v);
				System.out.print(" - ");
				System.out.println(u);
			}
			
		}
		
	}
	// function to sort adjacency list by weights increasing 
	public static HashMap<String, BigDecimal> sortbyweight(HashMap<String, BigDecimal> hm) 
	{ 
		// Create a list from elements of HashMap 
		List<Map.Entry<String, BigDecimal> > list = 
			new LinkedList<Map.Entry<String, BigDecimal> >(hm.entrySet()); 

		// Sort the list 
		Collections.sort(list, new Comparator<Map.Entry<String, BigDecimal> >() { 
			public int compare(Map.Entry<String, BigDecimal> o1, 
							Map.Entry<String, BigDecimal> o2) 
			{ 
				return (o1.getValue()).compareTo(o2.getValue()); 
			} 
		}); 
		
		// put data from sorted list to hashmap 
		HashMap<String, BigDecimal> temp = new LinkedHashMap<String, BigDecimal>(); 
		for (Map.Entry<String, BigDecimal> aa : list) { 
			temp.put(aa.getKey(), aa.getValue()); 
		} 
		return temp; 
	} 
	
	//#3
	//init_Single_Source has two inputs: the graph and the source node ID. it initiate the d and pi for Dijkstra's algorithm.
	private static void init_Single_Source(Graph g, String S) 
	{
		g.d.clear();
		g.pi.clear();
		for ( String nodeID : g.nodeList.keySet() ) {
			g.d.put(nodeID,null);
			g.pi.put(nodeID,"NIL");
		}
		BigDecimal Zero= new BigDecimal("0");
		g.d.put(S,Zero);
	}
	//relax function for Dijkstra's algorithm.
	private static void relax(String u, String v, Graph g) 
	{
		BigDecimal w=g.getNode(u).adjacent.get(v);
		if(g.d.get(u)==null){
			return;
		}
		BigDecimal added= w.add(g.d.get(u));
		if(g.d.get(v)==null){
			g.d.put(v,added);
			g.pi.put(v,u);
		}
		else if((g.d.get(v).compareTo(added) > 0)){
			g.d.put(v,added);
			g.pi.put(v,u);
		}
	}
	//Dijkstra's Algorithm. Take two inputs: the graph and the source node id.
	private static void Dijkstra(Graph g, String s) 
	{
		init_Single_Source(g, s);
		HashSet<String> S =new HashSet<String>();
		HashSet<String> Q =new HashSet<String>();
		for ( String nodeID : g.nodeList.keySet() ) {
			Q.add(nodeID);
		}
		BigDecimal min=new BigDecimal("90000000000000000000000000000000000000000000000000000000000000000000");
		String minID=null;
		String nullID=null;
		int nullcounter= 0;
		while(Q.size()>0){
			minID=null;
			nullID=null;
			nullcounter= 0;
			min=new BigDecimal("90000000000000000000000000000000000000000000000000000000000000000000");
			for (String NID : Q) {
				BigDecimal temp=g.d.get(NID);
				if(temp==null){nullcounter++;nullID=NID;continue;}
				if(temp.compareTo(min) < 0){
					min=temp;minID=NID;}
			}
			if(nullcounter==Q.size()){break;}
			if(minID==null){minID=nullID;}
			Q.remove(minID);
			S.add(minID);
			Graph.Node u=g.getNode(minID);
			for(String v : u.adjacent.keySet())
			{
				relax(u.id,v,g);
			}
		}
	}
	private static void DijkstraOutput(Graph g) 
	{
		System.out.println("Printing Dijikstra's Algorithm Output");
		System.out.print("Format:\t");
		System.out.println("Node ID \t Shortest Path cost from the source \t Predecessor Node");
		System.out.println("");
		for (String ID : g.nodeList.keySet()) {
			BigDecimal cost=g.d.get(ID);
			if(cost==null){continue;}
			String pre=g.pi.get(ID);
			System.out.print(ID);
			System.out.print("\t");
			System.out.print(cost);
			System.out.print("\t");
			System.out.print(pre);
			System.out.println();
		}
	}
	//Tests all of functions and reports all of the query results and their running time.
	private static void reporting(ArrayList<String[]> transactions) 
	{
		long startTime;
		long endTime;
		//preprocessing	
		System.out.println("");
		System.out.println("1.Build a graph from the list of from's and to's of Transactions:");
		System.out.println("The graph is built on the complete data set of Transactions (2GB). but only 100 nodes are shown here by using adjacency lists to save space.");
		System.out.println("Graph is Loading!");
		startTime = System.currentTimeMillis();
		Graph g= createGraphnodes(transactions);
		System.out.println("Graph's nodes have been created, The edges are loading, Please stand by!");
		g=addEdgesToGraph(g,transactions);
		System.out.println("Graph Loaded successfully.");
		endTime = System.currentTimeMillis();
		System.out.print("**Number of Nodes in the Graph: ");
		System.out.println(g.nodeList.size());
		System.out.print("running time of Building a graph from the list of from's and to's of Transaction and tokens, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("-----------------------------");
		printGraph(g,50);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#1
		//DFS Test
		System.out.println("Calling DFS function on the created graph:");
		System.out.println("Printing the node Id's accordion to DFS Finishing Time Decreasing:");
		System.out.println(" ");
		System.out.println("Format: Finishing Time - Node Id - Predecessor Id");
		startTime = System.currentTimeMillis();
		DFS(g);
		ArrayList<String> L = new ArrayList<String>();//save nodeID based on finishing time decreasing for SCC
		ArrayList<String[]> L2 = new ArrayList<String[]>();//save nodeID & parent ID based on finishing time decreasing for MST
		HashMap<Integer, String[]> finishingTime =g.FinishingTime;
		ArrayList<Integer> keys = new ArrayList<>(finishingTime.keySet());
		Collections.sort(keys);
		Collections.reverse(keys);
		for (Integer num : keys) { 		      
        String[] t=finishingTime.get(num);
		String nodeid=t[0];
		String parentid=t[1];
		System.out.print(num);
		System.out.print(" - ");
		System.out.print(nodeid);
		System.out.print(" - ");
		System.out.println(parentid);
		L.add(nodeid);
		L2.add(t);
		}
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of DFS, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		
		
		//#2
		System.out.println("2-");
		//SCC
		System.out.println("   SCC");
		System.out.println("Calculating The Transpose of the Graph. Please Wait!");
		startTime = System.currentTimeMillis();
		Graph gT= graphTranspose(g);
		endTime = System.currentTimeMillis();
		System.out.print("running time of Transposing the Graph, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("");
		System.out.println("Identifying SCC's off of the DFS forest:");
		startTime = System.currentTimeMillis();
		HashMap<String, Integer> sccresult=scc(gT,L); 
		endTime = System.currentTimeMillis();
		System.out.println("Printing SCC's that has at least 2 nodes (all of the nodes are 1 node SCC):");
		int last= -1;
		int counter=0;
		int sccCounter=0;
		ArrayList<String> sccOutput = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : sccresult.entrySet()) {
			String ID = entry.getKey();
			int c = entry.getValue();
			counter++;
			if(last!=c){
				if(sccOutput.size()<=1){sccOutput= new ArrayList<String>();}
				else{
					sccCounter++;
					System.out.println(" ");
					System.out.print("\tSCC number: ");System.out.print(sccCounter);System.out.println(": ");
					for (String nodeId : sccOutput) {System.out.print("\t  ");System.out.println(nodeId);}
					sccOutput= new ArrayList<String>();
				}
				counter=0;}
			sccOutput.add(ID);
			last=c;
		}
		System.out.println("");
		System.out.print("running time of Identifying SCC's off of the DFS forest, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("-----------------------------");
		//MST
		System.out.println("");
		System.out.println("   MST");
		startTime = System.currentTimeMillis();
		MSTOutput(L2, g);
		endTime = System.currentTimeMillis();
		System.out.println("");
		System.out.println("-----------------------------");
		System.out.print("running time of Identifying MST's off of each DFS trees, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		
		
		//#3
		System.out.println("");
		System.out.println("3.Dijikstra's algorithm on a single source node:");
		String s="0x82fb6037b1d7122c30b2873de32b94a62928972f";
		System.out.print("Test source node ID: ");
		System.out.println(s);
		startTime = System.currentTimeMillis();
		Dijkstra(g,s);
		endTime = System.currentTimeMillis();
		DijkstraOutput(g);
		System.out.print("running time of Dijikstra's algorithm, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		
	}
	
	public static void main(String [] args)
	{
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		System.out.println();
		System.out.println("Reza Marzban");
		System.out.println("The following functions are done on the whole data provided for 6 month period (On the whole 2 GB data).");
		System.out.println("-----------------------------");
		System.out.println("Loading Data into memory. Please wait!");
		startTime = System.currentTimeMillis();
		ArrayList<String[]> transactions = csvreader("transactions.txt");
		endTime = System.currentTimeMillis();
		System.out.print("data loading time(in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("Data loaded successfully!");
		System.out.println("-----------------------------");
		System.out.println("Testing the functions and printing their results:");
		//reports all of the query results and their running time.
		reporting(transactions);
		endTime = System.currentTimeMillis();
		System.out.print("The Total Program runtime on the whole data (6 month data) with 3 queries (in seconds): ");
		System.out.println((endTime-startTime)/1000);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println("End of the OutPut");
		System.out.println("");
	}
}

