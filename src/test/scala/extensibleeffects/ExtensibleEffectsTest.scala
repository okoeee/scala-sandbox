package extensibleeffects

import extensibleeffects.ExtensibleEffects.Functor
import org.scalatest.freespec.AnyFreeSpec
import org.scalatestplus.mockito.MockitoSugar.mock

class ExtensibleEffectsTest extends AnyFreeSpec {

  "hoge" in {
    mock[Functor[_]]
  }

}
