import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable, Serializable {

    private static final int PORT = 8989;
    private List<Product> shoppingList = new ArrayList<>();


    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Новое подключение принято");

                    String clientMess = in.readLine();
                    JSONParser parser = new JSONParser();
                    JSONObject jsonIn = (JSONObject) parser.parse(clientMess);
                    String title = (String) jsonIn.get("title");
                    String date = (String) jsonIn.get("date");
                    int sum = (int) (long) jsonIn.get("sum");

                    shoppingList.add(new Product(title, date, sum));
                    System.out.println("Сообщение получено");

                    String jsonOut = MaxCategory.infoByCategory(shoppingList);
                    out.println(jsonOut);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Невозможно запустить сервер");
            e.printStackTrace();
        }
    }

}




