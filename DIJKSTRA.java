import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class Main {
	static class Task {
		class Pair {
			int f, s;
			public Pair(int i, int j) {
				f = i;
				s = j;
			}
		}

		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;

		int n;
		int m;
		int source;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					sb.append(result.get(i)).append(' ');
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// pairs => first = node; second = distance
		private void Dijkstra(int source, ArrayList<Integer> d) {
			ArrayList<Integer> p = new ArrayList<>(n + 1);
			ArrayList<Boolean> selectat = new ArrayList<>(n + 1);

			Queue<Pair> queue = new PriorityQueue<>(new Comparator<Pair>() {
				@Override
				public int compare(Pair p1, Pair p2) {
				   return p1.s - p2.s;
			    }
			});

			queue.add(new Pair(source, 0));
			d.set(source, 0);

			for (int i = 0; i <= n; i++) {
				if (i != source)
					d.set(i, Integer.MAX_VALUE);
				p.add(0);
				selectat.add(false);
			}

			while(!queue.isEmpty()) {
				Pair u = queue.remove();
				selectat.set(u.f, true);
				for (Edge nod : adj[u.f]) {
					if (!selectat.get(nod.node) && d.get(nod.node) > d.get(u.f) + nod.cost) {
						queue.remove(new Pair(nod.node, d.get(nod.node)));
						d.set(nod.node, d.get(u.f) + nod.cost);
						p.set(nod.node, u.f);
						queue.add(new Pair(nod.node, d.get(nod.node)));
					}
				}
			}

		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.
			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.

			ArrayList<Integer> d = new ArrayList<>(n + 1);

			for (int i = 0; i <= n; i++) {
				d.add(0);
			}
			Dijkstra(source, d);

			for (int i = 0; i <= n; i++) {
				if (d.get(i) == Integer.MAX_VALUE)
					d.set(i, -1);
			}

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
