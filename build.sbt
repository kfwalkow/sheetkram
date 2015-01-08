organization := "sheetkram"

name := "sheetkram"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies += "org.jopendocument" % "jOpenDocument" % "1.3"

// src/main/scala only:
unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

// src/test/scala only:
unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil
