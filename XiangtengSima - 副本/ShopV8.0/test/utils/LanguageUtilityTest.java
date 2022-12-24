package utils;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LanguageUtilityTest {

    @Test
    void getSuppliersReturnsFullSuppliersSet() {
        Set<String> suppliers = LanguageUtility.getLanguages();
        assertEquals(4, suppliers.size());
        assertTrue(suppliers.contains("ENGLISH"));
        assertTrue(suppliers.contains("GERMAN"));
        assertFalse(suppliers.contains(""));
    }

    @Test
    void isValidSupplierTrueWhenSupplierExists() {
        assertTrue(LanguageUtility.isValidLanguage("english"));
        assertTrue(LanguageUtility.isValidLanguage("English"));
        assertTrue(LanguageUtility.isValidLanguage("ENGLISH"));
    }

    @Test
    void isValidSupplierFalseWhenSupplierDoesNotExist() {
        assertFalse(LanguageUtility.isValidLanguage("eng"));
        assertFalse(LanguageUtility.isValidLanguage("german "));
        assertFalse(LanguageUtility.isValidLanguage(""));
    }
}