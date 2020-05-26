package utez

class Habilidad {

    String nombre
    Persona persona

    static belongsTo = [Persona]

    static mapping = {
        version false
    }

    static constraints = {
    }
}
