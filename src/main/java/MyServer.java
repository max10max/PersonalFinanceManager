import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable, Serializable {

    private static final int PORT = 8080;
    private static final File file = new File("data.bin");
    private List<Product> shoppingList;

    public List<Product> getShoppingList() {
        return shoppingList;
    }

    @Override
    public void run() {

        if(file.exists()){
            try {
                shoppingList = loadFromBin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            shoppingList = new ArrayList<>();
        }

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
                    saveBIn();
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Невозможно запустить сервер");
            e.printStackTrace();
        }
    }


    public void saveBIn() throws IOException{
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(this);
        }
    }

    public static List<Product> loadFromBin() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            MyServer temp = (MyServer) in.readObject();
            return temp.getShoppingList();

        }
    }
}



