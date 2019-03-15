/*
Reza Marzban
*/

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.math.BigDecimal;
	

class Graph
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
		HashMap<Node, BigDecimal> adjacent = new HashMap<Node, BigDecimal>();
		public Node(String publicKey)
		{id=publicKey;}
	}
	public Node getNode(String id)
	{
		return nodeList.get(id);
	}
	public void addEdge(String source,String destination, BigDecimal Weight)
	{
		Node s=getNode(source);
		Node d=getNode(destination);
		if(s.adjacent.containsKey(d))
		{
			BigDecimal oldWeight=s.adjacent.get(d);
			BigDecimal newWeight=oldWeight.add(Weight);
			s.adjacent.replace(d,newWeight);
		}
		else{s.adjacent.put(d,Weight);}
	}
	//for DFS usage:
	public HashMap<String, Integer> color = new HashMap<String, Integer>();//0->White(not Discovered), 1->gray(discovered), 2->Black(Finished)
	public HashMap<Integer, String> discoveryTime = new HashMap<Integer, String>();
	public HashMap<Integer, String> FinishingTime = new HashMap<Integer, String>();
}

public class Pgm1
{
	//read CSV contents from file
	private static ArrayList<String[]> csvreader(String fileName) 
	{ 
		ArrayList<String[]> contents = new ArrayList<String[]>();
		try (FileReader file = new FileReader(fileName)) {
		BufferedReader br = new BufferedReader(file);
		String header = br.readLine(); 
		String line = br.readLine();
		while (line != null) { 
		String[] columns = line.split(",");
		line = br.readLine();
		//get rid of repeated headers
		if((fileName== "Blocks.csv") && !(columns[0].matches("-?\\d+(\\.\\d+)?"))) continue;
		if((fileName== "Transactions.csv") && !(columns[1].matches("-?\\d+(\\.\\d+)?"))) continue;
		if((fileName== "Tokens Transfer.csv") && !(columns[1].matches("-?\\d+(\\.\\d+)?"))) continue;
		contents.add(columns);} } 
		catch (IOException ioe) { ioe.printStackTrace(); } 
		return contents; 
	}
	// print (num) rows from the contents of table, if (num) is equal to zero, it will print all of the data.
	private static void printData(ArrayList<String[]> data, int num) 
	{ 
		int rows= data.size();
		boolean printAll= false;
		if(rows<=0){
			System.out.println("Empty");
			System.out.println(" ");
			return;
		}
		if(num==0)
			printAll= true;
		int columns = data.get(0).length;
		System.out.println(" ");
		for(int i=0; i<rows; i++)
		{
			columns = data.get(i).length;
			if(num<=0 && printAll==false){break;}
			for(int j=0; j<columns; j++)
				{System.out.print(data.get(i)[j]);
				if(j!=(columns-1)){System.out.print("\t");}}
			System.out.println(" ");
			System.out.println(" ");
			num--;
		}
		System.out.println(" ");
	}
	//get two inputs, an arraylist data and an int n, then it will sum up all of the nth column's elements in data
	private static Double sumcolumn(ArrayList<String[]> data, int n) 
	{ 
		int rows= data.size();
		Double sum = 0.0;
		if(rows<=0 || !data.get(0)[n].matches("-?\\d+(\\.\\d+)?")){
			return 0.0;
		}
		for(int i=0; i<rows; i++)
		{
			if(!data.get(0)[n].matches("-?\\d+(\\.\\d+)?")){continue;}
			sum+=Double.parseDouble(data.get(i)[n]);
		}
		return sum;
	}
	//The implementation of Quick sort for three different type of input: (int, float, String)
	public static int[] quicksort(int[] main) {
	int[] index = new int[main.length];
	index[0]=0;
	for(int i=1; i<index.length; i++)
	{
		index[i]=index[i-1]+1;
	}
    quicksort(main, index, 0, index.length - 1);
	return index;
	}
	public static int[] quicksort(float[] main) {
	int[] index = new int[main.length];
	index[0]=0;
	for(int i=1; i<index.length; i++)
	{
		index[i]=index[i-1]+1;
	}
    quicksort(main, index, 0, index.length - 1);
	return index;
	}
	public static int[] quicksort(String[] main) {
	int[] index = new int[main.length];
	index[0]=0;
	for(int i=1; i<index.length; i++)
	{
		index[i]=index[i-1]+1;
	}
    quicksort(main, index, 0, index.length - 1);
	return index;
	}
	public static int[] quicksort(int[] a, int[] index, int left, int right) {
		if (right <= left) return index;
		int i = partition(a, index, left, right);
		quicksort(a, index, left, i-1);
		quicksort(a, index, i+1, right);
		return index;
	}
	public static int[] quicksort(float[] a, int[] index, int left, int right) {
		if (right <= left) return index;
		int i = partition(a, index, left, right);
		quicksort(a, index, left, i-1);
		quicksort(a, index, i+1, right);
		return index;
	}
	public static int[] quicksort(String[] a, int[] index, int left, int right) {
		if (right <= left) return index;
		int i = partition(a, index, left, right);
		quicksort(a, index, left, i-1);
		quicksort(a, index, i+1, right);
		return index;
	}
	private static int partition(int[] a, int[] index,int left, int right) {
		int i = left - 1;
		int j = right;
		while (true) {
			while (a[++i]<a[right])
				; 
			while (a[right]<a[--j]) 
				if (j == left) break; 
			if (i >= j) break;    
			swap(a, index, i, j); 
		}
		swap(a, index, i, right);   
		return i;
	}
	private static int partition(float[] a, int[] index,int left, int right) {
		int i = left - 1;
		int j = right;
		while (true) {
			while (a[++i]<a[right])
				; 
			while (a[right]<a[--j]) 
				if (j == left) break; 
			if (i >= j) break;    
			swap(a, index, i, j); 
		}
		swap(a, index, i, right);   
		return i;
	}
	private static int partition(String[] a, int[] index,int left, int right) {
		int i = left - 1;
		int j = right;
		while (true) {
			while ((a[++i].compareTo(a[right]))<0)
				; 
			while ((a[right].compareTo(a[--j]))<0) 
				if (j == left) break; 
			if (i >= j) break;    
			swap(a, index, i, j); 
		}
		swap(a, index, i, right);   
		return i;
	}
	private static void swap(int[] a, int[] index, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		int b = index[i];
		index[i] = index[j];
		index[j] = b;
	}
	private static void swap(float[] a, int[] index, int i, int j) {
		float temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		int b = index[i];
		index[i] = index[j];
		index[j] = b;
	}
	private static void swap(String[] a, int[] index, int i, int j) {
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		int b = index[i];
		index[i] = index[j];
		index[j] = b;
	}
	//sort blocks by their gas used in increasing order (Quick Sort) //#1
	private static ArrayList<String[]> blocksGasUsedSort(ArrayList<String[]> data) 
	{ 
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		int rows=data.size();
		int[] gas = new int[rows];
		int[] gasSort = new int[rows];
		for(int i=0; i<rows; i++)
		{
			String a= data.get(i)[7];
			int temp = Integer.parseInt(a);
			gas[i] = temp;
			gasSort[i] = temp;
		}
		int[] index = quicksort(gasSort);
		
		for(int i=0; i<rows; i++)
		{
			sorted.add(i,data.get(index[i]));
		}
		return sorted;
	}
	//sort blocks by their # Transaction in increasing order (Quick Sort) //#2
	private static ArrayList<String[]> blocksTransNoSort(ArrayList<String[]> blocks,ArrayList<String[]> transactions) 
	{ 
		ArrayList<String[]> transsorted = new ArrayList<String[]>();
		int rows=transactions.size();
		int[] txindex = new int[rows];
		for(int i=0; i<rows; i++)
		{
			if(transactions.get(i).length<=7){continue;}
			String a= transactions.get(i)[7];
			int temp = Integer.parseInt(a);
			txindex[i] = temp;
		}
		int[] index = quicksort(txindex);
		
		for(int i=0; i<rows; i++)
		{
			transsorted.add(i,transactions.get(index[i]));
		}
		Collections.reverse(transsorted); 
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		ArrayList<Integer> blocksSorted = new ArrayList<Integer>();
		rows=transsorted.size();
		for(int i=0; i<rows; i++)
		{
			if(transsorted.get(i).length<=1){continue;}
			int temp1 = Integer.parseInt(transsorted.get(i)[1]);
			if(!blocksSorted.contains(temp1))
			{blocksSorted.add(temp1);}
		}
		Collections.reverse(blocksSorted);
		rows=blocksSorted.size();
		for(int i=0; i<rows; i++)
		{
			int currentBlock=blocksSorted.get(i);
			int rows1=blocks.size();
			for(int j=0; j<rows1; j++)
			{
				String a= blocks.get(j)[1];
				int temp = Integer.parseInt(a);
				if(temp==currentBlock)
				{sorted.add(blocks.get(j));}
			}
		}
		return sorted;
	}
	//sort Transactions by their transaction fee (gas price) in increasing order (Quick Sort) //#3
	private static ArrayList<String[]> transactionsFeeSort(ArrayList<String[]> data) 
	{ 
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		int rows=data.size();
		float[] fee = new float[rows];
		float[] feeSort = new float[rows];
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=5){continue;}
			String a= data.get(i)[5];
			float temp = Float.parseFloat(a);
			fee[i] = temp;
			feeSort[i] = temp;
		}
		int[] index = quicksort(feeSort);
		
		for(int i=0; i<rows; i++)
		{
			sorted.add(i,data.get(index[i]));
		}
		return sorted;
	}
	//sort all transactions per block in an increasing order of gas fee(Quick Sort) //#4
	private static ArrayList<String[]> transactionsBlockGasfeesort(ArrayList<String[]> data) 
	{ 
		ArrayList<String[]> blocksorted = new ArrayList<String[]>();
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		int rows=data.size();
		int[] blockno = new int[rows];
		int[] blocknoSort = new int[rows];
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=1){continue;}
			String a= data.get(i)[1];
			int temp = Integer.parseInt(a);
			blockno[i] = temp;
			blocknoSort[i] = temp;
		}
		int[] index = quicksort(blocknoSort);
		for(int i=0; i<rows; i++)
		{
			blocksorted.add(i,data.get(index[i]));
		}

		rows=blocksorted.size();
		ArrayList<String[]> activeblock = new ArrayList<String[]>();
		for(int i=0; i<rows; i++)
		{
			if(blocksorted.get(i).length==0){continue;}
			if(activeblock.size()!=0){
			if(Integer.parseInt(blocksorted.get(i)[1])!=Integer.parseInt(activeblock.get(activeblock.size()-1)[1]))
			{
				sorted.addAll(transactionsFeeSort(activeblock));
				activeblock.clear();
			}}
			activeblock.add(blocksorted.get(i));			
		}
		return sorted;
	}
	private static ArrayList<String[]> blocksBlockNoSort(ArrayList<String[]> data) 
	{ 
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		int rows=data.size();
		int[] blockno = new int[rows];
		int[] blocknoSort = new int[rows];
		for(int i=0; i<rows; i++)
		{
			int temp = Integer.parseInt(data.get(i)[1]);
			blockno[i] = temp;
			blocknoSort[i] = temp;
		}
		int[] index = quicksort(blocknoSort);
		
		for(int i=0; i<rows; i++)
		{
			sorted.add(i,data.get(index[i]));
		}
		return sorted;
	}
	//sort all transactions per contract Add in an increasing order of block#(Quick Sort) //#5
	private static ArrayList<String[]> transcontractblock(ArrayList<String[]> data) 
	{ 
		ArrayList<String[]> contractsorted = new ArrayList<String[]>();
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		int rows=data.size();
		String[] hashSort = new String[rows];
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=4){hashSort[i] ="0xffffffffffffffffffffffffffffff";continue;}
			if(data.get(i)[4]=="null"||data.get(i)[4]==null){hashSort[i] ="0xffffffffffffffffffffffffffffff";continue;}
			String a= data.get(i)[4];
			hashSort[i] = a;
		}
		int[] index = quicksort(hashSort);
		for(int i=0; i<index.length; i++)
		{
			contractsorted.add(data.get(index[i]));
		}

		rows=contractsorted.size();
		ArrayList<String[]> activeblock = new ArrayList<String[]>();
		for(int i=0; i<rows; i++)
		{
			if(contractsorted.get(i).length==0){continue;}
			if(activeblock.size()!=0){
			if(!contractsorted.get(i)[4].equals(activeblock.get(activeblock.size()-1)[4]))
			{
				sorted.addAll(blocksBlockNoSort(activeblock));
				activeblock.clear();
			}}
			if(contractsorted.get(i)[4]=="null"||contractsorted.get(i)[4]==null||contractsorted.get(i)[4]==""){continue;}
			activeblock.add(contractsorted.get(i));			
		}
		return sorted;
	}
	//Search for a particular block# and then display its transaction List //#6
	private static ArrayList<String[]> transactionsBlockSearch(ArrayList<String[]> data, int blockno) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=1){continue;}
			String a= data.get(i)[1];
			int temp = Integer.parseInt(a);
			if(temp==blockno)
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	//Search for a particular transaction Hash code and then display its gas fee and Block# //#7
	private static ArrayList<String[]> transactionshashSearch(ArrayList<String[]> data, String hash) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		String[] row = new String[4];
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=6){continue;}
			String temp= data.get(i)[6];
			if(hash.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				row[0]=data.get(i)[1];
				row[1]=data.get(i)[7];
				row[2]=data.get(i)[5];
				row[3]=data.get(i)[6];
				result.add(row);
			}
		}
		return result;
	}
	//Identify and list up all transactions from a particular node (public key) //#8
	private static Double transactionsfromsum(ArrayList<String[]> data, String from) 
	{ 
		ArrayList<String[]> result = transactionsfrom(data,from);
		return sumcolumn(result,5);
	}
	private static ArrayList<String[]> transactionsfrom(ArrayList<String[]> data, String from) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=2){continue;}
			String temp= data.get(i)[2];
			if(from.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	//Identify and list up all transactions to a particular node (public key) //#9
	private static Double transactionstosum(ArrayList<String[]> data, String to) 
	{ 
		ArrayList<String[]> result = transactionsto(data,to);
		return sumcolumn(result,5);
	}
	private static ArrayList<String[]> transactionsto(ArrayList<String[]> data, String to) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=3){continue;}
			String temp= data.get(i)[3];
			if(to.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	private static ArrayList<String[]> transactionsContract(ArrayList<String[]> data, String contract) 
	{ 
		ArrayList<String[]> result = new ArrayList<String[]>();
		int rows=data.size();
		for(int i=0; i<rows; i++)
		{
			if(data.get(i).length<=4){continue;}
			String temp= data.get(i)[4];
			if(contract.equalsIgnoreCase(temp.replaceAll("\\s+","")))
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
	
	//#10
	//Identify and returns the transaction ID in a particular node (Contract ID) with the Smallest and largest amount of tokens //#10
	//has two input, the tokens data, the node public key.
	private static String[] TokensAmttransactionno(ArrayList<String[]> data,String nodeadd) 
	{ 
		String[] results = new String[2];
		ArrayList<String[]> transactions = transactionsContract(data,nodeadd);		
		int rows=transactions.size();
		BigInteger min = new BigInteger("200000000000000000000");
		BigInteger max = new BigInteger("0");
		int index=0;
		int index1=0;
		for(int i=0; i<rows; i++)
		{
			try{
			BigDecimal bd  = new BigDecimal(transactions.get(i)[8]);
			BigInteger active = bd.toBigInteger();
			if(active.compareTo(min)<0){min=active;index=i;}
			if(active.compareTo(max)>0){max=active;index1=i;}
			}
			catch(NumberFormatException e){continue;}
		}
		
		results[0]=transactions.get(index)[6];
		results[1]=transactions.get(index1)[6];
		return results;
	}
	
	//#11
	//Initiate the graph with just nodes.
	private static Graph createGraphnodes(ArrayList<String[]> transactions) 
	{
		HashSet<String> nodes =new HashSet<String>();
		int rows=transactions.size();
		for(int i=0; i<rows; i++)
		{
			if(transactions.get(i).length<2){continue;}
			String from= transactions.get(i)[2];
			String to= transactions.get(i)[3];
			if(!nodes.contains(from))
				nodes.add(from);
			if(!nodes.contains(to))
				nodes.add(to);
		}
		Graph g =new Graph(nodes);
		return g;
	}
	//Add edges to the initiated graph.
	private static Graph addEdgesToGraph(Graph g, ArrayList<String[]> transactions) 
	{
		for (Graph.Node node : g.nodeList.values()) {
			String id= node.id;
			ArrayList<String[]> trans = transactionsfrom(transactions, id);
			HashMap<String, BigDecimal> targetNodes = new HashMap<String, BigDecimal>();
			int rows=trans.size();
			
			for(int i=0; i<rows; i++)
			{
				String to= trans.get(i)[3];
				if(trans.get(i).length<8){continue;}
				
				BigDecimal weight  = new BigDecimal(trans.get(i)[8]);
				if(targetNodes.containsKey(to))
				{
					BigDecimal oldWeight=targetNodes.get(to);
					BigDecimal newWeight=oldWeight.add(weight);
					targetNodes.replace(to,oldWeight,newWeight);
				}
				else{targetNodes.put(to,weight);}				
			}
			for (Map.Entry<String, BigDecimal> entry : targetNodes.entrySet()) {
			g.addEdge(id,entry.getKey(),entry.getValue());
			}
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
			for (Map.Entry<Graph.Node, BigDecimal> entry : node.adjacent.entrySet()) {
			if(entry.getKey().id==null||entry.getKey().id=="null"||entry.getKey()==null){continue;}
			System.out.print(entry.getKey().id + " (" + entry.getValue()+"), ");
			}		
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	//Breadth-First Search - has two inputs, the graph and the starting node. an output that maps other nodes id to shortest path to S
	private static HashMap<String, Integer> BFS(Graph g, Graph.Node s) 
	{
		HashMap<String, Integer> nodeFlag = new HashMap<String, Integer>();//if the node is not visited it will be -1, if visited, it will be the shortest path from S (edge count).
		Queue<String> q = new LinkedList<>(); 
		for (String nodeId : g.nodeList.keySet()) {
			if(nodeId==s.id){nodeFlag.put(nodeId,0);}
			else{nodeFlag.put(nodeId,-1);}
		}
		q.add(s.id);
		while(q.size()!=0)
		{
			String nId= q.remove();
			Graph.Node active=g.getNode(nId);
			for (Graph.Node neighbornode : active.adjacent.keySet()) {
				if(nodeFlag.get(neighbornode.id)==-1){
					int tem=nodeFlag.get(active.id)+1;
					nodeFlag.put(neighbornode.id,tem);
					q.add(neighbornode.id);
				}
			}
		}
		return nodeFlag;
	}
	//Depth_first Search - has one input, the graph.
	public static HashMap<Integer, String> DFS(Graph g) 
	{
		int time=0;
		g.color = new HashMap<String, Integer>();//0->White(not Discovered), 1->gray(discovered), 2->Black(Finished)
		g.discoveryTime = new HashMap<Integer, String>();
		g.FinishingTime = new HashMap<Integer, String>();
		for (String nodeId : g.nodeList.keySet()) {
			g.color.put(nodeId,0);//white (undixcovered)
		}
		for (String nodeId : g.nodeList.keySet()) {
			if(g.color.get(nodeId)==0)
				{time=DFSVisit(nodeId,time,g);}
		}
		return g.FinishingTime;
	}
	public static int DFSVisit(String nodeId,int time, Graph g) 
	{
		g.color.put(nodeId,1);//gray (discovered)
		time++;
		g.discoveryTime.put(time,nodeId);
		Graph.Node active=g.getNode(nodeId);
		for (Graph.Node neighbornode : active.adjacent.keySet()) {
			if(g.color.get(neighbornode.id)==0)
				{DFSVisit(neighbornode.id,time,g);}
		}
		g.color.put(nodeId,2);//black (finished)
		time++;
		g.FinishingTime.put(time,nodeId);
		return time;
	}
	
	private static void reporting(ArrayList<String[]> blocks, ArrayList<String[]> transactions) 
	{
		long startTime;
		long endTime;
		//Tests all of functions and reports all of the query results and their running time.
		//#1
		System.out.println("1.Sorting Blocks by Gas Used increasing:");
		System.out.println("block hash\tblock number\tsize\ttime stamp\ttotal difficulty\tminer\tgas limit\tgas used");
		startTime = System.currentTimeMillis();
		printData(blocksGasUsedSort(blocks),50);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Sorting Blocks by Gas Used increasing on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#2
		System.out.println("2.Sorting Blocks by number of Transaction increasing (cross refrensed to transaction to compare number of transactions in each block):");
		System.out.println("block hash\tblock number\tsize\ttime stamp\ttotal difficulty\tminer\tgas limit\tgas used");
		startTime = System.currentTimeMillis();
		printData(blocksTransNoSort(blocks,transactions),50);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Sorting Blocks by number of Transaction increasing on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#3
		System.out.println("3.Sorting Transactions by Transaction Fee (gas Price) increasing:");
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transactionsFeeSort(transactions),50);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Sorting Blocks by Transaction Fee (ETH) increasing on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#4
		System.out.println("4.sort all transactions per block in an increasing order of gas fee:");
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transactionsBlockGasfeesort(transactions),50);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of sort all transactions per block in an increasing order of gas fee on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#5
		System.out.println("5.sort all transactions per contract Add in an increasing order of block#(Quick Sort) :");
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transcontractblock(transactions),50);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of sort all transactions per contract Add in an increasing order of block#(Quick Sort)  on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#6
		System.out.println("6.Searching for a particular Block# (3110014 in this case) and display its transaction List:");
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transactionsBlockSearch(transactions,3110014),0);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Searching for a particular Block# (3110014 in this case) and display its transaction List on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#7
		System.out.println("7.Searching for a particular Transaction Hash# and display its Block# and gas fee:");
		System.out.println("(Searching for: 0xa44d655c8bbf8a2b7ca44a65fb52bfa9d7eadbb6da8e551bdd466abf8c253c0a)");
		System.out.println("Blockno   index   TXN Fee(ETH) \t Tx hash ");
		startTime = System.currentTimeMillis();
		printData(transactionshashSearch(transactions,"0xa44d655c8bbf8a2b7ca44a65fb52bfa9d7eadbb6da8e551bdd466abf8c253c0a"),0);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Searching for a particular Transaction Hash# and display its Block# and gas fee, on the whole data(in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#8
		System.out.println("8.Identify and list up all transactions from a particular node (public key) and its total transaction fee:");
		System.out.println("(Searching transactions from: 0xa455232135b2d6f2e50d7a1131d7e5752f9cb77d)");
		System.out.print("Total Transaction Fee (ETH): ");
		System.out.println(transactionsfromsum(transactions,"0xa455232135b2d6f2e50d7a1131d7e5752f9cb77d"));
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transactionsfrom(transactions,"0xa455232135b2d6f2e50d7a1131d7e5752f9cb77d"),0);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Identify and list up all transactions from a particular node (public key), on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#9
		System.out.println("9.Identify and list up all transactions to a particular node (public key):");
		System.out.println("(Searching transactions to: 0x7b36d8be6f92818dc30f532ae2a67128b4b92b21)");
		System.out.print("Total Transaction Fee (ETH): ");
		System.out.println(transactionstosum(transactions,"0x7b36d8be6f92818dc30f532ae2a67128b4b92b21"));
		System.out.println("block hash\tblock number\tfrom\tto\tcontract id\tgas price\ttxn hash\tindex\tvalue(token)");
		startTime = System.currentTimeMillis();
		printData(transactionsto(transactions,"0x7b36d8be6f92818dc30f532ae2a67128b4b92b21"),0);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Identify and list up all transactions to a particular node (public key), on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//#10
		
		System.out.println("10.Identify and returns the transaction ID in a particular node (Contract ID) with the Smallest and largest amount of tokens:");
		System.out.println("(Searching transactions of node (Contract ID): 0xc25b3dbbfe2d84c1195b8b73dc61a306720cbfc6)");
		System.out.println("Transaction code with smallest amount of tokens \t Transaction code with largest amount of tokens ");
		startTime = System.currentTimeMillis();
		String[] res=TokensAmttransactionno(transactions,"0xc25b3dbbfe2d84c1195b8b73dc61a306720cbfc6");
		endTime = System.currentTimeMillis();
		System.out.print(res[0]);
		System.out.print("\t");
		System.out.println(res[1]);
		System.out.println("-----------------------------");
		System.out.print("running time of Identify and list up all transactions to a particular node (public key), on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		
		//#11
		System.out.println("11.Build a graph from the list of from's and to's of Transactions:");
		System.out.println("The graph is built on the complete data set of Transactions. but only 100 nodes are shown here by using adjacency lists to save space.");
		System.out.println("Graph Loading, Please wait!");
		startTime = System.currentTimeMillis();
		Graph g= createGraphnodes(transactions);
		g=addEdgesToGraph(g,transactions);
		printGraph(g,100);
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of Building a graph from the list of from's and to's of Transaction and tokens, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("The running time of the Graph creation is high due to the fact that I used arraylist instead of Hashmaps, which saves a lot of memory.");
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//BFS test
		System.out.println("Calling BFS function on the created graph and 0x2976924b350bcee8263f36d86cebd584d2363c1f as the starting node:");
		startTime = System.currentTimeMillis();
		Graph.Node n=g.getNode("0x2976924b350bcee8263f36d86cebd584d2363c1f");
		HashMap<String, Integer> temp =BFS(g, n);
		System.out.println("The shortest path from 0x2976924b350bcee8263f36d86cebd584d2363c1f to other accessible nodes accordint to BFS:");
		System.out.println("NodeId - Shortest path(number of edges)");
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			if(entry.getValue()==-1){continue;}
			if(entry.getKey()==null||entry.getKey()==""||entry.getKey()=="null"){continue;}
			System.out.print(entry.getKey());
			System.out.print(" - ");
			System.out.println(entry.getValue());
			}
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of BFS, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println();
		//DFS Test
		System.out.println("Calling DFS function on the created graph:");
		System.out.println("Printing the node Id's accordion to DFS Finishing Time Decreasing:");
		System.out.println("Finishing Time - Node Id");
		startTime = System.currentTimeMillis();
		HashMap<Integer, String> finishingTime =DFS(g);
		ArrayList<Integer> keys = new ArrayList<>(finishingTime.keySet());
		Collections.sort(keys);
		Collections.reverse(keys);
		for (Integer num : keys) { 		      
        String nodeid=finishingTime.get(num);
		if(num==2){continue;}
		System.out.print(num);
		System.out.print(" - ");
		System.out.println(nodeid);
		}
		endTime = System.currentTimeMillis();
		System.out.println("-----------------------------");
		System.out.print("running time of DFS, on the whole data (in milliseconds): ");
		System.out.println(endTime-startTime);
		System.out.println("__________________________________________________________________________________________________");
		System.out.println("End of the OutPut");
		System.out.println("");
	}
	
	public static void main(String [] args)
	{
		System.out.println();
		System.out.println("Reza Marzban");
		System.out.println("The following sort functions are done on the whole data provided, but only 50 record of each query's result are printed just to show a subset of results and also save space.");
		System.out.println("-----------------------------");
		ArrayList<String[]> blocks = csvreader("blocks.csv");
		ArrayList<String[]> transactions = csvreader("transactions.csv");
		System.out.println("Testing the functions and printing their results:");
		//reports all of the query results and their running time.
		reporting(blocks, transactions);
	}
}

