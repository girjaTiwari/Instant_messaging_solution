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

package im.vector.app.timeshare.api_request_body;

public class SignupRequest {
    String first_name,last_name,email_id,profile_name,password,mobile_number,device_os,device_type,chat_id,chat_password;

    public SignupRequest(String first_name, String last_name, String email_id, String profile_name, String password, String mobile_number, String device_os, String device_type, String chat_id, String chat_password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.profile_name = profile_name;
        this.password = password;
        this.mobile_number = mobile_number;
        this.device_os = device_os;
        this.device_type = device_type;
        this.chat_id = chat_id;
        this.chat_password = chat_password;
    }
}

