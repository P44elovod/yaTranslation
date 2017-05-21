import com.rmtheis.yandtran.language.Language;
import com.rmtheis.yandtran.translate.Translate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class yaTranslationFunReserch {

    private static final Pattern TEXT_SPLIT_PATTERN = Pattern.compile("[!?]");


    public static void main(String[] args) {
      translation(Paths.get("C:\\yaTranslation-master\\resources\\in.txt"));

    }

    static List<String> translate(String inLine) {
        List<String> lines = new ArrayList<>();
        List<String> translatedLines = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(inLine, ".", true);
        StringBuilder stringBuilder = new StringBuilder();


        while (tokenizer.hasMoreTokens()) {

            lines.add(tokenizer.nextToken());
        }

        if (lines.size() / 10 == 0) {
            for (int i = 0; i < lines.size() % 10; i++) {
                stringBuilder.append(lines.get(i));
            }
        } else {
            for (int i = 0; i < lines.size() / 10 + 1; i++) {
                if (i == lines.size() / 10) {
                    for (int j = 0; j < lines.size() % 10; j++) {
                        stringBuilder.append(lines.get(j));
                    }
                }
                stringBuilder.append(lines.get(i));
            }

        }


        try {
            for (int count = 0; count < 1; count++) {
                stringBuilder.replace(0, stringBuilder.length(), Translate.execute(stringBuilder.toString(), Language.RUSSIAN, Language.ENGLISH));
                stringBuilder.replace(0, stringBuilder.length(), Translate.execute(stringBuilder.toString(), Language.ENGLISH, Language.RUSSIAN));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        translatedLines.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();


        return translatedLines;
    }


    static void writeToFile(Optional<List<String>> outLine) {

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> result = new ArrayList<>();
        outLine.ifPresent(result::addAll);

        for (String s : result) {

            stringBuilder.append(s + System.getProperty("line.separator"));

        }

        try {

            FileWriter fileWriter = new FileWriter("C:\\yaTranslation-master\\resources\\out.txt", true);
            fileWriter.write(stringBuilder.toString() + System.getProperty("line.separator") + "\n");

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void translation(Path path) {
        Translate.setKey("trnsl.1.1.20170508T204041Z.28e80bf4ecfc6070.2c53fb29fe2e03715d5f594d7ea8ec10534faca7");
        try {

            writeToFile(
                    Files.readAllLines(path).stream()
                            .map(s -> s.replaceAll(TEXT_SPLIT_PATTERN.toString(), ". "))
                            .map(yaTranslationFunReserch::translate)
                            .reduce((s1, s2) -> {
                                s1.addAll(s2);
                                return s1;
                            }));
        } catch (IOException e) {
            System.out.println("что-то пошло не так :(");
            e.printStackTrace();
        }

    }

}