package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Button
import reorganise.client.components.generic.Icon._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.GlobalStyles._

class ReorderControl[Model <: AnyRef, Item] (item: Item) {
  @inline private def bss = bootstrapStyles

  def move (item: Item, oldOrder: Vector[Item], up: Boolean, toLimit: Boolean): Vector[Item] = {
    val index = oldOrder.indexOf (item)
    if (up && index > 0)
      if (toLimit) (item +: oldOrder.take (index)) ++ oldOrder.drop (index + 1)
      else (oldOrder.take (index - 1) :+ item :+ oldOrder (index - 1)) ++ oldOrder.drop (index + 1)
    else if (!up && index < oldOrder.size - 1)
      if (toLimit) oldOrder.take (index) ++ oldOrder.drop (index + 1) :+ item
      else (oldOrder.take (index) :+ oldOrder (index + 1) :+ item) ++ oldOrder.drop (index + 2)
    else oldOrder
  }

  val component = ReactComponentB[Variable[Model, Vector[Item]]] ("ReorderControl")
    .render_P { p =>
      def moveUpAll   = p.set (move (item, p.value, up = true,  toLimit = true))
      def moveUpOne   = p.set (move (item, p.value, up = true,  toLimit = false))
      def moveDownOne = p.set (move (item, p.value, up = false, toLimit = false))
      def moveDownAll = p.set (move (item, p.value, up = false, toLimit = true))
      <.div (
        <.span (Button (Button.Props (onClick = moveUpAll,   addStyles = Seq (bss.buttonSmall)), longArrowUp)),
        <.span (Button (Button.Props (onClick = moveUpOne,   addStyles = Seq (bss.buttonSmall)), chevronCircleUp)),
        <.span (Button (Button.Props (onClick = moveDownOne, addStyles = Seq (bss.buttonSmall)), chevronCircleDown)),
        <.span (Button (Button.Props (onClick = moveDownAll, addStyles = Seq (bss.buttonSmall)), longArrowDown))
      )
    }.build

  def apply (data: Variable[Model, Vector[Item]]) =
    data.createEditor (component)
}
