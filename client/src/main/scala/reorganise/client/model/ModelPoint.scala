package reorganise.client.model

import diode.{Action, Circuit, FastEq, ModelR, ModelRO}
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, _}
import reorganise.client.model.generic.Variable
import scala.scalajs.js

case class ModelPoint[Model <: AnyRef, Point] (data: ModelR[Model, Point], circuit: Circuit[Model]) {
  /*def reader (component: ReactComponentC.ReqProps[ModelPoint[Model, Point], _, _, _])
             (implicit feq: diode.FastEq[_ >: Point]) = {
    val proxy = connect (feq)
    proxy (component (_))
  }

  def reader (component: ReactComponentC.ReqProps[ModelPoint[Model, Point], _, _, _], children: ReactNode*)
             (implicit feq: diode.FastEq[_ >: Point]) = {
    val proxy = connect (feq)
    proxy (component (_, children))
  }*/

  def dispatch (action: Action): Callback =
    Callback (circuit.dispatch (action))

/*  def scopedVariable[Scope, Var] (scope: ModelPoint[Model, Scope], variable: Variable[Model, Var])
                                 (implicit feqS: diode.FastEq[_ >: Scope], feqV: diode.FastEq[_ >: Var]): ScopedVariable[Model, Scope, Var] =
    ScopedVariable[Model, Scope, Var] (scope.zip (variable.model), variable.update)

  def scopedVariable[Scope] (scope: ModelPoint[Model, Scope], update: Point => Action)
                            (implicit feqS: diode.FastEq[_ >: Scope], feqP: diode.FastEq[_ >: Point]): ScopedVariable[Model, Scope, Point] =
    scopedVariable[Scope, Point] (scope, variable (update))

  def scopedVariable[Scope, Owner, Var] (getScope: Point => Scope, getOwner: Point => Owner, getVar: Owner => Var,  update: (Owner, Var) => Action)
                                        (implicit feqS: diode.FastEq[_ >: Scope], feqO: diode.FastEq[_ >: Owner], feqV: diode.FastEq[_ >: Var]): ScopedVariable[Model, Scope, Var] =
    scopedVariable (zoom (getScope), zoom (getOwner).variable (getVar, update))
*/
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

  def zoom[Sub1, Sub2] (get1: Point => Sub1, get2: Point => Sub2)
                       (implicit feq1: diode.FastEq[_ >: Sub1], feq2: diode.FastEq[_ >: Sub2]): ModelPoint[Model, (Sub1, Sub2)] =
    ModelPoint (data.zoom (get1).zip (data.zoom (get2)), circuit)

  def zoom[Sub1, Sub2, Sub3] (get1: Point => Sub1, get2: Point => Sub2, get3: Point => Sub3)
                             (implicit feq1: diode.FastEq[_ >: Sub1], feq2: diode.FastEq[_ >: Sub2], feq3: diode.FastEq[_ >: Sub3]): ModelPoint[Model, ((Sub1, Sub2), Sub3)] =
    ModelPoint (data.zoom (get1).zip (data.zoom (get2)).zip (data.zoom (get3)), circuit)

  def zoom[Sub1, Sub2, Sub3, Sub4] (get1: Point => Sub1, get2: Point => Sub2, get3: Point => Sub3, get4: Point => Sub4)
                                   (implicit feq1: diode.FastEq[_ >: Sub1], feq2: diode.FastEq[_ >: Sub2], feq3: diode.FastEq[_ >: Sub3], feq4: diode.FastEq[_ >: Sub4]): ModelPoint[Model, (((Sub1, Sub2), Sub3), Sub4)] =
    ModelPoint (data.zoom (get1).zip (data.zoom (get2)).zip (data.zoom (get3)).zip (data.zoom (get4)), circuit)

  def zoom[Sub1, Sub2, Sub3, Sub4, Sub5] (get1: Point => Sub1, get2: Point => Sub2, get3: Point => Sub3, get4: Point => Sub4, get5: Point => Sub5)
                                         (implicit feq1: diode.FastEq[_ >: Sub1], feq2: diode.FastEq[_ >: Sub2], feq3: diode.FastEq[_ >: Sub3], feq4: diode.FastEq[_ >: Sub4], feq5: diode.FastEq[_ >: Sub5]): ModelPoint[Model, ((((Sub1, Sub2), Sub3), Sub4), Sub5)] =
    ModelPoint (data.zoom (get1).zip (data.zoom (get2)).zip (data.zoom (get3)).zip (data.zoom (get4)).zip (data.zoom (get5)), circuit)

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
