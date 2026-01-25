package category

import cats.data._
import cats.implicits._

object StateSample {

  type 状態 = Int

  def _状態更新(v: Int): Int = v + 1

  def f(): State[状態, Int] = {
    for {
      _状態 <- State.get[Int]

      _更新後状態 = _状態更新(_状態)

      _ <- State.set(_更新後状態)
    } yield _状態
  }

}

object StateTSample {

  def _状態更新(v: Int): Either[Failure, Int] =
    if (v == 0) Failure.ValueIsZERO.asLeft
    else (v + 1).asRight[Failure]

  sealed trait Failure
  object Failure {
    case object ValueIsZERO extends Failure
  }

  type FailureOr[A] = Either[Failure, A]

  def f(): StateT[FailureOr, Int, Int] = {
    for {
      _状態 <- StateT.get[FailureOr, Int]

      _更新後状態 <- StateT.liftF(_状態更新(_状態))

      _ <- StateT.set[FailureOr, Int](_更新後状態)
    } yield _状態
  }

}

object StateTWithGenericsSample {

  sealed trait Failure[G]
  object Failure {
    case class ValueIsZERO[G]() extends Failure[G]
  }

  def _状態更新[G](v: Int): Either[Failure[G], Int] =
    if (v == 0) Failure.ValueIsZERO[G]().asLeft
    else (v + 1).asRight

  /*
  型ラムダ: 型を引数にとって、新しい型を返す型レベルの関数
  ラムダ: 名前を持たない関数

  ↓の名前なしver
  ```
  trait Box {
    type L[A]
  }
  ```

  定義したいのは↓と同じ
  StateT[({ type L[A] = Either[Failure[G], A] })#L, Int, Int]
   */
  type FailureOr[G] = {
    type L[A] = Either[Failure[G], A]
  }

  def f[G](): StateT[FailureOr[G]#L, Int, Int] =
    for {
      s <- StateT.get[FailureOr[G]#L, Int]

      newS <- StateT.liftF[FailureOr[G]#L, Int, Int](
                _状態更新[G](s)
              )

      _ <- StateT.modify[FailureOr[G]#L, Int](_ => newS)
    } yield s

}

// sample
//StateTWithGenericsSample
//  .f[Unit]()
//  .run(1)
//  .getOrElse(throw new IllegalArgumentException("失敗"))
