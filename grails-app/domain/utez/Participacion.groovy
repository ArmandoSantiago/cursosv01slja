package utez

class Participacion {

    Date fechaRegistro

    static belongsTo = [persona:Persona, curso:Curso]

    static mapping = {
        version false
    }

    static constraints = {
    }
}
