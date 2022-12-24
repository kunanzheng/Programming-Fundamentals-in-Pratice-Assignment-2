package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Product;
import utils.Utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The store class saves all the entered products into a primitive ArrayList.
 *
 * @author Mairead Meagher, Siobhan Drohan
 * @version 7.1
 */

public class Store {

    private ArrayList<Product> products;

    public Store() {
        products = new ArrayList<Product>();
    }

    /**
     * This method returns the products ArrayList
     *
     * @return the products ArrayList
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * This method takes in an ArrayList of product objects and sets the products ArrayList to it.
     *
     * @param products An ArrayList of product objects.
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Add the product object, passed as a parameter, to the ArrayList.
     *
     * @param product Product object to be added to the ArrayList.
     */
    public boolean add(Product product) {
        return products.add(product);
    }

    /**
     * Update a Product in the ArrayList with the contents passed in the Product object parameter.
     *
     * @param indexToUpdate Index of the Product object in the ArrayList
     * @param updateDetails A Product object containing the updated details
     * @return The status of the update, True or False
     */
    public boolean updateProduct(int indexToUpdate, Product updateDetails) {
        //find the product object by the index number
        Product foundProduct = findProduct(indexToUpdate);

        //if the product exists, use the details passed in the updateDetails parameter to
        //update the found product in the ArrayList.
        if (foundProduct != null) {
            foundProduct.setProductName(updateDetails.getProductName());
            foundProduct.setProductCode(updateDetails.getProductCode());
            foundProduct.setUnitCost(updateDetails.getUnitCost());
            foundProduct.setInCurrentProductLine(updateDetails.isInCurrentProductLine());
            return true;
        }

        //if the product was not found, return false, indicating that the update was not successful
        return false;
    }

