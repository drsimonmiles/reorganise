package reorganise.client.model

import reorganise.shared.model.Task

object ModelOps {
  def visible (state: ClientState, task: Task): Boolean =
    state.view.exists (_.includeCompleted || !task.completed)

  def orderWithVisibility (state: ClientState, order: Vector[Long]): Vector[(Long, Boolean)] =
    order.flatMap { taskID =>
      state.visible.task (taskID).map (task => (taskID, visible (state, task)))
    }
}
