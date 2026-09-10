import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task3 {
     public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("javac -cp \".;.\\lib\\gson-2.14.0.jar\" Task3.java");
            System.out.println("You need to pass three file paths");
            System.out.println("java -cp \".;lib/*\" Task3 <.json 1> <.json 2> <.json 3>");
            return;
        }

        try (
            Scanner valuesScanner = new Scanner(Path.of(args[0]));
            Scanner testsScanner = new Scanner(Path.of(args[1]));
        ) {
            // Читаем первый файл целиком
            valuesScanner.useDelimiter("\\A");
            String valuesText = valuesScanner.next();

            JsonObject valuesFile =
                    JsonParser.parseString(valuesText).getAsJsonObject();

            // Сохраняем результаты по id
            Map<String, String> results = new HashMap<>();

            for (JsonElement element : valuesFile.getAsJsonArray("values")) {
                JsonObject item = element.getAsJsonObject();

                String id = item.get("id").getAsString();
                String value = item.get("value").getAsString();

                results.put(id, value);
            }

            // Читаем второй файл целиком
            testsScanner.useDelimiter("\\A");
            String testsText = testsScanner.next();

            JsonObject testsFile =
                    JsonParser.parseString(testsText).getAsJsonObject();

            // Заполняем поля value
            fillValues(testsFile.getAsJsonArray("tests"), results);

            // Сохраняем отчёт
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.writeString(Path.of(args[2]), gson.toJson(testsFile));

        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
        }
    }

    private static void fillValues(
            JsonArray tests, Map<String, String> results) {

        for (JsonElement element : tests) {
            JsonObject test = element.getAsJsonObject();
            String id = test.get("id").getAsString();

            if (test.has("value") && results.containsKey(id)) {
                test.addProperty("value", results.get(id));
            }

            if (test.has("values")) {
                fillValues(test.getAsJsonArray("values"), results);
            }
        }
    }
}
