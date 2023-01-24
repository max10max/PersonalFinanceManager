import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Product implements Serializable{
    private String name;
    private String category;
    private String date;
    private int sum;
      public static File file = new File("categories.tvs");

    private static Map<String, String> categoriesProducts = new LinkedHashMap<>();

    static {
        try {
            setCategoriesProducts(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product(String name, String date, int sum) {
        this.name = name;
        this.date = date;
        this.sum = sum;
        if (categoriesProducts.get(name) != null) {
            this.category = categoriesProducts.get(name);
        } else {
            this.category = "другое";
        }
    }

    public static void setCategoriesProducts(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split("\t");
                categoriesProducts.put(lineArray[0], lineArray[1]);
                line = reader.readLine();
            }
        }
    }


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}
