package generics.authentication

sealed trait UserType {}

object UserType {
  case object Admin extends UserType
  case object User extends UserType
}
