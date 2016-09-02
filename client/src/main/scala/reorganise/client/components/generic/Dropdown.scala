package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object Dropdown {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props[Item] (title: String, items: Seq[Item], label: Item => String, onClick: Item => Callback)

  def component[Item] = ReactComponentB[Props[Item]] ("Dropdown")
    .render_P (p =>
      <.div (bss.dropdown,
        <.button (bss.buttonOpt (primary), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          p.title + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          p.items.map (item => <.li (bss.listGroup.item, p.label (item), ^.onClick --> p.onClick (item)))
        )
      )
    ).build

  def apply[Item] (title: String, items: Seq[Item], label: Item => String, onClick: Item => Callback) =
    component (Props (title, items, label, onClick))
}