    /**
     * Delete a Product from the ArrayList, if it exists, at the index passed as a parameter.
     *
     * @param indexToDelete Index of the Product object in the ArrayList
     * @return The deleted product object or null if no object is at the index location
     */
    public Product deleteProduct(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return products.remove(indexToDelete);
        }
        return null;
    }

    /**
     * Find a product object at a specific index location.
     * If the index location is not valid, return null.
     *
     * @param index Index of the Product object in the ArrayList
     * @return The product object or null if no object is at the index location
     */
    public Product findProduct(int index) {
        if (isValidIndex(index)) {
            return products.get(index);
        }
        return null;
    }

    /**
     * This method returns a string containing all products whose product name contained (regardless of case)
     * the search string.  The index location of the product in the arraylist is also returned.
     *
     * @param productName  The string to search by
     * @return  products whose product name contains the search string
     */
    public String searchByProductName(String productName) {
        String matchingProducts = "";
        for(Product product : products) {
            if (product.getProductName().toUpperCase().contains(productName.toUpperCase())) {
                matchingProducts += products.indexOf(product) + ": " + product + "\n";
            }
        }

        if (matchingProducts.equals("")){
            return "No products match your search";
        }
        else{
            return matchingProducts;
        }
    }
    /**
     * Selection sort algorithm for physically sorting the arraylist of products by product name ascending.
     */
    public void sortProductsByNameAscending()
    {
        for (int i = products.size() -1; i >= 0; i--)
        {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++)
            {
                if (products.get(j).getProductName().compareTo(products.get(highestIndex).getProductName()) > 0) {
                    highestIndex = j;
                }
            }
            swapProducts(products, i, highestIndex);
        }
    }

    private void swapProducts(ArrayList<Product> employees, int i, int j) {
        Product smaller = products.get(i);
        Product bigger = products.get(j);

        products.set(i,bigger);
        products.set(j,smaller);
    }

    /**
     * This method returns the number of product objects stored in the ArrayList.
     *
     * @return An int value representing the number of product objects in the ArrayList.
     */
    public int numberOfProducts() {
        return products.size();
    }

    /**
     * This method takes in a number and checks if it is a valid index in the products ArrayList.
     *
     * @param index A number representing a potential index in the ArrayList.
     * @return True of the index number passed is a valid index in the ArrayList, false otherwise.
     */
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < products.size());
    }

    /**
     * This method builds and returns a String containing all the products in the ArrayList.
     * For each product stored, the associated index number is included.
     * If no products are stored in the ArrayList, the String "No products in the store" is returned.
     *
     * @return A String containing all the products in the ArrayList, or "No products in the store",
     * if empty.
     */
    public String listProducts() {
        if (products.isEmpty()) {
            return "No products in the store";
        } else {
            String listOfProducts = "";
            for (int i = 0; i < products.size(); i++) {
                listOfProducts += i + ": " + products.get(i) + "\n";
            }
            return listOfProducts;
        }
    }

    /**
     * This method returns the cheapest product in the ArrayList.
     * If no products are stored in the ArrayList, null is returned.
     *
     * @return The cheapest Product in the ArrayList or null, if no products are added yet.
     */
    public Product cheapestProduct() {
        if (!products.isEmpty()) {
            Product cheapestProduct = products.get(0);
            for (Product product : products) {
                if (product.getUnitCost() < cheapestProduct.getUnitCost())
                    cheapestProduct = product;
            }
            return cheapestProduct;
        } else {
            return null;
        }
    }

    /**
     * This method builds and returns a String containing all the products in the ArrayList
     * that are in the current product line.
     * <p>
     * For each product added to the String, the associated index number is included.
     *
     * @return A String containing all the products in the ArrayList, or "No products are in our current
     * product line", if none in the current line.  If no products are stored in the ArrayList, the
     * returned String indicates this.
     */
    public String listCurrentProducts() {
        if (products.isEmpty()) {
            return "No Products in the store";
        } else {
            String listOfProducts = "";
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).isInCurrentProductLine())
                    listOfProducts += i + ": " + products.get(i) + "\n";
            }
            if (listOfProducts.equals("")) {
                return "No Products are in our current product line";
            } else {
                return listOfProducts;
            }
        }
    }

    /**
     * This method returns the average product price of all the products in the ArrayList.
     * If no products are stored in the ArrayList, the value returned is -1.
     *
     * @return The average product price, or -1 if no products exist.
     */
    public double averageProductPrice() {
        if (!products.isEmpty()) {
            double totalPrice = 0;
            for (Product product : products) {
                totalPrice += product.getUnitCost();
            }
            return Utilities.toTwoDecimalPlaces(totalPrice / products.size());
        } else {
            return -1;
        }

    }

    /**
     * This method builds and returns a String containing all the products in the ArrayList
     * that are more expensive that a specific amount (passed as a parameter).
     * <p>
     * For each product added to the String, the associated index number is included.
     * If no products are stored in the ArrayList, the returned String indicates this.
     *
     * @param price The value used to filter for products costing more than it.
     * @return A String containing all the products in the ArrayList more expensive than the parameter value
     * or "No Products are more expensive than: ", if none are more expensive.  If no products are
     * in the ArrayList, the returned String contains "No products in store".
     */
    public String listProductsAboveAPrice(double price) {
        if (products.isEmpty()) {
            return "No Products in the store";
        } else {
            String str = "";
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getUnitCost() > price)
                    str += i + ": " + products.get(i) + "\n";
            }
            if (str.equals("")) {
                return "No products are more expensive than: " + price;
            } else {
                return str;
            }
        }
    }

    /**
     * The load method uses the XStream component to read all the product objects from the products.xml
     * file stored on the hard disk.  The read products are loaded into the products ArrayList
     *
     * @throws Exception  An exception is thrown if an error occurred during the load e.g. a missing file.
     */
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[] { Product.class };

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("products.xml"));
        products = (ArrayList<Product>) is.readObject();
        is.close();
    }

    /**
     * The save method uses the XStream component to write all the product objects in the products ArrayList
     * to the products.xml file stored on the hard disk.
     *
     * @throws Exception  An exception is thrown if an error occurred during the save e.g. drive is full.
     */
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("products.xml"));
        out.writeObject(products);
        out.close();
    }

}
