import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.io.File;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("База пуста");
        System.out.print("Начать заполнение базы (+/-): ");

        String confirm = scanner.nextLine();
        if (confirm.equals("+")) {

        int tempPercent;
        int tempIdToy = 1;
        int remainder = 100;
        Magigrush[] toys = new Magigrush[10];

        while (remainder > 0){
           toys[tempIdToy] = new Magigrush();
           System.out.printf("Введите наименование %d-й игрушки на английском языке: " ,tempIdToy);
           String testNameToy = scanner.nextLine();
           System.out.print("Введите вероятность выпадения данной игрушки (от 1 до 100): ");
           tempPercent = Integer.parseInt(scanner.nextLine());

           toys[tempIdToy].nameToy = testNameToy;
           toys[tempIdToy].idToy = tempIdToy;
           toys[tempIdToy].weightToy = CheckPercent(tempPercent, remainder) / 10;

           remainder = remainder - tempPercent;
           tempIdToy++;

       }
            System.out.println();
            System.out.println("База для розыгрыша:");
       for (int i = 1; i < tempIdToy ;i++){
           System.out.println(toys[i].idToy + " - " + toys[i].nameToy);
       }

            int[] id = new int[10];
            int[] weight = new int[10];
            String[] name = new String[10];


            //---------------------------------------------------------------------------
            int count = 1;
            int j = 1;
            for (int i = 0; i < id.length; i++){

                if (count <= toys[j].weightToy){
                    id[i] = toys[j].idToy;
                    weight[i] = toys[j].weightToy;
                    name[i] = toys[j].nameToy;
                    count++;
                }
                else {
                    j++;
                    count = 1;
                    i--;
                }

            }

            //---------------------------------------------------------------------------
            try {
                File file = new File("result.txt");

                if(!file.exists()) {
                    file.createNewFile();
                }
                PrintWriter pw = new PrintWriter(file);

                Random random = new Random();
                int num;
                System.out.print("Какое колличество раундов: ");
                int round = Integer.parseInt(scanner.nextLine());
                for(int i = 0; i < round; i++){
                    System.out.printf("%d -й раунд! И %d-й приз, это:\n ", i+1, i+1);
                    num = random.nextInt(10);
                    System.out.printf("%s (№%d с вероятностью %d процентов)", name[num], id[num], weight[num] * 10 );
                    pw.printf("%s (№%d с вероятностью %d процентов) \n", name[num], id[num], weight[num] * 10 );
                    System.out.println();
                }
                pw.close();
                System.out.println("Результаты сохранены в файл result.txt");
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }



            //----------------------------------
        } else if (confirm.equals("-")) {
            System.out.println("Программа завершена.");
            return;
        } else {
            System.out.println("Не верный символ, повторите попытку");
        }

    }
    static int CheckPercent(int percent, int remPercent){
        if ((remPercent - percent) < 0 ){
            System.out.println("Проверка корректности, процента вероятности для игрушки...");
            int toMach = remPercent - percent;
            int newPercent = percent + toMach;
            System.out.printf("Оставшийся процент вероятности для распрелеления - %d значение исправлено автоматически.", newPercent);
            return newPercent;
        }
        return percent;
    }
}
