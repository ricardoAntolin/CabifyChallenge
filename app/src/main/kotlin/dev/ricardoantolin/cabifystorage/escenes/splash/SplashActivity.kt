package dev.ricardoantolin.cabifystorage.escenes.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import dev.ricardoantolin.cabifystorage.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this)
            .load(R.raw.screen_splash)
            .into(DrawableImageViewTarget(splashAnimation))
    }
}
