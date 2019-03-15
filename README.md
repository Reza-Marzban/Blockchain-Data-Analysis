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

