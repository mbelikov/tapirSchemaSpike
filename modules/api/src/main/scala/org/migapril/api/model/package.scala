package org.migapril.api

import eu.timepit.refined.W
import eu.timepit.refined.api.{ Refined, RefinedTypeOps }
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.types.string.NonEmptyString

package object model {
  // from https://stackoverflow.com/questions/3143070/javascript-regex-iso-datetime/3143231
  type Rgx = W.`"""^\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+([+-][0-2]\\d:[0-5]\\d|Z)$"""`.T

  type DateTimeString = String Refined MatchesRegex[Rgx]
  object DateTimeString extends RefinedTypeOps[DateTimeString, String]

  type ParameterMap = Map[NonEmptyString, ParameterValue]

  type DeviceId = NonEmptyString
}
