import java.util.Arrays;

import java.util.Scanner;

public class Game {
    int countMistake = 0;
    Word hangman = new Word();

    String word = hangman.randomWord();

    char[] wordChar = word.toCharArray();
    char[] lastWord = new char[wordChar.length];

    int countWin = wordChar.length;

    StringBuilder rightLetter = new StringBuilder();
    StringBuilder wrongLetter = new StringBuilder();

    private void gameLoop() {

        Arrays.fill(lastWord, '_');
        hangmanPicture();//countMistake
        String firstOutEmptyWord = new String(lastWord);
        System.out.println(GameConstants.WORD_COMPLIT);
        System.out.println(firstOutEmptyWord);

        while (countMistake < 7 && countWin > 0) {

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase();

            char[] sch = input.toCharArray();

            if (!input.matches("[а-яА-ЯёЁ]+") || input.isEmpty()) {

                incorrectInputMessage();

            } else {



                boolean containsInput = word.contains(input) && !Arrays.toString(lastWord).contains(input)
                        && !rightLetter.toString().contains(input)
                        && !wrongLetter.toString().contains(input);

                searchingLetterInMask(containsInput,sch,input);

                rightAndWrongLetterOutput(lastWord, rightLetter, wrongLetter);
            }

        }
        gameResultOutput( rightLetter, lastWord, word);

    }

    private void searchingLetterInMask(boolean containsInput,char[] sch,String input){
        if (containsInput) {
            for (int i = 0; i < wordChar.length; i++) {
                if (wordChar[i] == sch[0]) {
                    lastWord[i] = wordChar[i];
                    countWin--;
                } else if (lastWord[i] == 0) {
                    lastWord[i] = '_';
                }
            }
            rightLetter.append(sch[0]);
        } else if (!word.contains(input) && !wrongLetter.toString().contains(input)
        ) {
            countMistake++;
            wrongLetter.append(sch[0]);
        } else if (rightLetter.toString().contains(input) || wrongLetter.toString().contains(input)) {
            System.out.println(GameConstants.LETTER_ALREADY_ENTERED);
        }
    }

    private void rightAndWrongLetterOutput(char[] lastWord, StringBuilder rightLetter,
                                           StringBuilder wrongLetter) { //,int countMistake
        hangmanPicture();//countMistake
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetter + " ");
        System.out.println(GameConstants.MISTAKES + wrongLetter + " ");
    }


    private void gameResultOutput( StringBuilder rightLetter, char[] lastWord,
                                   String word) {
        Game hangman = new Game();
        if (countMistake >= 7 && rightLetter.toString().length() != lastWord.length) {
            System.out.println();
            System.out.println(GameConstants.LOSE_AND_MYSTERIOUS_WORD + word);
            hangman.startOrFinish();
        } else if (countMistake < 7) {
            System.out.println();
            System.out.println(GameConstants.WIN);
            System.out.println(GameConstants.THE_MYSTERIOUS_WORD + word);
            hangman.startOrFinish();
        }
    }

    private void incorrectInputMessage() {
        System.out.println(GameConstants.IS_NOT_CORRECT_INPUT);
        hangmanPicture();
        String result = new String(lastWord);
        System.out.println(result);
        System.out.print(GameConstants.GUESSED_LETTERS + rightLetter + " ");
        System.out.println(GameConstants.MISTAKES + wrongLetter + " ");

    } //char[] lastWord, StringBuilder rightLetter,StringBuilder wrongLetter

    private void hangmanPicture() {//int countMistake

        HangmanPictures[] pictures = HangmanPictures.values();
        System.out.println(pictures[countMistake].getValue());

    }

    enum HangmanPictures {
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
                |_____________|"""),
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
        SEVEN("""
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
