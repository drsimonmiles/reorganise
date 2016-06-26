enablePlugins(ScalaJSPlugin)

name := "reorganise"

version := "1.0"

scalaVersion := "2.11.8"

scalaJSStage in Global := FastOptStage

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.0"

libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.5.5"

libraryDependencies += "org.scala-js" %%% "scalajs-java-time" % "0.1.0"

skip in packageJSDependencies := false
