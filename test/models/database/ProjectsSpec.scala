package models.database

import org.specs2.mutable.Specification
import play.api.test.FakeApplication
import play.api.test.Helpers._

/**
 * Created by FScoward on 2014/04/20.
 */
class ProjectsSpec extends Specification {
  "Project#findProjectById" should {
    "return Project('f', 1)" in {
      running(FakeApplication()){
        Projects.findProjectById(1).head === Project("f", 1)
      }
    }
  }
}
