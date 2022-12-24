package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product tv42Valid;
    private Product tv32Valid;
    private Product tv50Invalid;
    private Product tv60WithDescriptions;

    @BeforeEach
    void setUp(){
        tv32Valid = new Product("TV 32Inches SuperHD", 1000, 239.99, false);
        tv42Valid = new Product("TV 42 Inches SuperHD", 999999, 359.99, true);
        tv50Invalid = new Product("TV 50 Inches Super HD", 999, -1, true);
        tv60WithDescriptions = new Product("TV 60 Inches", 5021, 659.95 , true);
        tv60WithDescriptions.addDescription("ENGLISH", "60 Inch TV, Super HD, with black surround");
        tv60WithDescriptions.addDescription("GERMAN", "60-Zoll-Fernseher, Super HD, mit schwarzem Surround");
    }

    @AfterEach
    void tearDown(){
        tv32Valid = tv42Valid = tv50Invalid = tv60WithDescriptions = null;
    }

    @Nested
    class BoilerPlate {
        @Test
        void unitCostGetAndSetWorkingCorrectly() {
            assertEquals(239.99, tv32Valid.getUnitCost(), 0.01);
            tv32Valid.setUnitCost(249.99);
            assertEquals(249.99, tv32Valid.getUnitCost(), 0.01);

            assertEquals(359.99, tv42Valid.getUnitCost(), 0.01);
            tv42Valid.setUnitCost(0);
            assertEquals(359.99, tv42Valid.getUnitCost(), 0.01);
            tv42Valid.setUnitCost(0.01);
            assertEquals(0.01, tv42Valid.getUnitCost(), 0.01);
            tv42Valid.setUnitCost(99999.99);
            assertEquals(99999.99, tv42Valid.getUnitCost(), 0.01);
            tv42Valid.setUnitCost(100000);
            assertEquals(99999.99, tv42Valid.getUnitCost(), 0.01);

            assertEquals(0, tv50Invalid.getUnitCost(), 0.01);
        }

        @Test
        void productCodeGetAndSetWorkingCorrectly() {
            assertEquals(1000, tv32Valid.getProductCode());
            tv32Valid.setProductCode(999999);
            assertEquals(999999, tv32Valid.getProductCode());
            tv32Valid.setProductCode(1000000);
            assertEquals(999999, tv32Valid.getProductCode());


            assertEquals(999999, tv42Valid.getProductCode());
            tv42Valid.setProductCode(1000);
            assertEquals(1000, tv42Valid.getProductCode());
            tv42Valid.setProductCode(999);
            assertEquals(1000, tv42Valid.getProductCode());

            assertEquals(-1, tv50Invalid.getProductCode());
        }

        @Test
        void productNameGetAndSetWorkingCorrectly() {
            //testing 19 chars
            assertEquals("TV 32Inches SuperHD", tv32Valid.getProductName());
            tv32Valid.setProductName("0123456789012345678");
            assertEquals("0123456789012345678", tv32Valid.getProductName());

            //testing 20 chars
            assertEquals("TV 42 Inches SuperHD", tv42Valid.getProductName());
            tv42Valid.setProductName("01234567890123456789");
            assertEquals("01234567890123456789", tv42Valid.getProductName());

            //tests 21 chars
            assertEquals("TV 50 Inches Super H", tv50Invalid.getProductName());
            tv50Invalid.setProductName("012345678901234567890");
            assertEquals("TV 50 Inches Super H", tv50Invalid.getProductName());
        }

        @Test
        void inCurrentProdLineGetAndSetWorkingCorrectly() {
            assertFalse(tv32Valid.isInCurrentProductLine());
            assertTrue(tv42Valid.isInCurrentProductLine());
            assertTrue(tv50Invalid.isInCurrentProductLine());

            tv32Valid.setInCurrentProductLine(true);
            assertTrue(tv32Valid.isInCurrentProductLine());
            tv42Valid.setInCurrentProductLine(false);
            assertFalse(tv42Valid.isInCurrentProductLine());
        }

        @Test
        void productDescriptionsMapGetterReturnsCorrectly() {
            //Testing when descriptions exist
            assertEquals(2, tv60WithDescriptions.getProductDescriptions().size());
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("GERMAN"));
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("POLISH"));

            //Testing when NO descriptions exist
            assertTrue(tv32Valid.getProductDescriptions().isEmpty());
        }

        @Test
        void productDescriptionsMapSetterUpdatesCorrectly() {
            //Check existing product contains 2 entries and validate the entries
            assertEquals(2, tv60WithDescriptions.getProductDescriptions().size());
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("GERMAN"));

            //Create a new Map with one entry.  Use the setter to update the HashMap.
            //Then check the new map contains 1 entry and validate the entry.
            //Also ensure that ENGLISH and GERMAN are no longer in the map.
            Map<String, String> newMap = new HashMap<>();
            newMap.put("SPANISH", "Televisor de 60 pulgadas, Super HD, con sonido envolvente negro");
            tv60WithDescriptions.setProductDescriptions(newMap);
            assertEquals(1, tv60WithDescriptions.getProductDescriptions().size());
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("SPANISH"));
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("GERMAN"));

            //Testing adding newMap to a product that has no descriptions to start with
            assertTrue(tv32Valid.getProductDescriptions().isEmpty());
            tv32Valid.setProductDescriptions(newMap);
            assertEquals(1, tv32Valid.getProductDescriptions().size());
            assertTrue(tv32Valid.getProductDescriptions().containsKey("SPANISH"));
        }

        @Test
        void validatingTheEqualsMethod() {
            Product tv32ValidData = new Product("TV 32Inches SuperHD", 1000, 239.99, false);
            assertNotSame(tv32Valid, tv32ValidData);  //checking they are not the same identify i.e. memory location
            assertEquals(tv32Valid, tv32ValidData);  //checking they have the same object state i.e. variable contents.
        }

        @Test
        void toStringContainsAllFieldsInAProductObject() {
            assertTrue(tv32Valid.toString().toLowerCase().contains("tv 32"));
            assertTrue(tv32Valid.toString().contains("1000"));
            assertTrue(tv32Valid.toString().contains("239.99"));
            assertTrue(tv32Valid.toString().toLowerCase().contains("n"));
        }

    }

    @Nested
    class MapCRUD{

        @Test
        void numberOfDescriptionsCountsCorrectly(){
            assertEquals(2, tv60WithDescriptions.numberOfDescriptions());
            assertEquals(tv60WithDescriptions.getProductDescriptions().size(), tv60WithDescriptions.numberOfDescriptions());
        }

        @Test
        void addDescriptionWhenDescriptionAlreadyExistsReturnsFalse(){
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertFalse(tv60WithDescriptions.addDescription("ENGLISH", "Should return false"));
        }

        @Test
        void addDescriptionWhenDescriptionDoesNotExistReturnsTrue(){
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("SPANISH"));
            assertTrue(tv60WithDescriptions.addDescription("SPANISH", "Televisor de 60 pulgadas, Super HD, con sonido envolvente negro"));
        }

        @Test
        void addDescriptionWhenLanguageDoesNotExistReturnsFalse(){
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("IRISH"));
            assertFalse(tv60WithDescriptions.addDescription("IRISH", "60 Inch TV, Super HD, le timpeallán dubh"));
        }

        @Test
        void listProductDescriptionsDisplaysAllEntries(){
            //Check existing product contains 2 entries and validate the entries
            assertEquals(2, tv60WithDescriptions.getProductDescriptions().size());
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("GERMAN"));

            //Verify the returned String contains the description details.
            var listProductDescriptions = tv60WithDescriptions.listDescriptions().toLowerCase();
            assertTrue(listProductDescriptions.contains("english"));
            assertTrue(listProductDescriptions.contains("german"));
            assertTrue(listProductDescriptions.contains("60 Inch TV, Super HD, with black surround".toLowerCase()));
            assertTrue(listProductDescriptions.contains("60-Zoll-Fernseher, Super HD, mit schwarzem Surround".toLowerCase()));

            //Check existing product contains 0 entries
            assertEquals(0, tv32Valid.getProductDescriptions().size());
            assertEquals("No descriptions added yet", tv32Valid.listDescriptions());
        }

        @Test
        void isAlreadyAddedLanguageReturnsTrueWhenLanguageExists() {
            assertTrue(tv60WithDescriptions.getProductDescriptions().containsKey("ENGLISH"));
            assertTrue(tv60WithDescriptions.isAlreadyAddedLanguage("ENGLISH"));
        }

        @Test
        void isAlreadyAddedLanguageReturnsFalseWhenLanguageDoesNotExist() {
            assertFalse(tv60WithDescriptions.getProductDescriptions().containsKey("IRISH"));
            assertFalse(tv60WithDescriptions.isAlreadyAddedLanguage("IRISH"));
        }

        @Test
        void findDescriptionReturnsDescriptionWhenLanguageExists() {
            assertEquals("60 Inch TV, Super HD, with black surround", tv60WithDescriptions.findDescription("ENGLISH"));
            assertEquals("60-Zoll-Fernseher, Super HD, mit schwarzem Surround", tv60WithDescriptions.findDescription("GERMAN"));
        }

        @Test
        void findDescriptionReturnsNullWhenLanguageIsInvalid() {
            assertNull(tv60WithDescriptions.findDescription("SPANISH"));
        }

        @Test
        void deleteDescriptionReturnsNullWhenKeyIsInvalid() {
            assertNull(tv60WithDescriptions.deleteDescription("SPANISH"));
        }

        @Test
        void deleteDescriptionReturnsDescriptionWhenKeyIsValid() {
            assertEquals("60 Inch TV, Super HD, with black surround", tv60WithDescriptions.deleteDescription("ENGLISH"));
        }

        @Test
        void updateDescriptionReturnsFalseWhenKeyIsInvalid() {
            assertFalse(tv60WithDescriptions.isAlreadyAddedLanguage("IRISH"));
            assertFalse(tv60WithDescriptions.updateDescription("IRISH", "60 Inch TV, Super HD, le timpeallán dubh"));
            assertNull(tv60WithDescriptions.findDescription("IRISH"));
        }

        @Test
        void updateDescriptionUpdatesAndReturnsTrueWhenKeyIsValid() {
            assertTrue(tv60WithDescriptions.isAlreadyAddedLanguage("ENGLISH"));
            assertTrue(tv60WithDescriptions.updateDescription("ENGLISH", "60 Inch TV, Super HD, with white surround"));
            assertEquals("60 Inch TV, Super HD, with white surround", tv60WithDescriptions.findDescription("ENGLISH"));
        }
    }
}