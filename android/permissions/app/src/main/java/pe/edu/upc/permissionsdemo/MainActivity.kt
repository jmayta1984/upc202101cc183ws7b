package pe.edu.upc.permissionsdemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Step 1: Declare views
    private lateinit var btCamera: Button
    private lateinit var btStorage: Button

    // Step 4: Declare request codes
    companion object {
        const val CAMERA_PERMISSION_CODE = 100
        const val STORAGE_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step 2: Initialize views
        initViews()

        // Step 3: Setup listeners
        setupListeners()
    }

    private fun setupListeners() {
        btCamera.setOnClickListener {
            // Step 4: Create a function to check permissions
            checkPermissionDemo(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)

        }

        btStorage.setOnClickListener {
            // Step 4: Create a function to check permissions
            checkPermissionDemo(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
        }
    }

    private fun checkPermissionDemo(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Step 5: Request permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // Step 6: Override request permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initViews() {
        btCamera = findViewById(R.id.btCamera)
        btStorage = findViewById(R.id.btStorage)
    }
}