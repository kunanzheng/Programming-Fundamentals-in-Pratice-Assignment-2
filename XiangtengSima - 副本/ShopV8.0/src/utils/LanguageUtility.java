package utils;

import java.util.HashSet;
import java.util.Set;

/**
 * This utility class populates, manages and validates the languages used in the app.
 *
 * @author Mairead Meagher, Siobhan Drohan
 * @version 1.0
 */
public class LanguageUtility {

    //Available languages in the app
    private static Set<String> languages = new HashSet<>(){{
        add("ENGLISH");
        add("GERMAN");
        add("FRENCH");
        add("SPANISH");
    }};

    /**
     * This method returns the set of languages used in the app.
     *
     * @return Set of languages
     */
    public static Set<String> getLanguages() {
        return languages;
    }

    /**
     * This method validates a language, passed as a parameter, against the pre-populated set
     * of languages.
     *
     * @param languageToCheck  Language to validate
     * @return True if the language exists, and false otherwise.  Case is ignored during validation.
     */
    public static boolean isValidLanguage(String languageToCheck) {
        for (String language : languages) {
            if (language.equalsIgnoreCase(languageToCheck)) {
                return true;
            }
        }
        return false;
    }

}
