package lab3;

import java.util.*;

public class SocialNetwork {
    private final List<Profile> profiles = new ArrayList<>();

    public void addProfile(Profile p) {
        profiles.add(p);
    }

    public List<Profile> getProfiles() {
        return Collections.unmodifiableList(profiles);
    }

    // Person-Person
    public void connect(Person a, Person b, String how) {
        a.addRelation(b, new Relationship(Relationship.Type.KNOWS, how));
        b.addRelation(a, new Relationship(Relationship.Type.KNOWS, how));
    }

    // Person-Company
    public void employ(Person p, Company c, String position) {
        p.addRelation(c, new Relationship(Relationship.Type.WORKS_FOR, position));
    }


    public void recomputeCompanyImportance() {
        Map<Company, Integer> count = new HashMap<>();

        for (Profile pr : profiles) {
            if (pr instanceof Person person) {
                for (Map.Entry<Profile, Relationship> e : person.getRelations().entrySet()) {
                    Profile other = e.getKey();
                    Relationship rel = e.getValue();

                    if (other instanceof Company company &&
                            rel.getType() == Relationship.Type.WORKS_FOR) {
                        count.put(company, count.getOrDefault(company, 0) + 1);
                    }
                }
            }
        }

        for (Profile pr : profiles) {
            if (pr instanceof Company c) {
                c.setImportance(count.getOrDefault(c, 0));
            }
        }
    }

    public List<Profile> sortedByImportance() {
        recomputeCompanyImportance();

        List<Profile> copy = new ArrayList<>(profiles);
        copy.sort(Comparator
                .comparingInt(Profile::getImportance).reversed()
                .thenComparing(Profile::getName, String.CASE_INSENSITIVE_ORDER));
        return copy;
    }

    public void printNetwork() {
        List<Profile> sorted = sortedByImportance();
        for (Profile pr : sorted) {
            System.out.println(pr.getName() + " [id=" + pr.getId() + ", imp=" + pr.getImportance() + "]");
            if (pr instanceof Person p) {
                for (Map.Entry<Profile, Relationship> e : p.getRelations().entrySet()) {
                    System.out.println("   -> " + e.getKey().getName() + " : " + e.getValue());
                }
            }
        }
    }


    private Map<Profile, List<Profile>> buildUndirectedGraph() {
        Map<Profile, List<Profile>> adj = new HashMap<>();


        for (Profile p : profiles) adj.put(p, new ArrayList<>());


        for (Profile pr : profiles) {
            if (pr instanceof Person person) {
                for (Profile other : person.getRelations().keySet()) {
                    addUndirectedEdge(adj, person, other);
                }
            }
        }
        return adj;
    }

    private void addUndirectedEdge(Map<Profile, List<Profile>> adj, Profile a, Profile b) {

        if (!adj.containsKey(a) || !adj.containsKey(b)) return;

        if (!adj.get(a).contains(b)) adj.get(a).add(b);
        if (!adj.get(b).contains(a)) adj.get(b).add(a);
    }



    private static class Edge {
        Profile u, v;
        Edge(Profile u, Profile v) { this.u = u; this.v = v; }
    }

    public static class TarjanResult {
        public final Set<Profile> articulationPoints;
        public final List<Set<Profile>> biconnectedComponents;

        TarjanResult(Set<Profile> ap, List<Set<Profile>> bcc) {
            this.articulationPoints = ap;
            this.biconnectedComponents = bcc;
        }
    }

    public TarjanResult analyzeConnectivityTarjan() {
        Map<Profile, List<Profile>> adj = buildUndirectedGraph();

        Map<Profile, Integer> disc = new HashMap<>();  // discovery time
        Map<Profile, Integer> low  = new HashMap<>();  // low-link
        Map<Profile, Profile> parent = new HashMap<>();
        Set<Profile> articulation = new HashSet<>();

        Deque<Edge> edgeStack = new ArrayDeque<>();
        List<Set<Profile>> bccList = new ArrayList<>();


        for (Profile v : adj.keySet()) {
            disc.put(v, -1);
            low.put(v, -1);
            parent.put(v, null);
        }

        int[] time = {0};

        for (Profile start : adj.keySet()) {
            if (disc.get(start) == -1) {
                dfsTarjan(start, adj, disc, low, parent, articulation, edgeStack, bccList, time);


                if (!edgeStack.isEmpty()) {
                    Set<Profile> comp = new HashSet<>();
                    while (!edgeStack.isEmpty()) {
                        Edge e = edgeStack.pop();
                        comp.add(e.u);
                        comp.add(e.v);
                    }
                    bccList.add(comp);
                }
            }
        }

        return new TarjanResult(articulation, bccList);
    }

    private void dfsTarjan(
            Profile u,
            Map<Profile, List<Profile>> adj,
            Map<Profile, Integer> disc,
            Map<Profile, Integer> low,
            Map<Profile, Profile> parent,
            Set<Profile> articulation,
            Deque<Edge> edgeStack,
            List<Set<Profile>> bccList,
            int[] time
    ) {
        disc.put(u, time[0]);
        low.put(u, time[0]);
        time[0]++;

        int children = 0;

        for (Profile v : adj.get(u)) {
            if (disc.get(v) == -1) {
                // tree edge
                children++;
                parent.put(v, u);
                edgeStack.push(new Edge(u, v));

                dfsTarjan(v, adj, disc, low, parent, articulation, edgeStack, bccList, time);


                low.put(u, Math.min(low.get(u), low.get(v)));


                if (parent.get(u) == null && children > 1) {
                    articulation.add(u);


                    Set<Profile> comp = popComponentUntil(edgeStack, u, v);
                    if (!comp.isEmpty()) bccList.add(comp);
                }


                if (parent.get(u) != null && low.get(v) >= disc.get(u)) {
                    articulation.add(u);

                    Set<Profile> comp = popComponentUntil(edgeStack, u, v);
                    if (!comp.isEmpty()) bccList.add(comp);
                }

            } else if (!v.equals(parent.get(u))) {
                // back edge (u -> v)
                if (disc.get(v) < disc.get(u)) {
                    edgeStack.push(new Edge(u, v));
                }
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }
    }

    private Set<Profile> popComponentUntil(Deque<Edge> stack, Profile u, Profile v) {
        Set<Profile> comp = new HashSet<>();
        while (!stack.isEmpty()) {
            Edge e = stack.pop();
            comp.add(e.u);
            comp.add(e.v);


            if ((e.u.equals(u) && e.v.equals(v)) || (e.u.equals(v) && e.v.equals(u))) {
                break;
            }
        }
        return comp;
    }
}