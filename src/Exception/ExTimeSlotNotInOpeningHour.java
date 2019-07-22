package Exception;

public class ExTimeSlotNotInOpeningHour extends Exception {
    public ExTimeSlotNotInOpeningHour(){
        super("\nThe opening hour is from 10am to 12am. Please enter another time slot.");
    }
}
