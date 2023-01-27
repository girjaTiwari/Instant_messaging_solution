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

public class JoinActivityRequest {
    String activity_uuid,user_uuid,is_joining;

    public JoinActivityRequest(String activity_uuid, String user_uuid, String is_joining) {
        this.activity_uuid = activity_uuid;
        this.user_uuid = user_uuid;
        this.is_joining = is_joining;
    }
}
