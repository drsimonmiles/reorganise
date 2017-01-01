package reorganise.client.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB, ReactComponentU, ReactElement, ReactNode, TopNode}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import scalacss.Defaults.StyleA
import scalacss.ScalaCssReact._

object Button {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (onClick: Callback, style: AlertStyle = default, addStyles: Seq[StyleA] = Seq ())

  private val component = ReactComponentB[Props] ("Button")
    .renderPC ((_, p, c) =>
      <.button (bss.buttonOpt (p.style), p.addStyles, ^.tpe := "button", ^.onClick --> p.onClick, c)
    ).build

  def apply (props: Props, children: ReactNode*): ReactElement =
    component (props, children: _*)

  def apply (onClick: Callback, children: ReactNode*): ReactElement =
    component (Props (onClick), children: _*)

  def small (onClick: Callback, children: ReactNode*): ReactElement =
    component (Props (onClick, addStyles = Seq (bss.buttonSmall)), children: _*)

}
