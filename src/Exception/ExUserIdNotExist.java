package Exception;

public class ExUserIdNotExist extends Exception {
    public ExUserIdNotExist (String userid) {
        super(userid + "does not exist!");
    }
}
