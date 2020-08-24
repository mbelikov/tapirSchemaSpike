package org.migapril.api

import org.migapril.api.http.ClientError.{ BadRequest, InternalServerError, NotAuthorized, NotFound }
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{ oneOf, statusMapping, EndpointOutput }

package object http {
  val httpErrors: EndpointOutput.OneOf[ClientError, ClientError] = oneOf(
    statusMapping(StatusCode.InternalServerError, jsonBody[InternalServerError]),
    statusMapping(StatusCode.BadRequest, jsonBody[BadRequest]),
    statusMapping(StatusCode.NotFound, jsonBody[NotFound]),
    statusMapping(StatusCode.Unauthorized, jsonBody[NotAuthorized])
  )
}
