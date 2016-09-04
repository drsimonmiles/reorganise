package reorganise.client.model

import diode.{ActionHandler, Effect, ModelRW}
import scala.concurrent.ExecutionContext.Implicits.global

class TaskFeatureUpdater[Model] (modelRW: ModelRW[Model, TaskFeature]) extends ActionHandler (modelRW) {
  def handle = {
    case ChangeFeature (newFeature) =>
      updated (newFeature)
  }
}
