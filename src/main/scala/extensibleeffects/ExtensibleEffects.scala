package extensibleeffects

/** Reference: https://halcat.org/scala/extensible/index.html
  */
object ExtensibleEffects {

  trait Functor[F[_]] {
    def map[A, B]
  }

}
