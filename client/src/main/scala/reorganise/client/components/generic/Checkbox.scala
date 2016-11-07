package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.Variable

object Checkbox {
  def component[Model <: AnyRef] = ReactComponentB[Variable[Model, Boolean]] ("Checkbox")
    .render_P (p =>
      <.input.checkbox (^.checked := p.value, ^.onChange --> p.set (!p.value))
    ).build

  def apply[Model <: AnyRef] (variable: Variable[Model, Boolean]) =
    variable.createEditor (component)
}
