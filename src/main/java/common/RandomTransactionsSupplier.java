package common;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static common.Constants.DEFAULT_CUSTOMER_NAMES;


public class RandomTransactionsSupplier implements Supplier<Map.Entry<String, TransactionEvent>> {
    Map<String, Integer> charger_speeds = new HashMap<>();
    List<Customer> currentCustomers = new ArrayList<>();
    int current_customer = 0;

    private static class Customer {
        public String name;
        public Instant nextChargingSession;
        public Boolean isCharging;
        public Integer batteryLevel;
        public Integer currentChargingTx;
        public Integer capacity;
        public String chargingStation;
        public String txId;
    }

    public RandomTransactionsSupplier() {
        this(5);
    }

    public RandomTransactionsSupplier(int numCustomers) {
        charger_speeds.put("BER", 15);
        charger_speeds.put("HAM", 5);
        charger_speeds.put("MUC", 15);
        charger_speeds.put("CGN", 5);
        charger_speeds.put("FFX", 15);
        charger_speeds.put("STG", 5);
        charger_speeds.put("DUS", 5);
        charger_speeds.put("DRS", 5);
        genCustomers(numCustomers);
    }

    private void genCustomers(int num) {
        for (int i = 0; i < num; i++) {
            Customer customer = new Customer();
            customer.name = DEFAULT_CUSTOMER_NAMES.get(i % DEFAULT_CUSTOMER_NAMES.size()) + "_" + i;
            customer.nextChargingSession = Instant.now().plusSeconds(Constants.randInt(0, 10));
            customer.isCharging = Math.random() > 0.5;
            customer.batteryLevel = Constants.randInt(0, 50);
            customer.currentChargingTx = 0;
            customer.capacity = Constants.randInt(50, 150);
            List<String> stations = new ArrayList<>(charger_speeds.keySet());
            customer.chargingStation = stations.get(Constants.randInt(0, stations.size() - 1));
            customer.txId = "chrg_" + customer.name + "_" + Constants.randInt(100000, 1000000);
            currentCustomers.add(customer);
        }
    }

    @Override
    public Map.Entry<String, TransactionEvent> get() {
        Customer customer = currentCustomers.get(current_customer);
        current_customer = (current_customer + 1) % currentCustomers.size();
        int station_speed = charger_speeds.get(customer.chargingStation);

        if (customer.isCharging) {
            TransactionEvent transactionEvent = new TransactionEvent();
            int toAdd = Math.min(Constants.randInt(1, station_speed), customer.capacity - customer.batteryLevel);
            customer.currentChargingTx += toAdd;
            customer.batteryLevel += toAdd;
            boolean isFinished = customer.batteryLevel >= customer.capacity;
            transactionEvent.station = customer.chargingStation;
            transactionEvent.customer = customer.name;
            transactionEvent.finished = isFinished;
            transactionEvent.charged = toAdd;
            transactionEvent.total_charged = customer.currentChargingTx;
            if (isFinished) {
                customer.isCharging = false;
                customer.nextChargingSession = Instant.now().plusSeconds(Constants.randInt(2, 10));
                customer.batteryLevel = Constants.randInt(0, customer.capacity);
                customer.currentChargingTx = 0;
                List<String> stations = new ArrayList<>(charger_speeds.keySet());
                customer.chargingStation = stations.get(Constants.randInt(0, stations.size() - 1));
                customer.txId = "chrg_" + customer.name + "_" + Constants.randInt(100000, 1000000);
            }
            return Map.entry(customer.name, transactionEvent);
        } else {
            if (Instant.now().isAfter(customer.nextChargingSession)) {
                customer.isCharging = true;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return get();
        }
    }
}
