package edu.neo.saludable.viewmodel

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.api.ApiTrago
import edu.neo.saludable.api.implementacion.ApiTragoImp
import edu.neo.saludable.model.Comida
import edu.neo.saludable.model.Trago
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ComidaViewModel : ViewModel() {


    fun registrarComida(comida : Comida, context : Context, db : FirebaseFirestore, email : String?) : Boolean {

        if(comida.tipo.isNotBlank() && comida.principal.isNotBlank() && comida.secundaria.isNotBlank()
            &&  comida.bebida.isNotBlank() && comida.postre.isNotBlank() && comida.tentacion.isNotBlank()) {

            var calendar = Calendar.getInstance()
            comida.fecha = obtenerHoy(calendar.timeZone.toString())
            comida.hora = obtenerHora(calendar.timeZone.toString())

            db.collection("pacientes/"+email+"/comidas").add(comida)
                .addOnSuccessListener { _ ->
                    Toast.makeText(context,"Comida registrada", Toast.LENGTH_SHORT).show()
                }

                .addOnFailureListener{ _ ->
                    Toast.makeText(context,"No se pudo registrar", Toast.LENGTH_SHORT).show()
                }

            return true
        }
        else return false
    }



    private fun obtenerFechaConFormato (formato : String, zonaHoraria : String) : String {
        var calendar = Calendar.getInstance()
        var date = calendar.time
        var sdf = SimpleDateFormat(formato)
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria))
        return sdf.format(date)
    }

    private fun obtenerHoy (zonaHoraria : String) : String {
        var formato = "dd-MM-yyyy"
        return obtenerFechaConFormato(formato, zonaHoraria);
    }

    private fun obtenerHora (zonaHoraria : String) : String {
        var formato = "HH:mm"
        return obtenerFechaConFormato(formato, zonaHoraria)
    }


}