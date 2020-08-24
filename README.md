# tapirSchemaSpike
spike demonstrating tapir schema generation (issues?)

the project structure has been inspired by the https://github.com/saraiva132/zio-cats-backend.

this project should demonstrate an usage of the tapir + http4s to derive automatically an OpenAPI schema and showcase
possible issues corresponding to it.
Having an API model like this:
```scala
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyString

sealed trait ParameterValue
object ParameterValue {
  case class StringValue(value: String) extends ParameterValue
  case class IntValue(value: Int) extends ParameterValue
  case class ListValue(value: List[ParameterValue]) extends ParameterValue
}

type ParameterMap = Map[NonEmptyString, ParameterValue]

case class DeviceConfiguration(
    version: NonNegInt,
    createdAt: DateTimeString,
    parameters: ParameterMap
)

case class DeviceConfigurationInfo(
    defaultConfiguration: DeviceConfiguration,
    lastConfiguration: DeviceConfiguration
)

```

the tapir generates the following weird schema interpreting the 
- ```Map[NonEmptyString, ParameterValue]``` as an array
- ```List[ParameterValue]``` as an 'oneOf' ```Cons``` and ```Nil```:
```json
...
    DeviceConfigurationInfo:
      required:
      - defaultConfiguration
      - lastConfiguration
      type: object
      properties:
        defaultConfiguration:
          $ref: '#/components/schemas/DeviceConfiguration'
        lastConfiguration:
          $ref: '#/components/schemas/DeviceConfiguration'
    DeviceConfiguration:
      required:
      - version
      - createdAt
      type: object
      properties:
        version:
          type: integer
        createdAt:
          type: string
          pattern: ^\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+([+-][0-2]\d:[0-5]\d|Z)$
        parameters:
          type: array
          items:
            $ref: '#/components/schemas/ParameterValue'
    ParameterValue:
      oneOf:
      - $ref: '#/components/schemas/IntValue'
      - $ref: '#/components/schemas/ListValue'
      - $ref: '#/components/schemas/StringValue'
    IntValue:
      required:
      - value
      type: object
      properties:
        value:
          type: integer
    ListValue:
      required:
      - value
      type: object
      properties:
        value:
          $ref: '#/components/schemas/List_ParameterValue'
    List_ParameterValue:
      oneOf:
      - $ref: '#/components/schemas/::_ParameterValue'
      - $ref: '#/components/schemas/Nil'
    ::_ParameterValue:
      required:
      - head
      - next
...
```

whereas the circe does the job well (get example for http://localhost:9090/deviceConfigurations/1):

[responseExample.json](modules/tests/src/test/resources/responseExample.json)
```json
{
...
    "parameters": {
      "intParameter": {
        "IntValue": {
          "value": 10
        }
      },
      "stringParameter": {
        "StringValue": {
          "value": "sponge bob"
        }
      },
      "listParameter": {
        "ListValue": {
          "value": [
            {
              "IntValue": {
                "value": 1
              }
            },
            {
              "IntValue": {
                "value": 2
              }
            },
            {
              "IntValue": {
                "value": 3
              }
            }
          ]
        }
      }
    }
...
}
```