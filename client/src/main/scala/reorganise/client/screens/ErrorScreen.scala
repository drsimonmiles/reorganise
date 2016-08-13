package reorganise.client.screens

import japgolly.scalajs.react.{ReactElement, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._

object ErrorScreen {
  val component = ReactComponentB[Unit] ("ErrorScreen")
    .renderPC ((_, p, c) =>
      <.div ("Error found")
    ).build

  def apply (): ReactElement = component ()
}
