package utez



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InformacionContactoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond InformacionContacto.list(params), model:[informacionContactoInstanceCount: InformacionContacto.count()]
    }

    def show(InformacionContacto informacionContactoInstance) {
        respond informacionContactoInstance
    }

    def create() {
        respond new InformacionContacto(params)
    }

    @Transactional
    def save(InformacionContacto informacionContactoInstance) {
        if (informacionContactoInstance == null) {
            notFound()
            return
        }

        if (informacionContactoInstance.hasErrors()) {
            respond informacionContactoInstance.errors, view:'create'
            return
        }

        informacionContactoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'informacionContacto.label', default: 'InformacionContacto'), informacionContactoInstance.id])
                redirect informacionContactoInstance
            }
            '*' { respond informacionContactoInstance, [status: CREATED] }
        }
    }

    def edit(InformacionContacto informacionContactoInstance) {
        respond informacionContactoInstance
    }

    @Transactional
    def update(InformacionContacto informacionContactoInstance) {
        if (informacionContactoInstance == null) {
            notFound()
            return
        }

        if (informacionContactoInstance.hasErrors()) {
            respond informacionContactoInstance.errors, view:'edit'
            return
        }

        informacionContactoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'InformacionContacto.label', default: 'InformacionContacto'), informacionContactoInstance.id])
                redirect informacionContactoInstance
            }
            '*'{ respond informacionContactoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(InformacionContacto informacionContactoInstance) {

        if (informacionContactoInstance == null) {
            notFound()
            return
        }

        informacionContactoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'InformacionContacto.label', default: 'InformacionContacto'), informacionContactoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'informacionContacto.label', default: 'InformacionContacto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
