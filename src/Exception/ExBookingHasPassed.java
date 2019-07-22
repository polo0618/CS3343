package Exception;

public class ExBookingHasPassed extends Exception {
    public ExBookingHasPassed(){
        super("Your booking has passed. Booking cannot be cancelled.");
        }
}

