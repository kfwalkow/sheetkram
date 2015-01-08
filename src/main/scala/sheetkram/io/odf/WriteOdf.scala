package sheetkram.io.odf

import java.io.File
import org.jopendocument.dom.spreadsheet.{ SpreadSheet => OdfWorkbook }
import org.jopendocument.dom.spreadsheet.{ Sheet => OdfSheet }
import org.jopendocument.dom.spreadsheet.{ Cell => OdfCell }
import sheetkram.io.WriteWorkbook
import sheetkram.model.Workbook
import org.jopendocument.dom.OOUtils
import javax.swing.table.TableModel
import javax.swing.table.DefaultTableModel
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel
import sheetkram.model.Sheet
import sheetkram.model.Cell
import sheetkram.model.TextCell
import sheetkram.model.NumberCell
import sheetkram.model.DateCell

object WriteOdf extends WriteWorkbook {

  def toFile( workbook : Workbook, file : File ) : Unit = {

    val odfWorkbook : OdfWorkbook = OdfWorkbook.createEmpty( new DefaultTableModel() )

    workbook.allSheets.foreach { sheet : Sheet =>

      val isFirstSheet = sheet.position.idx == 0

      val odfSheet : OdfSheet =
        if ( isFirstSheet ) odfWorkbook.getFirstSheet() else odfWorkbook.addSheet( sheet.position.idx, sheet.name )

      if ( isFirstSheet )
        odfSheet.setName( sheet.name )

      odfSheet.ensureColumnCount( sheet.allColumns.keySet.max + 1 )
      odfSheet.ensureRowCount( sheet.allRows.keySet.max + 1 )

      sheet.allRows.values.foreach { row : Map[ Int, Cell ] =>
        row.values.foreach { cell : Cell =>
          cell match {
            case NumberCell( _, _ ) => cell.valueAsNumber.foreach { v =>
              odfSheet.setValueAt( v.bigDecimal, cell.position.colIdx, cell.position.rowIdx )
            }
            case DateCell( _, _ ) => cell.valueAsDate.foreach { v =>
              odfSheet.setValueAt( v, cell.position.colIdx, cell.position.rowIdx )
            }
            case _ => odfSheet.setValueAt( cell.valueAsText, cell.position.colIdx, cell.position.rowIdx )
          }
        }
      }

    }

    odfWorkbook.saveAs( file )

  }

}
