import java.util.List;
import java.util.Map;

public interface Graph<V> {
    void addVertex(V v);
    void addEdge(V from, V to, int weight);
    void removeVertex(V v);
    void removeEdge(V from, V to);
    List<V> getAdjacent(V v);
    List<V> getVertices();
    boolean containsVertex(V v);
    boolean containsEdge(V from, V to);
    Integer getEdgeWeight(V from, V to);
    void dfs(V start);
    void bfs(V start);
    Map<V, Integer> dijkstra(V start);
    int[][] floydWarshall();
    Map<V, Integer> bellmanFord(V start);
    int getVertexCount();
    int getEdgeCount();
}