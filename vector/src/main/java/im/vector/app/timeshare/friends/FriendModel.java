package im.vector.app.timeshare.friends;

public class FriendModel {
    String friend_uuid,name,profile_pic;

    public FriendModel(String friend_uuid, String name, String profile_pic) {
        this.friend_uuid = friend_uuid;
        this.name = name;
        this.profile_pic = profile_pic;
    }

    public String getFriend_uuid() {
        return friend_uuid;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }
}
