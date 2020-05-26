package utez



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ParticipacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Participacion.list(params), model:[participacionInstanceCount: Participacion.count()]
    }

    def show(Participacion participacionInstance) {
        respond participacionInstance
    }

    def create() {
        respond new Participacion(params)
    }

    @Transactional
    def save(Participacion participacionInstance) {
        if (participacionInstance == null) {
            notFound()
            return
        }

        if (participacionInstance.hasErrors()) {
            respond participacionInstance.errors, view:'create'
            return
        }

        participacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'participacion.label', default: 'Participacion'), participacionInstance.id])
                redirect participacionInstance
            }
            '*' { respond participacionInstance, [status: CREATED] }
        }
    }

    def edit(Participacion participacionInstance) {
        respond participacionInstance
    }

    @Transactional
    def update(Participacion participacionInstance) {
        if (participacionInstance == null) {
            notFound()
            return
        }

        if (participacionInstance.hasErrors()) {
            respond participacionInstance.errors, view:'edit'
            return
        }

        participacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Participacion.label', default: 'Participacion'), participacionInstance.id])
                redirect participacionInstance
            }
            '*'{ respond participacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Participacion participacionInstance) {

        if (participacionInstance == null) {
            notFound()
            return
        }

        participacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Participacion.label', default: 'Participacion'), participacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'participacion.label', default: 'Participacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
