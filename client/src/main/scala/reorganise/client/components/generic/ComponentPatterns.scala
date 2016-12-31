package reorganise.client.components.generic

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactNode}
import reorganise.client.model.generic.DiodeVariable

object ComponentPatterns {
  case class StatefulVariable[Var] (state: Var, set: Var => Callback, commit: Callback)

  case class Structured[Props] (props: Props, children: Seq[ReactNode])

  def editor[Var] (name: String)(html: DiodeVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement =
    ReactComponentB[DiodeVariable[Var]] (name).render_P (html).build (_)

  def atomicEditor[Var] (name: String)(html: StatefulVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement = {
    class Backend (scope: BackendScope[DiodeVariable[Var], Var]) {
      def render (props: DiodeVariable[Var], state: Var): ReactElement =
        html (StatefulVariable (state, scope.setState (_), props.set (state)))
    }
    ReactComponentB[DiodeVariable[Var]] (name).initialState_P (_.value).renderBackend [Backend].build (_)
  }

  def structuredStatic[Props] (name: String)(html: Structured[Props] => ReactElement): (Props, ReactNode) => ReactElement =
    { case s: (Props, ReactNode) => ReactComponentB[Props] (name).renderPC ((_, p, c) => html (Structured (p, c.toSeq))).build (s._1, s._2) }
}
