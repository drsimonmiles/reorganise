package reorganise.client.components

import directed.ComponentPatterns._
import directed.DiodeVariable
import japgolly.scalajs.react.{Callback, ReactElement, ReactEventI, TopNode}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FAIcon.{chevronCircleDown, chevronCircleUp, longArrowDown, longArrowUp, refresh}
import reorganise.client.components.GenericComponents._
import org.querki.facades.bootstrap.datepicker._
import org.querki.jquery._

object SpecificComponents {
  def datePicker: DiodeVariable[String] => ReactElement = {
    val datePickerOptions = BootstrapDatepickerOptions.
      format ("yyyy-mm-dd").
      autoclose (true).
      todayHighlight (true).
      todayBtnLinked ().
      disableTouchKeyboard (true).
      orientation (Orientation.Auto)._result

    def datePickerDynamics (v: DiodeVariable[String], n: TopNode) = Callback {
      $ (n).datepicker (datePickerOptions)
      def change (x: JQueryEventObject, y: Any): Any = v.set ($ (n).getFormattedDate ()).runNow ()
      $ (n).on ("changeDate", change _)
    }

/*    atomicExternalEditor[String] ("DatePicker") (datePickerDynamics) { v =>
      <.input (^.tpe := "text", ^.value := v.state)
    }*/

    externalEditor[String] ("DatePicker") (datePickerDynamics) { v =>
      <.input (^.tpe := "text", ^.value := v.value)
    }
  }

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
      <.span (buttonSmall (p.set (moveUpAll),   Seq (longArrowUp ()))),
      <.span (buttonSmall (p.set (moveUpOne),   Seq (chevronCircleUp ()))),
      <.span (buttonSmall (p.set (moveDownOne), Seq (chevronCircleDown ()))),
      <.span (buttonSmall (p.set (moveDownAll), Seq (longArrowDown ())))

    )
  }
}
