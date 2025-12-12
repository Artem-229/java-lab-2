import java.util.Arrays;

class MyArrayList<V> {
    private Object[] elements; 

    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Емкость должна быть больше 0");
        }
        elements = new Object[initialCapacity];
        size = 0;
    }

    public void add(V element) {
        if (size == elements.length) {
            resize();
        }
        elements[size] = element;

        size++;
    }

    public V get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс" + index + ", размер " + size);
        }
        return (V) elements[index];
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = elements.length*2;
        Object[] newArray = new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(V element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == null) {
                if (element == null) return true; 
            } else if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(V element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == null) {
                if (element == null) {
                    return i;
                }
            } else if (elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Такого элекмента не существует");
        }

        for (int i = index; i < size; i++) {
            elements[i] = elements[i+1];
        }
        elements[size - 1] = null;
        size--;
    }

    public void set(int index, V element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Такого элемента не существует");
           
        }
        elements[index] = element;
    }

    public String toString() {
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
    
    public void clear() {
    for (int i = 0; i < size; i++) {
        elements[i] = null;
    }
    size = 0;
    }
}

class MyHashMap<K, V> {
    public static class Entry<K, V> {
        public K key;
        public V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    
    public MyHashMap() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    public MyArrayList<Entry<K, V>> entrySet() {
        MyArrayList<Entry<K, V>> entries = new MyArrayList<>();
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                entries.add(current);
                current = current.next;
            }
        }
        return entries;
    }
    
    public MyArrayList<V> values() {
        MyArrayList<V> values = new MyArrayList<>();
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                values.add(current.value);
                current = current.next;
            }
        }
        return values;
    }

    private int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % table.length;
    }
    public V put(K key, V value) {
        int index = hash(key);
        
        Entry<K, V> current = table[index];
        while (current != null) {
            if ((key == null && current.key == null) || 
                (key != null && key.equals(current.key))) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
        
        return null;
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        
        while (current != null) {
            if ((key == null && current.key == null) || 
                (key != null && key.equals(current.key))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        
        while (current != null) {
            if ((key == null && current.key == null) || 
                (key != null && key.equals(current.key))) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        
        while (current != null) {
            if ((key == null && current.key == null) || 
                (key != null && key.equals(current.key))) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public MyArrayList<K> keySet() {
        MyArrayList<K> keys = new MyArrayList<>();
        
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
}

class MyStack<V> {
    private Object[] elements;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;
    
    public MyStack() {
        elements = new Object[DEFAULT_CAPACITY];
        top = -1;
    }
    
    public void push(V element) {
        if (top == elements.length - 1) {
            Object[] newArray = new Object[elements.length * 2];
            for (int i = 0; i <= top; i++) {
                newArray[i] = elements[i];
            }
            elements = newArray;
        }
        elements[++top] = element;
    }
    
    public V pop() {
        if (isEmpty()) {
            throw new RuntimeException("Стек пуст");
        }
        V element = (V) elements[top];
        elements[top] = null;
        top--;
        return element;
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public V peek() {
        if (isEmpty()) {
            throw new RuntimeException("Стек пуст");
        }
        return (V) elements[top];
    }
}

class MyQueue<V> {
    private Object[] elements;
    private int front;
    private int rear;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    
    public MyQueue() {
        elements = new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }
    
    public void offer(V element) {
        if (size == elements.length) {
            resize();
        }
        rear = (rear + 1) % elements.length;
        elements[rear] = element;
        size++;
    }
    
    public V poll() {
        if (isEmpty()) {
            throw new RuntimeException("Очередь пуста");
        }
        V element = (V) elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return element;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public V peek() {
        if (isEmpty()) {
            throw new RuntimeException("Очередь пуста");
        }
        return (V) elements[front];
    }
    
    private void resize() {
        Object[] newArray = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(front + i) % elements.length];
        }
        elements = newArray;
        front = 0;
        rear = size - 1;
    }
}

class MyHashSet<V> {
    private MyHashMap<V, Object> map;
    private static final Object PRESENT = new Object();
    
    public MyHashSet() {
        map = new MyHashMap<>();
    }
    
    public boolean add(V element) {
        return map.put(element, PRESENT) == null;
    }
    
    public boolean contains(V element) {
        return map.containsKey(element);
    }
    
    public boolean remove(V element) {
        return map.remove(element) != null;
    }
    
    public int size() {
        return map.size();
    }
    
    public boolean isEmpty() {
        return map.isEmpty();
    }
}

public class SimpleGraph<V> implements Graph<V> {
    private MyHashMap<V, MyHashMap<V, Integer>> adjList;
    
    public SimpleGraph() {
        adjList = new MyHashMap<>();
    }
    
    public void addVertex(V v) {
        if (v == null) throw new IllegalArgumentException("Вершина не может быть null");
        if (!adjList.containsKey(v)) {  
        adjList.put(v, new MyHashMap<>());
    }
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
        
        MyArrayList<V> keys = adjList.keySet();
        for (int i = 0; i < keys.size(); i++) {
            V other = keys.get(i);
            adjList.get(other).remove(v);
        }
        adjList.remove(v);
    }
    
    public void removeEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) return;
        adjList.get(from).remove(to);
        adjList.get(to).remove(from);
    }
    
    public MyArrayList<V> getAdjacent(V v) {
        if (!containsVertex(v)) return new MyArrayList<>();
        
        MyArrayList<V> result = new MyArrayList<>();
        MyArrayList<V> keys = adjList.get(v).keySet();
        for (int i = 0; i < keys.size(); i++) {
            result.add(keys.get(i));
        }
        return result;
    }
    
    public MyArrayList<V> getVertices() {
        MyArrayList<V> result = new MyArrayList<>();
        MyArrayList<V> keys = adjList.keySet();
        for (int i = 0; i < keys.size(); i++) {
            result.add(keys.get(i));
        }
        return result;
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
        
        MyHashSet<V> visited = new MyHashSet<>();
        MyStack<V> stack = new MyStack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            V current = stack.pop();
            
            if (!visited.contains(current)) {
                System.out.print(current + " ");
                visited.add(current);
                
                // Добавляем соседей в стек
                MyArrayList<V> neighbors = getAdjacent(current);
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
        
        MyHashSet<V> visited = new MyHashSet<>();
        MyQueue<V> queue = new MyQueue<>();
        
        visited.add(start);
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            V current = queue.poll();
            System.out.print(current + " ");
            
            MyArrayList<V> neighbors = getAdjacent(current);
            for (int i = 0; i < neighbors.size(); i++) {
                V neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    // Метод для получения результата DFS
    public MyArrayList<V> getDFSResult(V start) {
        MyArrayList<V> result = new MyArrayList<>();
        if (!containsVertex(start)) return result;
        
        MyHashSet<V> visited = new MyHashSet<>();
        MyStack<V> stack = new MyStack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            V current = stack.pop();
            
            if (!visited.contains(current)) {
                result.add(current);
                visited.add(current);
                
                MyArrayList<V> neighbors = getAdjacent(current);
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
    public MyArrayList<V> getBFSResult(V start) {
        MyArrayList<V> result = new MyArrayList<>();
        if (!containsVertex(start)) return result;
        
        MyHashSet<V> visited = new MyHashSet<>();
        MyQueue<V> queue = new MyQueue<>();
        
        visited.add(start);
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            V current = queue.poll();
            result.add(current);
            
            MyArrayList<V> neighbors = getAdjacent(current);
            for (int i = 0; i < neighbors.size(); i++) {
                V neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return result;
    }
    
    public MyHashMap<V, Integer> dijkstra(V start) {
        MyHashMap<V, Integer> distances = new MyHashMap<>();
        MyArrayList<V> vertices = getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            V vertex = vertices.get(i);
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        return distances;
    }
    
    // Алгоритм Флойда-Уоршелла
    public int[][] floydWarshall() {
        MyArrayList<V> vertices = getVertices();
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
    public MyHashMap<V, Integer> bellmanFord(V start) {
        MyHashMap<V, Integer> distances = new MyHashMap<>();
        MyArrayList<Edge<V>> edges = getAllEdges();
        
        // Инициализация расстояний
        MyArrayList<V> vertices = getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            V vertex = vertices.get(i);
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        // Релаксация ребер
        for (int i = 0; i < getVertexCount() - 1; i++) {
        for (int j = 0; j < edges.size(); j++) {
            Edge<V> edge = edges.get(j);
            if (distances.get(edge.from) != Integer.MAX_VALUE && 
                distances.get(edge.from) + edge.weight < distances.get(edge.to)) {
                distances.put(edge.to, distances.get(edge.from) + edge.weight);
            }
        }
    }
        
        return distances;
    }
    
    // Вспомогательный метод для получения всех ребер
    private MyArrayList<Edge<V>> getAllEdges() {
        MyArrayList<Edge<V>> edges = new MyArrayList<>();
        MyArrayList<V> vertices = getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            V from = vertices.get(i);
            MyArrayList<V> adjacent = getAdjacent(from);
            for (int j = 0; j < adjacent.size(); j++) {
                V to = adjacent.get(j);
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
        MyArrayList<V> keys = adjList.keySet();
        for (int i = 0; i < keys.size(); i++) {
            V vertex = keys.get(i);
            MyHashMap<V, Integer> edges = adjList.get(vertex);
            count += edges.size();
        }
        return count / 2;
    }
    
    public String getAdjacencyMatrixString() {
        MyArrayList<V> vertices = getVertices();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Матрица смежности:\n  ");
        for (int i = 0; i < vertices.size(); i++) {
            V vertex = vertices.get(i);
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
        MyArrayList<V> vertices = getVertices();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Матрица кратчайших путей (Флойд-Уоршелл):\n  ");
        for (int i = 0; i < vertices.size(); i++) {
            V vertex = vertices.get(i);
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
        MyHashMap<V, Integer> distances = bellmanFord(start);
        StringBuilder sb = new StringBuilder();
        
        sb.append("Кратчайшие пути (Беллман-Форд) из ").append(start).append(":\n");
        MyArrayList<MyHashMap.Entry<V, Integer>> entries = distances.entrySet(); 
        for (int i = 0; i < entries.size(); i++) {
            MyHashMap.Entry<V, Integer> entry = entries.get(i);
            sb.append("До ").append(entry.key).append(": ");  
            if (entry.value == Integer.MAX_VALUE) {
                sb.append("INF\n");
            } else {
                sb.append(entry.value).append("\n");
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