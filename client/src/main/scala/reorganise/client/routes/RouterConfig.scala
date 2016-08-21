package reorganise.client.routes

import japgolly.scalajs.react.extra.router.{Redirect, RouterConfigDsl}
import reorganise.client.model.{LoadableModel, ModelController}
import reorganise.client.screens.{ErrorScreen, TasksScreen, GlobalLayout}

object RouterConfig {
  val routes = RouterConfigDsl[ScreenID].buildConfig { dsl =>
    import dsl._

    val taskRoute = staticRoute (root, TasksScreenID) ~> renderR { routerControl =>
      val componentProxy = ModelController.connect ({m: LoadableModel => m})
      componentProxy (TasksScreen (_))
    }
    val errorRoute = staticRoute (root, ErrorScreenID) ~> render (ErrorScreen ())

    (taskRoute | errorRoute).notFound (redirectToPage (ErrorScreenID)(Redirect.Replace))
  }.renderWith (GlobalLayout.layout)
}
