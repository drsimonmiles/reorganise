package reorganise.client.model

import diode.{Action, Circuit}
import diode.data.{Empty, Pot}
import diode.react.ReactConnector
import japgolly.scalajs.react.Callback

object ModelController extends Circuit[Model] with ReactConnector[Model] {
  def initialModel = Model (Empty)

  def actionHandler: ModelController.HandlerFunction =
    new ClientStateUpdater (zoomTo (_.state))

  def dispatchCB (action: Action): Callback =
    Callback (dispatch (action))
}
