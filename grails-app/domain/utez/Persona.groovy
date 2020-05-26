package utez

class Persona {

    String nombre
    String apellidoPaterno
    String apellidoMaterno
    int edad

    static hasOne = [informacion: InformacionContacto]

    static hasMany = [habilidades: Habilidad, participaciones: Participacion]

    static mapping = {
        version false
    }

    static constraints = {
    }
}
