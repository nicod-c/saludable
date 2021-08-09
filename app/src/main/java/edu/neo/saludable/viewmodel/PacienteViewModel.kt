package edu.neo.saludable.viewmodel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.neo.saludable.R
import edu.neo.saludable.model.Paciente
import edu.neo.saludable.view.HomeActivity

class PacienteViewModel : ViewModel() {

    fun iniciarSesion(email : String, pass : String, context: Context){

        if(email.isNotBlank() && pass.isNotBlank()) {

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {

                    if(it.isSuccessful){
                        showHome(it.result!!.user!!.email!!, context)
                    }
                    else{
                        errorRegistro(context)
                    }
                }
        }

        else{
            errorRegistro(context)
        }

    }


    fun registrarPaciente(paciente : Paciente, context: Context, db: FirebaseFirestore){

        if(paciente.Email.isNotEmpty() && paciente.Password.isNotEmpty()) {

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(paciente.Email, paciente.Password)
                .addOnCompleteListener {

                    if(it.isSuccessful && paciente.Nombre.isNotBlank() && paciente.Apellido.isNotBlank() &&
                        paciente.Dni.isNotBlank() && paciente.fechaNacimiento.isNotBlank() &&
                        paciente.Localidad.isNotBlank() && paciente.Email.isNotBlank()
                        && paciente.Tratamiento.isNotBlank() && paciente.Sexo.isNotBlank()){

                        db.collection("pacientes").document(paciente.Email).set(paciente)

                        showHome(it.result?.user?.email ?: "", context)
                    }
                    else{
                        errorRegistro(context)
                    }
                }
        }
    }

    fun getDatos(viewDatos : View, context: Context, db : FirebaseFirestore, mail: String?){

        val nombre : TextView = viewDatos.findViewById(R.id.t_nombre)
        val apellido : TextView = viewDatos.findViewById(R.id.t_apellido)
        val dni : TextView = viewDatos.findViewById(R.id.t_dni)
        val sexo : TextView = viewDatos.findViewById(R.id.t_sexo)
        val nacimiento : TextView = viewDatos.findViewById(R.id.t_nacimiento)
        val localidad : TextView = viewDatos.findViewById(R.id.t_localidad)
        val email : TextView = viewDatos.findViewById(R.id.t_mail)
        val tratamiento : TextView = viewDatos.findViewById(R.id.t_tratamiento)

        if (mail != null) {
            db.collection("pacientes").document(mail).get().addOnSuccessListener {

                nombre.setText(it.get("nombre") as String?)
                apellido.setText(it.get("apellido") as String?)
                dni.setText(it.get("dni") as String?)
                sexo.setText(it.get("sexo") as String?)
                nacimiento.setText(it.get("fechaNacimiento") as String?)
                localidad.setText(it.get("localidad") as String?)
                email.setText(it.get("email") as String?)
                tratamiento.setText(it.get("tratamiento") as String?)
            }
        }
    }



    fun errorRegistro(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showHome(email : String, context: Context){
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("email", email)
        startActivity(context, intent, null)
    }


}