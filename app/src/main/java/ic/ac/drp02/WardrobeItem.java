package ic.ac.drp02;


import java.util.List;

public class WardrobeItem {

    private String imageUrl;
    private String description;
    private List<String> tags;
    private String itemName;
    private String itemType;

    public WardrobeItem(String imageUrl, String description, List<String> tags, String itemName, String itemType) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.tags = tags;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public String getTagString() {
        return tags.toString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }
}
