package utez



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HabilidadController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Habilidad.list(params), model:[habilidadInstanceCount: Habilidad.count()]
    }

    def show(Habilidad habilidadInstance) {
        respond habilidadInstance
    }

    def create() {
        respond new Habilidad(params)
    }

    @Transactional
    def save(Habilidad habilidadInstance) {
        if (habilidadInstance == null) {
            notFound()
            return
        }

        if (habilidadInstance.hasErrors()) {
            respond habilidadInstance.errors, view:'create'
            return
        }

        habilidadInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'habilidad.label', default: 'Habilidad'), habilidadInstance.id])
                redirect habilidadInstance
            }
            '*' { respond habilidadInstance, [status: CREATED] }
        }
    }

    def edit(Habilidad habilidadInstance) {
        respond habilidadInstance
    }

    @Transactional
    def update(Habilidad habilidadInstance) {
        if (habilidadInstance == null) {
            notFound()
            return
        }

        if (habilidadInstance.hasErrors()) {
            respond habilidadInstance.errors, view:'edit'
            return
        }

        habilidadInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Habilidad.label', default: 'Habilidad'), habilidadInstance.id])
                redirect habilidadInstance
            }
            '*'{ respond habilidadInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Habilidad habilidadInstance) {

        if (habilidadInstance == null) {
            notFound()
            return
        }

        habilidadInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Habilidad.label', default: 'Habilidad'), habilidadInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'habilidad.label', default: 'Habilidad'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
