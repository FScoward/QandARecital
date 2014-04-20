package models.auth

/**
 * Created by FScoward on 2014/04/18.
 */

trait Member{
  val name: String
}
case class Administrator(name: String) extends Member

class Authentication {

}
