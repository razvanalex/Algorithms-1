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

		class Edge {
			int x;
			int y;

			Edge(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

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

		private void writeOutput(ArrayList<Edge> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (Edge e : result) {
					pw.printf("%d %d\n", e.x, e.y);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		int[] low;
		int[] lvl;

		private void DFS(Integer u, ArrayList<Edge> sol) {
			low[u] = lvl[u];
			boolean isCritic = false;
			int numSons = 0;

			for (Integer v : adj[u]) {
				if(lvl[v] == 0) {
					lvl[v] = lvl[u] + 1;
					DFS(v, sol);
					numSons++;
					if (low[v] >= lvl[v]) {
						sol.add(new Edge(u, v));
					}
					low[u] = Math.min(low[u], low[v]);
				} else if (lvl[v] < lvl[u] - 1) {
					low[u] = Math.min(low[u], lvl[v]);
				}
			}
		}

		private ArrayList<Edge> getResult() {
			// TODO: Gasiti muchiile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Edge> sol = new ArrayList<>();
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
