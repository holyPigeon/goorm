import java.util.HashSet;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Set<Product> products = new HashSet<>();

    Product 치약1 = new Product(1,"치약", 1000);
    Product 치약2 = new Product(2,"치약", 1000);
    Product 치약3 = new Product(3,"치약", 1000);
    Product 칫솔 = new Product(4,"칫솔", 2000);
    Product 샴푸 = new Product(5,"샴푸", 2000);

    products.add(치약1);
    products.add(치약2);
    products.add(치약3);
    products.add(칫솔);
    products.add(샴푸);

    Cart cart = new Cart();
    cart.removeProduct(치약1, 1);

  }
}