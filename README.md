# Task definition

## Write service abnormal-values-recognizer

### checks a current pulse value for patient id against a provided range

range is provided by some local implementation of the interface RangeProviderClient <br>
the interface RangeProviderClient inside the project abnormal-values-recognizer has method getRange(long patientId) returning record Range with minimal and maximal allowed values

### all abnormal values should be pushed in a Data Stream as AnormalPulseValue

AbnormalPulseValue is the Record with patientId, value and Range fields

### Local invocation using local debugger of VSCode for testing purpose

# Task definition

 ## Write service pulse-values-reducer
 ### computes the average value for a predefined number of values received from the stream pulse_values and sending to another stream average_pulse_values 
 ## Uses the same record SensorData
 ### Uses the same LatestValuesSaverImpl like for jump-pulse-recognizer (just another methods) for saving last pulse values for each patientId
 ### Uses the same MiddlewareDataStream
  put jump data inside new created DynamoDB table "average-pulse-values" as data stream <br>
  for local debugging the TestStream as  MiddlewareDataStream should be used 
 ### Adds two additional resources inside template.yaml
 PulseValuesReducerFunction (lambda function) <br>
 AveragePulseValuesStream (DynamoDB table)
 ### Adds configuration for local debugging

# Task definition

## Writing LatestValuesSaverMap class implementing LatestValuesSaver interface
### Simple implementation based on HashMap in the project last-values-saver-impl
## Update jump-pulse-recognizer
### Uses LatestValuesSaver 
### Introduces environment variable FACTOR with default value 0.5
### Recognizes whether there a jump by comparing a last value with the current one
Math.abs(last - current) / (float)last >= factor is criteria of a jump
### Uses MiddlewareDataStream
 put jump data inside new created DynamoDB table "jump-pulse-values" as data stream

# Task definition

## Update pulse-values-imitator
### Distribution of the pulse values for each patient should be more less real with consideration following properties
JUMP_PROB - probability of jump, for example 10 percents<br>
MIN_JUMP_PERCENT - minimal percent of jump, for example: 10; means 10% jump, for last value = 100, jump will be  10<br>
MAX_JUMP_PERCENT - maximal percent of jump, for example: 100; means 100% jump, for last value = 100, jump will be  100<br>
JUMP_POSITIVE_PROB - probability of positive jump, for example: 70% means: if last value 100, jump 20% - with probability 70% new value will be 120, with probability 30% - 80
### Testing
introduce constant PATIENT_ID_FOR_INFO_LOGGING, for example 3 <br>
with logger level 'info' to log all generated pulse values for PATIENT_ID_FOR_INFO_LOGGING patient id <br>
DEFAULT_N_PACKETS = 1000 <br>
DEFAULT_N_PATIENTS = 10 <br>
see the distibution of all values for the specified patient id
### Logging
All sent sensor data should be logged under level 'finest'
Only jump data should be logged under level 'debug'
All pulse values for the PATIENT_ID_FOR_INFO_LOGGING should be logged under level 'info'
## Update pulse-values-receiver
### Introduce logging
All received sensor data objects (JSON's) should be logged under level 'finest'
Sensor data containing values greater than 220 should be logged under level 'warning'
Sensor data containing values greater than 230 should be logged under level 'severe'