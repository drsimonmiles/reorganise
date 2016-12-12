package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._

class ReorderControl[Model <: AnyRef, Item] (item: Item) {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[Variable[Model, Vector[(Item, Boolean)]]] ("ReorderControl")
    .render_P { p =>
      val order = p.value
      val index = order.indexOf (item)
      val prior = order.take (index).reverse.dropWhile (!_._2).size - 1
      val next = order.drop (index + 1).takeWhile (!_._2).size + index + 1
      val styles = Seq (bss.buttonSmall)

      def moveUpAll   =
        if (index > 0) (order (index) +: order.take (index)) ++ order.drop (index + 1) else order
      def moveUpOne   =
        if (index > 0) (order.take (prior) :+ order (index)) ++ order.slice (prior, index) ++ order.drop (index + 1) else order
      def moveDownOne =
        if (index < order.size - 1) (order.take (index) ++ order.slice (index + 1, next) :+ order (index)) ++ order.drop (next + 1) else order
      def moveDownAll =
        if (index < order.size - 1) order.take (index) ++ order.drop (index + 1) :+ order (index) else order

      <.div (
        <.span (Button (Button.Props (p.set (moveUpAll),   default, styles), longArrowUp)),
        <.span (Button (Button.Props (p.set (moveUpOne),   default, styles), chevronCircleUp)),
        <.span (Button (Button.Props (p.set (moveDownOne), default, styles), chevronCircleDown)),
        <.span (Button (Button.Props (p.set (moveDownAll), default, styles), longArrowDown))
      )
    }.build

  def apply (data: Variable[Model, Vector[(Item, Boolean)]]) =
    component (data)
}
