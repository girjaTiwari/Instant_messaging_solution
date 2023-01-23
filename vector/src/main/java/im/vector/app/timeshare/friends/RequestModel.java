package im.vector.app.timeshare.friends;

public class RequestModel {
    String friend_request_uuid,sender_uuid,sender_name,sender_pic,mutual_friends,created_at;

    public RequestModel(String friend_request_uuid, String sender_uuid, String sender_name, String sender_pic,String mutual_friends, String created_at) {
        this.friend_request_uuid = friend_request_uuid;
        this.sender_uuid = sender_uuid;
        this.sender_name = sender_name;
        this.sender_pic = sender_pic;
        this.mutual_friends = mutual_friends;
        this.created_at = created_at;
    }

    public String getFriend_request_uuid() {
        return friend_request_uuid;
    }

    public String getSender_uuid() {
        return sender_uuid;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getSender_pic() {
        return sender_pic;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }

    public String getCreated_at() {
        return created_at;
    }
}
