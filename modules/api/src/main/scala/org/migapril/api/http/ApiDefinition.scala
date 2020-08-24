package org.migapril.api.http

import eu.timepit.refined.types.string.NonEmptyString
import org.migapril.api.model.DeviceConfigurationInfo
import sttp.tapir.Endpoint
import sttp.tapir.codec.refined._
import sttp.tapir.docs.openapi._
import sttp.tapir.json.circe._
import sttp.tapir.ztapir._

object apiDefinitionObject extends ApiDefinition

trait ApiDefinition extends jsonCodecs {
  object pathNames {
    val configurations = "deviceConfigurations"
  }

  protected val securedEndpoint =
    endpoint
      .in(header[Option[String]]("Authorization"))
      .errorOut(httpErrors)

  protected val getConfiguration
      : Endpoint[(Option[String], NonEmptyString), ClientError, DeviceConfigurationInfo, Nothing] =
    securedEndpoint.get
      .in(pathNames.configurations / path[NonEmptyString]("id"))
      .out(jsonBody[DeviceConfigurationInfo])

  val docs =
    List(getConfiguration).toOpenAPI("Device Configuration demo API", "1.0.0")
}
