package edu.neo.saludable.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import edu.neo.saludable.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imagen : ImageView = findViewById(R.id.img_inicio)
        imagen.setImageResource(R.mipmap.saludable)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            this.finish()
        }, 2000)

    }
}