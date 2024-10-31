package CLI;
import java.util.ArrayList;
import java.util.List;

public class CD_Command implements Command {
    private List<String> operands;
    public CD_Command() {
        this.operands = new ArrayList<>();
    }
    public CD_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
    }
    public void execute()
    {

    }
    public void appendOperand(String op)
    {
        this.operands.add(op);
    }
}
