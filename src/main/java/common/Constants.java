package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Constants {
    public static List<String> DEFAULT_CUSTOMER_NAMES = List.of("Alice", "Bob", "Eve", "Dave", "Mallory", "Oscar", "Peggy", "Trudy", "Trent");
    public static List<String> DEFAULT_LOCATIONS = List.of("Berlin", "München", "Dresden", "Leipzig", "Köln", "Düsseldorf");

    static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static List<Map.Entry<String, Customer>> genCustomers(int num) {
        List<Map.Entry<String, Customer>> currentCustomers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Customer customer = new Customer();
            String name = DEFAULT_CUSTOMER_NAMES.get(i % DEFAULT_CUSTOMER_NAMES.size()) + "_" + i;
            customer.isPremium = Math.random() > 0.5;
            customer.externalIds = Collections.emptyMap();
            customer.location = DEFAULT_LOCATIONS.get(randInt(0, DEFAULT_LOCATIONS.size() - 1));
            currentCustomers.add(Map.entry(name, customer));
        }
        return currentCustomers;
    }
}
