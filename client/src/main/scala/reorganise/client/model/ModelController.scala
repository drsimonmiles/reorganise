package reorganise.client.model

import diode.Circuit
import diode.data.Empty
import diode.react.ReactConnector

object ModelController extends Circuit[LoadableModel] with ReactConnector[LoadableModel] {
  def initialModel = LoadableModel (Empty)

  def actionHandler: ModelController.HandlerFunction =
    new ClientStateUpdater (zoomRW (_.data) ((model, value) => model.copy (data = value)))
}
