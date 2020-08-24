package org.migapril.backend.system

import eu.timepit.refined.types.string.NonEmptyString
import eu.timepit.refined.auto._

case class Context(
    user: Option[NonEmptyString],
    authToken: Option[NonEmptyString]
)

object Context {
  val test: Context = Context(
    user = Some("babyboss"),
    authToken = Some("....")
  )
}
