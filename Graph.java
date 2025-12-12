public interface Graph<V> {
    void addVertex(V v);
    void addEdge(V from, V to, int weight);
    void removeVertex(V v);
    void removeEdge(V from, V to);
    MyArrayList<V> getAdjacent(V v);
    MyArrayList<V> getVertices();
    boolean containsVertex(V v);
    boolean containsEdge(V from, V to);
    Integer getEdgeWeight(V from, V to);
    void dfs(V start);
    void bfs(V start);
    MyHashMap<V, Integer> dijkstra(V start);
    int[][] floydWarshall();
    MyHashMap<V, Integer> bellmanFord(V start);
    int getVertexCount();
    int getEdgeCount();
}