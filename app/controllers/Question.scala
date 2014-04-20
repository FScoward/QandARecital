package controllers

/**
 * Created by FScoward on 2014/04/19.
 */
import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._

object Question extends Controller {
  def index(projectId: Int) = Action {
    Ok(views.html.qa()).withSession(
      "projectId" -> projectId.toString
    )
  }
  
  def makeQuestion = Action { implicit request =>
    val questionForm = Form(
      tuple(
        "from" -> text,
        "to" -> text,
        "question" -> text,
        "limit" -> date("YYYY/MM/DD"),
        "status" -> text
      )
    )
  
    
    questionForm.bindFromRequest.fold(
      error => {
        request.session.get("projectId") match {
          case Some(p) => {
             Redirect(routes.Question.index(p.toInt)).flashing(
              "error" -> "Error"
            )   
          }
          case None => {
            Ok
          }
        }
      },
      success => {
        play.Logger.debug("ID: " + request.path)
        Ok
      }
    )
  }
}
