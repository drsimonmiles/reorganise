package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.GlobalStyles._

//noinspection TypeAnnotation
class ReorderControl[Model <: AnyRef, Item] (item: Item) {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[Variable[Model, Vector[(Item, Boolean)]]] ("ReorderControl")
    .render_P { p =>
      val order = p.value
      val index = order.indexWhere (_._1 == item)
      val styles = Seq (bss.buttonSmall)

      def moveUpAll =
        if (index > 0) (order (index) +: order.take (index)) ++ order.drop (index + 1)
        else order
      def moveUpOne =
        if (index > 0) {
          val prior = order.take (index).reverse.dropWhile (!_._2).size - 1
          (order.take (prior) :+ order (index)) ++ order.slice (prior, index) ++ order.drop (index + 1)
        } else order
      def moveDownOne =
        if (index >= 0 && index < order.size - 1) {
          val next = order.drop (index + 1).takeWhile (!_._2).size + index + 1
          (order.take (index) ++ order.slice (index + 1, next + 1) :+ order (index)) ++ order.drop (next + 1)
        } else order
      def moveDownAll =
        if (index >= 0 && index < order.size - 1) order.take (index) ++ order.drop (index + 1) :+ order (index)
        else order

      <.div (
        <.span (Button (Button.Props (onClick = p.set (moveUpAll),   addStyles = styles), longArrowUp)),
        <.span (Button (Button.Props (onClick = p.set (moveUpOne),   addStyles = styles), chevronCircleUp)),
        <.span (Button (Button.Props (onClick = p.set (moveDownOne), addStyles = styles), chevronCircleDown)),
        <.span (Button (Button.Props (onClick = p.set (moveDownAll), addStyles = styles), longArrowDown))
      )
    }.build

  def apply (data: Variable[Model, Vector[(Item, Boolean)]]) =
    component (data)
}
