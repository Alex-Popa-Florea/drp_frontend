package ic.ac.drp02;

import java.util.List;

public class NewWardrobeItem {


    private String imageUrl;
    private List<String> pics;
    private List<String> tags;
    private String description;

    private String name;
    private String type_;
    private Integer likes;
    private String creation_time;

    public NewWardrobeItem(List<String> pics, String description, List<String> tags, String name, String type_, Integer likes,String creation_time) {
        this.pics = pics;
        this.imageUrl = pics.get(0);
        this.description = description;
        this.tags = tags;
        this.name = name;
        this.type_ = type_;
        this.likes = likes;
        this.creation_time = creation_time;
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
        return name;
    }

    public String getItemType() {
        return type_;
    }

    public Integer getLikes() {
        return likes;
    }
}
