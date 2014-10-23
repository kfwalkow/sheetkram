package sheetkram.model

case class ColumnPosition( idx : Int )

case class RowPosition( idx : Int )

case class Column( position : ColumnPosition,
                   cells : Map[ RowPosition, Cell ] )

case class Row( position : RowPosition,
                cells : Map[ ColumnPosition, Cell ] )
