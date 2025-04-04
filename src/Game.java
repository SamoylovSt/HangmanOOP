import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Game {
    int mistakesCounter = 0;
    WordGenerator hangman = new WordGenerator();

    String word = hangman.getRandomWord();

    char[] wordChar = word.toCharArray();
    char[] lastWord = new char[wordChar.length];

    int countWin = wordChar.length;

    StringBuilder rightLetter = new StringBuilder();
    StringBuilder wrongLetter = new StringBuilder();

    private void gameLoop() {
        Arrays.fill(lastWord, '_');
        hangmanPicture();
        String firstOutEmptyWord = new String(lastWord);
        System.out.println(GameConstants.WORD_COMPLIT);
        System.out.println(firstOutEmptyWord);

        while (mistakesCounter < 7 && countWin > 0) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase();
            char[] sch = input.toCharArray();

            if (!input.matches("[а-яА-ЯёЁ]+") || input.isEmpty()) {
                incorrectInputMessage();
            } else {

                HashSet<Character> wordToHeshSet =new HashSet<>();
                for (char element: word.toCharArray()){
                    wordToHeshSet.add(element);
                }
                HashSet<Character> lastWordToHeshSet =new HashSet<>();
                for (char element: lastWord){
                    lastWordToHeshSet.add(element);
                }
                HashSet<Character> rightLetterToHeshSet =new HashSet<>();
                for (char element: rightLetter.toString().toCharArray()){
                    rightLetterToHeshSet.add(element);
                }
                HashSet<Character> wrongLetterToHeshSet =new HashSet<>();
                for (char element: wrongLetter.toString().toCharArray()){
                    wrongLetterToHeshSet.add(element);
                }

                boolean containsInput = wordToHeshSet.contains(sch[0])
                        && !lastWordToHeshSet.contains(sch[0])
                        && !rightLetterToHeshSet.contains(sch[0])
                        && !wrongLetterToHeshSet.contains(sch[0]);

                searchingLetterInMask(containsInput, sch, input);
                rightAndWrongLetterOutput(lastWord, rightLetter, wrongLetter);
            }
        }
        gameResultOutput(rightLetter, lastWord, word);
    }

    private void searchingLetterInMask(boolean containsInput, char[] sch, String input) {
        if (containsInput) {
            for (int i = 0; i < wordChar.length; i++) {
                if (wordChar[i] == sch[0]) {
                    lastWord[i] = wordChar[i];
                    countWin--;
                } else if (lastWord[i] == 0) {
                    lastWord[i] = '_';
                }
            }
            rightLetter.append(sch[0]); //rightLetter
        } else if (!word.contains(input) && !wrongLetter.toString().contains(input)
        ) {
            mistakesCounter++;
            wrongLetter.append(sch[0]);
        } else if (this.rightLetter.toString().contains(input) || wrongLetter.toString().contains(input)) {
            System.out.println(GameConstants.LETTER_ALREADY_ENTERED);
        }
    }

    private void rightAndWrongLetterOutput(char[] lastWord, StringBuilder rightLetter,
                                           StringBuilder wrongLetter) {
        hangmanPicture();
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetter + " ");
        System.out.println(GameConstants.MISTAKES + wrongLetter + " ");
    }


    private void gameResultOutput(StringBuilder rightLetter, char[] lastWord, String word) {
        if (mistakesCounter >= 7 && rightLetter.toString().length() != lastWord.length) {
            System.out.println();
            System.out.println(GameConstants.LOSE_AND_MYSTERIOUS_WORD + word);
            startOrFinish();
        } else if (mistakesCounter < 7) {
            System.out.println();
            System.out.println(GameConstants.WIN);
            System.out.println(GameConstants.THE_MYSTERIOUS_WORD + word);
            startOrFinish();
        }
    }

    private void incorrectInputMessage() {
        System.out.println(GameConstants.IS_NOT_CORRECT_INPUT);
        hangmanPicture();
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetter + " ");
        System.out.println(GameConstants.MISTAKES + wrongLetter + " ");
    }

    private void hangmanPicture() {

        HangmanPictures[] pictures = HangmanPictures.values();
        System.out.println(pictures[mistakesCounter].getValue());

    }


    public void startOrFinish() {
        Game hangman = new Game();
        System.out.println();
        System.out.println(GameConstants.GAME_HANGMAN);
        System.out.println(GameConstants.START_OR_FINISH_INPUT);
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
                System.out.println(GameConstants.IS_NOT_CORRECT_INPUT_2);
            } else {
                System.out.print(GameConstants.EMPTY_INPUT);
            }
        }
    }


}
