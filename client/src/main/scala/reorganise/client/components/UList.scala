package reorganise.client.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, Callback, ReactComponentB}
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object UList {
  @inline private def bss = GlobalStyles.bootstrapStyles

  private def component[Item] (itemComponent: Item => ReactElement) = ReactComponentB[Seq[Item]] ("TaskList")
    .render_P (p =>
      <.ul (bss.listGroup.listGroup) (p.map {
        item => itemComponent (item)
      })).build

  def apply[Item] (items: Seq[Item], itemComponent: Item => ReactElement) =
    component (itemComponent) (items)

  def menu[Item] (items: Seq[Item], label: Item => String, interaction: Item => Callback, current: Item => Boolean) =
    component ({ item: Item =>
      <.li (bss.listGroup.item, ^.onClick --> interaction (item), label (item), current (item) ?= (^.className := "active"))
    }) (items)
}
