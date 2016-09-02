package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB, ReactNode}
import reorganise.client.styles.BootstrapAlertStyles.default
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.Defaults.StyleA
import scalacss.ScalaCssReact._

object Button {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (onClick: Callback, style: BootstrapAlertStyles.Value = default, addStyles: Seq[StyleA] = Seq ())

  val component = ReactComponentB[Props] ("Button")
    .renderPC ((_, p, c) =>
      <.button (bss.buttonOpt (p.style), p.addStyles, ^.tpe := "button", ^.onClick --> p.onClick, c)
    ).build

  def apply (props: Props, children: ReactNode*) = component(props, children: _*)
  def apply () = component
}
