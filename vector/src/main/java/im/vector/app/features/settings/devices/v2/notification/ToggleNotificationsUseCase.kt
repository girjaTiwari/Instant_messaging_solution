/*
 * Copyright (c) 2022 New Vector Ltd
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

package im.vector.app.features.settings.devices.v2.notification

import im.vector.app.core.di.ActiveSessionHolder
import org.matrix.android.sdk.api.account.LocalNotificationSettingsContent
import javax.inject.Inject

class ToggleNotificationsUseCase @Inject constructor(
        private val activeSessionHolder: ActiveSessionHolder,
        private val checkIfCanToggleNotificationsViaPusherUseCase: CheckIfCanToggleNotificationsViaPusherUseCase,
        private val checkIfCanToggleNotificationsViaAccountDataUseCase: CheckIfCanToggleNotificationsViaAccountDataUseCase,
        private val setNotificationSettingsAccountDataUseCase: SetNotificationSettingsAccountDataUseCase,
) {

    suspend fun execute(deviceId: String, enabled: Boolean) {
        val session = activeSessionHolder.getSafeActiveSession() ?: return

        if (checkIfCanToggleNotificationsViaPusherUseCase.execute(session)) {
            val devicePusher = session.pushersService().getPushers().firstOrNull { it.deviceId == deviceId }
            devicePusher?.let { pusher ->
                session.pushersService().togglePusher(pusher, enabled)
            }
        }

        if (checkIfCanToggleNotificationsViaAccountDataUseCase.execute(session, deviceId)) {
            val newNotificationSettingsContent = LocalNotificationSettingsContent(isSilenced = !enabled)
            setNotificationSettingsAccountDataUseCase.execute(session, deviceId, newNotificationSettingsContent)
        }
    }
}
