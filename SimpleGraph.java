import java.util.*;

public class SimpleGraph<V> implements Graph<V> {
    private Map<V, Map<V, Integer>> adjList;
    
    public SimpleGraph() {
        adjList = new HashMap<>();
    }
    
    public void addVertex(V v) {
        if (v == null) throw new IllegalArgumentException("Вершина не может быть null");
        adjList.putIfAbsent(v, new HashMap<>());
    }
    
    public void addEdge(V from, V to, int weight) {
        if (from == null || to == null) throw new IllegalArgumentException("Вершины не могут быть null");
        if (!adjList.containsKey(from)) addVertex(from);
        if (!adjList.containsKey(to)) addVertex(to);
        
        adjList.get(from).put(to, weight);
        adjList.get(to).put(from, weight);
    }
    
    public void removeVertex(V v) {
        if (!adjList.containsKey(v)) return;
        
        for (V other : adjList.keySet()) {
            adjList.get(other).remove(v);
        }
        adjList.remove(v);
    }
    
    public void removeEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) return;
        adjList.get(from).remove(to);
        adjList.get(to).remove(from);
    }
    
    public List<V> getAdjacent(V v) {
        if (!containsVertex(v)) return new ArrayList<>();
        return new ArrayList<>(adjList.get(v).keySet());
    }
    
    public List<V> getVertices() {
        return new ArrayList<>(adjList.keySet());
    }
    
    public boolean containsVertex(V v) {
        return adjList.containsKey(v);
    }
    
    public boolean containsEdge(V from, V to) {
        return containsVertex(from) && adjList.get(from).containsKey(to);
    }
    
    public Integer getEdgeWeight(V from, V to) {
        if (!containsEdge(from, to)) return null;
        return adjList.get(from).get(to);
    }
    
    // Реализация обхода в глубину
    public void dfs(V start) {
        if (!containsVertex(start)) {
            System.out.println("Ошибка: вершина " + start + " не существует");
            return;
        }
        
        System.out.print("DFS обход из " + start + ": ");
        
        Set<V> visited = new HashSet<>();
        Stack<V> stack = new Stack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            V current = stack.pop();
            
            if (!visited.contains(current)) {
                System.out.print(current + " ");
                visited.add(current);
                
                // Добавляем соседей в стек
                List<V> neighbors = getAdjacent(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    V neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }
    
    // Реализация обхода в ширину
    public void bfs(V start) {
        if (!containsVertex(start)) {
            System.out.println("Ошибка: вершина " + start + " не существует");
            return;
        }
        
        System.out.print("BFS обход из " + start + ": ");
        
        Set<V> visited = new HashSet<>();
        Queue<V> queue = new LinkedList<>();
        
        visited.add(start);
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            V current = queue.poll();
            System.out.print(current + " ");
            
            for (V neighbor : getAdjacent(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    // Метод для получения результата DFS
    public List<V> getDFSResult(V start) {
        List<V> result = new ArrayList<>();
        if (!containsVertex(start)) return result;
        
        Set<V> visited = new HashSet<>();
        Stack<V> stack = new Stack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            V current = stack.pop();
            
            if (!visited.contains(current)) {
                result.add(current);
                visited.add(current);
                
                List<V> neighbors = getAdjacent(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    V neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return result;
    }
    
    // Метод для получения результата BFS
    public List<V> getBFSResult(V start) {
        List<V> result = new ArrayList<>();
        if (!containsVertex(start)) return result;
        
        Set<V> visited = new HashSet<>();
        Queue<V> queue = new LinkedList<>();
        
        visited.add(start);
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            V current = queue.poll();
            result.add(current);
            
            for (V neighbor : getAdjacent(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return result;
    }
    
    public Map<V, Integer> dijkstra(V start) {
        Map<V, Integer> distances = new HashMap<>();
        for (V vertex : getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        return distances;
    }
    
    // Алгоритм Флойда-Уоршелла
    public int[][] floydWarshall() {
        List<V> vertices = getVertices();
        int n = vertices.size();
        int[][] dist = new int[n][n];
        
        // Инициализация матрицы
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], 99999);
            dist[i][i] = 0;
            
            for (int j = 0; j < n; j++) {
                V from = vertices.get(i);
                V to = vertices.get(j);
                Integer weight = getEdgeWeight(from, to);
                if (weight != null) {
                    dist[i][j] = weight;
                }
            }
        }
        
        // Основной алгоритм
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        
        return dist;
    }
    
    // Алгоритм Беллмана-Форда
    public Map<V, Integer> bellmanFord(V start) {
        Map<V, Integer> distances = new HashMap<>();
        List<Edge<V>> edges = getAllEdges();
        
        // Инициализация расстояний
        for (V vertex : getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        // Релаксация ребер
        for (int i = 0; i < getVertexCount() - 1; i++) {
            for (Edge<V> edge : edges) {
                if (distances.get(edge.from) != Integer.MAX_VALUE && 
                    distances.get(edge.from) + edge.weight < distances.get(edge.to)) {
                    distances.put(edge.to, distances.get(edge.from) + edge.weight);
                }
            }
        }
        
        return distances;
    }
    
    // Вспомогательный метод для получения всех ребер
    private List<Edge<V>> getAllEdges() {
        List<Edge<V>> edges = new ArrayList<>();
        for (V from : getVertices()) {
            for (V to : getAdjacent(from)) {
                edges.add(new Edge<>(from, to, getEdgeWeight(from, to)));
            }
        }
        return edges;
    }
    
    public int getVertexCount() {
        return adjList.size();
    }
    
    public int getEdgeCount() {
        int count = 0;
        for (Map<V, Integer> edges : adjList.values()) {
            count += edges.size();
        }
        return count / 2;
    }
    
    public String getAdjacencyMatrixString() {
        List<V> vertices = getVertices();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Матрица смежности:\n  ");
        for (V vertex : vertices) {
            sb.append(vertex).append(" ");
        }
        sb.append("\n");
        
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(" ");
            for (int j = 0; j < vertices.size(); j++) {
                V from = vertices.get(i);
                V to = vertices.get(j);
                Integer weight = getEdgeWeight(from, to);
                sb.append(weight != null ? weight : "0").append(" ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public String getFloydWarshallString() {
        int[][] dist = floydWarshall();
        List<V> vertices = getVertices();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Матрица кратчайших путей (Флойд-Уоршелл):\n  ");
        for (V vertex : vertices) {
            sb.append(vertex).append(" ");
        }
        sb.append("\n");
        
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(" ");
            for (int j = 0; j < vertices.size(); j++) {
                if (dist[i][j] == 99999) {
                    sb.append("INF ");
                } else {
                    sb.append(dist[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public String getBellmanFordString(V start) {
        Map<V, Integer> distances = bellmanFord(start);
        StringBuilder sb = new StringBuilder();
        
        sb.append("Кратчайшие пути (Беллман-Форд) из ").append(start).append(":\n");
        for (Map.Entry<V, Integer> entry : distances.entrySet()) {
            sb.append("До ").append(entry.getKey()).append(": ");
            if (entry.getValue() == Integer.MAX_VALUE) {
                sb.append("INF\n");
            } else {
                sb.append(entry.getValue()).append("\n");
            }
        }
        
        return sb.toString();
    }
    
    // Вспомогательный класс для хранения ребер
    private static class Edge<V> {
        V from, to;
        int weight;
        
        Edge(V from, V to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}