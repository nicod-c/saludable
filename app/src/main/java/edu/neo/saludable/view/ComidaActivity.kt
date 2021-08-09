package edu.neo.saludable.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.R
import edu.neo.saludable.api.implementacion.ApiTragoImp
import edu.neo.saludable.model.Comida
import edu.neo.saludable.model.Trago
import edu.neo.saludable.viewmodel.ComidaViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.Locale
import android.content.ComponentCallbacks2
import android.content.Intent
import com.bumptech.glide.Glide

class ComidaActivity : AppCompatActivity() {

    lateinit var sp_comidas: Spinner
    lateinit var principal: EditText
    lateinit var secundaria: EditText
    lateinit var bebida: EditText
    lateinit var ingirioPostre: RadioGroup
    lateinit var postreSi: RadioButton
    lateinit var postreNo: RadioButton
    lateinit var postre: EditText
    lateinit var seTento: RadioGroup
    lateinit var tentacionSi: RadioButton
    lateinit var tentacionNo: RadioButton
    lateinit var tentacion: EditText
    lateinit var hambre: RadioGroup
    lateinit var hambreSi: RadioButton
    lateinit var hambreNo : RadioButton
    lateinit var guardar: Button
    lateinit var comidaseleccionada : String


    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comida)

        inicializar()
        inicializarSpinner()

        val bundle = intent.extras
        val mail = bundle?.getString("email")
        val comidaVM = ViewModelProvider(this).get(ComidaViewModel::class.java)

        val comidas = arrayOf("Desayuno","Almuerzo","Merienda","Cena")

        sp_comidas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext,"Seleccione una comida", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                comidaseleccionada = comidas[position]
            }
        }

        guardar.setOnClickListener(
            View.OnClickListener {

                val selectedpostre: Int = ingirioPostre.checkedRadioButtonId
                val rb_selectedpostre: RadioButton = findViewById(selectedpostre)

                if (rb_selectedpostre.text == "NO") {
                    postre.setText("NO").toString()
                }

                val selectedtentacion: Int = seTento.checkedRadioButtonId
                val rb_selectedtentacion: RadioButton = findViewById(selectedtentacion)

                if (rb_selectedtentacion.text == "NO") {
                    tentacion.setText("NO").toString()
                }

                val selectedhambre: Int = hambre.checkedRadioButtonId
                val rb_selectedhambre: RadioButton = findViewById(selectedhambre)
                var quedoHambre = rb_selectedhambre.text

                var fecha = String()
                var hora = String()

                var comida = Comida(
                    comidaseleccionada, bebida.text.toString(), tentacion.text.toString(),
                    postre.text.toString(), principal.text.toString(), secundaria.text.toString(),
                    quedoHambre.toString(), fecha, hora
                )

                if (comidaVM.registrarComida(comida, this, db, mail) == true) {

                    val intent = Intent(this, TragoActivity::class.java)
                    intent.putExtra("email", mail)
                    startActivity(intent)
                }
            }
        )

        postreSi.setOnClickListener(
            View.OnClickListener {
                postre.isVisible = true
            }
        )

        postreNo.setOnClickListener(
            View.OnClickListener {
                postre.isVisible = false
            }
        )

        tentacionSi.setOnClickListener(
            View.OnClickListener {
                tentacion.isVisible = true
            }
        )

        tentacionNo.setOnClickListener(
            View.OnClickListener {
                tentacion.isVisible = false
            }
        )


    }

    private fun inicializarSpinner(){
        val comidas = arrayOf("Desayuno","Almuerzo","Merienda","Cena")
        sp_comidas.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,comidas)
    }

    private fun inicializar(){
        sp_comidas = findViewById(R.id.spinner_comidas)
        principal = findViewById(R.id.e_principal)
        secundaria = findViewById(R.id.e_secundaria)
        bebida = findViewById(R.id.e_bebida)
        ingirioPostre = findViewById(R.id.rg_postre)
        postreSi = findViewById(R.id.rb_postre_si)
        postreNo = findViewById(R.id.rb_postre_no)
        postre = findViewById(R.id.e_postre)
        seTento = findViewById(R.id.rg_setento)
        tentacionSi = findViewById(R.id.rb_tentacion_si)
        tentacionNo = findViewById(R.id.rb_tentacion_no)
        tentacion = findViewById(R.id.e_tentacion)
        hambre = findViewById(R.id.rg_hambre)
        hambreSi = findViewById(R.id.rb_hambreSi)
        hambreNo = findViewById(R.id.rb_hambreNo)
        guardar = findViewById(R.id.b_guardar)

    }


}