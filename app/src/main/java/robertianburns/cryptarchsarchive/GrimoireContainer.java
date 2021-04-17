package robertianburns.cryptarchsarchive;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * The Grimoire container.
 * <p>
 * Java Containers provide functionality, such as handling web request, provide user authentication,
 * logging, or the connection to a database. GrimoireContainer is used to grab grimoire and apply
 * them to setters.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireContainer {
    private static volatile GrimoireContainer object;

    /* Bungie ordered the Grimoire as:
     * THEME (i.e. Enemies) > PAGE (i.e. Fallen) > CARD (i.e. Dreg)
     *
     * I changed it to:
     * SECTION (for a section of the Grimoire) > CATEGORY (for a category of the section) > CARD
     * (for a card of the category) as this is easier for me to 'visualise' what is what.
     *
     * This is why there are still 'theme' and 'page' tags (as Bungie uses them), even though I
     * don't personally use those words for variables.
     */
    private JsonArray sectionCollection;
    private JsonArray categoryCollection;
    private JsonArray cardCollection;
    private JsonObject cardJson;
    private String section;
    private String category;
    private String card;

    /**
     * Creates a new GrimoireContainer object.
     * <p>
     * Used 'synchronized' so different threads can read and write to the same variables, objects,
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
     * Gets the sectionCollection.
     *
     * @return Returns sectionCollection.
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonArray getSectionCollection() {
        return sectionCollection;
    }

    /**
     * Sets the sectionCollection.
     *
     * @param sectionCollection The section collection.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setSectionCollection(JsonArray sectionCollection) {
        if (this.sectionCollection == null) {
            this.sectionCollection = sectionCollection;
        }
    }

    /**
     * Gets the categoryCollection.
     *
     * @return Returns categoryCollection.
     * @author Robert Burns
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonArray getCategoryCollection() {
        return categoryCollection;
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
     * Sets the section.
     *
     * @param section The section.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setSection(String section) {
        this.section = section;
        setPageCollection();
    }

    /**
     * Sets the category.
     *
     * @param category The Grimoire Page.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setCategory(String category) {
        this.category = category;
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
     * Sets the category collection.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void setPageCollection() {
        categoryCollection = Objects.requireNonNull(getJsonObjectFromArray(sectionCollection, "themeId", section)).getAsJsonArray("pageCollection");
    }

    /**
     * Sets the card collection.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void setCardCollection() {
        cardCollection = Objects.requireNonNull(getJsonObjectFromArray(categoryCollection, "pageName", category)).getAsJsonArray("cardCollection");
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
     * the section, the category, the category collection, the card collection, and the card ID.
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