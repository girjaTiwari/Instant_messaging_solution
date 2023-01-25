/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.timeshare.api_response_body;

public class TimelineDetails {
    String activity_uuid,user_uuid,activity_name,activity_description,first_name,last_name,profile_name,
            user_pic,category_name,sub_category,start_date_and_time,end_date_and_time,post_path,location,
            comment_list,like_count,joining_count,is_like,is_joining,created_at;

    public TimelineDetails(String activity_uuid, String user_uuid, String activity_name, String activity_description, String first_name, String last_name, String profile_name, String user_pic, String category_name, String sub_category, String start_date_and_time, String end_date_and_time, String post_path, String location, String comment_list, String like_count, String joining_count, String is_like, String is_joining, String created_at) {
        this.activity_uuid = activity_uuid;
        this.user_uuid = user_uuid;
        this.activity_name = activity_name;
        this.activity_description = activity_description;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_name = profile_name;
        this.user_pic = user_pic;
        this.category_name = category_name;
        this.sub_category = sub_category;
        this.start_date_and_time = start_date_and_time;
        this.end_date_and_time = end_date_and_time;
        this.post_path = post_path;
        this.location = location;
        this.comment_list = comment_list;
        this.like_count = like_count;
        this.joining_count = joining_count;
        this.is_like = is_like;
        this.is_joining = is_joining;
        this.created_at = created_at;
    }

    public String getActivity_uuid() {
        return activity_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getActivity_description() {
        return activity_description;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getStart_date_and_time() {
        return start_date_and_time;
    }

    public String getEnd_date_and_time() {
        return end_date_and_time;
    }

    public String getPost_path() {
        return post_path;
    }

    public String getLocation() {
        return location;
    }

    public String getComment_list() {
        return comment_list;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getJoining_count() {
        return joining_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public String getIs_joining() {
        return is_joining;
    }

    public String getCreated_at() {
        return created_at;
    }
}
