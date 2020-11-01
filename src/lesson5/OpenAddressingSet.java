package lesson5;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private boolean[] flags;

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
        flags = new boolean[capacity];
        Arrays.fill(flags, false);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        if (t == null) return false;
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        int startIndex = startingIndex(o);
        int index = startIndex;
        Object current = storage[index];
        while (current != null) {
            int oldIndex = index;
            index = (index + 1) % capacity;
            Object newCurrent = storage[index];
            if (newCurrent != null) {
                storage[oldIndex] = newCurrent;
                storage[index] = null;
                size--;
                return true;
            }
            current = storage[index];
        }
        storage[startIndex] = null;
        return false;
    }


    /**
     * Создание итератора для обхода таблицы
     * <p>
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingSetIterator();
    }

    public class OpenAddressingSetIterator implements Iterator<T> {
        int currentIndex = -1;
        int newIndex;

        public void findIndex() {
            while (newIndex < capacity && storage[newIndex] == null) {
                newIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            if (storage.length == 0 || size == 0) return false;
            return newIndex < capacity;
        }

        @Override
        public T next() {
            if (newIndex >= capacity) throw new IllegalStateException();
            currentIndex = newIndex;
            newIndex++;
            Object current = storage[currentIndex];
            findIndex();
            return (T) current;
        }

        //@Override
        //public void remove() {
        //    if (currentIndex != -1 && storage[currentIndex] != null){
        //        storage[currentIndex] = null;
        //        size--;
        //        currentIndex--;
        //    }
        //}
    }
}