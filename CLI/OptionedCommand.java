package CLI;

public interface OptionedCommand extends Command {
    void setOption(String opt) throws Exception;
}
