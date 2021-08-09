package edu.neo.saludable.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.R
import edu.neo.saludable.model.Paciente
import edu.neo.saludable.viewmodel.PacienteViewModel

class RegistroActivity : AppCompatActivity() {

    lateinit var tratamiento : Spinner
    lateinit var tratamientoSeleccionado : String
    lateinit var nombre : EditText
    lateinit var apellido : EditText
    lateinit var dni : EditText
    lateinit var rg_sexo : RadioGroup
    lateinit var nacimiento : EditText
    lateinit var localidad : EditText
    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var confirmar : Button
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        inicializar()
        inicializarSpinner()
        val tratamientos = arrayOf("Anorexia","Bulimia","Obesidad")

        val pacienteVM : PacienteViewModel = ViewModelProvider(this).get(PacienteViewModel::class.java)

        tratamiento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext,"Seleccione un tratamiento", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tratamientoSeleccionado = tratamientos[position]
            }
        }


        confirmar.setOnClickListener(
            View.OnClickListener {

                val selectedsexo : Int = rg_sexo?.checkedRadioButtonId
                val rb_selectedsexo : RadioButton = findViewById(selectedsexo)
                var sexo = rb_selectedsexo.text.toString()

                var paciente = Paciente(nombre.text.toString(), apellido.text.toString(), dni.text.toString(),
                 sexo, nacimiento.text.toString(), localidad.text.toString(), email.text.toString(),
                 pass.text.toString(),tratamientoSeleccionado)

                pacienteVM.registrarPaciente(paciente,this,db)
                this@RegistroActivity.finish()
                }
        )


    }


    private fun inicializarSpinner(){
        val tratamientos = arrayOf("Anorexia","Bulimia","Obesidad")
        tratamiento.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,tratamientos)
    }

    private fun inicializar(){
        nombre = findViewById(R.id.t_nombre_trago)
        apellido = findViewById(R.id.e_apellido)
        dni = findViewById(R.id.e_dni)
        rg_sexo = findViewById(R.id.rg_sexo)
        nacimiento = findViewById(R.id.e_nacimiento)
        localidad = findViewById(R.id.e_localidad)
        email = findViewById(R.id.e_email)
        pass = findViewById(R.id.e_password)
        tratamiento = findViewById(R.id.spinner_tratamiento)
        confirmar = findViewById(R.id.b_confirmar)
    }

}