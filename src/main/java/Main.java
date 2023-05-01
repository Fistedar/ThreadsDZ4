import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    static ArrayBlockingQueue<String> textCheckA = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> textCheckB = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> textCheckC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        new Thread(Main::startPut).start();
        new Thread(() -> System.out.println(checkChar(textCheckA, 'a') + "раз(а),максимум когда попался символ 'a'")).start();
        new Thread(() -> System.out.println(checkChar(textCheckB, 'b') + "раз(а),максимум когда попался символ 'b'")).start();
        new Thread(() -> System.out.println(checkChar(textCheckC, 'c') + "раз(а),максимум когда попался символ 'c'")).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static Integer checkChar(ArrayBlockingQueue<String> textCheck, char symbol) {
        int maxCount = 0;
        for (int i = 0; i < 10000; i++) {
            int count = 0;
            try {
                String text = textCheck.take();
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == symbol) {
                        count++;
                    }
                }
                if (maxCount < count) {
                    maxCount = count;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return maxCount;
    }

    public static void startPut() {
        for (int i = 0; i < 10000; i++) {
            try {
                textCheckA.put(generateText("abc", 10000));
                textCheckB.put(generateText("abc", 10000));
                textCheckC.put(generateText("abc", 10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}