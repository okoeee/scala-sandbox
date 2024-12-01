package generics.authentication.adt

import generics.authentication.UserType

import scala.concurrent.Future

class Authentication {

  def authenticate = ???

  def authorize(userType: UserType): Future[Boolean] =
    userType match {
      case UserType.Admin => Future.successful(true)
      case UserType.User  => Future.successful(false)
    }

}
