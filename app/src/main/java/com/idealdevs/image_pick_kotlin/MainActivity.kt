package com.idealdevs.image_pick_kotlin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        image_pick_button.setOnClickListener {
// button to pick image
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // OS is > = than Mashmallow ..

                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
                {

                    // permission is denied ...
                    val permissions=arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, requestPermission);

                }
                else
                {
                    // permission is granted.
                    PickImageFromGallerty();
                }
            }
            else
            {
                // OS is < Mashmallow

                PickImageFromGallerty();
            }
        }

    }

    private fun PickImageFromGallerty() {


        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        try {
            startActivityForResult(intent, imagepick)
        }catch (e: Exception)
        {

        }
    }

    companion object
    {
        val imagepick =21;
        val requestPermission=22;
        
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode)
        {
            requestPermission->
            {
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
PickImageFromGallerty();
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }

            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK && requestCode == imagepick)
        {
            image_view.setImageURI(data?.data)
        }
    }

}
