package reorganise.client.components

import japgolly.scalajs.react.{ReactNode, ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.{GlobalStyles, BootstrapAlertStyles}
import reorganise.client.styles.BootstrapAlertStyles.default
import scalacss.ScalaCssReact._
import scalacss.Defaults.StyleA

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
