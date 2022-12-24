package controllers;

import models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    private Product tv42Valid, tv32Valid, tv50Invalid, iPhone8, iPhone10;
    private Store acmeStore, noProductStore;

    @BeforeEach
    void setUp() {
        tv32Valid = new Product("TV 32Inches SuperHD", 1000, 239.99, false);
        tv42Valid = new Product("TV 42 Inches SuperHD", 999999, 359.99, true);
        tv50Invalid = new Product("TV 50 Inches Super HD", 999, -1, true);
        iPhone8 = new Product("iPhone 8", 1208, 345.99, false);
        iPhone10 = new Product("iPhone 10", 1210, 525.99, true);

        acmeStore = new Store();
        acmeStore.add(tv32Valid);
        acmeStore.add(tv42Valid);
        acmeStore.add(tv50Invalid);
        noProductStore = new Store();
    }

    @AfterEach
    void tearDown() {
        tv32Valid = tv42Valid = tv50Invalid = null;
        acmeStore = noProductStore = null;
    }

    @Nested
    class CRUD{
        @Test
        void addingProductsToStoreWorksCorrectly() {
            assertEquals(3, acmeStore.numberOfProducts());
            acmeStore.add(iPhone8);
            assertEquals(4, acmeStore.numberOfProducts());
            assertEquals(iPhone8, acmeStore.findProduct(3));

            assertEquals(0, noProductStore.numberOfProducts());
            noProductStore.add(iPhone10);
            assertEquals(1, noProductStore.numberOfProducts());
            assertEquals(iPhone10, noProductStore.findProduct(0));
        }

        @Test
        void findProductsByIdWorksCorrectly() {
            assertEquals(tv32Valid, acmeStore.findProduct(0));
            assertEquals(tv42Valid, acmeStore.findProduct(1));
            assertEquals(tv50Invalid, acmeStore.findProduct(2));
            assertNull(acmeStore.findProduct(-1));
            assertNull(acmeStore.findProduct(3));

            assertNull(noProductStore.findProduct(0));
        }

        @Test
        void updateProductsByIndexWorksCorrectly() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertEquals(tv50Invalid, acmeStore.findProduct(2));

            Product updatedProduct =  new Product("TV 55 Inches", 1276, 689.95, false);
            assertTrue(acmeStore.updateProduct(2, updatedProduct));
            assertEquals(tv50Invalid, acmeStore.findProduct(2));
            assertEquals(updatedProduct, acmeStore.findProduct(2));
            assertEquals(updatedProduct, tv50Invalid);

            assertEquals(0, noProductStore.numberOfProducts());
            assertFalse(noProductStore.updateProduct(0, updatedProduct));
        }

        @Test
        void deleteProductsByIndexWorksCorrectly() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertEquals(tv50Invalid, acmeStore.findProduct(2));
            acmeStore.deleteProduct(2);
            assertEquals(2, acmeStore.numberOfProducts());

            assertEquals(0, noProductStore.numberOfProducts());
            assertNull(noProductStore.deleteProduct(0));
        }

    }

    @Nested
    class BoilerPlate {

        @Test
        void productsListGetAndSetWorkingCorrectly() {
            assertEquals(3, acmeStore.getProducts().size());
            assertEquals(tv32Valid, acmeStore.getProducts().get(0));
            assertEquals(tv42Valid, acmeStore.getProducts().get(1));
            assertEquals(tv50Invalid, acmeStore.getProducts().get(2));

            assertEquals(0, noProductStore.getProducts().size());
            ArrayList<Product> tempProducts = new ArrayList<>();
            var product1 = new Product("temp product 1", 1234, 34.99, true);
            var product2 = new Product("temp product 2", 1235, 39.99, false);
            tempProducts.add(product1);
            tempProducts.add(product2);
            noProductStore.setProducts(tempProducts);
            assertEquals(2, noProductStore.getProducts().size());
        }
    }

    @Nested
    class Listing {

        @Test
        void listProductsContainsAllProductsStored() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertTrue(acmeStore.listProducts().toLowerCase().contains("TV 32Inches SuperHD".toLowerCase()));
            assertTrue(acmeStore.listProducts().toLowerCase().contains("TV 42 Inches SuperHD".toLowerCase()));
            assertTrue(acmeStore.listProducts().toLowerCase().contains("TV 50 Inches Super H".toLowerCase()));

            assertEquals(0, noProductStore.numberOfProducts());
            assertTrue(noProductStore.listProducts().toLowerCase().contains("No products".toLowerCase()));
        }

        @Test
        void listCurrentProductsContainsActiveProductsOnly() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertFalse(acmeStore.listCurrentProducts().toLowerCase().contains("TV 32Inches SuperHD".toLowerCase()));
            assertTrue(acmeStore.listCurrentProducts().toLowerCase().contains("TV 42 Inches SuperHD".toLowerCase()));
            assertTrue(acmeStore.listCurrentProducts().toLowerCase().contains("TV 50 Inches Super H".toLowerCase()));

            assertEquals(0, noProductStore.numberOfProducts());
            assertTrue(noProductStore.listCurrentProducts().toLowerCase().contains("No products".toLowerCase()));

            noProductStore.add(iPhone8);
            assertEquals(1, noProductStore.numberOfProducts());
            assertTrue(noProductStore.listCurrentProducts().toLowerCase().contains("No Products are in our current product line".toLowerCase()));
        }

        @Test
        void listProductsAboveAPriceContainsCorrectProducts() {
            assertEquals(3, acmeStore.numberOfProducts());

            var productsAboveAPrice = acmeStore.listProductsAboveAPrice(-1).toLowerCase();
            assertTrue(productsAboveAPrice.contains("TV 32Inches SuperHD".toLowerCase()));
            assertTrue(productsAboveAPrice.contains("TV 42 Inches SuperHD".toLowerCase()));
            assertTrue(productsAboveAPrice.contains("TV 50 Inches Super H".toLowerCase()));

            productsAboveAPrice = acmeStore.listProductsAboveAPrice(0).toLowerCase();
            assertTrue(productsAboveAPrice.contains("TV 32Inches SuperHD".toLowerCase()));
            assertTrue(productsAboveAPrice.contains("TV 42 Inches SuperHD".toLowerCase()));
            assertFalse(productsAboveAPrice.contains("TV 50 Inches Super H".toLowerCase()));

            productsAboveAPrice = acmeStore.listProductsAboveAPrice(239.99).toLowerCase();
            assertFalse(productsAboveAPrice.contains("TV 32Inches SuperHD".toLowerCase()));
            assertTrue(productsAboveAPrice.contains("TV 42 Inches SuperHD".toLowerCase()));
            assertFalse(productsAboveAPrice.contains("TV 50 Inches Super H".toLowerCase()));

            productsAboveAPrice = acmeStore.listProductsAboveAPrice(359.99).toLowerCase();
            assertTrue(productsAboveAPrice.contains("No products are more expensive".toLowerCase()));

            assertEquals(0, noProductStore.numberOfProducts());
            productsAboveAPrice = noProductStore.listProductsAboveAPrice(-1).toLowerCase();
            assertTrue(productsAboveAPrice.contains("No products".toLowerCase()));
       }

    }

    @Nested
    class Stats {
        @Test
        void averageProductPriceCalculatesCorrectly() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertEquals(199.99, acmeStore.averageProductPrice(), 0.01);

            assertEquals(0, noProductStore.numberOfProducts());
            assertEquals(-1, noProductStore.averageProductPrice(), 0.01);

            noProductStore.add(iPhone8);
            assertEquals(1, noProductStore.numberOfProducts());
            assertEquals(345.99, noProductStore.averageProductPrice(), 0.01);
        }

        @Test
        void cheapestProductCalculatedCorrectly() {
            assertEquals(3, acmeStore.numberOfProducts());
            assertEquals(tv50Invalid, acmeStore.cheapestProduct());
            tv50Invalid.setUnitCost(999.99);
            assertEquals(tv32Valid, acmeStore.cheapestProduct());
            tv32Valid.setUnitCost(360);
            assertEquals(tv42Valid, acmeStore.cheapestProduct());

            assertEquals(0, noProductStore.numberOfProducts());
            assertNull(noProductStore.cheapestProduct());
        }

    }

}
