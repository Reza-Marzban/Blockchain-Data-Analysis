# Blockchain-Data-Analysis
Implementation of Sorting, Searching, Graph creation on Blockchain data

Implemented sorting and searching on Blockchain data and created a graph corresponding to the data.
Each node in the graph was a user public key, and edges were the transactions. Several graph algorithms were applied (Dijkstra, Topological sort, DFS, BFS, ...).

I used Quick Sort for all sorting queries. It’s growth rate is O(n log n) on average. And it is one of the best comparison sorts. It can operate in-place. So it does not waste memory.
All of my queries were executed on the whole data provided, and the running time is based on the whole data, but in the output, I just printed 50 top rows of each query’s result.
My sorting queries running time with quick sort are 12, 51, 18, 35, 25 (Milliseconds) for queries 1 to 5.
On average, it took 28.2 milliseconds, the fastest one was on sorting blocks on Gas used, as it is sorted according to a number (Integer) value, and the slowest took 53 because it had to cross reference both tables to count the number of transactions in each block (from transactions.csv) and sort the block data (from blocks.csv) according to that information, so most of the running time of this query is spent on cross referencing, so if we don’t take that specific query into account, our sorting average running time is 22.5 milliseconds.

Graph:

Our graph is built on the whole Blockchain data provided, and it is a directed weighted graph. Weights in our graph is the Tokens value of all transaction sent from public key A to public key B. My Graph construction time is high due to the fact that I used arraylists instead of hashmaps for keeping the original data, and It reduced memory usage a lot and as we are dealing with a subset of a very big data, we should try to minimize the memory usage so that our algorithms can work on huge data as well. However, after creating the Graph, my BFS and DFS run supper fast.
My BFS and DFS follow the standard algorithm covered in the class lecture, The BFS running time is so fast as it does not travers on whole graph nodes (it just took 2 milliseconds), but the DFS travers on the whole graph and it passes every single node, my DFS took 15 milliseconds to run on whole graph and sort it topologically, which is very fast in comparison to the size of graph (my graph was built on whole data provided).
I presented my graph in two ways: an adjacency list and the finishing time of each node (decreasing) as a topological sort.

Challenges and running time report

This Phase was consisted of 3 queries on a huge amount of blockchain data (6 month). One of the main challenges was lack of memory needed for reading the huge data and create a graph according to it. In my code I used buffer reader to minimize the memory use. The other challenge was the running time. The new data has much deeper networks and it takes more time to traverse with DFS on all leaves and then return to the root. To decrease the running time and optimize my program, I used hash-maps, to decrease the time needed for data retrieval. As a result, my whole program on the 3 queries is run in 38 seconds, and 19.5 seconds out of that is just the time spent on data reading from disk file. It means that the whole program (three queries and all of their necessary components) run in 18.5 seconds which is very fast compared to the amount of data being analyzed.

Result analysis

According to the output, there are 373,851 total nodes in the 6-month data (2 GB version), and obviously the highest finishing time of the DFS is twice the nodes count which is 747,702. 
The largest SCC found on the data is 2-node SCC, and the largest MST found on the provided data is a 1004-node MST. 
The result seems to be unexpected, as we expect a broader market with much larger SCC’s. But our result means that the system has more localized market. Also as the MST length are much higher than the SCC, it means that we can find large trees in the system but we cannot expect to see much dense networks, in which all of the nodes are accessible. By other words, it means that we expect to see a one-way flow from a group of nodes to another group, but this flow does not have a counter flow, and the first group of nodes are not accessible from any node in the second group. 

Conclusion

It seems that the Market is not mature enough yet. It is too sparse, consisted of many small localized markets, and It even does not have small dense groups of nodes. It has some large one-way flows (e.g. a root sends dozens of transactions to different nodes) but the root is never accessible from leaves. Maybe if we check the data on a larger scale, it will improve a little but not much. When we have too many vertices (users) but very few edges (transactions), it just has one meaning: The system does not have many active users. If we could come up with a way to measure the monthly active users (MAU) of the network, we could verify that, but before that we have to define “active users”, It may have different meanings (e.g. one transaction or more, to be a receiver or sender,…).
