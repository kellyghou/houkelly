package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        Collection<EdgeWithData<Room, Wall>> edges = new HashSet<>();
        Set<Wall> removedWalls = new HashSet<>();
        for (Wall wall : walls) {
            EdgeWithData<Room, Wall> edge = new EdgeWithData<Room, Wall>(wall.getRoom1(), wall.getRoom2(),
                rand.nextDouble(), wall);
            edges.add(edge);
        }
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> tree =
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        //removedWalls = tree.edges();
        Iterator<EdgeWithData<Room, Wall>> iter = tree.edges().iterator();
        while (iter.hasNext()) {
            removedWalls.add(iter.next().data());
        }
        return removedWalls;
    }
}
