package controllers

import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import models.database._

/**
 * Created by FScoward on 2014/04/19.
 */
object Project extends Controller {
  
  val projectForm = Form(
    tuple(
      "projectName" -> text,
      "owner" -> number 
    )
  )
  
  def index = Action { implicit request =>
    Ok(views.html.project(Projects.findProjectById(1)))
  }
  
  def makeProject = Action { implicit request =>
    projectForm.bindFromRequest().fold(
      error => {
        Status(INTERNAL_SERVER_ERROR)
      },
      success => {
        val project = models.database.Project(success._1, success._2)
        models.database.Projects.insertProject(project)
        Redirect(routes.Project.index())
      }
    )
  }
  
  def deleteProject = Action { implicit request =>
    // validation
    val deleteProjectForm = Form(
      "projectId" -> number
    )
    deleteProjectForm.bindFromRequest.fold(
      error => {
        BadRequest
      },
      success => {
        Projects.deleteProject(success)
        Redirect(routes.Project.index()).flashing(
          "message" -> "Delete is Successful"
        )
      }
    )
  }
  
}
