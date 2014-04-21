package controllers

/**
 * Created by FScoward on 2014/04/19.
 */
import play.api.mvc.{Controller, Action}
import play.api.data.Form
import play.api.data.Forms._
import models.database.Questions
import models.database

object Question extends Controller {
  def index(projectId: Int) = Action { implicit request =>
    val questionList = Questions.findQuestionById(projectId)
    Ok(views.html.qa(questionList)).withSession(
      "projectId" -> projectId.toString
    )
  }
  
  def makeQuestion = Action { implicit request =>
    val questionForm = Form(
      tuple(
        "from" -> number,
        "to" -> number,
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
             "error" -> "Error occured"
            )   
          }
          case None => {
            Redirect(routes.Application.index())
          }
        }
      },
      success => {
        val (from, to, question, limit, status) = success
        // TODO Refactor
        val projectId = Option(request.session.get("projectId").get.toInt)
        
        Questions.insertQuestion(database.Question(question, from, projectId))
        Ok
      }
    )
  }
}
