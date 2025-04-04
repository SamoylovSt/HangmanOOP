import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class WordGenerator {

    public String getRandomWord() {
        File dictionary = new File("newnouns.txt");
        Random random = new Random();
        int randomNumber = random.nextInt(1000);

        Scanner scanner = null;

        try {
            scanner = new Scanner(dictionary);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла" + e.getMessage());
        }

        Map<Integer, String> words = new HashMap<>();
        int count = 0;

        while (scanner != null && scanner.hasNextLine()) {
            words.put(count, scanner.nextLine());
            count++;
        }
        return words.get(randomNumber);
    }
}