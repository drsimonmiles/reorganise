package reorganise.client.model.generic

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement}

object VariableOps {
  implicit class ProxyOps[Point] (proxy: ModelProxy[Point]) {
    def variable (update: Point => Action) =
      DiodeVariable (proxy, update)

    def variable[Var] (get: Point => Var, update: (Point, Var) => Action)
                      (implicit feq: diode.FastEq[_ >: Var]): DiodeVariable[Var] =
      DiodeVariable[Var] (proxy.zoom (get), varValue => update (proxy.value, varValue))

    def editor (update: Point => Action, html: DiodeVariable[Point] => ReactElement): ReactElement =
      html (variable (update))

    def editor[Var] (get: Point => Var, update: (Point, Var) => Action, html: DiodeVariable[Var] => ReactElement)
                    (implicit feq: diode.FastEq[_ >: Var]): ReactElement =
      html (variable (get, update))
  }
}
