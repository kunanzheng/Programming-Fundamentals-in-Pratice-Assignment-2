import controllers.AppStoreAPI;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Epic Games", "www.Epicgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0,  1);
        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0, 1.99, 10);
        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5,  2.99,  11);
        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,  0);

        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0);
        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99);
        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99);
        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00);

        gameAppBelowBoundary = new GameApp(developerEAGames, "mineCraft", 1, 1.0, 0.0,  false);
        gameAppOnBoundary = new GameApp(developerKoolGames, "battleField", 1000, 2.0, 1.99,  true);
        gameAppAboveBoundary = new GameApp(developerEAGames, "CallOfDuty", 1001, 3.5,  2.99, false);
        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0,  -1.00,  true);


        appStore.addApp(edAppOnBoundary);
        appStore.addApp(edAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(edAppInvalidData);

        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(prodAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);

        appStore.addApp(gameAppOnBoundary);
        appStore.addApp(gameAppBelowBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(gameAppInvalidData);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {
    }
    @Nested
    class CRUDMethods {
        @Test
        void addingAnAppToArrayList() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            assertEquals(12, appStore.numberOfApps());
            assertTrue(appStore.addApp(app));
            assertTrue(appStore.getAppByName("Evernote").getAppName().contains("Evernote"));
            assertEquals(13, appStore.numberOfApps());
            appStore.deleteAppByIndex(12);
        }

        @Test
        void gettingAppByName() {
            App app = setupProductivityAppWithRating(2,4);
            appStore.addApp(app);
            assertNull(appStore.getAppByName("AppThatCannotExist"));
            assertEquals("Evernote", appStore.getAppByName("Evernote").getAppName());
            assertEquals("Evernote", appStore.getAppByName("eVeRnoTe").getAppName());
            appStore.deleteAppByIndex(12);
        }

        @Test
        void removingAnAppThatDoesNotExistReturnsNull() {
            assertNull(appStore.deleteAppByIndex(666));
            assertNull(appStore.deleteAppByIndex(-200));
            assertNull(appStore.deleteAppByIndex(13));
        }

        @Test
        void removingAnAppFromArrayList() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            appStore.addApp(app);
            assertEquals(13, appStore.numberOfApps());
            assertEquals(app, appStore.deleteAppByIndex(12));
            assertEquals(12, appStore.numberOfApps());
        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("battleField"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CallOfDuty"));
        }

        @Test
        void listAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(12, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(15, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(12, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listAllSummaryOfAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listSummaryOfAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listAllSummaryOfAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String summary = appStore.listSummaryOfAllApps();
            assertTrue(summary.contains("Outlook"));
            assertTrue(summary.contains(String.valueOf(2.0)));
        }

        @Test
        void listAllEducationAppsReturnsEducationAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String summary = appStore.listAllEducationApps();
            assertTrue(summary.contains("Spike"));
            assertTrue(summary.contains("WeDo"));
            assertFalse(summary.contains("CookOff"));
        }

        @Test
        void listAllGameAppsReturnsGameAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String summary = appStore.listAllGameApps();
            assertTrue(summary.contains("battleField"));
            assertTrue(summary.contains("CallOfDuty"));
            assertTrue(summary.contains("mineCraft"));
            assertFalse(summary.contains("Outlook"));
            assertFalse(summary.contains("EV3"));
        }

        @Test
        void listAllProductivityAppsReturnsNoProductivityAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllProductivityApps().toLowerCase().contains("no productivity apps"));
        }

        @Test
        void listAllProductivityAppsReturnsProductivityAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String summary = appStore.listAllProductivityApps();
            assertTrue(summary.contains("Pages"));
            assertTrue(summary.contains("NoteKeeper"));
            assertFalse(summary.contains("MazeRunner"));
            assertFalse(summary.contains("WeDo"));
        }

        @Test
        void listAllGameAppsReturnsNoGameAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllGameApps().toLowerCase().contains("no game apps"));
        }
    }

    @Nested
    class SearchingMethods {
        @Test
        void listAllAppsByNameWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String list = appStore.listAllAppsByName("Pages");
            assertTrue(list.contains("Pages"));
            assertFalse(list.contains("NoteKeeper"));
            assertTrue(list.contains("3.5"));
            assertTrue(list.contains("2.99"));
        }

        @Test
        void listAllAppsByNameReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsByName("NoteKeeper").toLowerCase().contains("no apps"));
        }

        @Test
        void listAllAppsAboveOrEqualAGivenStarRatingReturnsAppsMatchingStarRatingWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            assertTrue(appStore.listAllAppsAboveOrEqualAGivenStarRating(10).toLowerCase().contains("no apps"));
            assertTrue(appStore.listAllAppsAboveOrEqualAGivenStarRating(0).toLowerCase().contains("no apps"));
            appStore.addApp(setupProductivityAppWithRating(2,3));
            String list = appStore.listAllAppsAboveOrEqualAGivenStarRating(1);
            assertTrue(list.contains("Evernote"));
            assertTrue(list.contains("John101"));
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsNoAppsWhenTheDeveloperDoesNotExist() {
            assertTrue(appStore.listAllAppsByChosenDeveloper(developerSphero).toLowerCase().contains("no apps for developer"));
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsAppsMatchingDeveloperWhenArrayListHasAppsStored() {
            assertEquals(12, appStore.numberOfApps());
            String list = appStore.listAllAppsByChosenDeveloper(developerLego);
            assertTrue(list.contains("WeDo"));
            assertTrue(list.contains("Spike"));
            assertFalse(list.contains("CookOff"));
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsByChosenDeveloper(developerSphero).toLowerCase().contains("no apps for developer"));
        }

        @Test
        void numberOfAppsByChosenDeveloper() {
            assertEquals(12, appStore.numberOfApps());
            assertEquals(0, emptyAppStore.numberOfAppsByChosenDeveloper(developerMicrosoft));
            assertEquals(0, appStore.numberOfAppsByChosenDeveloper(developerSphero));
        }
    }

    @Nested
    class SortingMethods {


        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0,emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }

    }

    @Nested
    class PersistenceMethods {
        @Test
        void loadXMLFile() {
            assertEquals(12, appStore.numberOfApps());
            try {
                appStore.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertTrue(appStore.numberOfApps() >= 12);
            // Check whether
        }

        @Test
        void saveXMLFile() {
            File xmlFile = new File("apps.xml");
            if(xmlFile.exists()) xmlFile.delete();
            try {
                appStore.save();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertTrue(xmlFile.exists());
        }

    }

    @Nested
    class ValidationMethods {

        @Test
        void checkRandomApp() {
            assertNull(emptyAppStore.randomApp());
            assertNotNull(appStore.randomApp());
        }

        @Test
        void isValidAppName() {
            assertTrue(appStore.isValidAppName("battleField"));
            assertTrue(appStore.isValidAppName("CallOfDuty"));
            assertTrue(appStore.isValidAppName("mineCraft"));
            assertTrue(appStore.isValidAppName("Outlook"));
            assertFalse(emptyAppStore.isValidAppName("Outlook"));
        }
    }

    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1, 1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1, 1.0, 1.00, true);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1, 1.0, 1.99);
        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

}
