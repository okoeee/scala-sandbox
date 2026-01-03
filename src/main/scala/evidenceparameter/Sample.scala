package evidenceparameter

object Sample {

  sealed trait 時間
  sealed trait 分数

  case class 時間数[単位](value: Long) {

    def _時間数に切り上げ(implicit ev: 単位 =:= 分数): 時間数[時間] = {
      val _分 = ev.substituteCo(this)

      val _時間数 = _分.value / 60

      val _分数 = _分.value % 60

      val _切り上げた時間数 = if (_分数 > 0) {
        _時間数 + 1
      } else {
        _時間数
      }

      時間数[時間](_切り上げた時間数)
    }

  }

}
