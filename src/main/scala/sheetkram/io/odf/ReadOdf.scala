package sheetkram.io.odf

import java.io.File
import org.jopendocument.dom.spreadsheet.{ SpreadSheet => OdfWorkbook }
import org.jopendocument.dom.spreadsheet.{ Sheet => OdfSheet }
import org.jopendocument.dom.spreadsheet.{ Cell => OdfCell }
import org.jopendocument.dom.ODValueType
import java.util.Date
import sheetkram.model.Workbook
import sheetkram.model.NumberCell
import sheetkram.model.DateCell
import sheetkram.model.Cell
import sheetkram.model.TextCell
import sheetkram.io.ReadWorkbook

object ReadOdf extends ReadWorkbook {

  def fromFile( file : File ) : Workbook = {

    val odfWorkbook : OdfWorkbook = OdfWorkbook.createFromFile( file )
    var workbook : Workbook = Workbook()

    for ( sheetIdx : Int <- 0 to ( odfWorkbook.getSheetCount - 1 ) ) {
      val odfSheet : OdfSheet = odfWorkbook.getSheet( sheetIdx )
      workbook = workbook.appendSheet( odfSheet.getName )
      for ( rowIdx : Int <- 0 to ( odfSheet.getRowCount - 1 ) ) {
        for ( colIdx : Int <- 0 to ( odfSheet.getColumnCount - 1 ) ) {
          val odfCell : OdfCell[ OdfWorkbook ] = odfSheet.getCellAt( colIdx, rowIdx )
          if ( !odfCell.isEmpty ) {
            val cell : Cell = odfCell.getValueType match {
              case ODValueType.STRING     => TextCell( odfCell.getTextValue )
              case ODValueType.FLOAT      => NumberCell( odfCell.getValue.asInstanceOf[ java.math.BigDecimal ] )
              case ODValueType.PERCENTAGE => NumberCell( odfCell.getValue.asInstanceOf[ java.math.BigDecimal ] )
              case ODValueType.DATE       => DateCell( odfCell.getValue.asInstanceOf[ Date ] )
              case _                      => TextCell( odfCell.getTextValue ) // Unsupported datatypes
            }
            workbook = workbook.updateCell( sheetIdx, colIdx, rowIdx, cell )
          }
        }
      }
    }

    workbook
  }

}
