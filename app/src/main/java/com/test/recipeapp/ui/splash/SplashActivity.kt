package com.test.recipeapp.ui.splash

import android.content.Intent
import android.os.Bundle
import com.test.recipeapp.MainResources
import com.test.recipeapp.R
import com.test.recipeapp.ui.BaseActivity
import com.test.recipeapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
EasyPermissions.PermissionCallbacks{

    private val controller = SplashController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (hasReadStoragePermission()) {
            controller.readStorage()
        } else {
            EasyPermissions.requestPermissions(
                this@SplashActivity,
                "This app needs access to your storage,",
                MainResources.READ_STORAGE_PERMISSION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        btnGetStarted.setOnClickListener {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this@SplashActivity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        controller.readStorage()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this@SplashActivity, perms)) {
            AppSettingsDialog.Builder(this@SplashActivity).build().show()
        }
    }
}