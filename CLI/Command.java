package CLI;


public interface Command extends Executable { //fakess abstract
    //private String operator;
    // private List<String> operands;
    // Command(String op)
    // {
    //     //this.operator = op;
    //     operands = new ArrayList<>();
    // }
    public void appendOperand(String oper);
}
