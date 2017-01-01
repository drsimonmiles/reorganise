package directed

trait VariableCreator[Input, Var] {
  def from (start: Input): DiodeVariable[Var]
}
