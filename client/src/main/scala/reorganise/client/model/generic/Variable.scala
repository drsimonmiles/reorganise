package reorganise.client.model.generic

import diode.Action
import japgolly.scalajs.react.Callback
import reorganise.client.model.ModelPoint

case class Variable[Model <: AnyRef, Value] (model: ModelPoint[Model, Value], update: Value => Action) {
  def set (newValue: Value): Callback =
    model.dispatch (update (newValue))

  def value: Value =
    model.value
}
