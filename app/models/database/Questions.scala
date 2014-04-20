package models.database

/**
 * Created by FScoward on 2014/04/20.
 */
import play.api.db.DB
import scala.slick.driver.H2Driver.simple._
import play.api.Play.current

case class Question(content: String, questioner: Int, questionId: Option[Int] = None)
object Questions {
  val database = Database.forDataSource(DB.getDataSource())
  
  class Questions(tag: Tag) extends Table[Question](tag, "QUESTION") {
    def question = column[String]("QUESTION", O NotNull)
    def questioner = column[Int]("QUESTIONER")
    def questionId = column[Option[Int]]("QUESTION_ID", O AutoInc)
    def * = (question, questioner, questionId) <> (Question.tupled, Question.unapply)
  }
  
  val questions = TableQuery[Questions]
  
}
