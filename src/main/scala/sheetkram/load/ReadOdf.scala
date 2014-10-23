package sheetkram.load

import sheetkram.model.Workbook
import java.io.File
import org.jopendocument.dom.spreadsheet.{ SpreadSheet => OdfWorkbook }
import org.jopendocument.dom.spreadsheet.{ Sheet => OdfSheet }
import org.jopendocument.dom.spreadsheet.{ Cell => OdfCell }
import sheetkram.model.Cell
import sheetkram.model.Sheet
import sheetkram.model.SheetPosition
import sheetkram.model.CellPosition
import sheetkram.model.TextCell
import org.jopendocument.dom.ODValueType
import sheetkram.model.NumberCell
import java.util.Date
import sheetkram.model.DateCell

object ReadOdf extends ReadWorkbook {

  def fromFile( file : File ) : Workbook = {

    val odfWorkbook : OdfWorkbook = OdfWorkbook.createFromFile( file )
    var workbook : Workbook = Workbook()

    for ( sheetIdx : Int <- 0 to ( odfWorkbook.getSheetCount() - 1 ) ) {
      val odfSheet : OdfSheet = odfWorkbook.getSheet( sheetIdx )
      println( "Reading sheet '" + odfSheet.getName() + "', " + odfSheet.getColumnCount() + ", " + odfSheet.getRowCount() )
      workbook = workbook.updateSheet( Sheet( sheetIdx, odfSheet.getName() ) )
      for ( rowIdx : Int <- 0 to ( math.min( odfSheet.getRowCount() - 1, 5000 ) ) ) { // Begrenzung ist harter Hack!!!
        for ( colIdx : Int <- 0 to ( math.min( odfSheet.getColumnCount() - 1, 50 ) ) ) { // Begrenzung ist harter Hack!!!
          println( "Reading cell " + colIdx + ", " + rowIdx )
          val odfCell : OdfCell[ OdfWorkbook ] = odfSheet.getCellAt( colIdx, rowIdx )
          if ( !odfCell.isEmpty() ) {
            val cellPos = CellPosition( colIdx, rowIdx )
            val cell : Cell = odfCell.getValueType() match {
              case ODValueType.STRING     => TextCell( cellPos, odfCell.getTextValue() )
              case ODValueType.FLOAT      => NumberCell( cellPos, odfCell.getValue().asInstanceOf[ java.math.BigDecimal ] )
              case ODValueType.PERCENTAGE => NumberCell( cellPos, odfCell.getValue().asInstanceOf[ java.math.BigDecimal ] )
              case ODValueType.DATE       => DateCell( cellPos, odfCell.getValue().asInstanceOf[ Date ] )
              case _                      => TextCell( cellPos, odfCell.getTextValue() ) // Unsupported datatypes
            }
            workbook = workbook.sheetForUpdate( sheetIdx ).updateCell( cell )
          }
        }
      }
    }

    workbook
  }

}