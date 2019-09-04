lazy val slickVersion = "3.3.0"
lazy val mariadbDriverVersion = "2.4.1"
lazy val logbackVersion = "1.2.3"

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import",
)

lazy val root = (project in file(".")).settings(
  name := "slick-test",
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "org.mariadb.jdbc" % "mariadb-java-client" % mariadbDriverVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
  ),
)

lazy val `slick-codegen` = (project in file("slick-codegen")).settings(
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick-codegen" % slickVersion,
    "org.mariadb.jdbc" % "mariadb-java-client" % mariadbDriverVersion,
  ),
)
