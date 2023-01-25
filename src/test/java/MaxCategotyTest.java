import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;



public class MaxCategotyTest {
   static private List<Product> listTest = new ArrayList<>();
   static private Map<String, Integer> mapTest = new HashMap<>();

    @BeforeAll
    public static void beforeAll(){
        Product prod1 = new Product("булка","2022.02.08",200);
        Product prod2 = new Product("курица","2022.02.08",400);
        Product prod3 = new Product("тапки","2022.02.08",300);
        Product prod4 = new Product("шапка","2022.02.08",200);
        listTest.add(prod1);
        listTest.add(prod2);
        listTest.add(prod3);
        listTest.add(prod4);
        mapTest.put("еда", 600);
        mapTest.put("одежда", 500);
        System.out.println("Начало тестирование метода \"MaxCategory.fromListToMap\"");

    }
    @Test
    public void fromListToMapTest(){
        Assertions.assertEquals(mapTest, MaxCategory.fromListToMap(listTest));
    }
}
