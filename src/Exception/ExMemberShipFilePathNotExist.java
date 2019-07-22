package Exception;

public class ExMemberShipFilePathNotExist extends Exception {
    public ExMemberShipFilePathNotExist(){
        super("The membership files does not exist. Please check the path again.");
    }
}
