package Exception;

public class ExBookingNotExist extends Exception {
    public ExBookingNotExist () {
        super("Booking ID does not exist! Cannot delete!");
    }
}
