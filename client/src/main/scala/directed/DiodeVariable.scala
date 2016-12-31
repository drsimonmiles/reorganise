package directed

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react.Callback

case class DiodeVariable[Value] (model: ModelProxy[Value], update: Value => Action) {
  def set (newValue: Value): Callback =
    model.dispatchCB (update (newValue))

  def value: Value =
    model.value
}
