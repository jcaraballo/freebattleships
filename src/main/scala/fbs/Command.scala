package fbs

sealed abstract class Command[A]

case class Place(ship: Ship) extends Command[Unit]

case class Shoot(position: Position) extends Command[Boolean]