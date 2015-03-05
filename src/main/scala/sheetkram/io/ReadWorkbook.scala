package sheetkram.io

import java.io.File

import sheetkram.model.Workbook

trait ReadWorkbook {

  def fromFile( file : File ) : Workbook

}
