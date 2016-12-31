package reorganise.client.components

import directed.ComponentPatterns.editor
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FAIcon.{chevronCircleDown, chevronCircleUp, longArrowDown, longArrowUp, refresh}

object SpecificComponents {
  def recurControl = editor[Option[Int]] ("RecurControl") { p =>
    p.value match {
      case Some (days) => <.span (refresh.basic, days.toString)
      case None => <.span (refresh.banned)
    }
  }

  def reorderControl[Item] (item: Item) = editor[Vector[(Item, Boolean)]] ("ReorderControl") { p =>
    val order = p.value
    val index = order.indexWhere (_._1 == item)

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
      <.span (Button.small (p.set (moveUpAll),   longArrowUp ())),
      <.span (Button.small (p.set (moveUpOne),   chevronCircleUp ())),
      <.span (Button.small (p.set (moveDownOne), chevronCircleDown ())),
      <.span (Button.small (p.set (moveDownAll), longArrowDown ()))
    )
  }
}
