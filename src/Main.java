import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Main{
    public static int MISTAKES = 0;
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

        Pattern pattern = Pattern.compile("[а-яё]");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[N]ew game or [E]xit");
            String choose = scanner.nextLine().trim();
            switch (choose) {
                case "N":
                    char[] answerLetters = defineWord().toCharArray();

                    System.out.println("Слово загадано!");

                    char[] guessed = new char [answerLetters.length];

                    Set<Character> usedLetters = new LinkedHashSet<>();

                    Arrays.fill(guessed, '*');

                    while (MISTAKES < 6 && !Arrays.equals(answerLetters, guessed)) {
                        System.out.print("Введите букву: ");
                        try {
                            char input = scanner.nextLine().trim().toLowerCase().charAt(0);
                            gameRealization(answerLetters, input, guessed, usedLetters);
                        } catch (StringIndexOutOfBoundsException inputSpace){
                            System.out.println("Вы ввели пустое поле. Попробуйте еще раз!");
                        }

                    }
                    if (MISTAKES== 6) {
                        System.out.println("Вы проиграли, правильное слово:\n " + new String(answerLetters));
                        MISTAKES=0;
                        break;
                    } else {
                        System.out.println("Вы выиграли");
                        MISTAKES=0;
                    }
                    break;

                case "E":
                    return;
                default:
                    System.out.println("Ошибка: введите N или E");

            }
        }


    }

    public static String defineWord() throws FileNotFoundException {
        String separator = File.separator;
        int wordNumber;
        File file = new File("WordsForHangman.txt");

        Scanner scanner = new Scanner(file);
        String[] wordsArray = scanner.nextLine().split(" ");
        Random random = new Random();
        int countWords = wordsArray.length;
        wordNumber = random.nextInt(countWords);
        return wordsArray[wordNumber];

    }
    public static void gameRealization(char[] answer, char input, char[] usersAnswer, Set<Character> usedLetters){

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
            System.out.print(states[++MISTAKES] + "\n");
            for (char key: usersAnswer){
                System.out.print(key + " ");
            }
        }
        else{
            System.out.print(states[MISTAKES] + "\n");
            for (char key: usersAnswer){
                System.out.print(key + " ");
            }

        };
        System.out.println("\nОшибок: " + MISTAKES + " из 6");
        System.out.println("\nВы использовали буквы: ");
        for(char item: usedLetters){
            System.out.print(item + " ");
        }
        System.out.println("\n");
    }
}

