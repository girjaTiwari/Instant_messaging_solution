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

public class Accept_and_DeclineRequest {
    String friend_request_uuid,is_accepted;

    public Accept_and_DeclineRequest(String friend_request_uuid, String is_accepted) {
        this.friend_request_uuid = friend_request_uuid;
        this.is_accepted = is_accepted;
    }
}
