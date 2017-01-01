package directed

import diode.{Action, Circuit}
import japgolly.scalajs.react.Callback

class VariableUpdateModel (circuit: Circuit[_]) {
  private class SoloVariable[Var] (update: Var => Action) extends VariableCreator[Var, Var] {
    def from (value: Var): DiodeVariable[Var] = {
      def setValue (newValue: Var) = Callback (circuit.dispatch (update (newValue)))
      DiodeVariable[Var] (value, setValue)
    }
  }

  private class AttributeVariable[Point, Var] (get: Point => Var, update: (Point, Var) => Action) extends VariableCreator[Point, Var] {
    def from (point: Point): DiodeVariable[Var] = {
      def setValue (newValue: Var) = Callback (circuit.dispatch (update (point, newValue)))
      DiodeVariable[Var] (get (point), setValue)
    }
  }

  def attribute[Point, Var] (get: Point => Var, update: (Point, Var) => Action): VariableCreator[Point, Var] =
    new AttributeVariable[Point, Var] (get, update)

  def solo[Var] (update: Var => Action): VariableCreator[Var, Var] =
    new SoloVariable[Var] (update)

  implicit class ParameterisedActionAsSoloVariable[Var] (update: Var => Action) {
    def from (value: Var): DiodeVariable[Var] = {
      def setValue (newValue: Var) = Callback (circuit.dispatch (update (newValue)))
      DiodeVariable[Var] (value, setValue)
    }
  }
}
