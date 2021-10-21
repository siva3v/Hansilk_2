package com.hansilk.two

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hansilk.two.blocks.uploads.main.UploadsActivity
import com.hansilk.two.blocks.uploads.service.UploadService
import com.hansilk.two.databinding.ActivityMainBinding
import com.hansilk.two.support.utils.fileUtils.FilePath
import com.hansilk.two.support.utils.fileUtils.FileSize
import com.hansilk.two.support.utils.imageUtils.ImageCompress
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                if (data != null) {
                    if (data.clipData != null) {
                        val mClipData = data.clipData
                        for (i in 0 until mClipData!!.itemCount) {
                            val uri = mClipData.getItemAt(i).uri
                            val path = this.let { it1 -> FilePath.getPath(it1, uri) }
                            val file = File(path)
                            imcomp(file)
                        }
                    } else if (data.data != null) {
                        val uri = data.data!!
                        val path = this.let { it1 -> FilePath.getPath(it1, uri) }
                        val file = File(path)
                        imcomp(file)
                    }
                }

            }
        }

    fun imcomp(originalFile: File){

        val iv = ImageView(this)
        iv.setPadding(60,60,60,60)

        binding.mainLlv.addView(iv)

        lifecycleScope.launch {
            coroutineScope {

                val compressedFile = ImageCompress.compressImageSmall(applicationContext, originalFile)

                val originalFileSize = FileSize.fileSizeInKB(originalFile)
                val compressedFileSize = FileSize.fileSizeInKB(compressedFile)

                println("Size :: $originalFileSize :: $compressedFileSize")

                val bitmap = BitmapFactory.decodeFile(compressedFile.absolutePath)
                iv.setImageBitmap(bitmap)
            }
        }
    }


}