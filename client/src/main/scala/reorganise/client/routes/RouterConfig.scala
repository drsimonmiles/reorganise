package reorganise.client.routes

import japgolly.scalajs.react.extra.router.{Redirect, RouterConfigDsl}
import reorganise.client.model.ModelController
import reorganise.client.screens.{ErrorScreen, GlobalLayout, TasksScreen}

object RouterConfig {
  val routes = RouterConfigDsl[ScreenID].buildConfig { dsl =>
    import dsl._

    val taskRoute = staticRoute (root, TasksScreenID) ~> render {
      val y = ModelController.connect (_.state)
      y (TasksScreen.component (_))
    }
    val errorRoute = staticRoute (root, ErrorScreenID) ~> render (ErrorScreen ())

    (taskRoute | errorRoute).notFound (redirectToPage (ErrorScreenID)(Redirect.Replace))
  }.renderWith (GlobalLayout.layout)
}
