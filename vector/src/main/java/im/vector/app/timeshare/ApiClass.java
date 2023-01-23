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

package im.vector.app.timeshare;

public class ApiClass {

    public static String BASE_URL = "https://timeshare.telemo.io:20707/";
    public static String IMAGE_BASE_URL = "https://timeshare.telemo.io";


    public static String signup = "signup";
    public static String loggedin = "loggedin";
    public static String loggedout = "loggedout";
    public static String verify_email = "verify_email";

    public static String get_category = "get_category";
    public static String add_category = "add_category";

    public static String add_sub_category = "add_sub_category";
    public static String get_sub_category = "get_sub_category";
    public static String get_sub_category_by_category = "get_sub_category_by_category";


    public static String delete_account = "delete_account";
    public static String change_password = "change_password";
    public static String resend_otp = "resend_otp";
    public static String verify_otp = "verify_otp";
    public static String forget_password = "forget_password";


    // friends tab api's
    public static String get_friend_request="get-friend-request";
    public static String get_friend_list="get-friend-list";
    public static String friend_request="friend-requests";
    public static String get_suggestion_list="suggestion-list";
    public static String get_sent_friend_request="get-sent-friend-request";
    public static String send_friend_request="send-friend-request";
    public static String undo_sent_friend_request="undo-sent-friend-request";
    public static String api_unfriend="unfriend";

    //timeline home screen

    public static String get_timeline_by_user_uuid = "get-timeline-by-user-uuid";

    // activity api's

    public static String like_post="like-post";
    public static String edit_activity="edit-activity";
    public static String joining_activity="joining-activity";
    public static String get_joining_activity="get-joining-activity";
    public static String create_respond="create-respond";
    public static String get_respond="get-respond-by-activity-uuid";
    public static String reply_respond="create-respond-to-respond";
    public static String invite_friends="invite-friends";
    public static String delete_activity="delete-activity";

    //timeline details
    public static String get_activity_by_activity_uuid="get-activity-by-activity-uuid";
    public static String get_activity_by_user_uuid = "get-activity-by-user-uuid";

    // profile api's

    public static String get_profile="get-profile";
    public static String update_profile="update-profile";
    public static String update_profile_pic="upload-profile-pic";
    public static String remove_profile_pic="remove-profile-pic";


    // review app
    public static String add_review = "add_review";
    public static String get_review = "get_review";
    public static String update_review = "update_review";

    //follow and Following api's
    public static String get_follower_list = "get-follower-list";
    public static String get_following_list = "get-following-list";


    // report-bug
    public static String report_bug = "report-bug";
}
