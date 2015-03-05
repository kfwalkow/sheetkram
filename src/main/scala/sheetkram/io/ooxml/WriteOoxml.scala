package sheetkram.io.ooxml

import org.apache.poi.ss.usermodel.{ Row => OoxmlRow }
import org.apache.poi.ss.usermodel.{ Sheet => OoxmlSheet }
import org.apache.poi.ss.usermodel.{ Workbook => OoxmlWorkbook }

import sheetkram.model.BooleanCell
import sheetkram.model.Cell
import sheetkram.model.DateCell
import sheetkram.model.EmptyCell
import sheetkram.model.NumberCell
import sheetkram.model.Row
import sheetkram.model.Sheet
import sheetkram.model.Workbook

trait WriteOoxml {

  def doWrite( workbook : Workbook, ooxmlWorkbook : OoxmlWorkbook ) : Unit = {

    workbook.sheets.foreach { sheet : Sheet =>
      val ooxmlSheet : OoxmlSheet = ooxmlWorkbook.createSheet( sheet.name )
      sheet.rows.zipWithIndex.foreach {
        case ( row : Row, rowIdx : Int ) =>
          val ooxmlRow : OoxmlRow = ooxmlSheet.createRow( rowIdx )
          row.cells.zipWithIndex.foreach {
            case ( cell : Cell, colIdx : Int ) =>
              cell match {
                case NumberCell( v )  => ooxmlRow.createCell( colIdx ).setCellValue( v.doubleValue )
                case DateCell( v )    => ooxmlRow.createCell( colIdx ).setCellValue( v )
                case BooleanCell( v ) => ooxmlRow.createCell( colIdx ).setCellValue( v )
                case EmptyCell()      => ooxmlRow.createCell( colIdx )
                case _                => ooxmlRow.createCell( colIdx ).setCellValue( cell.valueAsText )
              }
          }
      }
    }

  }

}
