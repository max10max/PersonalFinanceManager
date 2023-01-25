import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MaxCategory {

    private String category;
    private int sum;

    public MaxCategory(String category, int sum) {
        this.category = category;
        this.sum = sum;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }


    public static String infoByCategory(List<Product> shoppingList) {
        Map<String, Integer> buysByCategory = fromListToMap(shoppingList);
        Map.Entry<String, Integer> searchMax = buysByCategory.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        MaxCategory maxCategory = new MaxCategory(searchMax.getKey(), searchMax.getValue());
        Gson gson = new Gson();
        String jsn = gson.toJson(maxCategory);
        return jsn;
    }

    public static Map<String, Integer> fromListToMap(List<Product> shoppingList) {
        Map<String, Integer> shoppingCategory = new HashMap<>();
        for (Product product : shoppingList) {
            String productCategory = product.getCategory();
            if (shoppingCategory.containsKey(productCategory)) {
                Integer allSum = shoppingCategory.get(productCategory) + product.getSum();
                shoppingCategory.replace(productCategory, allSum);
            } else {
                shoppingCategory.put(productCategory, product.getSum());
            }
        }
        return shoppingCategory;
    }

    public static String answerForClient(List<Product> shoppingList) {
        LocalDate lastDateBuy = shoppingList.get(shoppingList.size() - 1).getDate();
        List<Product> maxYearCategoty = shoppingList.stream()
                .filter(prod -> prod.getDate().getYear() == lastDateBuy.getYear())
                .collect(Collectors.toList());
        List<Product> maxMonthCategoty = shoppingList.stream()
                .filter(prod -> (prod.getDate().getYear() == lastDateBuy.getYear() &&
                        prod.getDate().getMonthValue() == lastDateBuy.getMonthValue()))
                .collect(Collectors.toList());
        List<Product> maxDayCategoty = shoppingList.stream()
                .filter(prod -> (prod.getDate().getYear() == lastDateBuy.getYear() &&
                        prod.getDate().getMonthValue() == lastDateBuy.getMonthValue() &&
                        prod.getDate().getDayOfMonth() == lastDateBuy.getDayOfMonth()))
                .collect(Collectors.toList());
        String answer = "{\"maxCategory\":" + infoByCategory(shoppingList) + ", \"maxYearCategory\": " +
                infoByCategory(maxYearCategoty) + ", \"maxMonthCategory\": " + infoByCategory(maxMonthCategoty) +
                ", \"maxDayCategory\": " + infoByCategory(maxDayCategoty) + "}";
        return answer;
    }

}
