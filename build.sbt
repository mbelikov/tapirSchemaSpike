import Dependencies._
import sbt.Keys.libraryDependencies

lazy val scala212               = "2.12.12"
lazy val scala213               = "2.13.3"
lazy val supportedScalaVersions = List(scala212, scala213)

val alias: Seq[sbt.Def.Setting[_]] =
  addCommandAlias(
    "build",
    "all test scalafmtCheck packagedArtifacts publish docker:stage"
  ) ++ addCommandAlias("integrationTest", "it:test") ++
      addCommandAlias("prepare", "fmt; fix")

ThisBuild / scalaVersion := scala213
ThisBuild / organization := "org.migapril"
ThisBuild / organizationName := "none"
ThisBuild / resolvers ++= Seq(DefaultMavenRepository)
ThisBuild / pomIncludeRepository := { _ =>
  false
}
ThisBuild / publishMavenStyle := true
ThisBuild / updateOptions := updateOptions.value.withGigahorse(false)

lazy val sharedLibraries = Seq(
  compilerPlugin(Libraries.kindProjector cross CrossVersion.full),
  compilerPlugin(Libraries.betterMonadicFor)
)
lazy val libraries = Seq(
    Libraries.refinedCats,
    Libraries.quicklens
  ) ++
      Libraries.zioAll ++
      Libraries.zioTestAll ++
      Libraries.http4sAll ++
      Libraries.circeAll ++
      Libraries.tapirAll ++
      Libraries.sttpAll ++
      Libraries.allPureconfig

lazy val root = (project in file("."))
  .settings(name := "tapir-schema-spike")
  .aggregate(core, tests)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    scalacOptions += "-Ymacro-annotations", // scala 2.13 only
    publish / skip := true,
    coverage / aggregate := false,
    Compile / mainClass := Some("org.migapril.backend.Main")
  )
  .enablePlugins(UniversalPlugin)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)

lazy val tests =
  (project in file("modules/tests"))
  //    .configs(IntegrationTest)
    .settings(
      name := "tapir-schema-spike-test",
      alias,
      crossScalaVersions := supportedScalaVersions,
      scalafmtOnCompile := true,
      Defaults.itSettings,
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
      scalacOptions += "-Ymacro-annotations", // scala 2.13 only
      testOptions += Tests.Argument(
            TestFrameworks.ScalaTest,
            "-u",
            "modules/tests/target/junit-report"
          ),
      libraryDependencies ++= sharedLibraries,
      libraryDependencies ++= Seq(
            Libraries.scalaCheck,
            Libraries.scalaTest,
            Libraries.scalaTestPlus
          ) ++ Libraries.zioTestAll,
      coverageEnabled := true
    )
    .dependsOn(api, core)

lazy val coverage = (project in file("modules/coverage"))
  .configs(Test)
  .settings(
    name := "tapir-schema-spike-test-coverage",
    crossScalaVersions := supportedScalaVersions,
    //    scalacOptions += "-Ymacro-annotations",   // scala 2.13 only
//    Defaults.itSettings,
    testOptions += Tests.Argument(
          TestFrameworks.ScalaTest,
          "-u",
          "modules/tests/target/junit-report"
        ),
    libraryDependencies ++= sharedLibraries,
    libraryDependencies ++= Seq(
          Libraries.scalaCheck,
          Libraries.scalaTest,
          Libraries.scalaTestPlus
        )
  )
  .dependsOn(core)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "tapir-schema-spike-core",
    alias,
    crossScalaVersions := supportedScalaVersions,
    scalacOptions += "-Ymacro-annotations", // scala 2.13 only
    scalafmtOnCompile := true,
    Defaults.itSettings,
    makeBatScripts := Seq(),
    libraryDependencies ++= sharedLibraries
          ++ libraries
          ++ Libraries.logbackAll
  )
  .configs(IntegrationTest extend Test)
  .dependsOn(api)

lazy val api = (project in file("modules/api"))
  .settings(
    name := "tapir-schema-spike-api",
    alias,
    crossScalaVersions := supportedScalaVersions,
    scalacOptions += "-Ymacro-annotations", // scala 2.13 only
    scalafmtOnCompile := true,
    Defaults.itSettings,
    makeBatScripts := Seq(),
    libraryDependencies ++=
        sharedLibraries
          ++ libraries
          ++ Libraries.logbackAll
  )
  .configs(IntegrationTest extend Test)
