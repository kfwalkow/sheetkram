package sheetkram.model

case class CellAddress( cellAddress : String ) {
  require( cellAddress.matches( "[A-Z]{1,3}[1-9]{1,7}" ), "Invalid cell address!" )

  private val letters = cellAddress.takeWhile { x => x.isLetter }
  private val digits = cellAddress.substring( letters.length() )

  val colIdx : Int = letters.
    reverse.
    zipWithIndex.
    foldLeft[ Int ]( 0 ) { ( res, letterWithIdx ) =>
      res + ( letterWithIdx._1 - 64 ) * CellAddress.POWS( letterWithIdx._2 )
    } - 1

  val rowIdx : Int = digits.toInt - 1

  val colIdxAndRowIdx : ( Int, Int ) = ( colIdx, rowIdx )

}

object CellAddress {
  private val POWS : IndexedSeq[ Int ] = IndexedSeq( 1, 26, 676 )
}
