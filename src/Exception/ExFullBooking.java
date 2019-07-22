package Exception;

public class ExFullBooking extends Exception{
    public ExFullBooking(String sportCentreName, String facilityType, int timeslot){
        super("All " + facilityType + " has been fulled in the time slot: " + timeslot + " in " + sportCentreName+"\n"
        +"Please choose another time slot or sport centre.");
    }

}
