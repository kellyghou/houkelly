package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        // return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        DisjointSets<V> msts = createDisjointSets();
        Set<V> vertices = new HashSet<>(graph.allVertices());
        for (V vertex : vertices) {
            msts.makeSet(vertex);
        }

        Set<E> finalMST = new HashSet<>();
        Collection<V> allVertices = graph.allVertices();
        if (allVertices.size() == 0 || allVertices.size() == 1) {
            return new MinimumSpanningTree.Success<V, E>(finalMST);
        }

        for (E edge: edges) {
            int uMST = msts.findSet(edge.from());
            int vMST = msts.findSet(edge.to());
            if (uMST != vMST) {
                finalMST.add(edge);
                msts.union(edge.from(), edge.to());
                if (finalMST.size() == vertices.size() - 1) {
                    return new MinimumSpanningTree.Success<V, E>(finalMST);
                }
            }
        }
        return new MinimumSpanningTree.Failure<V, E>();
    }
}
