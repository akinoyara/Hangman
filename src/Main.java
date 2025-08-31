import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Main{
    public static int countMistakes = 0;
    public static String[] states= {
            """
               +---+
               |
               |
               |
               |
               |
           =============""",
            """
               +---+
               |   |
               |
               |
               |
               |
           =============
           """,
            """
                +---+
                |   |
                |   O
                |
                |
                |
            =============
            """,
            """
                +---+
                |   |
                |   O
                |  /|
                |
            =============
            """,
            """
                +---+
                |   |
                |   O
                |  /|\\
                |
            =============
            """,
            """
                +---+
                |   |
                |   O
                |  /|\\
                |   |
                |  /
            =============
            """,
            """
                +---+
                |   |
                |   O
                |  /|\\
                |   |
                |  / \\
            =============
            """

    };

    public static void main(String[] args) throws FileNotFoundException {


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[N]ew game or [E]xit");
            String userChoice = scanner.nextLine().trim();
            switch (userChoice) {
                case "N":
                    char[] answerLetters = getGuessedWord().toCharArray();

                    System.out.println("Слово загадано!");

                    char[] guessedLetters = new char [answerLetters.length];

                    Set<Character> usedLetters = new LinkedHashSet<>();

                    Arrays.fill(guessedLetters, '*');

                    while (countMistakes < 6 && !Arrays.equals(answerLetters, guessedLetters)) {
                        System.out.print("Введите букву: ");
                        try {
                            char userInput = scanner.nextLine().trim().toLowerCase().charAt(0);
                            processGuess(answerLetters, userInput, guessedLetters, usedLetters);
                        } catch (StringIndexOutOfBoundsException inputSpace){
                            System.out.println("Вы ввели пустое поле. Попробуйте еще раз!");
                        }

                    }
                    if (countMistakes == 6) {
                        System.out.println("Вы проиграли, правильное слово:\n " + new String(answerLetters));
                        countMistakes =0;
                        break;
                    } else {
                        System.out.println("Вы выиграли");
                        countMistakes =0;
                    }
                    break;

                case "E":
                    return;
                default:
                    System.out.println("Ошибка: введите N или E");

            }
        }


    }

    public static String getGuessedWord() throws FileNotFoundException {

        int wordNumber;
        File file = new File("WordsForHangman.txt");

        Scanner scanner = new Scanner(file);
        String[] wordsArray = scanner.nextLine().split(" ");
        Random random = new Random();
        int countWords = wordsArray.length;
        wordNumber = random.nextInt(countWords);
        return wordsArray[wordNumber];

    }
    public static void processGuess(char[] answer, char input, char[] usersAnswer, Set<Character> usedLetters){

        boolean isCorrectLetter=false;

        if (usedLetters.contains(input)){
            System.out.println("Вы уже вводили эту букву");
            return;
        }
        if (!Character.toString(input).matches("[а-яё]"))
        {
            System.out.println("Введен неверный символ, пожалуйста, попробуйте снова");
            return;
        }
        usedLetters.add(input);
        for (int i = 0; i<answer.length; i++) {
            if (answer[i] == input) {
                usersAnswer[i] = input;
                isCorrectLetter = true;
            }
        }
        if (!isCorrectLetter){
            System.out.print(states[++countMistakes] + "\n");
            for (char key: usersAnswer){
                System.out.print(key + " ");
            }
        }
        else{
            System.out.print(states[countMistakes] + "\n");
            for (char key: usersAnswer){
                System.out.print(key + " ");
            }

        };
        System.out.println("\nОшибок: " + countMistakes + " из 6");
        System.out.println("\nВы использовали буквы: ");
        for(char item: usedLetters){
            System.out.print(item + " ");
        }
        System.out.println("\n");
    }

}

