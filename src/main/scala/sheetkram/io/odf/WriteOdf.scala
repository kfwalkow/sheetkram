package sheetkram.io.odf

import java.io.File

import org.jopendocument.dom.spreadsheet.{ SpreadSheet => OdfWorkbook }
import org.jopendocument.dom.spreadsheet.{ Sheet => OdfSheet }
import org.jopendocument.dom.spreadsheet.{ Cell => OdfCell }

import sheetkram.io.WriteWorkbook
import sheetkram.model.Workbook

object WriteOdf extends WriteWorkbook {

  def toFile( workbook : Workbook, file : File ) : Unit = {
    //
    //    OdfWorkbook.create
  }

}
