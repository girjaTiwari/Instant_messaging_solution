package im.vector.app.timeshare.home.model;

public class LikeModel {
    String activity_uuid,like_count,is_like;

    public LikeModel(String activity_uuid, String like_count, String is_like) {
        this.activity_uuid = activity_uuid;
        this.like_count = like_count;
        this.is_like = is_like;
    }

    public String getActivity_uuid() {
        return activity_uuid;
    }

    public void setActivity_uuid(String activity_uuid) {
        this.activity_uuid = activity_uuid;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }
}
