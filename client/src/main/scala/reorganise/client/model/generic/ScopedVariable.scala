package reorganise.client.model.generic

import diode.Action
import japgolly.scalajs.react.Callback
import reorganise.client.model.ModelPoint

case class ScopedVariable[Model <: AnyRef, Scope, Value] (model: ModelPoint[Model, (Scope, Value)], update: Value => Action) {
  def scope: Scope =
    model.value._1

  def set (newValue: Value): Callback =
    model.dispatch (update (newValue))

  def value: Value =
    model.value._2

  def scopeOnly (implicit feq: diode.FastEq[_ >: Scope]): ModelPoint[Model, Scope] =
    model.zoom (_._1)

  def variableOnly (implicit feq: diode.FastEq[_ >: Value]): Variable[Model, Value] =
    Variable[Model, Value] (model.zoom (_._2), update)
}
