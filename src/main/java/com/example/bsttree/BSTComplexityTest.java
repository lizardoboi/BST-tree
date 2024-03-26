package com.example.bsttree;

import java.util.Random;

public class BSTComplexityTest {
    private static final int NUM_TESTS = 50; // Количество тестов
    private static final int MAX_SIZE = 100; // Максимальный размер дерева

    public static void main(String[] args) {
        // Тестирование случайного BST-дерева
        System.out.println("Testing Random BST:");
        testRandomBST();

        // Тестирование вырожденного BST-дерева
        System.out.println("\nTesting Degenerate BST:");
        testDegenerateBST();
    }

    // Метод для генерации случайного BST-дерева заданного размера
    private static BST<Integer, Integer> generateRandomBST(int size) {
        BST<Integer, Integer> bst = new BST<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            bst.insert(random.nextInt(), i);
            bst.search(random.nextInt());
            bst.delete(random.nextInt());
        }
        return bst;
    }

    // Метод для генерации вырожденного BST-дерева заданного размера
    private static BST<Integer, Integer> generateDegenerateBST(int size) {
        BST<Integer, Integer> bst = new BST<>();
        for (int i = 0; i < size; i++) {
            bst.insert(i, i);
            bst.search(i);
            bst.delete(i);
        }
        return bst;
    }

    // Метод для измерения времени выполнения операции поиска в дереве
    private static long measureSearchTime(BST<Integer, Integer> bst, int key) {
        long startTime = System.nanoTime();
        bst.search(key);
        return System.nanoTime() - startTime;
    }

    // Метод для измерения времени выполнения операции вставки в дерево
    private static long measureInsertionTime(BST<Integer, Integer> bst, int key, int value) {
        long startTime = System.nanoTime();
        bst.insert(key, value);
        return System.nanoTime() - startTime;
    }
    // Метод для измерения времени выполнения операции удаления из дерева
    private static long measureDeletionTime(BST<Integer, Integer> bst, int key) {
        long startTime = System.nanoTime();
        bst.delete(key);
        return System.nanoTime() - startTime;
    }

    // Метод для тестирования случайного BST-дерева
    private static void testRandomBST() {
        for (int i = 1; i <= NUM_TESTS; i++) {
            int size = i * (MAX_SIZE / NUM_TESTS); // Размер дерева для текущего теста
            BST<Integer, Integer> bst = generateRandomBST(size);

            // Измеряем время выполнения операции поиска
            int randomKey = new Random().nextInt(); // Случайный ключ для поиска
            long searchTime = measureSearchTime(bst, randomKey);

            int searchCount = bst.getSearchCount(); // Получаем количество операций поиска
            System.out.println("Search Count: " + searchCount);

            // Измеряем время выполнения операции вставки
            int insertKey = new Random().nextInt(); // Случайный ключ для вставки
            int insertValue = new Random().nextInt(); // Случайное значение для вставки
            long insertionTime = measureInsertionTime(bst, insertKey, insertValue);

            int insertionCount = bst.getInsertionCount(); // Получаем количество операций вставки
            System.out.println("Insertion Count: " + insertionCount);

            // Измеряем время выполнения операции удаления
            int deleteKey = new Random().nextInt(); // Случайный ключ для удаления
            long deletionTime = measureDeletionTime(bst, deleteKey);

            int deletionCount = bst.getDeletionCount(); // Получаем количество операций удаления
            System.out.println("Deletion Count: " + deletionCount);

            // Вывод результатов
            System.out.println("Test " + i + ": Size=" + size + ", Search Time=" + searchTime + " ns, Insertion Time="
                    + insertionTime + " ns, Deletion Time=" + deletionTime + " ns");
        }
    }

    // Метод для тестирования вырожденного BST-дерева
    private static void testDegenerateBST() {
        for (int i = 1; i <= NUM_TESTS; i++) {
            int size = i * (MAX_SIZE / NUM_TESTS); // Размер дерева для текущего теста
            BST<Integer, Integer> bst = generateDegenerateBST(size);

            // Измеряем время выполнения операции поиска
            int randomKey = new Random().nextInt(); // Случайный ключ для поиска
            long searchTime = measureSearchTime(bst, randomKey);

            int searchCount = bst.getSearchCount(); // Получаем количество операций поиска
            System.out.println("Search Count: " + searchCount);

            // Измеряем время выполнения операции вставки
            int insertKey = new Random().nextInt(); // Случайный ключ для вставки
            int insertValue = new Random().nextInt(); // Случайное значение для вставки
            long insertionTime = measureInsertionTime(bst, insertKey, insertValue);

            int insertionCount = bst.getInsertionCount(); // Получаем количество операций вставки
            System.out.println("Insertion Count: " + insertionCount);

            // Измеряем время выполнения операции удаления
            int deleteKey = new Random().nextInt(); // Случайный ключ для удаления
            long deletionTime = measureDeletionTime(bst, deleteKey);

            int deletionCount = bst.getDeletionCount(); // Получаем количество операций удаления
            System.out.println("Deletion Count: " + deletionCount);

            // Вывод результатов
            System.out.println("Test " + i + ": Size=" + size + ", Search Time=" + searchTime + " ns, Insertion Time="
                    + insertionTime + " ns, Deletion Time=" + deletionTime + " ns");
        }
    }
}
