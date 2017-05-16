import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class yaTranslationFunReserch {

    public static void main(String[] args) throws Exception {
        Translate.setKey("trnsl.1.1.20170508T204041Z.28e80bf4ecfc6070.2c53fb29fe2e03715d5f594d7ea8ec10534faca7");


        Files.readAllLines(Paths.get("d:/in.txt")).stream()
                .map(yaTranslationFunReserch::translate)
                .forEach(yaTranslationFunReserch::writeToFile);


    }

    public static ArrayList<String> translate(String inLine) {
        inLine = inLine.replaceAll("[!?]\\s*", ".");
        StringTokenizer tokenizer = new StringTokenizer(inLine, ".", true);
        String line = "";
        int g = 0;
        ArrayList<String> lines = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            line += tokenizer.nextToken();
            g++;
            if (g % 10 == 0) {
                try {
                    for (int count = 0; count < 2; count++) {
                        line = Translate.execute(line, Language.RUSSIAN, Language.ENGLISH);
                        line = Translate.execute(line, Language.ENGLISH, Language.RUSSIAN);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lines.add(line);
                line = "";

            }


        }
        return lines;

    }


    public static void writeToFile(ArrayList<String> outLine) {


        try {

            FileWriter fileWriter = new FileWriter("d:/out.txt", true);
            fileWriter.write(outLine + System.getProperty("line.separator"));


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}