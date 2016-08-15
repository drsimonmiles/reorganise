package reorganise.shared.model

case class Task (id: Long, text: String, startDate: String, list: String, recur: Option[Int], completed: Boolean)
//case class Task (id: Long, text: String, startDate: String, list: String, recur: Int, completed: Boolean)
