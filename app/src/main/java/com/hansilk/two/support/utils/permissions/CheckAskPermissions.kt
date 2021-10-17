package com.hansilk.two.support.utils.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CheckAskPermissions {
    companion object{

        fun checkAskRequestStoragePermissions(activity: Activity, context: Context){
            checkAskRequestPermissions(activity, context, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionCodes.EXTERNAL_STORAGE_PERMISSION_CODE)
        }

        fun checkAskRequestCameraPermissions(activity: Activity, context: Context){
            checkAskRequestPermissions(activity, context, Manifest.permission.CAMERA, PermissionCodes.CAMERA)
        }

        private fun checkAskRequestPermissions(activity: Activity, context: Context, permissionString: String, permissionCode: Int){
            if (ContextCompat.checkSelfPermission(context, permissionString)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    permissionCode
                )
            }
        }

    }
}