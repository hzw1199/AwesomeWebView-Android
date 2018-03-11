package com.thefinestartist.finestwebview.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzongheng on 2016/11/5.
 */

public class PermissionHelper {
    private static final int REQUEST_CODE_PERMISSION = 300;
    public static final int REQUEST_CODE_PERMISSION_SETTINGS = 400;

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
        } else {
            PermissionListener listener = new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
                    // 权限申请成功回调。

                    // 这里的requestCode就是申请时设置的requestCode。
                    // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
                    if (requestCode == REQUEST_CODE_PERMISSION) {
                        if (checkPermissionListener != null) {
                            checkPermissionListener.onAllGranted(false);
                        }
                    }
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    // 权限申请失败回调。
                    if (requestCode == REQUEST_CODE_PERMISSION) {
                        if (checkPermissionListener != null) {
                            checkPermissionListener.onPartlyGranted(deniedPermissions, false);
                        }
                    }
                }
            };

            AndPermission.with(context)
                    .requestCode(REQUEST_CODE_PERMISSION)
                    .permission(permissionName)
                    .callback(listener)
                    .start();
        }
    }

}
