import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Scanner;

public class Task2 {
    
       public static void main(String[] args) {
        // Ожидаем 2 арг.
        if (args.length != 2) {
            System.out.println("You need to pass the paths to two files");
            return;
        }

        try (
            Scanner ellipse = new Scanner(Path.of(args[0]));
            Scanner points = new Scanner(Path.of(args[1]))
        ) {
            // BigDecimal был выбран из-за неточности double, чтобы не возникло проблем на границе элипса
            BigDecimal cx = new BigDecimal(ellipse.next());
            BigDecimal cy = new BigDecimal(ellipse.next());
            BigDecimal rx = new BigDecimal(ellipse.next());
            BigDecimal ry = new BigDecimal(ellipse.next());

            // Радиусы не могут быть отриц.
            if (rx.signum() <= 0 || ry.signum() <= 0) {
                System.err.println("The radii must be positive");
                return;
            }
            // Квадраты радиусов и правая часть формулы
            BigDecimal rx2 = rx.multiply(rx);
            BigDecimal ry2 = ry.multiply(ry);
            BigDecimal right = rx2.multiply(ry2); // rx^2 * ry^2

            while (points.hasNext()) {
                // Кор-ды точки
                BigDecimal x = new BigDecimal(points.next());
                BigDecimal y = new BigDecimal(points.next());

                BigDecimal dx = x.subtract(cx);  // (x - cx)
                BigDecimal dy = y.subtract(cy);  // (y - cy)

                BigDecimal left = dx.multiply(dx).multiply(ry2)  // левая часть
                        .add(dy.multiply(dy).multiply(rx2));     // (x - cx)^2 * ry^2 + (y - cy)^2 * rx^2

                int comparison = left.compareTo(right); // (x - cx)^2 * ry^2 + (y - cy)^2 * rx^2  ?   rx^2 * ry^2

                // Вывод на экран
                if (comparison == 0) {
                    System.out.println(0);
                } else if (comparison < 0) {
                    System.out.println(1);
                } else {
                    System.out.println(2);
                }
            }

        } catch (IOException e) {
            System.err.println("Could not open the file: " + e.getMessage());
        }
    }
}
