package sheetkram.model

trait CellVector {
  def cells : IndexedSeq[ Cell2 ]
}

object CellVector {

  def setCell( idx : Int, cell : Cell2, cells : IndexedSeq[ Cell2 ] ) : IndexedSeq[ Cell2 ] = {
    if ( idx >= cells.size )
      cells ++ IndexedSeq.fill( idx - cells.size )( EmptyCell2() ) :+ cell
    else
      cells.updated( idx, cell )
  }

  def ensure( idx : Int, cells : IndexedSeq[ Cell2 ] ) : IndexedSeq[ Cell2 ] =
    if ( idx >= cells.size )
      cells ++ IndexedSeq.fill( idx - cells.size + 1 )( EmptyCell2() )
    else
      cells

}

case class Column private (
  cells : IndexedSeq[ Cell2 ] ) extends CellVector {

  def update( rowIdx : Int, cell : Cell2 ) : Column = copy( cells = CellVector.setCell( rowIdx, cell, cells ) )

  def ensure( rowIdx : Int ) : Column = copy( cells = CellVector.ensure( rowIdx, cells ) )

}

object Column {

  def apply( rowCount : Int ) : Column = Column( IndexedSeq.fill( rowCount )( EmptyCell2() ) )

}

case class Row private (
  cells : IndexedSeq[ Cell2 ] ) extends CellVector {

  def update( colIdx : Int, cell : Cell2 ) : Row = copy( cells = CellVector.setCell( colIdx, cell, cells ) )

  def ensure( colIdx : Int ) : Row = copy( cells = CellVector.ensure( colIdx, cells ) )

}

object Row {

  def apply( colCount : Int ) : Row = Row( IndexedSeq.fill( colCount )( EmptyCell2() ) )

}

case class Sheet2 private (
  name : String,
  columns : IndexedSeq[ Column ],
  rows : IndexedSeq[ Row ] ) {

  def cell( colIdx : Int, rowIdx : Int ) : Option[ Cell2 ] =
    if ( colIdx < columns.size && rowIdx < rows.size ) Some( columns( colIdx ).cells( rowIdx ) ) else None

  def column( colIdx : Int ) : Option[ Column ] =
    if ( colIdx >= columns.size ) None else Some( columns( colIdx ) )

  def row( rowIdx : Int ) : Option[ Row ] =
    if ( rowIdx >= rows.size ) None else Some( rows( rowIdx ) )

  def update( colIdx : Int, rowIdx : Int, cell : Cell2 ) : Sheet2 = {
    val _columns : IndexedSeq[ Column ] =
      if ( colIdx >= columns.size ) {
        if ( rowIdx >= rows.size ) {
          columns.map { col => col.ensure( rowIdx ) } ++
            IndexedSeq.fill( colIdx - columns.size )( Column( rowIdx + 1 ) ) :+
            Column( rowIdx + 1 ).update( rowIdx, cell )
        } else {
          columns ++
            IndexedSeq.fill( colIdx - columns.size )( Column( rows.size ) ) :+
            Column( rows.size ).update( rowIdx, cell )
        }
      } else {
        if ( rowIdx >= rows.size ) {
          columns.map { col => col.ensure( rowIdx ) }.
            updated( colIdx, columns( colIdx ).update( rowIdx, cell ) )
        } else {
          columns.updated( colIdx, columns( colIdx ).update( rowIdx, cell ) )
        }
      }

    val _rows : IndexedSeq[ Row ] =
      if ( rowIdx >= rows.size ) {
        if ( colIdx >= columns.size ) {
          rows.map { row => row.ensure( colIdx ) } ++
            IndexedSeq.fill( rowIdx - rows.size )( Row( colIdx + 1 ) ) :+
            Row( colIdx + 1 ).update( colIdx, cell )
        } else {
          rows ++
            IndexedSeq.fill( rowIdx - rows.size )( Row( columns.size ) ) :+
            Row( columns.size ).update( colIdx, cell )
        }
      } else {
        if ( colIdx >= columns.size ) {
          rows.map { row => row.ensure( colIdx ) }.
            updated( rowIdx, rows( rowIdx ).update( colIdx, cell ) )
        } else {
          rows.updated( rowIdx, rows( rowIdx ).update( colIdx, cell ) )
        }
      }

    copy( columns = _columns, rows = _rows )

  }

}

object Sheet2 {

  def apply( name : String ) : Sheet2 = Sheet2( name, IndexedSeq(), IndexedSeq() )

}

// 1 2 3 size
// 0 1 2 index
//       3
// 0 1 2 3 4 5 6 7

//        colIdx 0 1 2 3 4 5
// rowIdx
// 0                       
// 1                       (5;1)
// 2
// 3
