package reorganise.client.components

import scala.scalajs.js

@js.native
trait BootstrapJQuery extends JQuery {
  def modal (action: String): BootstrapJQuery = js.native
  def modal (options: js.Any): BootstrapJQuery = js.native
}
