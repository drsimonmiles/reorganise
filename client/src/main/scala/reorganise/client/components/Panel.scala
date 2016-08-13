package reorganise.client.components

import japgolly.scalajs.react.{ReactNode, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.{GlobalStyles, BootstrapAlertStyles}
import reorganise.client.styles.BootstrapAlertStyles.default
import scalacss.ScalaCssReact._

object Panel {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (heading: String, style: BootstrapAlertStyles.Value = default)

  val component = ReactComponentB[Props]("Panel")
    .renderPC ((_, p, c) =>
      <.div (bss.panelOpt (p.style),
        <.div (bss.panelHeading, p.heading),
        <.div (bss.panelBody, c)
      )
    ).build

  def apply (props: Props, children: ReactNode*) = component (props, children: _*)
  def apply () = component
}
