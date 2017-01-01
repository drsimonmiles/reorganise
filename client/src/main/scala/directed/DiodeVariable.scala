package directed

import japgolly.scalajs.react.Callback

case class DiodeVariable[Value] (value: Value, update: Value => Callback) {
  def set (newValue: Value): Callback =
    update (newValue)
}
