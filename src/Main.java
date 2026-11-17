import java.util.Scanner;
import java.math.BigInteger;

/**
 * Демонстрация различных способов умножения двух целых чисел
 * без использования оператора {@code *}.
 */

class Main {

    /**
     * Точка входа в программу. Считывает два целых числа с консоли,
     * вычисляет их произведение всеми реализованными способами
     * и выводит результаты на экран.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ввод данных пользователем
        System.out.println("Введите первое число: ");
        int n1 = sc.nextInt();
        System.out.println("Введите второе число: ");
        int n2 = sc.nextInt();

        // Вычисление произведения разными способами
        int res = multiplyFor(n1, n2);
        int res0 = multiplyWhile(n1, n2);
        int res1 = multiplyWithRecursion(n1, n2);
        int res2 = multiplyWithShifts(n1, n2);
        int res3 = multiplyDoWhile(n1, n2);

        // Для метода с BigInteger числа нужно предварительно преобразовать
        BigInteger Bn1 = BigInteger.valueOf(n1);
        BigInteger Bn2 = BigInteger.valueOf(n2);
        int res4 = multiplyBigInt(Bn1, Bn2);

        int res5 = multiplyDivision(n1, n2);
        int res6 = multiplyLog10(n1, n2);

        // Вывод результатов
        System.out.println("Итог: " + res);
        System.out.println("Итог0: " + res0);
        System.out.println("Итог1: " + res1);
        System.out.println("Итог2: " + res2);
        System.out.println("Итог3: " + res3);
        System.out.println("Итог4: " + res4);
        System.out.println("Итог5: " + res5);
        System.out.println("Итог6: " + res6);
    }

    /**
     * Умножение через последовательное сложение в цикле {@code for}.
     *
     * <p>Число {@code a} складывается само с собой {@code b} раз.</p>
     * <p>Сложность: O(b) по времени, O(1) по памяти.</p>
     * <p>Ограничение: корректно работает только при {@code b > 0}.
     * При отрицательном {@code b} цикл не выполняется и результат будет 0.
     * Переполнение {@code int} не отслеживается.</p>
     *
     * @param a множимое
     * @param b множитель (количество сложений)
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyFor(int a, int b) {
        // Если один из множителей равен нулю, результат всегда 0
        if (a == 0 || b == 0) {
            return 0;
        } else {
            int res = 0;
            // Прибавляем a к результату b раз
            for (int i = 1; i <= b; i++) {
                res = res + a;
            }
            return res;
        }
    }

    /**
     * Умножение через последовательное сложение в цикле {@code while}.
     *
     * <p>Сложность: O(b) по времени, O(1) по памяти.</p>
     * <p>Ограничение: корректно работает только при {@code b > 0}.
     * При отрицательном {@code b} результат будет 0.</p>
     *
     * @param a множимое
     * @param b множитель (количество сложений)
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyWhile(int a, int b) {
        // Нулевой множитель — сразу возвращаем 0
        if (a == 0 || b == 0) {
            return 0;
        } else {
            int res = 0;
            int i = 0; // счётчик выполненных сложений
            // Пока не сложили a ровно b раз
            while (i < b) {
                res = res + a;
                i++;
            }
            return res;
        }
    }

    /**
     * Умножение с помощью рекурсии: {@code a * b = a + a * (b - 1)}.
     *
     * <p>Сложность: O(b) по времени и по памяти (глубина рекурсии).
     * При больших {@code b} возможен {@link StackOverflowError}.</p>
     * <p>Ограничение: при отрицательном {@code b} вызывается расчёт для {@code -b},
     * но знак результата не восстанавливается, поэтому ответ получается
     * с неверным знаком. Для {@code b == Integer.MIN_VALUE} рекурсия бесконечна,
     * так как {@code -b} равно {@code b}.</p>
     *
     * @param a множимое
     * @param b множитель
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyWithRecursion(int a, int b) {
        // Базовый случай: умножение на ноль
        if (a == 0 || b == 0) {
            return 0;
        }
        // Базовый случай: умножение на единицу
        if (b == 1) {
            return a;
        }
        // Отрицательный множитель сводим к положительному
        if (b < 0) {
            return multiplyWithRecursion(a, -b);
        }
        // Рекурсивный шаг: a * b = a + a * (b - 1)
        return a + multiplyWithRecursion(a, b - 1);
    }

    /**
     * Умножение с помощью побитовых сдвигов.
     *
     * <p>Идея: {@code b} раскладывается по битам. Если текущий бит {@code b}
     * равен 1, к результату прибавляется текущее значение {@code a}.
     * Затем {@code a} сдвигается влево (удваивается), а {@code b} вправо
     * (делится на 2).</p>
     * <p>Сложность: O(log b) по времени, O(1) по памяти.</p>
     * <p>Ограничение: при отрицательном {@code b} оператор {@code >>=}
     * сохраняет знаковый бит, и {@code b} никогда не станет 0 (дойдёт до -1),
     * поэтому цикл будет бесконечным. Для корректной работы с отрицательными
     * числами нужно использовать беззнаковый сдвиг {@code >>>=}.</p>
     *
     * @param a множимое
     * @param b множитель
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyWithShifts(int a, int b) {
        int res = 0;
        while (b != 0) {
            // Если младший бит b равен 1 — добавляем текущее a к результату
            if ((b & 1) != 0) {
                res += a;
            }
            a <<= 1; // a *= 2
            b >>= 1; // b /= 2 (с сохранением знака)
        }
        return res;
    }

    /**
     * Умножение через последовательное сложение в цикле {@code do-while}.
     *
     * <p>Сложность: O(b) по времени, O(1) по памяти.</p>
     * <p>Ограничение: тело цикла выполняется минимум один раз, поэтому
     * при отрицательном {@code b} метод вернёт {@code a}, что неверно.</p>
     *
     * @param a множимое
     * @param b множитель (количество сложений)
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyDoWhile(int a, int b) {
        // Проверка на ноль нужна, т.к. do-while выполнится хотя бы раз
        if (a == 0 || b == 0) {
            return 0;
        } else {
            int res = 0;
            int i = 0; // счётчик выполненных сложений
            do {
                res = res + a;
                i++;
            }
            while (i < b);
            return res;
        }
    }

    /**
     * Умножение с использованием класса {@link BigInteger}.
     *
     * <p>Внутри используется метод {@link BigInteger#multiply(BigInteger)},
     * то есть формально это обычное умножение, просто «спрятанное» в библиотеке,
     * поэтому способ не соответствует условию задачи. Приведён для сравнения.</p>
     * <p>Результат приводится к {@code int} методом {@link BigInteger#intValue()},
     * при этом сохраняются только младшие 32 бита (как при переполнении {@code int}).</p>
     *
     * @param a множимое
     * @param b множитель
     * @return произведение {@code a} и {@code b}, усечённое до {@code int}
     */
    public static int multiplyBigInt(BigInteger a, BigInteger b) {
        BigInteger res = a.multiply(b);
        // Приводим большое число к int (возможна потеря старших разрядов)
        return res.intValue();
    }

