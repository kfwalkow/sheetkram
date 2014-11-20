package sheetkram.io

import java.io.File

import sheetkram.model.Workbook

trait WriteWorkbook {

  def toFile( workbook : Workbook, file : File ) : Unit

}
