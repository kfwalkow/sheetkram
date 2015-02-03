package sheetkram.model

import java.util.Date

trait Cell2 {
  def valueAsText : String
  def valueAsNumber : Option[ BigDecimal ] = None
  def valueAsDate : Option[ Date ] = None
}

case class TextCell2( valueAsText : String ) extends Cell2 {
  assert( valueAsText != null )
}

case class NumberCell2( value : BigDecimal ) extends Cell2 {
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsNumber : Option[ BigDecimal ] = Some( value )
}

case class DateCell2( value : Date ) extends Cell2 {
  assert( value != null )
  def valueAsText : String = value.toString
  override def valueAsDate : Option[ Date ] = Some( value )
}

case class EmptyCell2() extends Cell2 {
  def valueAsText : String = ""
}
