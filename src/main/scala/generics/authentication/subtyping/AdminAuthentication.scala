package generics.authentication.subtyping
import generics.authentication.UserType

import scala.concurrent.Future

class AdminAuthentication extends Authentication[UserType.Admin.type] {

  override def authenticate: Future[UserType.Admin.type] = ???

  override def authorize: Future[Boolean] = ???

}
