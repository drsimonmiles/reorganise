package reorganise.client.routes

import japgolly.scalajs.react.extra.router.{Redirect, RouterConfigDsl}
import reorganise.client.model.{ModelPoint, LoadableModel, ModelController}
import reorganise.client.screens.{ErrorScreen, GlobalLayout, TasksScreen}

object RouterConfig {
  val routes = RouterConfigDsl[ScreenID].buildConfig { dsl =>
    import dsl._

    val taskRoute = staticRoute (root, TasksScreenID) ~> render {
      TasksScreen (ModelPoint [LoadableModel, LoadableModel](ModelController.zoom (x => x), ModelController))
    }
    val errorRoute = staticRoute (root, ErrorScreenID) ~> render (ErrorScreen ())

    (taskRoute | errorRoute).notFound (redirectToPage (ErrorScreenID)(Redirect.Replace))
  }.renderWith (GlobalLayout.layout)
}
