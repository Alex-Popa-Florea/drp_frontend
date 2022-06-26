package ic.ac.drp02;


import java.util.List;
import java.util.Objects;

public class WardrobeItem {

    private Integer id;
    private Integer uid;
    private String imageUrl;
    private List<String> pics;
    private List<String> tags;
    private String description;

    private String name;
    private String type_;
    private Integer likes;

    public WardrobeItem(Integer uid, List<String> pics, String description, List<String> tags, String name, String type_, Integer likes) {
        this.uid = uid;
        this.pics = pics;
        this.imageUrl = pics.get(0);
        this.description = description;
        this.tags = tags;
        this.name = name;
        this.type_ = type_;
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTagString() {
        return tags.toString();
    }

    public String getImageUrl() {
        return pics.get(0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WardrobeItem that = (WardrobeItem) o;
        return Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(pics, that.pics) && Objects.equals(tags, that.tags) && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(type_, that.type_) && Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, imageUrl, pics, tags, description, name, type_, likes);
    }
}
