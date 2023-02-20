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

package im.vector.app.timeshare.webservices;

public class AccountStatus {
    String user_uuid,first_name,last_name,email_id,profile_name,mobile_number,chat_id,chat_password,is_category,is_sub_category;


    public AccountStatus(String user_uuid, String first_name, String last_name, String email_id, String profile_name, String mobile_number, String chat_id, String chat_password, String is_category, String is_sub_category) {
        this.user_uuid = user_uuid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.profile_name = profile_name;
        this.mobile_number = mobile_number;
        this.chat_id = chat_id;
        this.chat_password = chat_password;
        this.is_category = is_category;
        this.is_sub_category = is_sub_category;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getChat_password() {
        return chat_password;
    }

    public String getIs_category() {
        return is_category;
    }

    public String getIs_sub_category() {
        return is_sub_category;
    }
}
