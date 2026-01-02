package designpattern

object StrategyPattern {

  // 状態を持つときに意味を持つ、ほとんど不要
  case class Context(value: SomeStrategy)

  sealed trait SomeStrategy {
    def doSomething(): Unit
  }
  object SomeStrategy {
    case object Some1 extends SomeStrategy {
      def doSomething(): Unit = println("some1")
    }
    case object Some2 extends SomeStrategy {
      def doSomething(): Unit = println("some1")
    }
  }

  def f(context: Context): Unit = {
    context.value.doSomething()
  }

}
