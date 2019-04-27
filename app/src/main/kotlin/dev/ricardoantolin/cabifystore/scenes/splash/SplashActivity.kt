package dev.ricardoantolin.cabifystore.scenes.splash

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this)
            .load(R.raw.screen_splash)
            .into(DrawableImageViewTarget(splashAnimation))
    }
}
