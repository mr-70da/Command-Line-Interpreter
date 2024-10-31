package Testing;

import CLI.PWD_Command;
import CLI.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PWD_CommandTest {
    private File initialDir;
    private PWD_Command pwdCommand;

    @BeforeEach
    void setUp() {
        initialDir = CLI.getDirr();
        pwdCommand = new PWD_Command();
    }

    @AfterEach
    void tearDown() {
        CLI.setDirr(initialDir);
    }

    @Test
    void testExecuteReturnsCurrentDirectoryPath() {
        pwdCommand.execute();
        String expectedOutput = initialDir.getAbsolutePath() + "\n";
        assertEquals(expectedOutput, pwdCommand.Output(), "The output should match the current directory path.");
    }
}
