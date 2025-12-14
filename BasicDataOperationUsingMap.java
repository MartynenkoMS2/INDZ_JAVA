/*
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
*/

import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {

    /**
     * Статичний клас для зберігання інформації про пітона (Python).
     * Містить поля: nickname (кличка) та skinSpots (кількість плям на шкірі).
     */
    public static class Python {
        private final String nickname;
        private final Integer skinSpots;

        public Python(String nickname, Integer skinSpots) {
            this.nickname = nickname;
            this.skinSpots = skinSpots;
        }

        public String getNickname() {
            return nickname;
        }

        public Integer getSkinSpots() {
            return skinSpots;
        }

        @Override
        public String toString() {
            return "Python{nickname='" + nickname + "', skinSpots=" + skinSpots + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Python python = (Python) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(python.nickname) : python.nickname == null;
            boolean skinSpotsEquals = skinSpots != null ? skinSpots.equals(python.skinSpots) : python.skinSpots == null;
            
            return nicknameEquals && skinSpotsEquals;
        }

        @Override
        public int hashCode() {
            int result = nickname != null ? nickname.hashCode() : 0;
            result = 31 * result + (skinSpots != null ? skinSpots.hashCode() : 0);
            return result;
        }
    }

    /**
     * Статичний Comparator для сортування Python за кличкою (за спаданням) і кількістю плям (за зростанням).
     * 
     * Порядок сортування:
     * 1. За кличкою (nickname) в зворотному порядку (спадання)
     * 2. За кількістю плям (skinSpots) в нормальному порядку (зростання)
     */
    private static final Comparator<Python> PET_COMPARATOR = 
        Comparator.comparing(Python::getNickname, Comparator.reverseOrder())
                  .thenComparing(Python::getSkinSpots);

    private final Python KEY_TO_SEARCH_AND_DELETE = new Python("Удавчик", 18);
    private final Python KEY_TO_ADD = new Python("Василіск", 26);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Мирослава";
    private final String VALUE_TO_ADD = "Дар'я";

    private Hashtable<Python, String> hashtable;
    private LinkedHashMap<Python, String> linkedHashMap;

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Python, значення: ім'я власника)
     * @param linkedHashMap LinkedHashMap з початковими даними (ключ: Python, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Python, String> hashtable, LinkedHashMap<Python, String> linkedHashMap) {
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        // Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // Потім обробляємо LinkedHashMap
        System.out.println("\n\n========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір LinkedHashMap: " + linkedHashMap.size());
        
        // Пошук до сортування
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();
        sortLinkedHashMap();
        printLinkedHashMap();

        // Пошук після сортування
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();
        
        addEntryToLinkedHashMap();
        
        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();
        
        System.out.println("Кінцевий розмір LinkedHashMap: " + linkedHashMap.size());
    }


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        hashtable.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує Hashtable за ключами.
     * Використовує PET_COMPARATOR для сортування за кличкою (спадання) та кількістю плям (зростання).
     * Перезаписує hashtable відсортованими даними.
     */
    private void sortHashtable() {
        long timeStart = System.nanoTime();

        hashtable = hashtable.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(PET_COMPARATOR))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        Hashtable::new
                ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Сортує LinkedHashMap за ключами.
     * Використовує PET_COMPARATOR для сортування за кличкою (спадання) та кількістю плям (зростання).
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap = linkedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(PET_COMPARATOR))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Python.hashCode() та Python.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в Hashtable.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.entrySet().stream()
                .anyMatch(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE));

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в Hashtable");

        if (found) {
            String owner = hashtable.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                    .map(entry -> entry.getKey().toString())
                    .findFirst()
                    .orElse("");
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Python: " + owner);
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Python='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        List<Python> keysToRemove = hashtable.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(hashtable::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записiв з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap.
     * LinkedHashMap зберігає порядок вставляння пар (подібно к LinkedList).
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");

        long timeStart = System.nanoTime();
        linkedHashMap.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в LinkedHashMap");
    }

    /**
     * Здійснює пошук елемента за ключем в LinkedHashMap.
     * Використовує Python.hashCode() та Python.equals() для пошуку.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = linkedHashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в LinkedHashMap");

        if (found) {
            String value = linkedHashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в LinkedHashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = linkedHashMap.entrySet().stream()
                .anyMatch(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE));

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в LinkedHashMap");

        if (found) {
            String owner = linkedHashMap.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                    .map(entry -> entry.getKey().toString())
                    .findFirst()
                    .orElse("");
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Python: " + owner);
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Додає новий запис до LinkedHashMap.
     */
    void addEntryToLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до LinkedHashMap");

        System.out.println("Додано новий запис: Python='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = linkedHashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Python> keysToRemove = linkedHashMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(linkedHashMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записiв з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Python, значення: ім'я власника)
        Hashtable<Python, String> hashtable = new Hashtable<>();
        hashtable.put(new Python("Змійка", 25), "Тарас");
        hashtable.put(new Python("Удавчик", 18), "Оксана");
        hashtable.put(new Python("Сиріус", 32), "Мирослава");
        hashtable.put(new Python("Полоз", 22), "Борис");
        hashtable.put(new Python("Удавчик", 15), "Лариса");
        hashtable.put(new Python("Оріон", 28), "Мирослава");
        hashtable.put(new Python("Нагайна", 20), "Всеволод");
        hashtable.put(new Python("Медуза", 30), "Оксана");
        hashtable.put(new Python("Кобра", 12), "Антон");
        hashtable.put(new Python("Аспід", 35), "Соломія");

        LinkedHashMap<Python, String> linkedHashMap = new LinkedHashMap<Python, String>() {{
            put(new Python("Змійка", 25), "Тарас");
            put(new Python("Удавчик", 18), "Оксана");
            put(new Python("Сиріус", 32), "Мирослава");
            put(new Python("Полоз", 22), "Борис");
            put(new Python("Удавчик", 15), "Лариса");
            put(new Python("Оріон", 28), "Мирослава");
            put(new Python("Нагайна", 20), "Всеволод");
            put(new Python("Медуза", 30), "Оксана");
            put(new Python("Кобра", 12), "Антон");
            put(new Python("Аспід", 35), "Соломія");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, linkedHashMap);
        operations.executeDataOperations();
    }
}