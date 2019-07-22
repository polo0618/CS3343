package Exception;

public class ExMaxFailLogin extends Exception {
    public ExMaxFailLogin(){
        super("You have reached the maximum fail login limit.");
    }
}
