package fbs

object Fight extends App {
/*
  println(Board(
    size = 10,
    fleet = List(
      Ship(Position(1, 1), Position(5, 1)),
      Ship(Position(3, 3), Position(6, 3)),
      Ship(Position(9, 1), Position(9, 5))
    ),
    shots = List(Position(1, 1), Position(1, 2))
  ))

  val initial = Board(10, Seq.empty)


  Interpreter.run(Program.commands)(initial)
*/

  println(Program.program.foldMap(Compiler.pureCompiler).run(Board(10, Seq.empty)).value)
}
