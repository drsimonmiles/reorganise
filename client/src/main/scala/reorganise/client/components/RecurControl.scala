package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Icon._
import reorganise.client.model.LoadableModel
import reorganise.client.model.generic.Variable
import reorganise.client.styles.GlobalStyles._

object RecurControl {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[Variable[LoadableModel, Option[Int]]] ("RecurControl")
    .render_P { p =>
      p.value match {
        case Some (days) => <.span (refresh, days.toString)
        case None => <.span (banned ("refresh"))
      }
    }.build

  def apply (data: Variable[LoadableModel, Option[Int]]) =
    //data.createEditor (component)
    component (data)
}
