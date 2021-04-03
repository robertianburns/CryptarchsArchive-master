package robertianburns.cryptarchsarchive;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * The Grimoire container.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireContainer {
    private static volatile GrimoireContainer object;
    private JsonArray themeCollection;
    private JsonArray pageCollection;
    private JsonArray cardCollection;
    private JsonObject cardJson;
    private String theme;
    private String page;
    private String card;

    /**
     * Creates a new GrimoireContainer object.
     * <p>
     * Using 'synchronized' so different threads can read and write to the same variables, objects,
     * and resources. If it couldn't get the GrimoireContainer object then a new object is made.
     *
     * @return Grimoire Card object.
     * @version 1.0.0
     * @since 1.0.0
     */
    public static GrimoireContainer getObject() {
        if (object == null) {
            synchronized (GrimoireContainer.class) {
                if (object == null) {
                    object = new GrimoireContainer();
                }
            }
        }
        return object;
    }

    /*
     *   GETTERS
     */

    /**
     * Gets the themeCollection.
     *
     * @return Returns themeCollection.
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonArray getThemeCollection() {
        return themeCollection;
    }

    /**
     * Sets the themeCollection.
     *
     * @param themeCollection The theme collection.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setThemeCollection(JsonArray themeCollection) {
        if (this.themeCollection == null) {
            this.themeCollection = themeCollection;
        }
    }

    /**
     * Gets the pageCollection.
     *
     * @return Returns pageCollection.
     * @author Robert Burns
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonArray getPageCollection() {
        return pageCollection;
    }

    /**
     * Gets the cardCollection.
     *
     * @return Returns cardCollection.
     * @author Robert Burns
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonArray getCardCollection() {
        return cardCollection;
    }

    /*
     *   SETTERS
     */

    /**
     * Gets the cardJson.
     *
     * @return Returns cardJson.
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonObject getCardJson() {
        return cardJson;
    }

    /**
     * Sets the theme.
     *
     * @param theme The theme.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setTheme(String theme) {
        this.theme = theme;
        setPageCollection();
    }

    /**
     * Sets the page.
     *
     * @param page The Grimoire Page.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setPage(String page) {
        this.page = page;
        setCardCollection();
    }

    /**
     * Sets the card.
     *
     * @param card The Grimoire Card.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setCard(String card) {
        this.card = card;
        setCardJson();
    }

    /**
     * Sets the page collection.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void setPageCollection() {
        pageCollection = Objects.requireNonNull(getJsonObjectFromArray(themeCollection, "themeId", theme)).getAsJsonArray("pageCollection");
    }

    /**
     * Sets the card collection.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void setCardCollection() {
        cardCollection = Objects.requireNonNull(getJsonObjectFromArray(pageCollection, "pageName", page)).getAsJsonArray("cardCollection");
    }

    /**
     * Sets the card JSON (ID).
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void setCardJson() {
        cardJson = getJsonObjectFromArray(cardCollection, "cardId", card);
    }

    /**
     * Get a JSON object from the array. Using a for loop, return the JSON object.
     * <p>
     * This is used for retrieving objects, and to set retrievable those objects into things like
     * the theme, the page, the page collection, the card collection, and the card ID.
     *
     * @return The JSON object from the JSON array.
     * @version 1.0.0
     * @since 1.0.0
     */
    private JsonObject getJsonObjectFromArray(JsonArray array, String key, String value) {
        for (JsonElement jElement : array) {
            JsonObject jObject = jElement.getAsJsonObject();
            if (jObject.get(key).getAsString().equals(value)) {
                return jObject;
            }
        }
        return null;
    }
}