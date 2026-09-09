import java.util.ArrayList;
import java.util.List;

public class Task1 {
    
    public static List<Integer> calculatePath(int n, int m) {

        List<Integer> path = new ArrayList<>();

        // Первый элемент
        int a = 1;

        while (true) {

            // Добавляем начало интервала в путь
            path.add(a);

            // Находим конец интервала, вернулись к первому элементу = закночили
            int end = (a + m - 2) % n + 1;            
            if (end == 1) {
                break;
            }

            // Конец текущего интервала становится началом следующего            
            a = end;
        }

        return path;
    }

    public static class MyThread extends Thread {

    private int n;
    private int m;
    private List<Integer> path;

    public MyThread(int n, int m){
        this.n = n;
        this.m = m;
    }

    @Override 
    public void run(){
        path = Task1.calculatePath(n, m);
    }

    public List<Integer> getPath(){
        return path;
    } 
}

    public static void main(String[] args) throws Exception {

        // Ожидаем 4 аргумента        
        if (args.length != 4) {
            System.out.println("Error: Exactly 4 values must be passed.");            
            return; 
        }

        int n1 = Integer.parseInt(args[0]);
        int m1 = Integer.parseInt(args[1]);
        int n2 = Integer.parseInt(args[2]);
        int m2 = Integer.parseInt(args[3]);
       

        // Создаем 2 потока для одовременной обработки 2х массивов
        MyThread th1 = new MyThread(n1, m1);
        MyThread th2 = new MyThread(n2, m2);

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        // Совмещаем результаты из потоков        
        List<Integer> Combined = new ArrayList<>();

        Combined.addAll(th1.getPath());
        Combined.addAll(th2.getPath());

        for (int val:Combined){
            System.out.print(val);
        }
        
    }

}
