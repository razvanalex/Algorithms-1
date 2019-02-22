import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

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

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// int[] idx = null;
		// int[] low = null;
		// int[] parent = null;
		// int time;
		//
		// private void criticalPoints(ArrayList<Integer> sol) {
		// 	time = 1;
		//
		// 	for (int v = 1; v <= n; v++) {
		// 		if (idx[v] == 0) {
		// 			dfsCV(v, sol);
		// 		}
		// 	}
		// }
		//
		// private void dfsCV(int v, ArrayList<Integer> sol) {
		// 	idx[v] = time;
		// 	low[v] = time;
		// 	time++;
		//
		// 	ArrayList<Integer> children = new ArrayList<>();
		//
		// 	for (Integer u : adj[v]) {
		// 		if (idx[u] == 0) {
		// 			children.add(u);
		// 			parent[u] = v;
		// 			dfsCV(u, sol);
		// 			low[v] = Math.min(low[v], low[u]);
		// 		} else {
		// 			low[v] = Math.min(low[v], idx[u]);
		// 		}
		// 	}
		//
		// 	if (parent[v] == -1) {
		// 		if (children.size() >= 2) {
		// 			sol.add(v);
		// 		}
		// 	} else {
		// 		if (checkKid(v, children)) {
		// 			sol.add(v);
		// 		}
		// 	}
		// }
		//
		// private boolean checkKid(int v, ArrayList<Integer> children) {
		// 	for (Integer u : children) {
		// 		if (low[u] >= idx[v])
		// 			return true;
		// 	}
		//
		// 	return false;
		// }
		//
		// private ArrayList<Integer> getResult() {
		// 	// TODO: Gasiti nodurile critice ale grafului neorientat stocat cu liste
		// 	// de adiacenta in adj.
		// 	ArrayList<Integer> sol = new ArrayList<>();
		// 	parent = new int[n + 1];
		// 	low = new int[n + 1];
		// 	idx = new int[n + 1];
		//
		// 	for (int i = 1; i <= n; i++)
		// 		parent[i] = -1;
		//
		// 	criticalPoints(sol);
		// 	return sol;
		// }

		int[] low;
		int[] lvl;

		private void DFS(Integer u, ArrayList<Integer> sol) {
			low[u] = lvl[u];
			boolean isCritic = false;
			int numSons = 0;

			for (Integer v : adj[u]) {
				if(lvl[v] == 0) {
					lvl[v] = lvl[u] + 1;
					DFS(v, sol);
					numSons++;
					if (low[v] >= lvl[u]) {
						isCritic = true;
					}
					low[u] = Math.min(low[u], low[v]);
				} else if (lvl[v] < lvl[u] - 1) {
					low[u] = Math.min(low[u], lvl[v]);
				}
			}
			if (lvl[u] == 1) {
				if (numSons > 1) {
					sol.add(u);
				}
			} else if (isCritic) {
				sol.add(u);
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti nodurile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Integer> sol = new ArrayList<>();
			low = new int[n + 1];
			lvl = new int[n + 1];

			for (int u = 1; u <= n; u++) {
				lvl[u] = 1;
				DFS(u, sol);
			}

			return sol;
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
