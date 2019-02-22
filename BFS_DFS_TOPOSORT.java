import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;

public class BFS_DFS_TOPOSORT {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		int source;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int[] result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int i = 1; i <= n; i++) {
					pw.printf("%d%c", result[i], i == n ? '\n' : ' ');
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}



		private void BFS(int start, ArrayList<Integer>adj[]) {
			Queue<Integer> queue = new LinkedList<>();
			boolean[] visited = new boolean[n + 1];
			
			// Add start node in queue and mark it as being visited
			queue.add(start);
			visited[start] = true;

			while (!queue.isEmpty()) {
				// Get the top of the queue
				Integer u = queue.peek();
				
				for (Integer v : adj[u]) {
					if (!visited[v]) {
						// Add neighbor to queue
						queue.add(v);
						visited[v] = true;
					}
				}

				// Add node to list (here stdout)
				System.out.print(u + " ");

				// Remove the top of the queue
				queue.poll();
			}
		}



		private void auxDFS(int u, boolean[] visited, ArrayList<Integer> adj[]) {
			visited[u] = true;
			System.out.print(u + " ");
			// here the discover time can be set

			for (Integer v : adj[u]) {
				if (!visited[v]) {
					auxDFS(v, visited, adj);
				}
			}

			// here the finalize time can be set
		}

		private void DFS(int start, ArrayList<Integer>adj[]) {
			boolean[] visited = new boolean[n + 1];
			auxDFS(start, visited, adj);
		}



		private void auxTopoSort(ArrayList<Integer> adj[], boolean[] visited, 
				Stack<Integer> S, int v) {

			visited[v] = true;
			for (Integer u : adj[v]) {
				if (!visited[u]) {
					auxTopoSort(adj, visited, S, u);
				}
			}
			S.push(v);
		}

		private void topoSort(ArrayList<Integer>adj[]) {
			ArrayList<Integer> topsort = new ArrayList<>();
			Stack<Integer> S = new Stack<Integer>();
			boolean[] visited = new boolean[n + 1];

			for (int u = 1; u <= n; u++) {
				if (!visited[u]) {
					auxTopoSort(adj, visited, S, u);
				}
			}

			while (!S.isEmpty()) {
				topsort.add(S.pop());
			}

			for (Integer i : topsort) {
				System.out.print(i + " ");
			}
		}


		
		private int[] getResult() {
			int d[] = new int[n + 1];

			System.out.print("BFS = ");
			BFS(source, adj);

			System.out.print("\nDFS = ");
			DFS(source, adj);

			System.out.print("\nTopoSort = ");
			topoSort(adj);

			System.out.print("\n");

			return d;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
