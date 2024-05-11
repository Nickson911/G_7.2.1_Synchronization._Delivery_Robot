package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final String letter = "RLRFR";
    public static final int generatedRoutes = 100;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < generatedRoutes; i++) {
            new Thread(() -> {
                String route = generateRoute(letter, generatedRoutes);
                int frequency = (int) route.chars()
                        .filter(x -> x == 'R')
                        .count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frequency)) {
                        sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
                    } else {
                        sizeToFreq.put(frequency, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.printf("Самое частое кол-во повторений %d (встретилось %d раз)%n", max.getKey(), max.getValue());

        System.out.println("Другие размеры: ");
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(x -> System.out.println(" - " + x.getKey() + " (" + x.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}