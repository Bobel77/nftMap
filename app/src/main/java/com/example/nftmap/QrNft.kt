package com.example.nftmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.nftmap.databinding.ActivityQrNftBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class QrNft() : AppCompatActivity() {
    private lateinit var binding: ActivityQrNftBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrNftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageNft: String? = intent.getStringExtra("imageNft")
        imageNft?.let {
            GlobalScope.launch(Dispatchers.IO) {
                // Perform network operation in a background thread
                val url = URL(it)
                val inputStream = url.openConnection().getInputStream()
                val icon = BitmapFactory.decodeStream(inputStream)

                // Update UI on the main thread
                launch(Dispatchers.Main) {
                    addImage(icon)
                }
            }
        }

    }

    private fun addImage(icon: Bitmap) {

        val imageView = ImageView(this)
        imageView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        ).apply {
            leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

        }
        imageView.setImageBitmap(icon)
        imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_200))
        binding.root.addView(imageView)
    }
}