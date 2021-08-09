package edu.neo.saludable.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.neo.saludable.R
import edu.neo.saludable.api.implementacion.ApiTragoImp
import edu.neo.saludable.model.Trago
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var nombre : TextView
lateinit var foto : ImageView
lateinit var volver : Button
lateinit var reclamar : Button

class TragoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trago)

        inicializar()
        val bundle = intent.extras
        val mail = bundle?.getString("email")
        val api: ApiTragoImp = ApiTragoImp()
        val random = "random.php"


        reclamar.setOnClickListener(
            View.OnClickListener {

                api.mostrarTrago(random).enqueue(object :
                    Callback<Trago> {
                    override fun onFailure(call: Call<Trago>, t: Throwable) {
                        Toast.makeText(applicationContext, "No hay trago", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Trago>, response: Response<Trago>) {

                        if (response.body() != null) {
                            val data = response.body()
                            nombre.text = data?.strDrink

                            Glide.with(it.context)
                                .load(data?.strDrinkThumb)
                                .centerCrop()
                                .into(foto)
                        }
                    }
                }
                )
            }
        )


        volver.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this,HomeActivity::class.java)
                intent.putExtra("email", mail)
                startActivity(intent)
                this@TragoActivity.finish()
            }
        )



    }


    private fun inicializar(){
        volver = findViewById(R.id.b_salir_trago)
        nombre  = findViewById(R.id.t_nombre_trago)
        foto = findViewById(R.id.img_trago)
        reclamar = findViewById(R.id.b_reclamar)
    }

}