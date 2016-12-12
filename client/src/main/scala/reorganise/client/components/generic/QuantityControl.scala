package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import scalacss.ScalaCssReact._

class QuantityControl[Model <: AnyRef] {
  @inline private def bss = bootstrapStyles

 val component = ReactComponentB[Variable[Model, Int]] ("QuantityControl")
    .render_P { p =>
      def button (icon: Icon, increment: Int) =
        <.span (bss.inputGroup.button,
          <.button (^.tpe := "button", bss.buttonOpt (default), icon,
            ^.onClick --> p.set ((p.model.value + increment).max (0))
          )
        )

      <.div (bss.inputGroup.inputGroup,
        button (Icon.minus, -1),
        <.input (bss.formControl, ^.tpe := "text", ^.name := "quantity", ^.value := p.value, ^.disabled := "yes"),
        button (Icon.plus, 1)
      )
    }.build

  def apply (model: Variable[Model, Int]) =
    //model.createEditor (component)
    component (model)
}
