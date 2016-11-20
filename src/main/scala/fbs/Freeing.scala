package fbs

import cats.free.Free

object Freeing {
  type CommandF[A] = Free[Command, A]

  import cats.free.Free.liftF

  def place[T](ship: Ship): CommandF[Unit] =
    liftF[Command, Unit](Place(ship))

  def shoot[T](position: Position): CommandF[Boolean] =
    liftF[Command, Boolean](Shoot(position))
}
