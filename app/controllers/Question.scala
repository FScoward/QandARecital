package controllers

/**
 * Created by FScoward on 2014/04/19.
 */
import play.api.mvc.{Controller, Action}
object Question extends Controller {
  def index(projectId: Int) = Action {
    Ok(views.html.qa())
  }
}
