package CLI;

import java.util.List;
import java.util.ArrayList;

public class CommandProcessor {

    public static List<Executable> CommandParser(String comm) throws Exception {
        List<Executable> ParsedCommands;
        ParsedCommands = new ArrayList<>();
        String subs = new String("");
        boolean insertion = false;
        boolean inQuotes = false;
        boolean escaped = false;
        boolean foundCommand = false;
        char quoteType = '\0';
        String opt = "";
        
        for (int i = 0; i < comm.length(); ++i) {
            
            if(escaped)
            {
                subs+=(comm.charAt(i));
                escaped = false;
                continue;
            }
            if (comm.charAt(i) == '\\') {
                escaped = true;
                continue;
            }
            if (comm.charAt(i) == '-' && !inQuotes && foundCommand && subs.isEmpty() && i + 1 < comm.length() && !insertion) {
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
                if (!inQuotes) {
                    inQuotes = true;
                    quoteType = comm.charAt(i);
                }
                else if (comm.charAt(i) == quoteType) {
                    inQuotes = false;
                    quoteType = '\0';
                }
                else {
                    subs+= comm.charAt(i);
                }
                continue;
            }
            if (comm.charAt(i) == '>' && i + 1 < comm.length() && comm.charAt(i + 1) == '>' && !inQuotes) {
                insertion = true;
                if(!opt.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();
                    if(temp instanceof OptionedCommand)
                    {
                        ((OptionedCommand)temp).setOption(""+opt);
                        ParsedCommands.set(ParsedCommands.size()-1, temp);
                    }   
                    else if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(opt);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);
                        subs = "";
                    }
                    opt = "";   
                }
                if(!subs.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();   
                    if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(subs);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    }
                }
                ParsedCommands.add(new InsertAppend_Command());
                subs = "";
                i++;
                continue;
            }
            if (comm.charAt(i) == '>' && !inQuotes) {
                insertion = true;
                if(!opt.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();
                    if(temp instanceof OptionedCommand)
                    {
                        ((OptionedCommand)temp).setOption(""+opt);
                        ParsedCommands.set(ParsedCommands.size()-1, temp);
                    }   
                    else if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(opt);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);
                        subs = ""; 
                    }
                    opt = "";
                }
                if(!subs.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();   
                    if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(subs);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    }
                }
                ParsedCommands.add(new Insertion_Command());
                subs = "";
                continue;
            }
            if(comm.charAt(i) == '|' && !inQuotes)
            {
                insertion = false;
                foundCommand = false;
                if(!opt.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();
                    if(temp instanceof OptionedCommand)
                    {
                        ((OptionedCommand)temp).setOption(""+opt);
                        ParsedCommands.set(ParsedCommands.size()-1, temp);
                    }   
                    else if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(opt);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    }
                    opt = "";
                    
                }
                if(!subs.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();   
                    if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(subs);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    }
                }
                ParsedCommands.add(new Pipe_Command());
                subs = "";
                foundCommand = false;
                continue;
            }
            if (comm.charAt(i) == ' ') {
                if(!opt.isEmpty())
                {
                    Executable temp = ParsedCommands.getLast();
                    if(temp instanceof OptionedCommand)
                    {
                        ((OptionedCommand)temp).setOption(""+opt);
                        ParsedCommands.set(ParsedCommands.size()-1, temp);
                    }   
                    else if(temp instanceof Command)
                    {
                        ((Command)temp).appendOperand(opt);
                        ParsedCommands.set(ParsedCommands.size() - 1, temp);   
                    }
                    opt = "";
                    continue;
                }
                if (subs.equals("cd") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new CD_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("touch") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new Touch_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("pwd") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new PWD_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("cat") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new Concat_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("mkdir") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new MkDir_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("rmdir") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new RmDir_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("rm") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new Rm_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("mv") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new Mv_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if (subs.equals("ls") && !inQuotes && !insertion && !foundCommand) {
                    ParsedCommands.add(new LS_Command());
                    subs = "";
                    foundCommand = true;
                    continue;
                }
                if(!subs.isEmpty())
                {
                    if(!inQuotes)
                    {
                        if(ParsedCommands.isEmpty())
                            throw new InvalidInputException("Invalid Command!\n");
                        Executable temp = ParsedCommands.getLast();
                        if(temp instanceof CD_Command && !((CD_Command)temp).emptyOperand())
                            throw new TooManyArgsException("cd: Too Many Arguments!\n");
                        if(temp instanceof Command)
                        {
                            ((Command)temp).appendOperand(subs);
                            ParsedCommands.set(ParsedCommands.size() - 1, temp);
                        }
                        // else{
                        //     //temp.output = "";
                        // }
                        subs = "";
                        continue;
                    }
                    subs += comm.charAt(i);
                }
                continue;
            }
            if(!opt.isEmpty())
            {
                opt+=comm.charAt(i);
            }
            else
            {
                subs+=comm.charAt(i);
            }
        }
        if(!opt.isEmpty())
        {
            Executable temp = ParsedCommands.getLast();
            if(temp instanceof OptionedCommand)
            {
                ((OptionedCommand)temp).setOption(""+opt);
                ParsedCommands.set(ParsedCommands.size()-1, temp);
            }
            if(temp instanceof Command)
            {
                ((Command)temp).appendOperand(subs);
                ParsedCommands.set(ParsedCommands.size() - 1, temp);
            }
        }
        else if (subs.equals("cd") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new CD_Command());
            subs = "";
        }
        else if (subs.equals("touch") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new Touch_Command());
            subs = "";
        }
        else if(subs.equals("pwd") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new PWD_Command());
            subs = "";
        }
        else if (subs.equals("cat") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new Concat_Command());
            subs = "";
            foundCommand = true;

        }
        else if (subs.equals("mkdir") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new MkDir_Command());
            subs = "";
        }
        else if (subs.equals("ls") && !inQuotes && !insertion && !foundCommand) {
            ParsedCommands.add(new LS_Command());
            subs = "";
        }
        else if(!inQuotes && !subs.isEmpty())
        {
            if(ParsedCommands.isEmpty())
                throw new InvalidInputException("Invalid Command!\n");
            Executable temp = ParsedCommands.getLast();
            if(temp instanceof Command)
            {
                ((Command)temp).appendOperand(subs);
                ParsedCommands.set(ParsedCommands.size() - 1, temp);
            }
            subs = "";
        }
        if (inQuotes || ParsedCommands.isEmpty())
            throw new InvalidInputException("Invalid Command!\n");
        
        return ParsedCommands;
    }

    
}
