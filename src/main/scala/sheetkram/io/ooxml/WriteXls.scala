package sheetkram.io.ooxml

import java.io.File
import java.io.FileOutputStream

import org.apache.poi.hssf.usermodel.{ HSSFWorkbook => XlsWorkbook }

import sheetkram.io.WriteWorkbook
import sheetkram.model.Workbook

object WriteXls extends WriteWorkbook with WriteOoxml {

  def toFile( workbook : Workbook, file : File ) : Unit = {
    val xlsWorkbook : XlsWorkbook = new XlsWorkbook()
    doWrite( workbook, xlsWorkbook )
    xlsWorkbook.write( new FileOutputStream( file ) ) // TODO: ressource handling
  }

}
