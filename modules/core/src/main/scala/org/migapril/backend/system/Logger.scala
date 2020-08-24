package org.migapril.backend.system

import zio.URLayer
import zio.clock.Clock
import zio.console.Console
import zio.logging.Logging

object Logger {
  val logFormat                                  = "[%s] %s"
  val test: URLayer[Console with Clock, Logging] = Logging.console()
}
