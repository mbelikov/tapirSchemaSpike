import sbt._

object Dependencies {

  object Versions {
    val zio               = "1.0.1"
    val zioInteropVersion = "2.1.4.0"
    val zioLogging        = "0.4.0"

    val http4s           = "0.21.6"
    val tapir            = "0.16.15"
    val sttp             = "2.2.5"

    val pureConfig         = "0.13.0"
    val pureConfigRefinedV = "0.9.15"

    // json
    val circe       = "0.13.0"
    val circeExtras = "0.13.0"

    val refined    = "0.9.14"
    val quicklens  = "1.6.1"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.11.0"
    val logback          = "1.2.3"

    val scalaCheck    = "1.14.3"
    val scalaTest     = "3.1.2"
    val scalaTestPlus = "3.1.2.0"
  }

  object Libraries {
    private val testWithIt = "it, test"

    val zio                   = "dev.zio" %% "zio" % Versions.zio
    val zioStreams            = "dev.zio" %% "zio-streams" % Versions.zio
    val zioMacros             = "dev.zio" %% "zio-macros" % Versions.zio
    val zioCatsInterop        = "dev.zio" %% "zio-interop-cats" % Versions.zioInteropVersion
    val zioLogging            = "dev.zio" %% "zio-logging" % Versions.zioLogging
    val zioLoggingSlf4j       = "dev.zio" %% "zio-logging-slf4j" % Versions.zioLogging
    val zioAll: Seq[ModuleID] = Seq(zio, zioStreams, zioMacros, zioCatsInterop, zioLogging, zioLoggingSlf4j)

    val zioTest                   = "dev.zio" %% "zio-test" % Versions.zio //% testWithIt
    val zioTestSbt                = "dev.zio" %% "zio-test-sbt" % Versions.zio //% testWithIt
    val zioTestAll: Seq[ModuleID] = Seq(zioTest, zioTestSbt)

    val http4sServer          = "org.http4s"        %% "http4s-blaze-server"       % Versions.http4s
    val http4sDsl             = "org.http4s"        %% "http4s-dsl"                % Versions.http4s
    val http4sClient          = "org.http4s"        %% "http4s-blaze-client"       % Versions.http4s
    val http4sCirce           = "org.http4s"        %% "http4s-circe"              % Versions.http4s
    val http4sPrometheus      = "org.http4s"        %% "http4s-prometheus-metrics" % Versions.http4s
    val http4sAll: Seq[ModuleID] =
      Seq(http4sServer, http4sDsl, http4sClient, http4sCirce, http4sPrometheus)

    val circeCore          = "io.circe" %% "circe-core"           % Versions.circe
    val circeGeneric       = "io.circe" %% "circe-generic"        % Versions.circe
    val circeParser        = "io.circe" %% "circe-parser"         % Versions.circe
    val circeRefined       = "io.circe" %% "circe-refined"        % Versions.circe
    val circeGenericExtras = "io.circe" %% "circe-generic-extras" % Versions.circeExtras
    val circeShapes        = "io.circe" %% "circe-shapes"         % Versions.circe
    val circeAll: Seq[ModuleID] =
      Seq(circeCore, circeGeneric, circeParser, circeRefined, circeGenericExtras, circeShapes)

    val tapirCore             = "com.softwaremill.sttp.tapir" %% "tapir-core"               % Versions.tapir
    val tapirDocs             = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"       % Versions.tapir
    val tapirOpenApiCirce     = "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe"      % Versions.tapir
    val tapirOpenApiCirceYaml = "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % Versions.tapir
    val tapirSttp             = "com.softwaremill.sttp.tapir" %% "tapir-sttp-client"        % Versions.tapir
    val tapirHttp4s           = "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"      % Versions.tapir
    val tapirZio              = "com.softwaremill.sttp.tapir" %% "tapir-zio"                % Versions.tapir
    val tapirZioHttp4sServer  = "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server"  % Versions.tapir
    val tapirCirce            = "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % Versions.tapir
    val tapirSwagger          = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-http4s"  % Versions.tapir
    val tapirRefined          = "com.softwaremill.sttp.tapir" %% "tapir-refined"            % Versions.tapir
    val tapirCats             = "com.softwaremill.sttp.tapir" %% "tapir-cats"               % Versions.tapir
    val tapirAll: Seq[ModuleID] =
      Seq(
        tapirCore,
        tapirDocs,
        tapirOpenApiCirce,
        tapirOpenApiCirceYaml,
        tapirSttp,
        tapirHttp4s,
        tapirZio,
        tapirZioHttp4sServer,
        tapirCirce,
        tapirSwagger,
        tapirRefined,
        tapirCats
      )

    val sttpZioClient          = "com.softwaremill.sttp.client" %% "async-http-client-backend-zio" % Versions.sttp
    val sttpClientCirce        = "com.softwaremill.sttp.client" %% "circe" % Versions.sttp
    val sttpAll: Seq[ModuleID] = Seq(sttpZioClient, sttpClientCirce)

    val pureconfig                   = "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig
    val pureconfigRefined            = "eu.timepit" %% "refined-pureconfig" % Versions.pureConfigRefinedV
    val allPureconfig: Seq[ModuleID] = Seq(pureconfig, pureconfigRefined)

    val refinedCore = "eu.timepit" %% "refined"      % Versions.refined
    val refinedCats = "eu.timepit" %% "refined-cats" % Versions.refined

    val quicklens = "com.softwaremill.quicklens" %% "quicklens" % Versions.quicklens

    // Compiler plugins
    val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
    val kindProjector    = "org.typelevel" % "kind-projector"      % Versions.kindProjector

    // Runtime
    val logbackCore               = "ch.qos.logback" % "logback-core" % Versions.logback
    val logbackLogback            = "ch.qos.logback" % "logback-classic" % Versions.logback
    val logbackAll: Seq[ModuleID] = Seq(logbackCore, logbackLogback)

    // Test
    val scalaCheck    = "org.scalacheck"    %% "scalacheck"      % Versions.scalaCheck
    val scalaTest     = "org.scalatest"     %% "scalatest"       % Versions.scalaTest
    val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-14" % Versions.scalaTestPlus
  }

}
