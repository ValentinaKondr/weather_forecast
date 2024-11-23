package org.example.service;

import org.example.domain.Operation;
import org.example.service.impl.ForecastServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {
    public static class ConsoleConstants {
        public static final String Latitude = "Введите ширину в координатах: ";
        public static final String Longitude = "Введите долготу в координатах: ";
        public static final String Days = "Введите инетересующий вас период в днях: ";
    }
    private final ForecastService forecastService = new ForecastServiceImpl();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        final List<String> operationNames = List.of("Получить прогноз", "Получить температуру в данный момент", "Получить среднюю температуру за период");
        while (true) {
            System.out.println("Введите команду:");
            List<String> operations = Arrays.stream(Operation.values()).map(operation -> operation.ordinal() + ": " + operationNames.get(operation.ordinal())).toList();
            for (String operation : operations) {
                System.out.println(operation);
            }
            double latitude;
            double longitude;
            int limit;
            try {
                switch (Operation.getByOrdinal(scanner.nextInt())) {
                    case GET_FORECAST -> {
                        System.out.println(ConsoleConstants.Latitude);
                        latitude = 55.44; //scanner.nextDouble();
                        System.out.println(ConsoleConstants.Longitude);
                        longitude = 37.36;//scanner.nextDouble();
                        System.out.println(forecastService.getForecast(latitude, longitude));
                    }
                    case GET_CURRENT_TEMPERATURE -> {
                        System.out.println(ConsoleConstants.Latitude);
                        latitude = 55.44; //scanner.nextDouble();
                        System.out.println(ConsoleConstants.Longitude);
                        longitude = 37.36; //scanner.nextDouble();
                        System.out.println(
                                "Температура: " +
                                        forecastService.getTemperature(latitude, longitude) + "°C");
                    }
                    case GET_AVERAGE_TEMPERATURE -> {
                        System.out.println(ConsoleConstants.Latitude);
                        latitude = 55.44;//scanner.nextDouble();
                        System.out.println(ConsoleConstants.Longitude);
                        longitude = 37.36; //scanner.nextDouble();
                        System.out.println(ConsoleConstants.Days);
                        limit = 5;//scanner.nextInt();
                        System.out.println(
                                "Средняя температура за период %d д.: ".formatted(limit) +
                                        "%.5s".formatted(forecastService.getAverageTemperature(latitude, longitude, limit) + "°C")
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.printf("Ошибка ввода%n%s%n", ex.getMessage());
                scanner.next();
            }
        }
    }
}
