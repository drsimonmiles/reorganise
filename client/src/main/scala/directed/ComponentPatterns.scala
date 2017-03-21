package directed

import japgolly.scalajs.react._

object ComponentPatterns {
  case class StatefulVariable[Var] (state: Var, set: Var => Callback, commit: Callback)

  case class Structured[Props] (props: Props, children: Seq[ReactNode])

  def editor[Var] (name: String)(html: DiodeVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement =
    ReactComponentB[DiodeVariable[Var]] (name).render_P (html).build (_)

  def externalEditor[Var] (name: String)(connector: (DiodeVariable[Var], TopNode) => Callback)(html: DiodeVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement =
    ReactComponentB[DiodeVariable[Var]] (name).render_P (html).
      componentDidMount (scope => connector (scope.props, scope.getDOMNode ())).build (_)

  def atomicEditor[Var] (name: String)(html: StatefulVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement = {
    class Backend (scope: BackendScope[DiodeVariable[Var], Var]) {
      def render (props: DiodeVariable[Var], state: Var): ReactElement =
        html (StatefulVariable (state, scope.setState (_), props.set (state)))
    }
    ReactComponentB[DiodeVariable[Var]] (name).initialState_P (_.value).renderBackend [Backend].build (_)
  }

  def atomicExternalEditor[Var] (name: String)(connector: (DiodeVariable[Var], TopNode) => Callback)(html: StatefulVariable[Var] => ReactElement): DiodeVariable[Var] => ReactElement = {
    class Backend (scope: BackendScope[DiodeVariable[Var], Var]) {
      def render (props: DiodeVariable[Var], state: Var): ReactElement =
        html (StatefulVariable (state, scope.setState (_), props.set (state)))
    }
    ReactComponentB[DiodeVariable[Var]] (name).initialState_P (_.value).renderBackend [Backend].
      componentDidMount (scope => connector (scope.props, scope.getDOMNode ())).build (_)
  }

  def container[Props] (name: String)(html: Structured[Props] => ReactElement): (Props, Seq[ReactNode]) => ReactElement = {
    case s: (Props, Seq[ReactNode]) =>
      ReactComponentB[Props] (name).renderPC ((_, p, c) => html (Structured (p, c.toSeq))).build (s._1, s._2)
  }

  def containerItem[Props] (name: String)(html: Structured[Props] => ReactElement): (Props, Seq[ReactNode], String) => ReactElement = {
    case s: (Props, Seq[ReactNode], String) =>
      ReactComponentB[Props] (name).renderPC ((_, p, c) => html (Structured (p, c.toSeq))).build.withKey (s._3) (s._1, s._2)
  }
}
