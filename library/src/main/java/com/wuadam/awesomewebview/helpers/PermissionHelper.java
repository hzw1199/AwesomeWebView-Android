package com.wuadam.awesomewebview.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzongheng on 2016/11/5.
 */

public class PermissionHelper {

    public static boolean hasPermissions(Context context, String... permissionName) {
        for (String permission : permissionName) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getGrantedPermissions(Context context, String... permissionName) {
        List<String> permissionsGranted = new ArrayList<>();
        for (String permission : permissionName) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                permissionsGranted.add(permission);
            }
        }
        return permissionsGranted;
    }

    public interface CheckPermissionListener {
        void onAllGranted(boolean sync);

        /**
         * Partly granted(deniedPermissions.size() >= 0) or all denied
         */
        void onPartlyGranted(List<String> permissionsDenied, boolean sync);
    }

    public static void CheckPermissions(final Context context, final CheckPermissionListener checkPermissionListener, String... permissionName) {

        if (hasPermissions(context, permissionName)) {
            if (checkPermissionListener != null) {
                checkPermissionListener.onAllGranted(true);
            }
        }else {
            AndPermission.with(context)
                    .runtime()
                    .permission(permissionName)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            // 权限申请成功回调。
                            if (checkPermissionListener != null) {
                                checkPermissionListener.onAllGranted(false);
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            if (checkPermissionListener != null) {
                                checkPermissionListener.onPartlyGranted(permissions, false);
                            }
                        }
                    })
                    .start();
        }
    }

}
