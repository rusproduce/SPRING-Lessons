package MyWorks;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.println("Введите математическое выражение:");
            String input = scn.nextLine();
            if (input.equals("q")) {
                scn.close();
                break;
            }
            input = input.replace(" ", "")
                    .replace(",", ".")
                    .replaceAll("[:\\\\]", "/")
                    .replaceAll("\\)\\(", "\\)*\\(");
            input = zamenaUmn(input);
            String inputNew = input;
            String part;
            if (check(input)) {
                for (int i = 0; i < input.replaceAll("[^()]", "").length() / 2; i++) {
                    int x = inputNew.lastIndexOf('(');
                    int y = inputNew.indexOf(')', x);
                    part = inputNew.substring(x + 1, y);
                    if (checkPart(part)) {
                        part = Double.toString(math(part));
                        inputNew = zamena(inputNew, part, x, y + 1);
                    } else {
                        break;
                    }
                }
                if (checkPart(inputNew)) {
                    System.out.println("Ответ: " + math(inputNew));
                    System.out.println();
                }
            }
        }
        scn.close();
    }

    public static boolean check(String input) {
        // matches
        if (input.matches("([+-])?\\d+([.]\\d+)?([*/+-]\\d+([.]\\d+)?)*")) {
            System.out.println("Ответ: " + math(input));
            System.out.println();
            return false;
        } else {
            String checkIn = input.replaceAll("[^()]", "");
            if (checkIn.equals("")) {
                System.out.println("Некорректное выражение");
                System.out.println(input);
                System.out.println();
                return false;
            }
            for (int i = 0; i < checkIn.length(); i++) {
                checkIn = checkIn.replaceAll("(\\(\\))", "");
                if (checkIn.equals(""))
                    break;
            }
            if (checkIn.equals("") && !input.contains("x")) {
                return true;
            } else {
                System.out.println("Проблема со скобками или по ошибке добавили " + "X:");
                System.out.println(input);
                System.out.println();
                return false;
            }
        }
    }

    public static boolean checkPart(String part) {
        part = part.replace("*x*", "*")
                .replace("/x/", "/");
        if (part.matches("([+-])?\\d+([.]\\d+)?([*/+-]\\d+([.]\\d+)?)*")) {            // ПРОВЕРКА
            return true;
        }
        System.out.println("Некорректно записана часть исходного выражения:");
        System.out.println(part);
        System.out.println();
        return false;
    }

    public static double math(String input) {
        String[] inputMas = input
                .replace("+", " +")
                .replace("-", " -")
                .trim()
                .split(" ");
        double sum = 0;
        // получили массив с +, -
        for (String inputMa : inputMas) {
            sum += strToDbl(inputMa);
        }
        return sum;
    }

    public static double strToDbl(String part) {
        if (part.matches("([+-])?\\d+([.]\\d+)?")) {
            return Double.parseDouble(part);
        }
        part = part.replaceAll("x", "-1");
        String[] partMas = part.split("[*/]");
        String[] znak = part.split("[^*/]+");
        double[] chisla = new double[partMas.length];
        for (int i = 0; i < partMas.length; i++) {
            chisla[i] = Double.parseDouble(partMas[i]);
        }
        double chislo = chisla[0];
        for (int i = 1; i < znak.length; i++) {
            switch (znak[i]) {
                case "*":
                    chislo = chislo * chisla[i];
                    break;
                case "/":
                    chislo = chislo / chisla[i];
                    break;
            }
        }
        return chislo;
    }

    public static String zamena(String str, String part, int from, int to) {
        str = str.substring(0, from) + part + str.substring(to);
        if (part.contains("-") && !str.substring(0, from).equals("")) {
            switch (str.substring(from - 1, from + 1)) {
                case "--":
                    str = str.replace("--", "+");
                case "+-":
                    str = str.replace("+-", "-");
                case "*-":
                    str = str.replace("*-", "*x*");
                case "/-":
                    str = str.replace("/-", "/x/");
            }
            return str;
        } else {
            return str;
        }
    }

    public static String zamenaUmn(String input) {
        for (int i = 0; i < 10; i++) {
            if (!(input.matches(".*\\d+\\(.+") || input.matches(".+\\)\\d+.*"))) {
                break;
            }
            input = input.replaceAll(Integer.toString(i) + "\\(", Integer.toString(i) + "*" + "(")
                    .replaceAll("\\)" + Integer.toString(i), ")" + "*" + Integer.toString(i));
  //          System.out.println(i + "   " + input);
        }
        return input;

    }
}