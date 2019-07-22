package Exception;

public class ExInputTimeEarlierThanCurrentTime extends Exception {
    public ExInputTimeEarlierThanCurrentTime(){
            super("Your input time has passed. Please enter a time slot later than the current time.");
    }

}
