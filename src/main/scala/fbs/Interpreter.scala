package fbs

import scala.annotation.tailrec

object Interpreter {
  def run[A](command: Command[A])(board: Board): (Board, A) = command match {
    case Place(ship) =>
      (board.copy(fleet = ship +: board.fleet), ())
    case Shoot(position) =>
      val outcome = board.fleetPositions.contains(position)
      (board.copy(shots = position +: board.shots), outcome)
  }

  def runAndPrint(command: Command[_])(board: Board): Board = command match {
    case p@Place(_) =>
      val (b, _): (Board, Unit) = run(p)(board)
      b
    case s@Shoot(_) =>
      val (b, outcome): (Board, Boolean) = run(s)(board)
      println(s"$s => $outcome")
      println(b)
      println("\n")
      b
  }

  @tailrec
  def run(commands: List[Command[_]])(board: Board): Unit = commands match {
    case head :: rest => run(rest)(runAndPrint(head)(board))
    case Nil => ()
  }
}
