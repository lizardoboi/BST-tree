package com.example.bsttree;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> extends Application {
    private Node<K, V> root;
    private int insertionCount;
    private int searchCount;
    private int deletionCount;
    // Конструктор
    public BST() {
        root = null;
        insertionCount = 0;
        searchCount = 0;
        deletionCount = 0;
    }
    public int getInsertionCount() {
        return insertionCount;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public int getDeletionCount() {
        return deletionCount;
    }
    
    // Конструктор копирования
    public BST(BST<K, V> other) {
        root = copyNode(other.root);
    }

    private Node<K, V> copyNode(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> newNode = new Node<>(node.key, node.value);
        newNode.left = copyNode(node.left);
        newNode.right = copyNode(node.right);
        return newNode;
    }

    // Метод поиска узла по ключу (итеративная реализация)
    private Node<K, V> findNode(K key) {
        Node<K, V> current = root;
        while (current != null && !current.key.equals(key)) {
            if (key.compareTo(current.key) < 0)
                current = current.left;
            else
                current = current.right;
        }
        return current;
    }

    public void insert(K key, V value) {
        root = insertIterative(root, key, value);
        insertionCount++;
    }

    // Метод вставки узла (итеративная реализация)
    private Node<K, V> insertIterative(Node<K, V> root, K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        if (root == null) {
            root = newNode;
            return root;
        }
        Node<K, V> current = root;
        Node<K, V> parent = null;
        while (true) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return root;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return root;
                }
            }
        }
    }

    public boolean search(K key) {
        searchCount++;
        return findNode(key) != null;
    }

    public void delete(K key) {
        root = deleteIterative(root, key);
        deletionCount++;
    }

    // Метод удаления узла (итеративная реализация)
    private Node<K, V> deleteIterative(Node<K, V> root, K key) {
        Node<K, V> parent = null;
        Node<K, V> current = root;
        while (current != null && !current.key.equals(key)) {
            parent = current;
            if (key.compareTo(current.key) < 0)
                current = current.left;
            else
                current = current.right;
        }
        if (current == null)
            return root;
        if (current.left == null && current.right == null) {
            if (current != root) {
                if (parent.left == current)
                    parent.left = null;
                else
                    parent.right = null;
            } else {
                root = null;
            }
        } else if (current.right == null) {
            if (current != root) {
                if (parent.left == current)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            } else {
                root = current.left;
            }
        } else if (current.left == null) {
            if (current != root) {
                if (parent.left == current)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            } else {
                root = current.right;
            }
        } else {
            Node<K, V> successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (parent.left == current)
                parent.left = successor;
            else
                parent.right = successor;
            successor.left = current.left;
        }
        return root;
    }

    private Node<K, V> getSuccessor(Node<K, V> deleteNode) {
        Node<K, V> successor = null;
        Node<K, V> successorParent = null;
        Node<K, V> current = deleteNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        if (successor != deleteNode.right) {
            successorParent.left = successor.right;
            successor.right = deleteNode.right;
        }
        return successor;
    }

    public void inorderTraversal(Node<K, V> root, TextArea output) {
        if (root != null) {
            Stack<Node<K, V>> stack = new Stack<>();
            Node<K, V> current = root;
            while (current != null || !stack.isEmpty()) {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                current = stack.pop();
                output.appendText(current.key + " ");
                current = current.right;
            }
        }
    }
    public void tLrTraversal(Node<K, V> root, TextArea output) {
        if (root != null) {
            output.appendText(root.key + " ");  // Посещаем текущую ноду
            tLrTraversal(root.left, output);    // Рекурсивно обходим левое поддерево
            tLrTraversal(root.right, output);   // Рекурсивно обходим правое поддерево
        }
    }

    // Деструктор (очистка дерева)
    public void clear() {
        root = null;
    }

    // Опрос размера дерева
    public int size() {
        return size(root);
    }

    private int size(Node<K, V> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    // Проверка дерева на пустоту
    public boolean isEmpty() {
        return root == null;
    }

    // Доступ по чтению к данным по ключу
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return node == null ? null : node.value;
    }

    // Запись данных по ключу
    public void set(K key, V value) {
        Node<K, V> node = findNode(key);
        if (node != null) {
            node.value = value;
        } else {
            insert(key, value);
        }
    }

    // Включение данных с заданным ключом
    public void put(K key, V value) {
        insert(key, value);
    }

    // Удаление данных с заданным ключом
    public void remove(K key) {
        delete(key);
    }
    // Определение количества узлов с ключами, большими заданного значения
    public int countNodesGreaterThan(K key) {
        return countNodesGreaterThan(root, key);
    }

    private int countNodesGreaterThan(Node<K, V> node, K key) {
        if (node == null) return 0;

        int count = 0;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            count++; // Увеличиваем счетчик, если ключ текущего узла больше заданного
            count += countNodesGreaterThan(node.left, key);
            count += countNodesGreaterThan(node.right, key);
        } else if (cmp >= 0) {
            count += countNodesGreaterThan(node.right, key);
        }
        return count;
    }

    // Запрос прямого итератора, установленного на узел дерева с минимальным ключом begin()
    public Iterator<K> begin() {
        return new BSTIterator(null,true);
    }

    // Запрос обратного итератора, установленного на узел дерева с максимальным ключом rbegin()
    public Iterator<K> rbegin() {
        return new BSTReverseIterator(root,true);
    }

    // Запрос «неустановленного» прямого итератора end()
    public Iterator<K> end() {
        return new BSTIterator<>(null,true);
    }

    // Запрос «неустановленного» обратного итератора rend()
    public Iterator<K> rend() {
        return new BSTReverseIterator(null,true);
    }

    // Операция доступа по чтению и записи к данным текущего узла
    public V getCurrentValue(K key) {
        Node<K, V> node = findNode(key);
        return node == null ? null : node.value;
    }

    public void setCurrentValue(K key, V value) {
        Node<K, V> node = findNode(key);
        if (node != null) {
            node.value = value;
        } else {
            insert(key, value);
        }
    }

    // Операция перехода к следующему по ключу узлу в дереве
    public K next(K key) {
        Iterator<K> iterator = begin();
        while (iterator.hasNext()) {
            K currentKey = iterator.next();
            if (currentKey.compareTo(key) > 0) {
                return currentKey;
            }
        }
        return null; // Вернуть null, если такой ключ не найден
    }

    // Операция перехода к предыдущему по ключу узлу в дереве
    public K previous(K key) {
        Iterator<K> iterator = rbegin();
        while (iterator.hasNext()) {
            K currentKey = iterator.next();
            if (currentKey.compareTo(key) < 0) {
                return currentKey;
            }
        }
        return null; // Вернуть null, если такой ключ не найден
    }

    // Проверка равенства однотипных итераторов
    public boolean isEqual(Iterator<K> iterator1, Iterator<K> iterator2) {
        return iterator1.equals(iterator2);
    }

    // Проверка неравенства однотипных итераторов
    public boolean isNotEqual(Iterator<K> iterator1, Iterator<K> iterator2) {
        return !iterator1.equals(iterator2);
    }

    // Опрос числа узлов дерева, просмотренных предыдущей операцией
    public int nodesVisited(Iterator<K> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    @Override
    public void start(Stage primaryStage) {
        TextField keyField = new TextField();
        TextField valueField = new TextField();
        Button insertButton = new Button("Insert");
        Button deleteButton = new Button("Delete");
        Button searchButton = new Button("Search");
        Button isEmptyButton = new Button("Is Empty"); // Кнопка для проверки на пустоту
        Button clearButton = new Button("Clear Tree"); // Кнопка для полной очистки дерева
        Button sizeButton = new Button("Size"); // Кнопка для проверки размера дерева
        Button getButton = new Button("Get"); // Кнопка для получения значения по ключу
        Button setButton = new Button("Set"); // Кнопка для установки значения по ключу
        Button putButton = new Button("Put"); // Кнопка для включения данных с заданным ключом
        Button removeButton = new Button("Remove"); // Кнопка для удаления данных с заданным ключом
        TextArea outputArea = new TextArea();
        Group graph = new Group();

        insertButton.setOnAction(event -> {
            try {
                K key = (K) keyField.getText();
                V value = (V) valueField.getText();
                insert(key, value);
                outputArea.clear();
                tLrTraversal(root, outputArea);

                // Перерисовываем дерево
                graph.getChildren().clear();
                drawTree(root, graph, 400, 20,0);
            } catch (Exception e) {
                outputArea.setText("Invalid input!");
            }
        });

        deleteButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            delete(key);
            outputArea.clear();
            tLrTraversal(root, outputArea);

            // Перерисовываем дерево после удаления узла
            graph.getChildren().clear();
            drawTree(root, graph, 400, 20,0);
        });

        searchButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            outputArea.setText("Key " + key + " found: " + search(key));
        });

        // Добавление обработчиков для новых кнопок
        getButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            V value = get(key);
            outputArea.setText("Value for key " + key + ": " + value);
        });

        setButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            V value = (V) valueField.getText();
            set(key, value);
        });

        putButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            V value = (V) valueField.getText();
            put(key, value);
        });

        removeButton.setOnAction(event -> {
            K key = (K) keyField.getText();
            remove(key);
        });

        isEmptyButton.setOnAction(event -> {
            outputArea.setText("Tree is empty: " + isEmpty());
        });

        clearButton.setOnAction(event -> {
            clear();
            outputArea.clear();
            graph.getChildren().clear();
        });

        sizeButton.setOnAction(event -> {
            outputArea.setText("Tree size: " + size());
        });

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(keyField, valueField, insertButton, deleteButton, searchButton,
                isEmptyButton, clearButton, sizeButton, getButton, setButton, putButton, removeButton);
        root.getChildren().addAll(inputBox, outputArea, graph);

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BST");
        primaryStage.show();
    }
    private double nodeSpacing = 100; // Расстояние между узлами
    private double verticalSpacing = 150; // Вертикальное расстояние между уровнями дерева

    private void drawNode(Node<K, V> node, K key, VBox treeView) {
        if (node != null) {
            if (node.key.equals(key)) {
                Circle circle = new Circle(20);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);
                Text text = new Text(node.key.toString());
                VBox nodeView = new VBox(circle, text);
                treeView.getChildren().add(nodeView);

                double x = nodeView.getBoundsInParent().getMinX() + 20;
                double y = nodeView.getBoundsInParent().getMinY() + 20;

                if (node.left != null) {
                    Line lineLeft = new Line(x, y, x - nodeSpacing, y + verticalSpacing);
                    Pane leftPane = new Pane();
                    leftPane.getChildren().addAll(lineLeft);
                    treeView.getChildren().add(leftPane);
                }

                if (node.right != null) {
                    Line lineRight = new Line(x, y, x + nodeSpacing, y + verticalSpacing);
                    Pane rightPane = new Pane();
                    rightPane.getChildren().addAll(lineRight);
                    treeView.getChildren().add(rightPane);
                }
            } else {
                drawNode(node.left, key, treeView);
                drawNode(node.right, key, treeView);
            }
        }
    }

    private void drawTree(Node<K, V> node, Group graph, double x, double y, int level) {
        if (node != null) {
            Circle circle = new Circle(x, y, 30); // Увеличиваем радиус круга до 30
            circle.setFill(Color.GREEN); // Раскрашиваем круг зеленым цветом
            circle.setStroke(Color.BLACK);
            Text text = new Text(x - 15, y + 5, node.key.toString() + ":" + node.value.toString());
            text.setStyle("-fx-font-weight: bold;"); // Делаем текст жирным
            graph.getChildren().addAll(circle, text);

            double verticalSpacing = 100; // Увеличиваем расстояние между уровнями
            double childrenY = y + verticalSpacing;

            if (node.left != null) {
                double leftX = x - nodeSpacing / Math.pow(2, level);
                drawTree(node.left, graph, leftX, childrenY, level + 1);
                Line lineLeft = new Line(x, y, leftX, childrenY);
                graph.getChildren().add(lineLeft);
            }

            if (node.right != null) {
                double rightX = x + nodeSpacing / Math.pow(2, level);
                drawTree(node.right, graph, rightX, childrenY, level + 1);
                Line lineRight = new Line(x, y, rightX, childrenY);
                graph.getChildren().add(lineRight);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}