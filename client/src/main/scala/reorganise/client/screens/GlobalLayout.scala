package reorganise.client.screens

import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.routes.ScreenID

object GlobalLayout {
  def layout (c: RouterCtl[ScreenID], r: Resolution[ScreenID]): ReactElement =
    <.div (
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.nav (^.className := "navbar navbar-inverse navbar-fixed-top",
        <.div (^.className := "container",
          <.div (^.className := "navbar-header", <.span (^.className := "navbar-brand", "Reorganise")),
          <.div (^.className := "collapse navbar-collapse", MainMenu (c, r.page))
          )
        ),
      // currently active module is shown in this container
      <.div (^.className := "container", r.render ())
    )
}
