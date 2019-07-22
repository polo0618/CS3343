package Exception;

public class ExFacilityIdNotExist extends Exception {
    public ExFacilityIdNotExist(){
        super("Your inputted facility id does not exist!");
    }
    public ExFacilityIdNotExist(String message){
        super(message);
    }
}
