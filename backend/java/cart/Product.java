import java.util.Objects;

public class Product {

  private Integer key;
  private String name;
  private int price;

  public Product(Integer key, String name, int price) {
    this.key = key;
    this.name = name;
    this.price = price;
  }

  public Integer getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Product product = (Product)o;
    return Objects.equals(key, product.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }
}
