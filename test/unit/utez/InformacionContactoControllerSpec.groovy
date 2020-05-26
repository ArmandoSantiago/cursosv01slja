package utez



import grails.test.mixin.*
import spock.lang.*

@TestFor(InformacionContactoController)
@Mock(InformacionContacto)
class InformacionContactoControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.informacionContactoInstanceList
            model.informacionContactoInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.informacionContactoInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def informacionContacto = new InformacionContacto()
            informacionContacto.validate()
            controller.save(informacionContacto)

        then:"The create view is rendered again with the correct model"
            model.informacionContactoInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            informacionContacto = new InformacionContacto(params)

            controller.save(informacionContacto)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/informacionContacto/show/1'
            controller.flash.message != null
            InformacionContacto.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def informacionContacto = new InformacionContacto(params)
            controller.show(informacionContacto)

        then:"A model is populated containing the domain instance"
            model.informacionContactoInstance == informacionContacto
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def informacionContacto = new InformacionContacto(params)
            controller.edit(informacionContacto)

        then:"A model is populated containing the domain instance"
            model.informacionContactoInstance == informacionContacto
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/informacionContacto/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def informacionContacto = new InformacionContacto()
            informacionContacto.validate()
            controller.update(informacionContacto)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.informacionContactoInstance == informacionContacto

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            informacionContacto = new InformacionContacto(params).save(flush: true)
            controller.update(informacionContacto)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/informacionContacto/show/$informacionContacto.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/informacionContacto/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def informacionContacto = new InformacionContacto(params).save(flush: true)

        then:"It exists"
            InformacionContacto.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(informacionContacto)

        then:"The instance is deleted"
            InformacionContacto.count() == 0
            response.redirectedUrl == '/informacionContacto/index'
            flash.message != null
    }
}
