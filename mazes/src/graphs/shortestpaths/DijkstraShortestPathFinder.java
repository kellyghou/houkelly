package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;


/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> edgeTo = new HashMap<>();

        Map<V, Double> distTo = new HashMap<>();
        distTo.put(start, 0.0);
        ExtrinsicMinPQ<V> perimeter = createMinPQ();
        perimeter.add(start, 0);

        while (!perimeter.isEmpty()) {
            V u = perimeter.removeMin();

            if (Objects.equals(u, end)) {
                return edgeTo;
            }

            Set<E> outgoing = new HashSet<>(graph.outgoingEdgesFrom(u));

            for (E edge : outgoing) {
                V v = edge.to();
                if (!distTo.containsKey(v)) {
                    distTo.put(v, Double.POSITIVE_INFINITY);
                }
                double oldDist = distTo.get(v);
                double newDist = distTo.get(u) + edge.weight();
                if (newDist < oldDist) {
                    distTo.put(v, newDist);
                    edgeTo.put(v, edge);
                    if (perimeter.contains(v)) {
                        perimeter.changePriority(v, newDist);
                    } else {
                        perimeter.add(v, newDist);
                    }
                }
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        if (spt.get(end) == null) {
            return new ShortestPath.Failure<>();
        }

        List<E> path = new LinkedList<>();

        V v = end;
        while (v != start && spt.get(v) != null) {
            path.add(spt.get(v));
            v = spt.get(v).from();
        }

        Collections.reverse(path);

        return new ShortestPath.Success<V, E>(path);
    }
}
