package Testing;

import CLI.LS_Command;
import CLI.MkDir_Command;
import CLI.Pipe_Command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;

public class PipeTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Pipe_Command pipeCommand;
    private Pipe_Command pipeCommand2;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        pipeCommand = new Pipe_Command(testDirectory); // Use custom directory for testing
    }
    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
    }

    private void deleteDirectory(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }
    @Test
    void NoOutPutOfLHS() throws Exception
    {
        LS_Command ls = new LS_Command("");
        MkDir_Command mkdir = new MkDir_Command(testDir+"/test");
        ls.execute();
        pipeCommand.execute(ls.Output());
        mkdir.execute(pipeCommand.Output());
        //File dir = new File(testDir,"test");
        assertEquals("",pipeCommand.getInput());
    }
    @Test
    void RHSExecuted() throws Exception
    {
        LS_Command ls = new LS_Command("");
        MkDir_Command mkdir = new MkDir_Command(testDir+"/test");
        ls.execute();
        pipeCommand.execute(ls.Output());
        mkdir.execute(pipeCommand.Output());
        File dir = new File(testDir,"test");
        assertTrue("RHS was not excuted",dir.exists());
    }
    @Test
    void MuitiplePipes() throws Exception
    {
        MkDir_Command mkdir1 = new MkDir_Command(testDir+"/test1");
        MkDir_Command mkdir2 = new MkDir_Command(testDir+"/test2");
        MkDir_Command mkdir3 = new MkDir_Command(testDir+"/test3");
        pipeCommand = new Pipe_Command();
        pipeCommand2 = new Pipe_Command();
        mkdir1.execute();
        pipeCommand.Input("");
        mkdir2.execute();
        pipeCommand.Input("");
        mkdir3.execute();
        File dir1 = new File(testDir,"test1");
        File dir2 = new File(testDir,"test2");
        File dir3 = new File(testDir,"test3");
        boolean check = dir1.exists() && dir2.exists() && dir3.exists();
        assertTrue("Error Occured During Multiple Pipes",check);

    }
}
