package sheetkram.io.ooxml

import java.io.File

import org.apache.poi.ss.usermodel.{ Workbook => OoxmlWorkbook }
import org.apache.poi.xssf.usermodel.{ XSSFWorkbook => XlsxWorkbook }

import sheetkram.io.ReadWorkbook
import sheetkram.model.Workbook

object ReadXlsx extends ReadWorkbook with ReadOoxml {

  def fromFile( file : File ) : Workbook = {
    val ooxmlWorkbook : OoxmlWorkbook = new XlsxWorkbook( file )
    doRead( ooxmlWorkbook )
  }

}
