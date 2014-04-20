package models.database

import play.api.db.DB
import scala.slick.driver.H2Driver.simple._
import play.api.Play.current

/**
 * Created by FScoward on 2014/04/19.
 */
case class Project(projectName: String, owner: Int, projectId: Option[Int] = None)
object Projects {
  val database = Database.forDataSource(DB.getDataSource())

  class Projects(tag: Tag) extends Table[Project](tag, "PROJECT") {
    def projectName = column[String]("PROJECT_NAME", O PrimaryKey)
    def owner = column[Int]("OWNER", O NotNull)
    def projectId = column[Option[Int]]("PROJECT_ID", O AutoInc)
    def * = (projectName, owner, projectId) <> (Project.tupled, Project.unapply)
  }

//  private val projectsAutoInc = projects returning projects.map(_.projectId) into { case (p, id) => p.copy(projectId = id)}
//  def insert(project: Project)(implicit session: Session): Project = projectsAutoInc.insert(project)
  val projects = TableQuery[Projects]
  
  def insertProject(project: Project) = database.withSession { implicit session: Session =>
    projects.insert(project)
  }
  
  def findProjectById(memberID: Int): List[Project] = database.withSession { implicit  session: Session =>
    projects.where(_.owner === memberID).list
  }
  
  def deleteProject(projectId: Int) = database.withSession { implicit session: Session =>
    projects.where(_.projectId === projectId).delete
  }
}
