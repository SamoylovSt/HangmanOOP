import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;


class Word{

    public  String randomWord(){
        File dictionary = new File("newnouns.txt");
        Random random = new Random();
        int randomNumber = random.nextInt(1000);

        Scanner scanner = null;
        try {
            scanner = new Scanner(dictionary);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла"+e.getMessage());
        }

        Map<Integer, String> words = new HashMap<>();
        int count = 0;

        while (scanner!=null && scanner.hasNextLine()) {

            words.put(count, scanner.nextLine());
            count++;
        }



        return words.get(randomNumber);
    }

}

class Interface {

    private   void gameLoop() {
        Word hangman =new Word();
        int countMistake = 0;

        String word = hangman.randomWord();              // загаданное слово
        char[] wordChar = word.toCharArray();        // слово в массив чаров
        char[] lastWord = new char[wordChar.length]; // lastWord массив для содержания и вывода уже отгаданного

        Arrays.fill(lastWord, '_');

        int countWin = wordChar.length;// счетчик отгаданного,чтобы закончить игру

        StringBuilder rightLetter = new StringBuilder(); //для  вывода отгаданных букв
        StringBuilder wrongLetter = new StringBuilder(); //для вывода ошибок

        hangmanPicture(countMistake);
        String firstOutEmptyWord = new String(lastWord);
        System.out.println("Слово загадано, отгадывай по одной букве на киррилице, при вводе нескольких учитывается только первая");
        System.out.println(firstOutEmptyWord);

        while (countMistake < 7 && countWin > 0) {

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase();

            char[] sch = input.toCharArray();

            if (!input.matches("[а-яА-ЯёЁ]+") || input.isEmpty()) {

                isNotCorrectInput(countMistake,lastWord,rightLetter,wrongLetter);

            } else {

                if (word.contains(input) && !Arrays.toString(lastWord).contains(input) && !rightLetter.toString().contains(input)
                        && !wrongLetter.toString().contains(input)) { // (|слово содержит input && конечный массив не содержит input-
                // стрингбилдер с отгаданными не содержит input && стрингбилдер с ошибками не содержит input|)
                    for (int i = 0; i < wordChar.length; i++) {
                        if (wordChar[i] == sch[0]) {
                            lastWord[i] = wordChar[i];
                            countWin--;
                        } else if (lastWord[i] == 0) {
                            lastWord[i] = '_';
                        }
                    }
                    rightLetter.append(sch[0]);
                } else if (!word.contains(input) && !wrongLetter.toString().contains(input) && input.matches("[a-zA-Zа-яА-Я]+")) {
                    countMistake++;          // счетчик ошибок и добавление ошибок в стрингбилдер// добавляется если содержит только бувы
                    wrongLetter.append(sch[0]);
                } else if (rightLetter.toString().contains(input) || wrongLetter.toString().contains(input)) {
                    System.out.println("Такая буква уже введена, введите другую.");
                }
                rightAndWrongLetterOutput(lastWord, rightLetter, wrongLetter, countMistake);
            }

        }
        gameResultOutput(countMistake, rightLetter, lastWord, word);

    }

    private  void rightAndWrongLetterOutput(char[] lastWord, StringBuilder rightLetter,
                                                 StringBuilder wrongLetter, int countMistake) {
        hangmanPicture(countMistake);
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print("Отгаданые буквы: " + rightLetter + " ");
        System.out.println("|  Ошибки: " + wrongLetter + " ");
    }


   private  void gameResultOutput(int countMistake, StringBuilder rightLetter, char[] lastWord,
                                        String word) {
        Interface hangman =new Interface();
        if (countMistake >= 7 && rightLetter.toString().length() != lastWord.length) {
            System.out.println();
            System.out.println("Проигрыш, загаданное слово: " + word);
           hangman.startOrFinish();
        } else if (countMistake < 7) {
            System.out.println();
            System.out.println("              Победа! Ура!");
            System.out.println("          Загаданное слово: " + word);
           hangman.startOrFinish();
        }
    }

    private  void isNotCorrectInput(int countMistake, char[] lastWord, StringBuilder rightLetter,
                                         StringBuilder wrongLetter) {
        System.out.println("Некорректный ввод, Введите кириллицу");
        hangmanPicture(countMistake);
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print("Отгаданые буквы: " + rightLetter + " ");
        System.out.println("|  Ошибки: " + wrongLetter + " ");
    }

    private  void hangmanPicture(int count) {
        switch (count) {
            case 0:
                System.out.println(HangmanPictures.ZERO.getValue());
                break;
            case 1:
                System.out.println(HangmanPictures.ONE.getValue());
                break;
            case 2:
                System.out.println(HangmanPictures.TWO.getValue());
                break;
            case 3:
                System.out.println(HangmanPictures.THREE.getValue());
                break;
            case 4:
                System.out.println(HangmanPictures.FOUR.getValue());
                break;
            case 5:
                System.out.println(HangmanPictures.FIVE.getValue());
                break;
            case 6:
                System.out.println(HangmanPictures.SIX.getValue());
                break;
            case 7:
                System.out.println(HangmanPictures.SEVEN.getValue());
                break;

        }


    }

    public    void startOrFinish() {
        Interface hangman =new Interface();
        System.out.println();
        System.out.println("              Игра Виселица ");
        System.out.println("Чтобы начать введите 1. Чтобы выйти нажмите 2");
        Scanner sc = new Scanner(System.in);

        boolean loop = true;
        while (loop) {
            String startOrFinish = sc.nextLine();
            if (startOrFinish.equals("1")) {
                hangman.gameLoop();
                loop = false;
            } else if (startOrFinish.equals("2")) {
                loop = false;

            } else if (!startOrFinish.isEmpty()) {
                System.out.println("Не корректный ввод, введите 1 чтобы начать, 2 чтобы выйти");
                // эксепш при ввобе букв

            } else {
                System.out.print("Пустой ввод, введите 1 чтобы начать, 2 чтобы выйти ");

            }
            //  !startOrFinish.equals("1") && !startOrFinish.equals("2") &&
        }

    }

}


 enum HangmanPictures{
    ZERO("""
            _______________
            |             |
            |             |
            |             |
            |             |
            |_____________|"""),
    ONE("""
            _______________
            |      |      |
            |             |
            |             |
            |             |
            |_____________|"""),
    TWO("""
            _______________
            |      |      |
            |     [oo]    |
            |             |
            |             |
            |_____________|"""),
    THREE("""
            _______________
            |      |      |
            |     [oo]__  |
            |             |
            |             |
            |_____________|"""),
    FOUR("""
            _______________
            |      |      |
            |   __[oo]__  |
            |             |
            |             |
            |_____________|""") ,
    FIVE("""
            _______________
            |      |      |
            |   __[oo]__  |
            |     (~~)    |
            |             |
            |_____________|"""),
    SIX("""
            _______________
            |      |      |
            |   __[oo]__  |
            |     (~~)    |
            |     |       |
            |_____________|"""),
    SEVEN( """
            _______________
            |      |      |
            |   __[oo]__  |
            |     (~~)    |
            |     |  |    |
            |_____________|""");

    private final String value;


    HangmanPictures(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}


public class Main {

    public static void main(String[] args)  {
        Interface hangman =new Interface();
        hangman.startOrFinish();


//        Interface.startOrFinish();

    }
}

