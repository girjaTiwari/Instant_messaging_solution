package im.vector.app.timeshare.home.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Event {

   String activity_uuid,activity_name,activity_description,user_uuid,profile_name,user_pic,first_name,
           last_name,category_name,sub_category,start_time,end_time,post_path,like_count,
        is_like,created_at,location;
    public List<JoiningUser> GetActivityJoinings = new ArrayList<>();

    public Event(String activity_uuid, String activity_name, String activity_description, String user_uuid, String profile_name, String user_pic, String first_name, String last_name, String category_name, String sub_category, String start_time, String end_time, String post_path, String like_count, String is_like, String created_at, String location, List<JoiningUser> getActivityJoinings) {
        this.activity_uuid = activity_uuid;
        this.activity_name = activity_name;
        this.activity_description = activity_description;
        this.user_uuid = user_uuid;
        this.profile_name = profile_name;
        this.user_pic = user_pic;
        this.first_name = first_name;
        this.last_name = last_name;
        this.category_name = category_name;
        this.sub_category = sub_category;
        this.start_time = start_time;
        this.end_time = end_time;
        this.post_path = post_path;
        this.like_count = like_count;
        this.is_like = is_like;
        this.created_at = created_at;
        this.location = location;
        GetActivityJoinings = getActivityJoinings;
    }

    public String getActivity_uuid() {
        return activity_uuid;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getActivity_description() {
        return activity_description;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getPost_path() {
        return post_path;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getLocation() {
        return location;
    }

    public List<JoiningUser> getGetActivityJoinings() {
        return GetActivityJoinings;
    }
}
