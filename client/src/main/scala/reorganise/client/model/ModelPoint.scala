package reorganise.client.model

import diode.{Action, Circuit, FastEq, ModelR, ModelRO}
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, _}
import reorganise.client.model.generic.Variable
import scala.scalajs.js

case class ModelPoint[Model <: AnyRef, Point] (data: ModelR[Model, Point], circuit: Circuit[Model]) {
  def dispatch (action: Action): Callback =
    Callback (circuit.dispatch (action))

  def value: Point =
    data.value

  def variable (update: Point => Action) =
    Variable (this, update)

  def variable[Var] (get: Point => Var, update: (Point, Var) => Action)
                    (implicit feq: diode.FastEq[_ >: Var]): Variable[Model, Var] =
    Variable[Model, Var] (zoom (get), varValue => update (data.value, varValue))

  def zip[Other] (otherPoint: ModelPoint[Model, Other])
                 (implicit feqP: diode.FastEq[_ >: Point], feqO: diode.FastEq[_ >: Other]): ModelPoint[Model, (Point, Other)] =
    ModelPoint (data.zip (otherPoint.data), circuit)

  def zoom[Sub] (get: Point => Sub)
                (implicit feq: diode.FastEq[_ >: Sub]): ModelPoint[Model, Sub] =
    new ModelPoint[Model, Sub] (data.zoom (get), circuit)

  def connect (implicit feq: FastEq[_ >: Point]): ReactComponentC.ReqProps[(ModelPoint[Model, Point]) => ReactElement, Point, _, TopNode] = {
    class Backend (t: BackendScope[ModelPoint[Model, Point] => ReactElement, Point]) {
      private var unsubscribe = Option.empty[() => Unit]

      def willUnmount = Callback {
        if (data.value.getClass.getSimpleName.equals ("ClientState")) println ("willUnmount")
        unsubscribe.foreach (f => f ())
        unsubscribe = None
      }

      def willMount = {
        if (data.value.getClass.getSimpleName.equals ("ClientState")) println ("willMount")
        Callback {
          unsubscribe = Some (circuit.subscribe (data)(changeHandler))
        } >> t.setState (data.value)
      }

      private def changeHandler (cursor: ModelRO[Point]): Unit = {
        if (data.value.getClass.getSimpleName.equals ("ClientState")) {
          println ("Creating change handler")
          println ("Is mounted? " + t.isMounted ())
          println ("Has state changed? " + (data =!= t.accessDirect.state))
        }
        // modify state if we are mounted and state has actually changed
        if (t.isMounted () && data =!= t.accessDirect.state)
          t.accessDirect.setState (data.value)
      }

      def render (s: Point, compB: ModelPoint[Model, Point] => ReactElement): ReactElement =
        compB (ModelPoint.this)
    }

    ReactComponentB[ModelPoint[Model, Point] => ReactElement] ("ModelPointWrapper")
      .initialState (data.value)
      .renderBackend[Backend]
      .componentWillMount {scope => if (data.value.getClass.getSimpleName.equals ("ClientState")) println ("Mounting"); scope.backend.willMount}
      .componentWillUnmount {scope => if (data.value.getClass.getSimpleName.equals ("ClientState")) println ("Unmounting"); scope.backend.willUnmount}
      .shouldComponentUpdate {scope => println (scope.nextProps + "-" + feq.neqv (scope.currentState, scope.nextState)); feq.neqv (scope.currentState, scope.nextState)}
      .build
      .set (js.undefined)
  }
}
