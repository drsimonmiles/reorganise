package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB, ReactNode}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import scalacss.Defaults.StyleA
import scalacss.ScalaCssReact._

object Button {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (onClick: Callback, style: AlertStyle = default, addStyles: Seq[StyleA] = Seq ())

  val component = ReactComponentB[Props] ("Button")
    .renderPC ((_, p, c) =>
      <.button (bss.buttonOpt (p.style), p.addStyles, ^.tpe := "button", ^.onClick --> p.onClick, c)
    ).build

  def apply (props: Props, children: ReactNode*) =
    component (props, children: _*)

  def apply (onClick: Callback, children: ReactNode*) =
    component (Props (onClick), children: _*)

  def small (onClick: Callback, children: ReactNode*) =
    component (Props (onClick, addStyles = Seq (bss.buttonSmall)), children: _*)

}
