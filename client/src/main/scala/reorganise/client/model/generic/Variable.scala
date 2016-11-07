package reorganise.client.model.generic

import diode.Action
import japgolly.scalajs.react.{Callback, ReactNode, ReactElement, ReactComponentC}
import reorganise.client.model.ModelPoint

case class Variable[Model <: AnyRef, Value] (model: ModelPoint[Model, Value], update: Value => Action) {
  def set (newValue: Value): Callback =
    model.dispatch (update (newValue))

  def value: Value =
    model.value

//  def createEditor (component: ReactComponentC.ReqProps[Variable[Model, Value], _, _, _]): ReactElement =
//    model.connect ().apply (newModel => component (withModel (newModel)))

  def createEditor (component: ReactComponentC.ReqProps[Variable[Model, Value], _, _, _], children: ReactNode*)
                   (implicit feq: diode.FastEq[_ >: Value]): ReactElement =
    model.connect (feq).apply (newModel => component (withModel (newModel), children))

  def withModel (newModel: ModelPoint[Model, Value]): Variable[Model, Value] =
    Variable (newModel, update)
}
