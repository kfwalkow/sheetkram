package sheetkram.io.ooxml

import java.io.File
import java.io.FileInputStream

import org.apache.poi.hssf.usermodel.{ HSSFWorkbook => XlsWorkbook }
import org.apache.poi.ss.usermodel.{ Workbook => OoxmlWorkbook }

import sheetkram.io.ReadWorkbook
import sheetkram.model.Workbook

object ReadXls extends ReadWorkbook with ReadOoxml {

  def fromFile( file : File ) : Workbook = {
    val ooxmlWorkbook : OoxmlWorkbook = new XlsWorkbook( new FileInputStream( file ) )
    doRead( ooxmlWorkbook )
  }

}
