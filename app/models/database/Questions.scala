package models.database

/**
 * Created by FScoward on 2014/04/20.
 */
import play.api.db.DB
import scala.slick.driver.H2Driver.simple._
import play.api.Play.current

case class Question(content: String, questioner: Int, projectId: Option[Int], questionId: Option[Int] = None)
object Questions {
  val database = Database.forDataSource(DB.getDataSource())
  
  class Questions(tag: Tag) extends Table[Question](tag, "QUESTION") {
    def question = column[String]("QUESTION", O NotNull)
    def questioner = column[Int]("QUESTIONER")
    def projectId = column[Option[Int]]("PROJECT_ID")
    def questionId = column[Option[Int]]("QUESTION_ID", O AutoInc)
    def * = (question, questioner, projectId, questionId) <> (Question.tupled, Question.unapply)
    def projectFK = foreignKey("project_fk", projectId, Projects.projects)(_.projectId)
  }
  
  val questions = TableQuery[Questions]
  
  def insertQuestion(question: Question) = database.withSession {
    implicit session: Session =>
      questions.insert(question)
  }
  
  def findQuestionById(projectId: Int) = database.withSession{ implicit session: Session =>
    questions.where(_.projectId === projectId).list
  }
  
}
