package edu.neo.saludable.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.R
import edu.neo.saludable.viewmodel.PacienteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var registro : TextView
    lateinit var login: Button
    lateinit var go : Button
    lateinit var email : EditText
    lateinit var pass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pacienteVM : PacienteViewModel = ViewModelProvider(this).get(PacienteViewModel::class.java)

        inicializar()


        go.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this,ComidaActivity::class.java)
                startActivity(intent)
            }
        )

        registro.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, RegistroActivity::class.java)
                startActivity(intent)
                this@MainActivity.finish()
            }
        )


        login.setOnClickListener(
            View.OnClickListener {
                pacienteVM.iniciarSesion(email.text.toString(), pass.text.toString(), this)
            this@MainActivity.finish()
            }
        )


    }

        private fun inicializar(){

        registro = findViewById(R.id.registrarse)
        login = findViewById(R.id.b_login)
        go = findViewById(R.id.prueba)
        email = findViewById(R.id.e_email_login)
            pass = findViewById(R.id.e_pass_login)
        }



}


