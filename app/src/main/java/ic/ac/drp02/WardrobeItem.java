package ic.ac.drp02;


import java.util.List;

public class WardrobeItem {

    private String imageUrl;
    private String description;
    private List<String> tags;

    public WardrobeItem(String imageUrl, String description, List<String> tags) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.tags = tags;
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
}
