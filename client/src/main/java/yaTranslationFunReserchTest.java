import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.Assert.*;


public class yaTranslationFunReserchTest {
    @Test
    public void main() throws Exception {

        yaTranslationFunReserch.translation(Paths.get("C:\\yaTranslation-master\\resources\\in.txt"));
        List<String> fileSample = Files.readAllLines(Paths.get("C:\\yaTranslation-master\\resources\\outSample.txt"));
        List<String> fileResult = Files.readAllLines(Paths.get("C:\\yaTranslation-master\\resources\\out.txt"));

for (int i = 0; i<fileSample.size(); i++){
    assertTrue("Результат сравнения строки № "+i, fileSample.get(i).equals(fileResult.get(i)));
}





    }

}