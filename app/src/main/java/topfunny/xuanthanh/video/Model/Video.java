package topfunny.xuanthanh.video.Model;

/**
 * Created by QUOC CUONG on 17/10/2017.
 * Creating Video object
 */

public class Video {
    private String CategoryId, Image, Title, VideoId;

    public Video() {
    }

    public Video(String categoryId, String image, String title, String videoId) {
        CategoryId = categoryId;
        Image = image;
        Title = title;
        VideoId = videoId;
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

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }
}
