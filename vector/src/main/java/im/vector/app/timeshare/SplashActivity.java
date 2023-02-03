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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


import im.vector.app.R;
import im.vector.app.features.MainActivity;
import im.vector.app.features.login.LoginActivity;
import im.vector.app.features.onboarding.OnboardingActivity;
import im.vector.app.timeshare.auth.TSLoginActivity;
import im.vector.app.timeshare.categ.CategoryActivity;
import im.vector.app.timeshare.categ.SubCategoryActivity;

public class SplashActivity extends AppCompatActivity {
    TSSessionManager tsSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tsSessionManager = new TSSessionManager(SplashActivity.this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
           // window.setNavigationBarColor(ContextCompat.getColor(this, R.color.element_accent_light));
         //  window.setStatusBarColor(ContextCompat.getColor(this, R.color.element_accent_light));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(intent);
               /* if (tsSessionManager.isLoggedIn()) {

                    if (tsSessionManager.isCategory() && tsSessionManager.isSubCategory()) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (tsSessionManager.isCategory() && !tsSessionManager.isSubCategory()) {
                        startActivity(new Intent(SplashActivity.this, SubCategoryActivity.class));
                        finish();
                    } else if (!tsSessionManager.isCategory() && !tsSessionManager.isSubCategory()) {
                        startActivity(new Intent(SplashActivity.this, CategoryActivity.class));
                       finish();
                    }
                }
                else {

                    Intent intent = new Intent(SplashActivity.this, TSLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
*/
            }
        },2000);
    }
}
