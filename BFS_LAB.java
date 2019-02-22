import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5
		public enum Color { ALB, GRI, NEGRU }
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

		private int[] BFS(int s, ArrayList<Integer> adj[], int[] p, int[] dist, Color[] c, Queue<Integer> Q, int crt) {
			while (!Q.isEmpty()) {
				int u = Q.peek();
				for (int v : adj[u]) {
					if (c[v] == Color.ALB) {
						dist[v] = dist[u] + 1;
						p[v] = u;
						c[v] = Color.GRI;
						Q.add(v);
					}
				}
				c[u] = Color.NEGRU;
				Q.remove(u);
			}

			return dist;
		}

		private int[] getResult() {
			int d[] = new int[n + 1];
			int[] p = new int[n + 1];
			int[] dist = new int[n + 1];
			Color[] c = new Color[n + 1];

			for (int u = 1; u <= n; u++) {
				p[u] = 0;
				dist[u] = NMAX;
				c[u] = Color.ALB;
			}

			for (int i = 0; i <= n; i++) d[i] = 0;
			// TODO: Faceti un BFS care sa construiasca in d valorile d[i] = numarul
			// minim de muchii de parcurs de la nodul source la nodul i.
			// d[source] = 0
			// d[x] = -1 daca nu exista drum de la source la x.
			// *******
			// ATENTIE: nodurile sunt indexate de la 1 la n.
			// *******

			dist[source] = 0;
			c[source] = Color.GRI;
			Queue<Integer> Q = new LinkedList<>();
			Q.add(source);

			for (int i = 1; i <= n; i++) {
				if (c[i] != Color.NEGRU && i != source)
					d = BFS(source, adj, p, dist, c, Q, i);
			}

			for (int i = 1; i<=n; i++)
				if (d[i] == NMAX)
					d[i] = -1;

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
