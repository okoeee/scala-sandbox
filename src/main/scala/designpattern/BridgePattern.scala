package designpattern

object BridgePattern {

  /*
  抽象(What)と実装(How)を切り離す
   */

  case class Value(value: Int)

  /** 抽象
    */
  abstract class Aggregation[A](
    ruleSet: RuleSet
  ) {
    def aggregate(a: A): Value
  }
  case class MonthlyAggregation(ruleSet: RuleSet) extends Aggregation[Int](ruleSet) {
    def aggregate(hours: Int): Value = Value(ruleSet.calc(hours))
  }
  case class YearlyAggregation(ruleSet: RuleSet) extends Aggregation[Int](ruleSet) {
    def aggregate(hours: Int): Value = ???
  }

  /** 実装
    */
  trait RuleSet {
    def calc(hours: Int): Int
  }
  class OldRule extends RuleSet {
    def calc(hours: Int): Int = hours
  }
  class NewRule extends RuleSet {
    def calc(hours: Int): Int = hours
  }

  def f(): Unit = {
    MonthlyAggregation(new OldRule).aggregate(1)
  }

}
