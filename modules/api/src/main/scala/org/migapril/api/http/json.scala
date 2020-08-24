package org.migapril.api.http

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.refined._
import io.circe.{ Decoder, Encoder }
import org.migapril.api.model.{ Device, DeviceConfiguration, DeviceConfigurationInfo, ParameterValue }

trait jsonCodecs {
//  implicit val xx Decoder: Decoder[] = deriveDecoder[]
//  implicit val xx Encoder: Encoder[] = deriveEncoder[]
  implicit val parameterValueDecoder: Decoder[ParameterValue] = deriveDecoder[ParameterValue]
  implicit val parameterValueEncoder: Encoder[ParameterValue] = deriveEncoder[ParameterValue]

//  DeviceConfiguration
  implicit val deviceConfigurationDecoder: Decoder[DeviceConfiguration] = deriveDecoder[DeviceConfiguration]
  implicit val deviceConfigurationEncoder: Encoder[DeviceConfiguration] = deriveEncoder[DeviceConfiguration]

//  DeviceConfigurationInfo
  implicit val deviceConfigurationInfoDecoder: Decoder[DeviceConfigurationInfo] = deriveDecoder[DeviceConfigurationInfo]
  implicit val deviceConfigurationInfoEncoder: Encoder[DeviceConfigurationInfo] = deriveEncoder[DeviceConfigurationInfo]

//  Device
  implicit val deviceDecoder: Decoder[Device] = deriveDecoder[Device]
  implicit val deviceEncoder: Encoder[Device] = deriveEncoder[Device]
}

object jsonOps extends jsonCodecs
