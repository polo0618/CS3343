package Exception;

public class ExAllowToBookOneHourOnly extends Exception {
    public ExAllowToBookOneHourOnly(){
        super("You are allowed to book 1 hour only for each booking. Please input again.");
    }
}
