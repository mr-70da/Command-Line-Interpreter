package CLI;

import java.util.ArrayList;
import java.util.List;

public class MkDir_Command implements Command {
    private List<String> operands;

    public MkDir_Command() {
        this.operands = new ArrayList<>();
    }

    public MkDir_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
            
    }

}
