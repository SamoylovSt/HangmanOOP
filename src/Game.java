import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Game {
    WordGenerator hangman = new WordGenerator();
    String word = hangman.getRandomWord();
    char[] wordChar = word.toCharArray();
    char[] lastWord = new char[wordChar.length];
    int countWin = wordChar.length;
    int mistakesCounter = 0;
    HashSet<Character> wordToHeshSet = new HashSet<>();
    HashSet<Character> rightLetterHeshSet = new HashSet<>();
    HashSet<Character> wrongLetterHeshSet = new HashSet<>();

    private void gameLoop() {
        Arrays.fill(lastWord, '_');
        hangmanPicture();
        String firstOutEmptyWord = new String(lastWord);

        System.out.println(GameConstants.WORD_COMPLIT);
        System.out.println(firstOutEmptyWord);

        while (mistakesCounter < 7 && countWin > 0) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase();
            char[] inputCharArray = input.toCharArray();

            for (char element : word.toCharArray()) {
                wordToHeshSet.add(element);
                }


            if (!input.matches("[а-яА-ЯёЁ]+") || input.isEmpty()) {
                incorrectInputMessage();
            } else {
                boolean containsInput = wordToHeshSet.contains(inputCharArray[0])
                        && !rightLetterHeshSet.contains(inputCharArray[0])
                        && !wrongLetterHeshSet.contains(inputCharArray[0]);

                searchingLetterInMask(containsInput, inputCharArray, input);
                rightAndWrongLetterOutput(lastWord);
            }
        }
        gameResultOutput(rightLetterHeshSet, lastWord, word);
    }

    private void searchingLetterInMask(boolean containsInput, char[] inputCharArray, String input) {
        if (containsInput) {
            for (int i = 0; i < wordChar.length; i++) {
                if (wordChar[i] == inputCharArray[0]) {
                    lastWord[i] = wordChar[i];
                    countWin--;
                }
            }
            rightLetterHeshSet.add(inputCharArray[0]);
        } else if (!wordToHeshSet.contains(input.charAt(0)) && !wrongLetterHeshSet.contains(input.charAt(0))) {
            mistakesCounter++;
             wrongLetterHeshSet.add(input.charAt(0));
        } else if (rightLetterHeshSet.toString().contains(input) || wrongLetterHeshSet.toString().contains(input)) {
            System.out.println(GameConstants.LETTER_ALREADY_ENTERED);
        }
    }

    private void rightAndWrongLetterOutput(char[] lastWord) {
        hangmanPicture();
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetterString()+ " ");
        System.out.println(GameConstants.MISTAKES + wrongtLetterString() + " ");
    }

    private void gameResultOutput(HashSet<Character> rightLetter, char[] lastWord, String word) {
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
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetterString() + " ");
        System.out.println(GameConstants.MISTAKES + wrongtLetterString() + " ");
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

        boolean isLoopEnabled = true;
        while (isLoopEnabled) {
            String startOrFinish = sc.nextLine();
            if (startOrFinish.equals("1")) {
                hangman.gameLoop();
                isLoopEnabled = false;
            } else if (startOrFinish.equals("2")) {
                isLoopEnabled = false;
            } else if (!startOrFinish.isEmpty()) {
                System.out.println(GameConstants.IS_NOT_CORRECT_INPUT_2);
            } else {
                System.out.print(GameConstants.EMPTY_INPUT);
            }
        }
    }

    private StringBuilder rightLetterString(){
        StringBuilder rightLetterString = new StringBuilder();
        for (char element : rightLetterHeshSet) {
            rightLetterString.append(element);
        }
        return  rightLetterString;
    }

    private StringBuilder wrongtLetterString(){
        StringBuilder wrongLetterString = new StringBuilder();
        for (char element : wrongLetterHeshSet) {
            wrongLetterString.append(element);
        }
        return  wrongLetterString;
    }


}


