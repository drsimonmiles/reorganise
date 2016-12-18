package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.DiodeVariable

object Checkbox {
  def component = ReactComponentB[DiodeVariable[Boolean]] ("Checkbox")
    .render_P (p =>
      <.input.checkbox (^.checked := p.value, ^.onChange --> p.set (!p.value))
    ).build

  def apply (variable: DiodeVariable[Boolean]) =
    component (variable)
}
