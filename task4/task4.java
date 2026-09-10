import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Task4 {
    
     public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You need to pass the file path");
            return;
        }

        try (Scanner scanner = new Scanner(Path.of(args[0]))) {
            ArrayList<Integer> nums = new ArrayList<>();

            // Читаем числа из файла
            while (scanner.hasNext()) {
                nums.add(scanner.nextInt());
            }

            if (nums.isEmpty()) {
                System.err.println("The file does not contain numbers");
                return;
            }

            // Сортируем по возрастанию
            Collections.sort(nums);

            // Выбираем средний элемент
            int median = nums.get(nums.size() / 2);

            int moves = 0;

            for (int number : nums) {
                moves += Math.abs(number - median);

                if (moves > 20) {
                    System.out.println(
                            "20 moves are not enough to make all elements of the array equal to the same number");
                    return;
                }
            }

            System.out.println(moves);

        } catch (IOException e) {
            System.err.println("File operation error: " + e.getMessage());
        }
    }

}
