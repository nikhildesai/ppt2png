import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

public class FileConverterTest {

    FileConverter converter = new FileConverter();

    @Test
    public void test() throws InvalidFormatException, IOException {
        converter.convert("/Users/ndesai/Downloads/MDS_MVP_phases.pptx", 1, -1);
    }
}
