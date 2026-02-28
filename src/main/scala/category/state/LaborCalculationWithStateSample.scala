package category.state

import cats.data._
import cats.implicits._

import java.time.Duration

object LaborCalculationWithStateSample {

  case class 法定内実労働時間数(value: Duration)

  case class 日の入力(
    _週のIndex: Long
  )

  case class 状態(
    // 初期値None, それ以降はSome
    _前日の週Index:             Option[Long],
    _集計対象日が属する週内の法定内実労働時間数: Duration,
    _直近3週の法定内実労働時間数:        List[Duration]
  )

  def _状態更新(
    _状態: 状態,
    _日の入力: 日の入力,
    _法定内実労働時間数: 法定内実労働時間数
  ): 状態 = {

    _状態._前日の週Index match {
      case None =>
        状態(
          _前日の週Index = _日の入力._週のIndex.some,
          _集計対象日が属する週内の法定内実労働時間数 = _法定内実労働時間数.value plus _状態._集計対象日が属する週内の法定内実労働時間数,
          _直近3週の法定内実労働時間数 = _状態._直近3週の法定内実労働時間数
        )

      case Some(_前日の週Index) =>
        if (_前日の週Index == _日の入力._週のIndex) {
          状態(
            _前日の週Index = _日の入力._週のIndex.some,
            _集計対象日が属する週内の法定内実労働時間数 = _法定内実労働時間数.value plus _状態._集計対象日が属する週内の法定内実労働時間数,
            _直近3週の法定内実労働時間数 = _状態._直近3週の法定内実労働時間数
          )
        } else {
          状態(
            _前日の週Index = _日の入力._週のIndex.some,
            _集計対象日が属する週内の法定内実労働時間数 = _法定内実労働時間数.value,
            _直近3週の法定内実労働時間数 = _状態._直近3週の法定内実労働時間数.drop(0) ++ List(_状態._集計対象日が属する週内の法定内実労働時間数)
          )
        }
    }

    状態(
      _前日の週Index = _日の入力._週のIndex.some,
      _集計対象日が属する週内の法定内実労働時間数 = _法定内実労働時間数.value plus _状態._集計対象日が属する週内の法定内実労働時間数,
      _直近3週の法定内実労働時間数 = ???
    )
  }

  def _1日の集計(
    _日の入力: 日の入力
  ): 法定内実労働時間数 = {
    法定内実労働時間数(
      Duration.ofHours(1)
    )
  }

  type 集計結果 = Unit

  def _1日の労務集計(
    _日の入力: 日の入力
  ): State[状態, 集計結果] = {

    for {
      _状態 <- State.get[状態]

      _1日の集計結果 = _1日の集計(_日の入力)

      _更新後状態 = _状態更新(
                 _状態 = _状態,
                 _日の入力 = _日の入力,
                 _法定内実労働時間数 = _1日の集計結果
               )

      _ <- State.set(_更新後状態)
    } yield ()

  }

}
