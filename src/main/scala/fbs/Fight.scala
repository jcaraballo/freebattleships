package fbs

object Fight extends App {
  println(Program.program.foldMap(Compiler.pureCompiler).run(Board(10, Seq.empty)).value)
}
