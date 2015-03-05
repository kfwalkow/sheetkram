package sheetkram.io.odf

import java.io.File

import org.jopendocument.dom.spreadsheet.{ Sheet => OdfSheet }
import org.jopendocument.dom.spreadsheet.{ SpreadSheet => OdfWorkbook }

import javax.swing.table.DefaultTableModel
import sheetkram.io.WriteWorkbook
import sheetkram.model.BooleanCell
import sheetkram.model.Cell
import sheetkram.model.DateCell
import sheetkram.model.EmptyCell
import sheetkram.model.NumberCell
import sheetkram.model.Row
import sheetkram.model.Sheet
import sheetkram.model.Workbook

object WriteOdf extends WriteWorkbook {

  def toFile( workbook : Workbook, file : File ) : Unit = {

    val odfWorkbook : OdfWorkbook = OdfWorkbook.createEmpty( new DefaultTableModel() )

    workbook.sheets.zipWithIndex.foreach {
      case ( sheet : Sheet, sheetIdx : Int ) =>

        val isFirstSheet = sheetIdx == 0

        val odfSheet : OdfSheet = if ( isFirstSheet ) odfWorkbook.getFirstSheet() else odfWorkbook.addSheet( sheetIdx, sheet.name )

        if ( isFirstSheet )
          odfSheet.setName( sheet.name )

        odfSheet.ensureColumnCount( sheet.columns.size )
        odfSheet.ensureRowCount( sheet.rows.size )

        sheet.rows.zipWithIndex.foreach {
          case ( row : Row, rowIdx : Int ) =>
            row.cells.zipWithIndex.foreach {
              case ( cell : Cell, colIdx : Int ) =>
                cell match {
                  case NumberCell( v )  => odfSheet.setValueAt( v.bigDecimal, colIdx, rowIdx )
                  case DateCell( v )    => odfSheet.setValueAt( v, colIdx, rowIdx )
                  case BooleanCell( v ) => odfSheet.setValueAt( v, colIdx, rowIdx )
                  case EmptyCell()      =>
                  case _                => odfSheet.setValueAt( cell.valueAsText, colIdx, rowIdx )
                }
            }
        }

    }

    odfWorkbook.saveAs( file )

  }

}
