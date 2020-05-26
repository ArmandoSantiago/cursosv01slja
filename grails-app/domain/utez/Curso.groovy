package utez

class Curso {

    String nombre
    Date fechaInicio
    Date fechaFin

    static hasMany = [participaciones: Participacion]

    static mapping = {
        version false
    }

    static constraints = {
    }
}
