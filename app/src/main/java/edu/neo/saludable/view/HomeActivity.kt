package edu.neo.saludable.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.R
import edu.neo.saludable.viewmodel.PacienteViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var datos : Button
    lateinit var cargarcomida : Button
    private lateinit var frameLayout: FrameLayout
   private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        inicializar()

        val bundle = intent.extras
        val mail = bundle?.getString("email")
        val pacienteVM = ViewModelProvider(this).get(PacienteViewModel::class.java)

        datos.setOnClickListener(
            View.OnClickListener {

                val viewDatos : View = LayoutInflater.from(this).inflate(R.layout.paciente_layout, null, false)
                val volver : Button = viewDatos.findViewById(R.id.b_ocultar)

                pacienteVM.getDatos(viewDatos,this,db,mail)

                frameLayout.isVisible = true
                frameLayout.addView(viewDatos)

                volver.setOnClickListener(
                    View.OnClickListener {

                        frameLayout.isVisible = false

                    }
                )
            }
        )


        cargarcomida.setOnClickListener(
            View.OnClickListener {

                val intent = Intent(this, ComidaActivity::class.java)
                intent.putExtra("email",mail)
                startActivity(intent)
            }
        )





    }


    private fun inicializar(){
        datos = findViewById(R.id.b_getdatos)
        cargarcomida = findViewById(R.id.b_cargarcomida)
        frameLayout = findViewById(R.id.home_frame)

    }
}