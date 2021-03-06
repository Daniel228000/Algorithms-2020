package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Трудоемкость(T): О(n * m)
     * Ресурсоемкость(R): О(n * m)
     * n, m — длины строк.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int firstLen = first.length();
        int secondLen = second.length();
        int[][] matrix = new int[firstLen + 1][secondLen + 1];
        for(int i = 1; i < firstLen + 1; i++) {
            for (int j = 1; j < secondLen + 1; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }
        StringBuilder string = new StringBuilder();
            while (firstLen > 0 && secondLen > 0) {
                //Character char1 = first.charAt(firstLen - 1);
                //Character char2 = second.charAt(secondLen - 1);
                if (first.charAt(firstLen - 1) == second.charAt(secondLen - 1)){
                    string.append(first.charAt(firstLen - 1));
                    firstLen--;
                    secondLen--;
                } else if (matrix[firstLen - 1][secondLen] > matrix[firstLen][secondLen - 1])
                    firstLen--;
                else secondLen--;
            }
            return string.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Трудоемкость(T): О(nlogn)
     * Ресурсоемкость(R): О(n)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        int[] a = new int[list.size()];
        int[] b = new int[list.size() + 1];
        int max = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            int count = 1;
            int c = max;
            while (count <= c) {
                int mid = (int) Math.ceil((count + c) / 2);
                if (list.get(b[mid]) <= list.get(i)) {
                    c = mid - 1;
                } else {
                    count = mid + 1;
                }
            }
            int newMax = count;
            a[i] = b[newMax - 1];
            b[newMax] = i;
            if (newMax > max) max = newMax;
        }
        int k = b[max];
        for (int i = max - 1; i >= 0; i--) {
            result.add(list.get(k));
            k = a[k];
        }
        return  result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
