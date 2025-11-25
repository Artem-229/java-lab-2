import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphGUI extends JFrame {
    private SimpleGraph<String> graph;
    private JTextArea outputArea;
    private JTextField vertexField;
    private JTextField fromField;
    private JTextField toField;
    private JTextField weightField;
    
    // Цвета для темной темы
    private final Color DARK_BG = new Color(45, 45, 48);
    private final Color DARK_PANEL = new Color(62, 62, 66);
    private final Color TEXT_COLOR = new Color(241, 241, 241);
    private final Color ACCENT_COLOR = new Color(0, 122, 204);
    
    public GraphGUI() {
        graph = new SimpleGraph<>();
        setupInterface();
        setupDarkTheme();
    }
    
    private void setupInterface() {
        setTitle("Граф - Лабораторная работа");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_BG);
        
        // Панель для ввода данных
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBackground(DARK_BG);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Управление графом"));
        
        // Создаем поля ввода
        vertexField = new JTextField();
        fromField = new JTextField();
        toField = new JTextField();
        weightField = new JTextField();
        
        // Метки для полей
        JLabel vertexLabel = new JLabel("Вершина:");
        JLabel fromLabel = new JLabel("Из вершины:");
        JLabel toLabel = new JLabel("В вершину:");
        JLabel weightLabel = new JLabel("Вес:");
        
        inputPanel.add(vertexLabel);
        inputPanel.add(vertexField);
        inputPanel.add(fromLabel);
        inputPanel.add(fromField);
        inputPanel.add(toLabel);
        inputPanel.add(toField);
        inputPanel.add(weightLabel);
        inputPanel.add(weightField);
        
        // Кнопки для работы с графом
        JButton addVertexBtn = new JButton("Добавить вершину");
        JButton addEdgeBtn = new JButton("Добавить ребро");
        JButton removeVertexBtn = new JButton("Удалить вершину");
        JButton removeEdgeBtn = new JButton("Удалить ребро");
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.setBackground(DARK_BG);
        buttonPanel.add(addVertexBtn);
        buttonPanel.add(addEdgeBtn);
        buttonPanel.add(removeVertexBtn);
        buttonPanel.add(removeEdgeBtn);
        
        // Панель с алгоритмами
        JPanel algoPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        algoPanel.setBackground(DARK_BG);
        algoPanel.setBorder(BorderFactory.createTitledBorder("Алгоритмы"));
        
        JButton showMatrixBtn = new JButton("Матрица смежности");
        JButton floydBtn = new JButton("Флойд-Уоршелл");
        JButton bellmanBtn = new JButton("Беллман-Форд");
        JButton dfsBtn = new JButton("DFS обход");
        JButton bfsBtn = new JButton("BFS обход");
        JButton showGraphBtn = new JButton("Показать граф");
        
        algoPanel.add(showMatrixBtn);
        algoPanel.add(floydBtn);
        algoPanel.add(bellmanBtn);
        algoPanel.add(dfsBtn);
        algoPanel.add(bfsBtn);
        algoPanel.add(showGraphBtn);
        
        // Область для вывода результатов
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Результаты"));
        
        // Собираем интерфейс
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(DARK_BG);
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(algoPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Назначаем действия для кнопок
        addVertexBtn.addActionListener(e -> addVertex());
        addEdgeBtn.addActionListener(e -> addEdge());
        removeVertexBtn.addActionListener(e -> removeVertex());
        removeEdgeBtn.addActionListener(e -> removeEdge());
        showMatrixBtn.addActionListener(e -> showAdjacencyMatrix());
        floydBtn.addActionListener(e -> showFloydWarshall());
        bellmanBtn.addActionListener(e -> showBellmanFord());
        dfsBtn.addActionListener(e -> doDFS());
        bfsBtn.addActionListener(e -> doBFS());
        showGraphBtn.addActionListener(e -> showGraphInfo());
    }
    
    private void setupDarkTheme() {
        Color darkPanel = new Color(62, 62, 66);
        Color textColor = new Color(241, 241, 241);
        
        // Настраиваем внешний вид полей ввода
        vertexField.setBackground(darkPanel);
        vertexField.setForeground(textColor);
        vertexField.setCaretColor(textColor);
        
        fromField.setBackground(darkPanel);
        fromField.setForeground(textColor);
        fromField.setCaretColor(textColor);
        
        toField.setBackground(darkPanel);
        toField.setForeground(textColor);
        toField.setCaretColor(textColor);
        
        weightField.setBackground(darkPanel);
        weightField.setForeground(textColor);
        weightField.setCaretColor(textColor);
        
        // Настраиваем текстовую область
        outputArea.setBackground(darkPanel);
        outputArea.setForeground(textColor);
        outputArea.setCaretColor(textColor);
        
        // Применяем тему ко всем компонентам
        applyThemeToComponents(getContentPane().getComponents());
        
        // Настраиваем скролл панель
        JScrollPane scrollPane = (JScrollPane) ((JPanel) getContentPane().getComponent(0)).getComponent(1);
        scrollPane.getViewport().setBackground(darkPanel);
    }
    
    private void applyThemeToComponents(Component[] components) {
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                panel.setBackground(DARK_BG);
                applyThemeToComponents(panel.getComponents());
            } else if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(ACCENT_COLOR);
                button.setForeground(TEXT_COLOR);
                button.setFocusPainted(false);
            } else if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(TEXT_COLOR);
            } else if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                scrollPane.getViewport().setBackground(DARK_PANEL);
            }
        }
    }
    
    private void addVertex() {
        String vertex = vertexField.getText().trim();
        if (vertex.isEmpty()) {
            showError("Введите название вершины");
            return;
        }
        
        if (graph.containsVertex(vertex)) {
            showError("Вершина " + vertex + " уже существует");
            return;
        }
        
        graph.addVertex(vertex);
        outputArea.append("Добавлена вершина: " + vertex + "\n");
        vertexField.setText("");
    }
    
    private void addEdge() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String weightStr = weightField.getText().trim();
        
        if (from.isEmpty() || to.isEmpty() || weightStr.isEmpty()) {
            showError("Заполните все поля для ребра");
            return;
        }
        
        try {
            int weight = Integer.parseInt(weightStr);
            
            if (!graph.containsVertex(from)) {
                showError("Вершина " + from + " не существует");
                return;
            }
            
            if (!graph.containsVertex(to)) {
                showError("Вершина " + to + " не существует");
                return;
            }
            
            graph.addEdge(from, to, weight);
            outputArea.append("Добавлено ребро: " + from + " - " + to + " (вес: " + weight + ")\n");
            
            fromField.setText("");
            toField.setText("");
            weightField.setText("");
            
        } catch (NumberFormatException e) {
            showError("Вес должен быть числом");
        }
    }
    
    private void removeVertex() {
        String vertex = vertexField.getText().trim();
        if (vertex.isEmpty()) {
            showError("Введите название вершины для удаления");
            return;
        }
        
        if (!graph.containsVertex(vertex)) {
            showError("Вершина " + vertex + " не существует");
            return;
        }
        
        graph.removeVertex(vertex);
        outputArea.append("Удалена вершина: " + vertex + "\n");
        vertexField.setText("");
    }
    
    private void removeEdge() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        
        if (from.isEmpty() || to.isEmpty()) {
            showError("Заполните поля Из вершины и В вершину");
            return;
        }
        
        if (!graph.containsEdge(from, to)) {
            showError("Ребро " + from + " - " + to + " не существует");
            return;
        }
        
        graph.removeEdge(from, to);
        outputArea.append("Удалено ребро: " + from + " - " + to + "\n");
        
        fromField.setText("");
        toField.setText("");
    }
    
    private void showAdjacencyMatrix() {
        if (graph.getVertexCount() == 0) {
            showError("Граф пустой. Добавьте вершины");
            return;
        }
        
        outputArea.append("\n" + graph.getAdjacencyMatrixString() + "\n");
    }
    
    private void showFloydWarshall() {
        if (graph.getVertexCount() == 0) {
            showError("Граф пустой. Добавьте вершины");
            return;
        }
        
        outputArea.append("\n" + graph.getFloydWarshallString() + "\n");
    }
    
    private void showBellmanFord() {
        if (graph.getVertexCount() == 0) {
            showError("Граф пустой. Добавьте вершины");
            return;
        }
        
        String start = JOptionPane.showInputDialog(this, "Введите стартовую вершину для Беллман-Форда:");
        if (start != null && !start.trim().isEmpty()) {
            if (!graph.containsVertex(start.trim())) {
                showError("Вершина " + start + " не существует");
                return;
            }
            
            outputArea.append("\n" + graph.getBellmanFordString(start.trim()) + "\n");
        }
    }
    
    private void doDFS() {
        String start = JOptionPane.showInputDialog(this, "Введите стартовую вершину для DFS:");
        if (start != null && !start.trim().isEmpty()) {
            try {
                List<String> result = graph.getDFSResult(start.trim());
                outputArea.append("DFS обход из " + start + ": " + String.join(" -> ", result) + "\n");
            } catch (Exception e) {
                showError("Ошибка при выполнении DFS");
            }
        }
    }
    
    private void doBFS() {
        String start = JOptionPane.showInputDialog(this, "Введите стартовую вершину для BFS:");
        if (start != null && !start.trim().isEmpty()) {
            try {
                List<String> result = graph.getBFSResult(start.trim());
                outputArea.append("BFS обход из " + start + ": " + String.join(" -> ", result) + "\n");
            } catch (Exception e) {
                showError("Ошибка при выполнении BFS");
            }
        }
    }
    
    private void showGraphInfo() {
        outputArea.append("\n=== Информация о графе ===\n");
        outputArea.append("Вершин: " + graph.getVertexCount() + "\n");
        outputArea.append("Ребер: " + graph.getEdgeCount() + "\n");
        outputArea.append("Вершины: " + graph.getVertices() + "\n");
        
        for (String vertex : graph.getVertices()) {
            outputArea.append("Смежные с " + vertex + ": " + graph.getAdjacent(vertex) + "\n");
        }
        outputArea.append("==========================\n\n");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphGUI().setVisible(true);
        });
    }
}