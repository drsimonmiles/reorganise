package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactNode}
import reorganise.client.styles.BootstrapAlertStyles.default
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.ScalaCssReact._

object Panel {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (heading: Option[String], style: BootstrapAlertStyles.Value = default)

  val component = ReactComponentB[Props]("Panel")
    .renderPC ((_, p, c) =>
      <.div (bss.panelOpt (p.style),
        p.heading.isDefined ?= <.div (bss.panelHeading, p.heading.get),
        <.div (bss.panelBody, c)
      )
    ).build

  def apply (props: Props, children: ReactNode*) = component (props, children: _*)
  def apply () = component
}
