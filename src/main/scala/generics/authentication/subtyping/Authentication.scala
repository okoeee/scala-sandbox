package generics.authentication.subtyping

import generics.authentication.UserType

import scala.concurrent.Future

trait Authentication[T <: UserType] {
  def authenticate: Future[T]
  def authorize: Future[Boolean]
}
