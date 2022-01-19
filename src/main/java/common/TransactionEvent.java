package common;

public class TransactionEvent {
    public String station;
    public String customer;
    public Boolean finished;
    public Integer charged;
    public Integer total_charged;

    @Override
    public String toString() {
        return "simple_producer_consumer.TransactionEvent{" +
                "station='" + station + '\'' +
                ", customer='" + customer + '\'' +
                ", isFinished=" + finished +
                ", charged=" + charged +
                ", total_charged=" + total_charged +
                '}';
    }
}
