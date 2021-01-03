package framwork.test;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HarToYamlTest {
    public static final Logger logger = LoggerFactory.getLogger(HarToYamlTest.class);


    @Test
    public void harTest() throws HarReaderException {
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File("src/test/resources/har/hogwarts_wework.har"));
        logger.info("Debugger!");
    }
}
