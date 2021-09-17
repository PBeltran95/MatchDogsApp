package ar.com.example.matchdogs.ui.spashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.databinding.ActivitySplashBinding
import ar.com.example.matchdogs.ui.MainActivity


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
        binding.imgLogo.alpha = 0f
        binding.imgLogo.animate().setDuration(750).alpha(1f).withEndAction{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}