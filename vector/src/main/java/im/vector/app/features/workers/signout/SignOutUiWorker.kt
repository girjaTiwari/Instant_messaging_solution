/*
 * Copyright 2019 New Vector Ltd
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

package im.vector.app.features.workers.signout

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.dropbox.core.v2.teamlog.EventType.logout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.ContextUtils.getActivity
import im.vector.app.R
import im.vector.app.core.extensions.cannotLogoutSafely
import im.vector.app.core.extensions.singletonEntryPoint
import im.vector.app.features.MainActivity
import im.vector.app.features.MainActivityArgs
import im.vector.app.timeshare.TSSessionManager
import im.vector.app.timeshare.api_request_body.CommonRequest
import im.vector.app.timeshare.api_request_body.LoginRequest
import im.vector.app.timeshare.api_response_body.CommonResponse
import im.vector.app.timeshare.api_response_body.LoginResponse
import im.vector.app.timeshare.webservices.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignOutUiWorker(private val activity: FragmentActivity) {
    lateinit var tsSessionManager:TSSessionManager;

    fun perform() {
        val session = activity.singletonEntryPoint().activeSessionHolder().getSafeActiveSession() ?: return
        if (session.cannotLogoutSafely()) {
            // The backup check on logout flow has to be displayed if there are keys in the store, and the keys backup state is not Ready
            val signOutDialog = SignOutBottomSheetDialogFragment.newInstance()
            signOutDialog.onSignOut = Runnable {
                doSignOut()
            }
            signOutDialog.show(activity.supportFragmentManager, "SO")
        } else {
            // Display a simple confirmation dialog
            MaterialAlertDialogBuilder(activity)
                    .setTitle(R.string.action_sign_out)
                    .setMessage(R.string.action_sign_out_confirmation_simple)
                    .setPositiveButton(R.string.action_sign_out) { _, _ ->
                        doSignOut()
                       /* if (tsSessionManager.isLoggedIn()) {
                            var user = HashMap<String?, String?>()
                            user = tsSessionManager.getUserDetails()
                            val uuid = user[TSSessionManager.KEY_user_uuid]
                            uuid?.let { logoutApiHit(it) }
                        }*/
                    }
                    .setNegativeButton(R.string.action_cancel, null)
                    .show()
        }
    }

    private fun doSignOut() {

        MainActivity.restartApp(activity, MainActivityArgs(clearCredentials = true))
    }

    private fun logoutApiHit(uuid: String) {
        val mAPIService = ApiUtils.getAPIService()
        val logoutRequest = CommonRequest(uuid)
        val call: Call<CommonResponse> = mAPIService.logout(logoutRequest)
        call.enqueue(object : Callback<CommonResponse?> {
            override fun onResponse(call: Call<CommonResponse?>, response: Response<CommonResponse?>) {
                  System.out.println("logoutApi>>" + response.toString());
                if (response.body() != null) {
                    val signupResponse = response.body()
                    val message = signupResponse?.msg
                    val status = signupResponse?.status
                    if (status == "1") {
                        tsSessionManager.logoutUser()
                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
                println("error>>" + t.cause)
            }
        })
    }
}
