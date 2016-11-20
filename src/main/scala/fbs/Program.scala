package fbs

import fbs.Freeing.{CommandF, place, shoot}

object Program {

  val commands = List(
    Place(Ship(Position(1, 1), Position(5, 1))),
    Place(Ship(Position(3, 3), Position(6, 3))),
    Place(Ship(Position(9, 1), Position(9, 5))),
    Shoot(Position(1, 1)),
    Shoot(Position(1, 2))
  )


  def program: CommandF[Boolean] =
    for {
      _ <- place(Ship(Position(1, 1), Position(5, 1)))
      _ <- place(Ship(Position(3, 3), Position(6, 3)))
      _ <- place(Ship(Position(9, 1), Position(9, 5)))
      shot1 <- shoot(Position(1, 1))
      shot2 <- shoot(Position(1, 2))
    } yield shot1 && shot2
}
