package organization.tho.entertaiment.Model;

/**
 * Created by QUOC CUONG on 17/10/2017.
 * Creating Video object
 */

public class Video {
    private String CategoryId, Image, Title, VideoLink;

    public Video() {
    }

    public Video(String categoryId, String image, String title, String videoId) {
        CategoryId = categoryId;
        Image = image;
        Title = title;
        VideoLink = videoId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }
}
