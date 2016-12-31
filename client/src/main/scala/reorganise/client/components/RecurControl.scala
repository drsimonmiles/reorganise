package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.FAIcon.refresh
import reorganise.client.model.generic.DiodeVariable
import reorganise.client.styles.GlobalStyles._

object RecurControl {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[DiodeVariable[Option[Int]]] ("RecurControl")
    .render_P { p =>
      p.value match {
        case Some (days) => <.span (refresh.basic, days.toString)
        case None => <.span (refresh.banned)
      }
    }.build

  def apply (data: DiodeVariable[Option[Int]]) =
    component (data)
}
