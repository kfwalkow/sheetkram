package sheetkram.model

import java.util.Date

sealed trait Cell {
  def valueAsText : String
  def valueAsNumber : Option[ BigDecimal ] = None
  def valueAsDate : Option[ Date ] = None
  def valueAsBoolean : Option[ Boolean ] = None
}

case class TextCell( valueAsText : String ) extends Cell {
  assert( valueAsText != null )
}

case class NumberCell( value : BigDecimal ) extends Cell {
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsNumber : Option[ BigDecimal ] = Some( value )
}

case class DateCell( value : Date ) extends Cell {
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsDate : Option[ Date ] = Some( value )
}

case class BooleanCell( value : Boolean ) extends Cell {
  def valueAsText : String = value.toString
  override def valueAsBoolean : Option[ Boolean ] = Some( value )
}

case class EmptyCell() extends Cell {
  def valueAsText : String = ""
}
