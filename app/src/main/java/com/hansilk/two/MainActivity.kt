package com.hansilk.two

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.hansilk.two.blocks.uploads.main.UploadsActivity
import com.hansilk.two.blocks.uploads.service.UploadService
import com.hansilk.two.support.utils.fileUtils.FilePath
import com.hansilk.two.support.utils.fileUtils.FileSize
import com.hansilk.two.support.utils.imageUtils.ImageEdit
import com.hansilk.two.support.utils.listUtils.ListUtils
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, UploadsActivity::class.java))
        UploadService.startService(applicationContext,"")

        //selectImages()
    }

    private fun selectImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true)
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data

                if (data?.data != null) {
                    val uri = data.data!!
                    val path = FilePath.getPath(this, uri)
                    val file = File(path)
                    imcomp(file)
                }
            }
        }

    fun imcomp(originalFile: File){
        lifecycleScope.launch {
            coroutineScope {
                val compressedFile = Compressor.compress(applicationContext, originalFile) {
                    resolution(1280, 720)
                    quality(80)
                    size(1)
                }
                //uploadFile(upload, originalFile)
                println("Size :: originalFile ${FileSize.fileSizeInKB(originalFile)}")
                println("Size :: compressedFile ${FileSize.fileSizeInKB(compressedFile)}")
            }
        }
    }

}