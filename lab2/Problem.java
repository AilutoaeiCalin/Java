package lab2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Locatii si drumuri
 */
public class Problem {

    private final ArrayList<Location> locations = new ArrayList<>();
    private final ArrayList<Road> roads = new ArrayList<>();

    public void addLocation(Location loc) {
        for (Location l : locations) {
            if (l.equals(loc)) {
                System.out.println("Location deja exista!");
                return;
            }
        }
        locations.add(loc);
    }

    public void addRoad(Road r) {
        for (Road rr : roads) {
            if (rr.equals(r)) {
                System.out.println("Road deja exista!");
                return;
            }
        }
        roads.add(r);
    }

    /**
     * Verific daca lungimea drumului e mai mica decat distanta intre capete si daca viteza e valida
     */
    public boolean isValid() {
        for (Road r : roads) {
            double dist = r.a.distanceTo(r.b);
            if (r.length < dist) return false;
            if (r.speedLimit <= 0) return false;
        }
        return true;
    }

    // indexul locatiei
    private int indexOf(Location loc) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).equals(loc)) return i;
        }
        return -1;
    }

    /**
     * BFS
     */
    public boolean canReach(Location start, Location target) {
        int s = indexOf(start);
        int t = indexOf(target);

        if (s == -1 || t == -1) return false;
        if (s == t) return true;

        int n = locations.size();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (Road r : roads) {
            int ia = indexOf(r.a);
            int ib = indexOf(r.b);
            if (ia == -1 || ib == -1) continue;

            adj.get(ia).add(ib);
            adj.get(ib).add(ia);
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();

        visited[s] = true;
        q.add(s);

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int nxt : adj.get(cur)) {
                if (!visited[nxt]) {
                    if (nxt == t) return true;
                    visited[nxt] = true;
                    q.add(nxt);
                }
            }
        }
        return false;
    }
}