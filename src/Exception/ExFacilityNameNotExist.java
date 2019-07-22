package Exception;

public class ExFacilityNameNotExist extends Exception {
    public ExFacilityNameNotExist(){
        super("The facility name does not exist. Please type again.\nFacility Type: badminton, tableTennis, activityRoom");
    }
}
