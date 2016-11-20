package fbs

import fbs.Freeing.{CommandF, place, shoot}

object Program {

  def program: CommandF[Boolean] =
    for {
      _ <- place(Ship(Position(1, 1), Position(5, 1)))
      _ <- place(Ship(Position(3, 3), Position(6, 3)))
      _ <- place(Ship(Position(9, 1), Position(9, 5)))
      shot1 <- shoot(Position(1, 1))
      shot2 <- shoot(Position(1, 2))
    } yield shot1 && shot2
}
