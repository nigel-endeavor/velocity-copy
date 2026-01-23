delete from qto.email_template;

INSERT INTO qto.email_template (name, subject, body, template_type, tenant_id) select 'Site Completion Notice', '$clientLocationId is Complete', 'Good Day,
$clientLocationId has been successfully completed as a $clientLocationInfo on the $carrier circuit.

$locationName
$address1
$city, $stateProvince $postalCode

The Manager on Duty has signed the completion form for this store.

See below for additional site information:

Network Complete Date: $networkCompleteDate
VoIP Complete Date: $voipCompleteDate

Tested Download Speed (Mbps): $testedDownloadSpeed
Tested Upload Speed (Mbps): $testedUploadSpeed

Signal/RSRP: $signalRsrp
SINR/RSRQ: $sinrRsrq
Backup Download Speed (Mbps): $backupDownloadSpeed
Backup Upload Speed (Mbps): $backupUploadSpeed

Location Downtime for Cutover: $locationDowntimeForCutover

Replace Cradlepoint with Broadband/DIA: $replace4g5gWithBroadbandDia

Total Appointment Time: $totalAppointmentTime', 'ACTIVATION', tenant_id from v_tenant;
INSERT INTO qto.email_template (name, subject, body, template_type, tenant_id) select 'Site Aborted Notice', '$clientLocationId has been aborted', 'Good Day,

$clientLocationId was aborted today.

$locationName
$address1
$city, $stateProvince $postalCode

See below for additional site information:

Network Complete Date: $networkCompleteDate
VoIP Complete Date: $voipCompleteDate

Tested Download Speed (Mbps): $testedDownloadSpeed
Tested Upload Speed (Mbps): $testedUploadSpeed

Signal/RSRP: $signalRsrp
SINR/RSRQ: $sinrRsrq
Backup Download Speed (Mbps): $backupDownloadSpeed
Backup Upload Speed (Mbps): $backupUploadSpeed

Location Downtime for Cutover: $locationDowntimeForCutover

Replace Cradlepoint with Broadband/DIA: $replace4g5gWithBroadbandDia

Total Appointment Time: $totalAppointmentTime', 'ACTIVATION', tenant_id from v_tenant;
INSERT INTO qto.email_template (name, subject, body, template_type, tenant_id) select 'Site Completion Notice - Followup Required', '$clientLocationId is Complete - Follow-up Required', 'Good Day,
$clientLocationId has been successfully completed as a $clientLocationInfo however follow-up is required. Please see the comments for customer below for further detail.

$locationName
$address1
$city, $stateProvince $postalCode

The Manager on Duty has signed the completion form for this store.

See below for additional site information:

Network Complete Date: $networkCompleteDate
VoIP Complete Date: $voipCompleteDate

Tested Download Speed (Mbps): $testedDownloadSpeed
Tested Upload Speed (Mbps): $testedUploadSpeed

Signal/RSRP: $signalRsrp
SINR/RSRQ: $sinrRsrq
Backup Download Speed (Mbps): $backupDownloadSpeed
Backup Upload Speed (Mbps): $backupUploadSpeed

Location Downtime for Cutover: $locationDowntimeForCutover

Replace Cradlepoint with Broadband/DIA: $replace4g5gWithBroadbandDia

Total Appointment Time: $totalAppointmentTime', 'ACTIVATION', tenant_id from v_tenant;
INSERT INTO qto.email_template (name, subject, body, template_type, tenant_id) select 'Site Partial Completion Notice', '$clientLocationId has been partially completed', 'Good Day,
$clientLocationId has been successfully completed as a $clientLocationInfo however follow-up is required. Please see the comments for customer below for further detail.

$locationName
$address1
$city, $stateProvince $postalCode

The Manager on Duty has signed the completion form for this store.

See below for additional site information:

Network Complete Date: $networkCompleteDate
VoIP Complete Date: $voipCompleteDate

Tested Download Speed (Mbps): $testedDownloadSpeed
Tested Upload Speed (Mbps): $testedUploadSpeed

Signal/RSRP: $signalRsrp
SINR/RSRQ: $sinrRsrq
Backup Download Speed (Mbps): $backupDownloadSpeed
Backup Upload Speed (Mbps): $backupUploadSpeed

Location Downtime for Cutover: $locationDowntimeForCutover

Replace Cradlepoint with Broadband/DIA: $replace4g5gWithBroadbandDia

Total Appointment Time: $totalAppointmentTime', 'ACTIVATION', tenant_id from v_tenant;