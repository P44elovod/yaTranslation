
import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class yaTranslationFunReserch {

    public static void main(String[] args) throws Exception {
        Translate.setKey("trnsl.1.1.20170508T204041Z.28e80bf4ecfc6070.2c53fb29fe2e03715d5f594d7ea8ec10534faca7");


        writeToFile(
                Files.readAllLines(Paths.get("d:/in.txt")).stream()
                        .map(yaTranslationFunReserch::translate)
                        .collect(Collectors.toList()));


    }

    public static Optional<String> translate(String inLine) {
        Optional translate = Optional.empty();
        try {
            for (int count = 0; count < 100; count++) {
                translate = Optional.of(Translate.execute(inLine, Language.RUSSIAN, Language.ENGLISH));
                translate = Optional.of(Translate.execute(translate.toString(), Language.ENGLISH, Language.RUSSIAN));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translate;

    }


    public static void writeToFile(List<String> outLine) {

        try {

            FileWriter fileWriter = new FileWriter("d:/out.txt", false);
            fileWriter.write(outLine + System.getProperty("line.separator"));


            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}