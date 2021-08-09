package edu.neo.saludable.model

import java.io.Serializable

data class Comida (val tipo : String, val bebida : String, val tentacion : String,
                   val postre : String, val principal : String, val secundaria : String,
                   val hambre : String, var fecha : String, var hora : String) : Serializable