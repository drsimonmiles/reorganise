package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon._
import reorganise.client.model.generic.DiodeVariable
import reorganise.client.styles.GlobalStyles._

object RecurControl {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[DiodeVariable[Option[Int]]] ("RecurControl")
    .render_P { p =>
      p.value match {
        case Some (days) => <.span (refresh, days.toString)
        case None => <.span (banned ("refresh"))
      }
    }.build

  def apply (data: DiodeVariable[Option[Int]]) =
    component (data)
}
