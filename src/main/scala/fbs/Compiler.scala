package fbs

import cats.data.State
import cats.{Id, ~>}

object Compiler {
  def impureCompiler: Command ~> Id =
    new (Command ~> Id) {
      var board = Board(10, Seq.empty)

      override def apply[A](command: Command[A]): Id[A] = command match {
        case Place(ship) =>
          board = board.copy(fleet = ship +: board.fleet)
          ()
        case s@ Shoot(position) =>
          val outcome = board.fleetPositions.contains(position)
          board = board.copy(shots = position +: board.shots)

          println(s"$s => $outcome")
          println(board)
          println("\n")

          outcome
      }
    }

  type BoardState[A] = State[Board, A]

  def pureCompiler: Command ~> BoardState =
    new (Command ~> BoardState) {

      override def apply[A](command: Command[A]): BoardState[A] = command match {
        case Place(ship) => State.modify[Board](b => b.copy(fleet = ship +: b.fleet))
        case Shoot(position) =>
          for {
            outcome <- State.inspect[Board, Boolean](_.fleetPositions.contains(position))
            _       <- State.modify[Board](b => b.copy(shots = position +: b.shots))
          } yield outcome
      }
    }
}
