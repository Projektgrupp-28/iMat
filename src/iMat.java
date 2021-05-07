import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class iMat {

    public static void main(String[] args) {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        Product product = dataHandler.getProduct(2);
        System.out.println(product.getName() + " kostar " + (int)product.getPrice() + "kr");

        System.out.println(dataHandler.getCustomer().getFirstName());
        System.out.println("Test William");
    }
}
