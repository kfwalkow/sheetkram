package sheetkram.io.odf

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
import sheetkram.model.CellPosition
import sheetkram.io.ReadWorkbook

object ReadOdf extends ReadWorkbook {

  def fromFile( file : File ) : Workbook = {

    val odfWorkbook : OdfWorkbook = OdfWorkbook.createFromFile( file )
    var workbook : Workbook = Workbook()

    for ( sheetIdx : Int <- 0 to ( odfWorkbook.getSheetCount() - 1 ) ) {
      val odfSheet : OdfSheet = odfWorkbook.getSheet( sheetIdx )
      workbook = workbook.createSheet( sheetIdx, odfSheet.getName )
      for ( rowIdx : Int <- 0 to ( odfSheet.getRowCount() - 1 ) ) {
        for ( colIdx : Int <- 0 to ( odfSheet.getColumnCount() - 1 ) ) {
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
            workbook = workbook.updateSheet( sheetIdx, cell )
          }
        }
      }
    }
    workbook
  }

}

