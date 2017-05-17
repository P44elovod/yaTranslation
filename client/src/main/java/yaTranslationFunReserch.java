import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class yaTranslationFunReserch {

    private static final Pattern TEXT_SPLIT_PATTERN = Pattern.compile("[!?]\\s*");

    public static void main(String[] args) {
        Translate.setKey("trnsl.1.1.20170508T204041Z.28e80bf4ecfc6070.2c53fb29fe2e03715d5f594d7ea8ec10534faca7");
        try {

            writeToFile(Files.readAllLines(Paths.get("d:/in.txt")).stream()
                    .map(s -> s.toString()
                            .replaceAll(TEXT_SPLIT_PATTERN.toString(), ". ")
                    )
                    .map(yaTranslationFunReserch::translate)
                    .reduce((s1, s2) -> {
                        List<String> list = new ArrayList<>();
                        list.add(s1.toString());
                        list.add(s2.toString());
                        return list;

                    }));
        } catch (IOException e) {
            System.out.println("что-то пошло не так :(");
            e.printStackTrace();
        }

    }

    public static List<String> translate(String inLine) {

        StringTokenizer tokenizer = new StringTokenizer(inLine, ".", true);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> lines = new ArrayList<>();
        int sentenceCounter = 0;
        while (tokenizer.hasMoreTokens()) {
            stringBuilder.append(tokenizer.nextToken());
            sentenceCounter++;
            if (sentenceCounter % 10 == 0) {
                try {
                    for (int count = 0; count < 2; count++) {
                        stringBuilder.replace(0, stringBuilder.length(), Translate.execute(stringBuilder.toString(), Language.RUSSIAN, Language.ENGLISH));
                        stringBuilder.replace(0, stringBuilder.length(), Translate.execute(stringBuilder.toString(), Language.ENGLISH, Language.RUSSIAN));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lines.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length() + 1);

            }


        }
        return lines;

    }


    public static void writeToFile(Optional<List<String>> outLine) {


        try {

            FileWriter fileWriter = new FileWriter("d:/out.txt", true);
            fileWriter.write(outLine + System.getProperty("line.separator"));


            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}