    /**
     * Умножение с помощью деления и удвоения (аналог метода со сдвигами,
     * но с арифметическими операциями вместо побитовых).
     *
     * <p>Если {@code b} нечётно, к результату прибавляется {@code a}.
     * Затем {@code a} удваивается, а {@code b} делится на 2 нацело.</p>
     * <p>Сложность: O(log b) по времени, O(1) по памяти.</p>
     * <p>Ограничение: работает только при {@code b >= 0}. При отрицательном
     * {@code b} цикл не выполняется и результат равен 0.</p>
     *
     * @param a множимое
     * @param b множитель
     * @return произведение {@code a} и {@code b}
     */
    public static int multiplyDivision(int a, int b) {
        int res = 0;
        while (b > 0) {
            // Нечётное b — добавляем текущее a
            if (b % 2 == 1) {
                res += a;
            }
            a += a;  // удвоение a без умножения
            b /= 2;  // целочисленное деление b на 2
        }
        return res;
    }

    /**
     * Умножение через логарифмы: {@code a * b = 10^(log10|a| + log10|b|)}.
     *
     * <p>Сложность: O(1), но результат вычисляется приближённо
     * (используется {@code double}), поэтому для больших значений
     * возможны ошибки округления.</p>
     * <p>Ограничение: знак определяется неверно, если оба числа отрицательные
     * (результат должен быть положительным, а метод вернёт отрицательный).</p>
     *
     * @param a множимое
     * @param b множитель
     * @return приближённое произведение {@code a} и {@code b}
     */
    public static int multiplyLog10(int a, int b) {
        // Логарифм нуля не определён, поэтому обрабатываем отдельно
        if (a == 0 || b == 0) {
            return 0;
        }
        // Определяем знак результата
        int sign = ((a < 0) || (b < 0)) ? -1 : 1;
        // Логарифм определён только для положительных чисел — берём модули
        double x = Math.abs(a);
        double y = Math.abs(b);
        // log(x) + log(y) = log(x * y)
        double logSum = Math.log10(x) + Math.log10(y);
        // Возвращаемся от логарифма к числу
        double result = Math.pow(10, logSum);
        // Восстанавливаем знак
        if (sign < 0) {
            result = -result;
        }
        // Округляем до ближайшего целого
        return (int) Math.round(result);
    }
}