package reorganise.client.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, Callback, ReactComponentB}
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object UList {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props[Item] (items: Seq[Item], itemComponent: Item => ReactElement)

  private def component[Item] = ReactComponentB[Props[Item]] ("TaskList")
    .render_P (p =>
      <.ul (bss.listGroup.listGroup) (p.items.map {
        item => p.itemComponent (item)
      })).build

  def apply[Item] (items: Seq[Item], itemComponent: Item => ReactElement) =
    component (Props (items, itemComponent))

  def menu[Item] (items: Seq[Item], label: Item => String, interaction: Item => Callback, current: Item => Boolean) =
    component (Props (items, { item: Item =>
      <.li (bss.listGroup.item, ^.onClick --> interaction (item), label (item), current (item) ?= (^.className := "active"))
    }))
}
