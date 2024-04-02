import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {

  Map<Product, Integer> items = new HashMap<>();

  public void addProduct(Product product, int amount) {
    items.merge(product, amount, Integer::sum);
  }

  public void removeProduct(Product product, int amount) {
    if(items.get(product) != null) {
      items.merge(product, amount, ((integer, integer2) -> Math.max(0, integer - integer2)));
    }
  }

  public Map<String, Integer> showItems() {
    return items.entrySet().stream()
      .filter(productIntegerEntry -> productIntegerEntry.getValue() > 0)
      .collect(Collectors.toMap(entry -> entry.getKey().getName() + entry.getKey().getKey(), Map.Entry::getValue));
  }
}
