package lesson5;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
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
        //if (o == null) return false;
        System.out.println("TARARARARRA");
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null || flags[index]) {
            if (current != null) {
                if (current.equals(o)) {
                    return true;
                    }
                }
                //if (flags[index] && current == null) {
                //    index = (index + 1) % capacity;
                //    current = storage[index];
                //    continue;
                //}
                index = (index + 1) % capacity;
                current = storage[index];
            }
            return false;
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
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
        if (current == null || contains(current)) return false;
        while (current != null || flags[index]) {
            if (current != null) {
                index = (index + 1) % capacity;
                if (index == startingIndex) {
                    throw new IllegalStateException("Table is full");
                }
                current = storage[index];
            } else {
                if (flags[index]) flags[index] = false;
                storage[index] = t;
                size++;
                return true;
            }
        }
        return false;
        //while (current != null || flags[index]) {
        //    if (current != null) {
        //        if (current.equals(t)) {
        //            return false;
        //        }
        //    }
        //    index = (index + 1) % capacity;
        //    if (index == startingIndex) {
        //        throw new IllegalStateException("Table is full");
        //    }
        //    current = storage[index];
        //}
        //if (flags[index]) flags[index] = false;
        //storage[index] = t;
        //size++;
        //return true;
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        int index = startingIndex(o);
        Object current = storage[index];
        if (contains(current)) {
            System.out.println("AHHAAHHAHAHA");
            flags[index] = true;
            storage[index] = null;
            size--;
            return true;
        } else return false;
        }


    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        // TODO
        throw new NotImplementedError();
    }
}
