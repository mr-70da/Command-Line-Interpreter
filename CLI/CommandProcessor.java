package CLI;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CommandProcessor {
    // private List<String> operand;

    public static List<Command> CommandParser(String comm) throws Exception {
        List<Command> ParsedCommands;
        Stack<Character> stk = new Stack<>();
        ParsedCommands = new ArrayList<Command>();
        String subs = new String("");
        boolean insertion = false;
        String opt = "";
        
        for (int i = 0; i < comm.length(); ++i) {
            if (comm.charAt(i) == '\\' && i + 1 < comm.length()) {
                subs += comm.charAt(i + 1);
                i++;
                continue;
            }
            if (comm.charAt(i) == '-' && !stk.empty() && subs.isEmpty() && i + 1 < comm.length() && !insertion) {
                opt = ""+comm.charAt(i + 1);
                if(opt ==" ")
                {
                    opt = "";
                    subs ="";
                }
                i++;
                continue;
            }
            if (comm.charAt(i) == '"' || comm.charAt(i) == '\'') {
                if (stk.empty()) {
                    stk.push(comm.charAt(i));
                } else if (stk.peek().equals(comm.charAt(i))) {
                    stk.pop();
                } else {
                    subs += comm.charAt(i);
                }
                continue;
            }
            if (comm.charAt(i) == '>' && i + 1 < comm.length() && comm.charAt(i + 1) == '>' && stk.empty()) {
                insertion = true;
                
                //ParsedCommands.add(new AppendInsertion());
                
                subs = "";
                continue;
            }
            if (comm.charAt(i) == '>' && stk.empty()) {
                insertion = true;
                //ParsedCommands.add(new OverwriteInsertion());
                
                subs = "";
                continue;
            }
            if (comm.charAt(i) == ' ') {
                if(!opt.isEmpty())
                {
                    Command temp = ParsedCommands.getLast();
                    if(temp instanceof OptionedCommand)
                    {
                        ((OptionedCommand)temp).setOption(""+opt);
                        ParsedCommands.set(ParsedCommands.size()-1, temp);
                    }   
                    temp.appendOperand(subs);
                    ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    continue;
                }
                if (subs.equals("cd") && stk.empty() && !insertion) {
                    ParsedCommands.add(new CD_Command());
                    subs = "";
                    continue;
                }
                if (subs.equals("touch") && stk.empty() && !insertion) {
                    //ParsedCommands.add(new TOUCH_Command());
                    subs = "";
                    continue;
                }
                if (subs.equals("mkdir") && stk.empty() && !insertion) {
                    //ParsedCommands.add(new MKDIR_Command(subs));
                    
                    subs = "";
                    continue;
                }
                if (subs.equals("ls") && stk.empty() && !insertion) {
                    ParsedCommands.add(new LS_Command());
                    subs = "";
                    continue;
                }
                if(stk.empty())
                {
                    if(ParsedCommands.isEmpty())
                        throw new InvalidInputException("Invalid Command!\n");
                    Command temp = ParsedCommands.getLast();
                    temp.appendOperand(subs);
                    ParsedCommands.set(ParsedCommands.size() - 1, temp);
                    subs = "";
                    continue;
                }
            }
            subs += comm.charAt(i);
        }
        if(!opt.isEmpty())
        {
            Command temp = ParsedCommands.getLast();
            if(temp instanceof OptionedCommand)
            {
                ((OptionedCommand)temp).setOption(""+opt);
                ParsedCommands.set(ParsedCommands.size()-1, temp);
            }   
            temp.appendOperand(subs);
            ParsedCommands.set(ParsedCommands.size() - 1, temp);   
        }
        else if (subs.equals("cd") && stk.empty() && !insertion) {
            ParsedCommands.add(new CD_Command());
            subs = "";
        }
        else if (subs.equals("touch") && stk.empty() && !insertion) {
            //ParsedCommands.add(new TOUCH_Command());
            subs = "";
        }
        else if (subs.equals("mkdir") && stk.empty() && !insertion) {
            //ParsedCommands.add(new MKDIR_Command(subs));
            subs = "";
        }
        else if (subs.equals("ls") && stk.empty() && !insertion) {
            ParsedCommands.add(new LS_Command());
            subs = "";
        }
        else if(stk.empty() && !subs.isEmpty())
        {
            if(ParsedCommands.isEmpty())
                throw new InvalidInputException("Invalid Command!\n");
            Command temp = ParsedCommands.getLast();
            temp.appendOperand(subs);
            ParsedCommands.set(ParsedCommands.size() - 1, temp);
            subs = "";

        }
        if (!stk.empty() || ParsedCommands.isEmpty())
            throw new InvalidInputException("Invalid Command!\n");
        // else
        // {
        //     Command temp = ParsedCommands.getLast();
        //     temp.appendOperand(subs);
        //     ParsedCommands.set(ParsedCommands.size() - 1, temp);
        // }
        return ParsedCommands;
    }

    // List<Command> getParsedCommand() {
    //     return this.ParsedCommands;
    // }
}
