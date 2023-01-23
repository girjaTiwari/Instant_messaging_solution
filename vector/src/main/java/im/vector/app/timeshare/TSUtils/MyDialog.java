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

package im.vector.app.timeshare.TSUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import im.vector.app.R;

public class MyDialog {
    private Dialog dialog;
    private static final int PREV_MONTH = 1;
    Context context;

    public MyDialog(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context, 0);
    }

    @SuppressLint("NewApi")
    public void showProgresbar(Context context) {
        try {
            dialog = new Dialog(context, R.style.DialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.progressbar);

            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void hideDialog(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
            dialog.cancel();
        }
    }

}
