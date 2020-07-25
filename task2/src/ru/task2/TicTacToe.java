package ru.task2;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        start();
    }

    static void start() {
        final char playerSign = 'X';
        final char computerSign = 'Y';

        char[][] field = initField();
        String winnerName = null;

        drawField(field);

        do {

            // Ход игрока
            doPlayerMove(field, playerSign);
            // Перерисовка поля, чтобы увидеть поставленные фишки
            drawField(field);

            // Проверка на ничью
            if (checkDraw(field) == 9) {
                System.out.println("Ничья!");
                break;
            }

            // Проверка на победу
            if (checkWin(field, playerSign)) {
                winnerName = "Player";
                break;
            }

            // Ход компьютера
            doAIMove(field, computerSign);
            // Перерисовка поля, чтобы увидеть поставленные фишки
            drawField(field);

            // Проверка на ничью
            if (checkDraw(field) == 9) {
                System.out.println("Ничья!");
                break;
            }

            // Проверка на победу
            if (checkWin(field, computerSign)) {
                winnerName = "Computer";
                break;
            }

        } while (true);

        if (checkDraw(field) != 9) {
            System.out.println("Sir, congratulations!");
            System.out.println("You are winner Mr. " + winnerName);
        }

    }

    static void doAIMove(char[][] field, char sign) {
        Random random = new Random();
        System.out.println("Computer's move...");
        // Вводим координаты Х, Y
        int xVal = random.nextInt(3);
        int yVal = random.nextInt(3);

        // Закрываем ходы игрока по вертикали
        for (int j = 0; j < field.length; j++) {
            if (field[0][j] == 'X' && field[1][j] == 'X' && field[2][j] != 'Y') {
                xVal = 2;
                yVal = j;
            } else if (field[1][j] == 'X' && field[2][j] == 'X' && field[0][j] != 'Y') {
                xVal = 0;
                yVal = j;
            } else if (field[0][j] == 'X' && field[2][j] == 'X' && field[1][j] != 'Y') {
                xVal = 1;
                yVal = j;
            }
        }

        // Закрываем ходы игрока по горизонтали
        for (int i = 0; i < field.length; i++) {
            if (field[i][0] == 'X' && field[i][1] == 'X' && field[i][2] != 'Y') {
                xVal = i;
                yVal = 2;
            } else if (field[i][1] == 'X' && field[i][2] == 'X' && field[i][0] != 'Y') {
                xVal = i;
                yVal = 0;
            } else if (field[i][0] == 'X' && field[i][2] == 'X' && field[i][1] != 'Y') {
                xVal = i;
                yVal = 1;
            }
        }

        // Закрываем ходы игрока по главной диагонали
        if (field[0][0] == 'X' && field[1][1] == 'X' && field[2][2] != 'Y') {
            xVal = 2;
            yVal = 2;
        } else if (field[1][1] == 'X' && field[2][2] == 'X' && field[0][0] != 'Y') {
            xVal = 0;
            yVal = 0;
        } else if (field[0][0] == 'X' && field[2][2] == 'X' && field[1][1] != 'Y') {
            xVal = 1;
            yVal = 1;
        }

        // Закрываем ходы игрока по побочной диагонали
        if (field[0][2] == 'X' && field[1][1] == 'X') {
            xVal = 2;
            yVal = 2;
        } else if (field[1][1] == 'X' && field[2][0] == 'X') {
            xVal = 0;
            yVal = 2;
        }
        if (field[0][2] == 'X' && field[2][0] == 'X') {
            xVal = 1;
            yVal = 1;
        }

        // Если значение по координатам занято, то делаем перегенерацию координат, пока не найдем свободные
        while (field[xVal][yVal] != '-') {
            xVal = random.nextInt(3);
            yVal = random.nextInt(3);
        }

        System.out.println(String.format("Computer's X-value: %s", xVal + 1));
        System.out.println(String.format("Computer's Y-value: %s", yVal + 1));

        field[xVal][yVal] = sign;
    }

    static void doPlayerMove(char[][] field, char sign) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sir, you move...");

        // Вводим координаты Х, Y
        System.out.println("Please enter X-value [1-3]");
        int xVal = scanner.nextInt() - 1;
        System.out.println("Please enter Y-value [1-3]");
        int yVal = scanner.nextInt() - 1;

        // Проверка на диапазон значений, чтобы не выйти за пределы массива
        while (true) {
            if (xVal < 0 || xVal > 2) {
                System.out.println("Please, enter the normal coordinate of 'x', don`t extent beyond the length of field");
                xVal = scanner.nextInt() - 1;
            } else {
                break;
            }
        }
        while (true) {
            if (yVal < 0 || yVal > 2) {
                System.out.println("Please, enter the normal coordinate of 'у', don`t extent beyond the length of field");
                yVal = scanner.nextInt() - 1;
            } else {
                break;
            }
        }

        // Если значение по координатам занято, то повторяем ввод координат, пока не найдем свободные
        while (field[xVal][yVal] != '-') {
            System.out.println(String.format("Field[%s][%s] is already occupied", xVal + 1, yVal + 1));
            System.out.println("Please enter X-value [1-3]");
            xVal = scanner.nextInt() - 1;

            System.out.println("Please enter Y-value [1-3]");
            yVal = scanner.nextInt() - 1;
        }

        field[xVal][yVal] = sign;
    }

    // Проверка победы
    static boolean checkWin(char[][] field, char sign) {
        // По горизонтали
        for (int i = 0; i < field.length; i++) {
            if (field[i][0] == sign && field[i][1] == sign && field[i][2] == sign) {
                return true;
            }
        }

        // По вертикали
        for (int i = 0; i < field.length; i++) {
            if (field[0][i] == sign && field[1][i] == sign && field[2][i] == sign) {
                return true;
            }
        }

        // По главной диагонали
        if (field[0][0] == sign && field[1][1] == sign && field[2][2] == sign) {
            return true;
        }

        // По побочной диагонали
        if (field[0][2] == sign && field[1][1] == sign && field[2][0] == sign) {
            return true;
        }

        return false;
    }

    // Проверка ничьи
    static int checkDraw(char[][] field) {
        int cnt = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 'X' || field[i][j] == 'Y') {
                    cnt++;
                } else break;
            }
        }
        return cnt;
    }

    // Отрисока пока как матрицы
    static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    static char[][] initField() {
        return new char[][]{
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'},
        };
    }
}
