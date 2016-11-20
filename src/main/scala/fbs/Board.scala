package fbs

case class Board(size: Int, fleet: Seq[Ship], shots: Seq[Position] = Seq.empty){

  def join(p1: Position, p2: Position): Seq[Position] = (p1, p2) match {
    case (Position(x1, y1), Position(x2, y2)) if x1==x2 && y1<=y2 => (y1 to y2).map(y => Position(x1, y))
    case (Position(x1, y1), Position(x2, y2)) if x1==x2 && y1>y2  => (y2 to y1).map(y => Position(x1, y))
    case (Position(x1, y1), Position(x2, y2)) if x1<=x2 && y1==y2 => (x1 to x2).map(x => Position(x, y1))
    case (Position(x1, y1), Position(x2, y2)) if x1>x2  && y1==y2 => (x1 to x2).map(x => Position(x, y1))
    case _ => ??? // Can't do diagonals
  }

  def update(vector: Vector[Vector[Char]], position: Position, character: Char => Char): Vector[Vector[Char]] = {
    val chosenRow: Vector[Char] = vector(position.zeroBasedY)
    val newCharacter = character(chosenRow(position.zeroBasedX))
    vector.updated(position.zeroBasedY, chosenRow.updated(position.zeroBasedX, newCharacter))
  }

  def updateMultiple(vector: Vector[Vector[Char]], positions: Seq[Position], character: Char => Char): Vector[Vector[Char]] =
    positions.foldLeft(vector)((v, p) => update(v, p, character))

  lazy val fleetPositions: Seq[Position] = fleet.flatMap(s => join(s.end1, s.end2))

  lazy val positions: Vector[Vector[Char]] = {
    val blank: Vector[Vector[Char]] = Vector.fill[Char](size, size)(' ')

    val withFleet = updateMultiple(blank, fleetPositions, _ => '*')

    updateMultiple(withFleet, shots, c => if (c=='*') '&' else '·')
  }

  def format: String = {
    val edge: String = " " + ("—" * size) + " \n"
    (0 until size).map(positions(_)).map(_.mkString("|", "", "|\n")).mkString(edge, "", edge)
  }

  override def toString: String = format
}

case class Ship(end1: Position, end2: Position)

case class Position(oneBasedX: Int, oneBasedY: Int){
  val x = oneBasedX
  val y = oneBasedY
  lazy val zeroBasedX = oneBasedX - 1
  lazy val zeroBasedY = oneBasedY - 1
}
