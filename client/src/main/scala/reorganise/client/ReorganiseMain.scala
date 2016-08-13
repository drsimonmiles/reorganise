package reorganise.client

import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.extra.router.{BaseUrl, Router}
import org.scalajs.dom
import reorganise.client.routes.RouterConfig
import reorganise.client.styles.GlobalStyles
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._

@JSExport("ReorganiseMain")
object ReorganiseMain extends js.JSApp {
  @JSExport
  def main (): Unit = {
    // create stylesheet
    GlobalStyles.addToDocument ()
    // create the router
    val router = Router (BaseUrl.until_#, RouterConfig.routes)
    // tell React to render the router in the document body
    ReactDOM.render (router (), dom.document.getElementById ("root"))
  }
}
