package sheetkram.io.ooxml

import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.math.BigDecimal.double2bigDecimal
import org.apache.poi.ss.usermodel.{ Cell => OoxmlCell }
import org.apache.poi.ss.usermodel.{ Row => OoxmlRow }
import org.apache.poi.ss.usermodel.{ Sheet => OoxmlSheet }
import org.apache.poi.ss.usermodel.{ Workbook => OoxmlWorkbook }
import sheetkram.model.BooleanCell
import sheetkram.model.Cell
import sheetkram.model.NumberCell
import sheetkram.model.TextCell
import sheetkram.model.Workbook
import sheetkram.model.EmptyCell

trait ReadOoxml {

  protected def doRead( ooxmlWorkbook : OoxmlWorkbook ) : Workbook = {
    var workbook : Workbook = Workbook()

    for ( sheetIdx : Int <- 0 to ( ooxmlWorkbook.getNumberOfSheets - 1 ) ) {
      val ooxmlSheet : OoxmlSheet = ooxmlWorkbook.getSheetAt( sheetIdx )
      workbook = workbook.appendSheet( ooxmlSheet.getSheetName )
      ooxmlSheet.asScala.foreach { ooxmlRow : OoxmlRow =>
        ooxmlRow.asScala.foreach { ooxmlCell : OoxmlCell =>
          val cell : Cell = ooxmlCell.getCellType match {
            case OoxmlCell.CELL_TYPE_STRING  => TextCell( ooxmlCell.getStringCellValue )
            case OoxmlCell.CELL_TYPE_NUMERIC => NumberCell( ooxmlCell.getNumericCellValue )
            case OoxmlCell.CELL_TYPE_BOOLEAN => BooleanCell( ooxmlCell.getBooleanCellValue )
            case OoxmlCell.CELL_TYPE_BLANK   => EmptyCell()
            case _                           => TextCell( ooxmlCell.getStringCellValue )
          }
          workbook = workbook.updateCell( sheetIdx, ooxmlCell.getColumnIndex, ooxmlCell.getRowIndex, cell )
        }
      }
    }

    workbook
  }

}
