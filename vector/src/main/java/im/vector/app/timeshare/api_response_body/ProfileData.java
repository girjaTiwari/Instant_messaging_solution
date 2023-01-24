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

public class ProfileData {
    String user_uuid,first_name,last_name,user_pic,profile_name,email_id,
            mobile_number,gender,date_of_birth,city,address,country,
            category1,category2,category3,category4,category5,mutual_friends;

    public ProfileData(String user_uuid, String first_name, String last_name, String user_pic, String profile_name, String email_id, String mobile_number, String gender, String date_of_birth, String city, String address, String country, String category1, String category2, String category3, String category4, String category5, String mutual_friends) {
        this.user_uuid = user_uuid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_pic = user_pic;
        this.profile_name = profile_name;
        this.email_id = email_id;
        this.mobile_number = mobile_number;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.city = city;
        this.address = address;
        this.country = country;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
        this.category5 = category5;
        this.mutual_friends = mutual_friends;
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

    public String getUser_pic() {
        return user_pic;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getGender() {
        return gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory1() {
        return category1;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory3() {
        return category3;
    }

    public String getCategory4() {
        return category4;
    }

    public String getCategory5() {
        return category5;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }
}
