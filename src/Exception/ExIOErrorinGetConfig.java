package Exception;

public class ExIOErrorinGetConfig extends Exception{
    public ExIOErrorinGetConfig() {
        super("IO exception found when getConfig(), please check the config file again.");
    }
}
