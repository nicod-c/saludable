package edu.neo.saludable.model

import java.io.Serializable

data class Paciente (val Nombre : String, val Apellido : String, val Dni : String, val Sexo : String, val fechaNacimiento : String,
                     val Localidad : String, val Email : String, val Password : String, val Tratamiento : String) : Serializable