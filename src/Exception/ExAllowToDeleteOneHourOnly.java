package Exception;

public class ExAllowToDeleteOneHourOnly extends Exception {
    public ExAllowToDeleteOneHourOnly(){
        super("You are allowed to book 1 hour only for each booking. Please input again.");
    }
}
