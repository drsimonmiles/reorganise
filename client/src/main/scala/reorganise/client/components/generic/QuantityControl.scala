package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.FAIcon.{minus, plus}
import reorganise.client.model.generic.DiodeVariable
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import scalacss.ScalaCssReact._

class QuantityControl {
  @inline private def bss = bootstrapStyles

 val component = ReactComponentB[DiodeVariable[Int]] ("QuantityControl")
    .render_P { p =>
      def button (icon: ReactNode, increment: Int) =
        <.span (bss.inputGroup.button,
          <.button (^.tpe := "button", bss.buttonOpt (default), icon,
            ^.onClick --> p.set ((p.model.value + increment).max (0))
          )
        )

      <.div (bss.inputGroup.inputGroup,
        button (minus (), -1),
        <.input (bss.formControl, ^.tpe := "text", ^.name := "quantity", ^.value := p.value, ^.disabled := "yes"),
        button (plus (), 1)
      )
    }.build

  def apply (model: DiodeVariable[Int]) =
    component (model)
}
