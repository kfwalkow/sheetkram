package sheetkram.model

import java.util.Date

case class CellPosition( colIdx : Int, rowIdx : Int )

trait Cell {
  def position : CellPosition
  def valueAsText : String
  def valueAsNumber : Option[ BigDecimal ] = None
  def valueAsDate : Option[ Date ] = None
}

case class TextCell( position : CellPosition, valueAsText : String ) extends Cell {
  assert( position != null )
  assert( valueAsText != null )
}

case class NumberCell( position : CellPosition, value : BigDecimal ) extends Cell {
  assert( position != null )
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsNumber : Option[ BigDecimal ] = Some( value )
}

case class DateCell( position : CellPosition, value : Date ) extends Cell {
  assert( position != null )
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsDate : Option[ Date ] = Some( value )
}
