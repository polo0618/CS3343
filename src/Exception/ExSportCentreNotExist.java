package Exception;

public class ExSportCentreNotExist extends Exception {
    public ExSportCentreNotExist(){
        super("The input Sport Centre ID does not exist!");
    }
    public ExSportCentreNotExist (String message) {
        super(message);
    }
}

