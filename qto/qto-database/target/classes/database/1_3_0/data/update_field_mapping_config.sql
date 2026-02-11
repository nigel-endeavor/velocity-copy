DELETE from config_property where config_property_key = 'SERVICE_PROPERTY_NAME_MAPPING';
INSERT INTO config_property (config_property_key, config_property_value)
VALUES
    ('SERVICE_PROPERTY_NAME_MAPPING',
 '{
    "clientServiceId": {
        "value": "Client Service ID",
        "type": "string"
    },
    "quoteSolutionId": {
        "value": "Quote Solution ID",
        "type": "string"
    },
    "subStatus": {
        "value": "Service Sub-Status",
        "type": "string"
    },
    "circuitOwner": {
        "value": "Circuit Owner",
        "type": "string"
    },
    "carrier": {
        "value": "Carrier",
        "type": "string"
    },
    "subProductType": {
        "value": "Sub Product Type",
        "type": "string"
    },
    "orderType": {
        "value": "Order Type",
        "type": "string"
    },
    "followUpDate": {
        "value": "Follow Up Date",
        "type": "date"
    },
    "clientLocationInfo": {
        "value": "Client Location Info",
        "type": "string"
    },
    "clientLocationType": {
        "value": "Client Location Type",
        "type": "string"
    },
    "description": {
        "value": "Description",
        "type": "string"
    },
    "contractTerm": {
        "value": "Contract Term",
        "type": "string"
    },
    "contractSignedDate": {
        "value": "Contract Signed Date",
        "type": "date"
    },
    "circuitTermEndDate": {
        "value": "Circuit Term End Date",
        "type": "date"
    },
    "poNumber": {
        "value": "PO Number",
        "type": "string"
    },
    "mrc": {
        "value": "MRC",
        "type": "currency"
    },
    "nrc": {
        "value": "NRC",
        "type": "currency"
    },
    "hasIcb": {
        "value": "ICB",
        "type": "string"
    },
    "icb": {
        "value": "ICB Unit Cost",
        "type": "currency"
    },
    "hasOsp": {
        "value": "OSP Construction",
        "type": "string"
    },
    "osp": {
        "value": "OSP Construction Cost",
        "type": "currency"
    },
    "burstableSpeedCost": {
        "value": "Burstable Speed Cost",
        "type": "currency"
    },
    "carrierSiteAccountNum": {
        "value": "Carrier Site Account #",
        "type": "string"
    },
    "carrierOrderNum": {
        "value": "Carrier Order #",
        "type": "string"
    },
    "downloadSpeed": {
        "value": "Download Speed",
        "type": "string"
    },
    "uploadSpeed": {
        "value": "Upload Speed",
        "type": "string"
    },
    "speed": {
        "value": "Speed",
        "type": "string"
    },
    "carrierCircuitId": {
        "value": "Carrier Circuit ID",
        "type": "string"
    },
    "ospConstIntervalEst": {
        "value": "OSP Construction Interval Estimate",
        "type": "string"
    },
    "mediaType": {
        "value": "Media Type",
        "type": "string"
    },
    "netStatus": {
        "value": "Net Status",
        "type": "string"
    },
    "buildingStatus": {
        "value": "Building Status",
        "type": "string"
    },
    "additionalIpBlockRequired": {
        "value": "Additional IP Block Required",
        "type": "string"
    },
    "insideWiringRequired": {
        "value": "Inside Wiring Required",
        "type": "string"
    },
    "dmarc": {
        "value": "DMARC",
        "type": "string"
    },
    "locationHours": {
        "value": "Location Hours",
        "type": "string"
    },
    "productInstallInterval": {
        "value": "Product Install Interval",
        "type": "string"
    },
    "modemMake": {
        "value": "Modem",
        "type": "string"
    },
    "macAddress": {
        "value": "Modem MAC",
        "type": "string"
    },
    "networkProtocol": {
        "value": "Network Protocol",
        "type": "string"
    },
    "pppoeUsername": {
        "value": "PPPoE Username",
        "type": "string"
    },
    "pppoePassword": {
        "value": "PPPoE Password",
        "type": "string"
    },
    "ipFormat": {
        "value": "IP Format",
        "type": "string"
    },
    "additionalIpBlock": {
        "value": "WAN Block",
        "type": "string"
    },
    "wanIps": {
        "value": "WAN IPs",
        "type": "string"
    },
    "wanGateway": {
        "value": "WAN Gateway",
        "type": "string"
    },
    "wanSubnet": {
        "value": "WAN Subnet",
        "type": "string"
    },
    "lanBlock": {
        "value": "LAN Block",
        "type": "string"
    },
    "lanIps": {
        "value": "LAN IPs",
        "type": "string"
    },
    "lanGateway": {
        "value": "LAN Gateway",
        "type": "string"
    },
    "lanSubnet": {
        "value": "LAN Subnet",
        "type": "string"
    },
    "dns1": {
        "value": "DNS1",
        "type": "string"
    },
    "dns2": {
        "value": "DNS2",
        "type": "string"
    },
    "lastMileCarrier": {
        "value": "Last Mile Carrier",
        "type": "string"
    },
    "expediteOrder": {
        "value": "Expedite Order",
        "type": "string"
    },
    "newAccessCircuitId": {
        "value": "Access Circuit ID",
        "type": "string"
    },
    "burstableSpeed": {
        "value": "Burstable Speed",
        "type": "string"
    },
    "interfaceConnector": {
        "value": "Interface Connector",
        "type": "string"
    },
    "carrierActivationMethod": {
        "value": "Carrier Order Activation Method",
        "type": "string"
    },
    "activationLink": {
        "value": "Activation Link",
        "type": "string"
    },
    "activationPhone": {
        "value": "Activation Phone",
        "type": "string"
    },
    "napNxx": {
        "value": "NPA/NXX",
        "type": "string"
    },
    "routerSerialNumber": {
        "value": "Router",
        "type": "string"
    },
    "routerMacAddress": {
        "value": "Router MAC",
        "type": "string"
    },
    "circuitPriority": {
        "value": "Circuit Priority",
        "type": "string"
    },
    "carrierSiteAccountNum": {
        "value": "Carrier Site Account #",
        "type": "string"
    },
    "uid": {
        "value": "UID",
        "type": "string"
    },
    "iccid": {
        "value": "ICCID",
        "type": "string"
    },
    "imei": {
        "value": "IMEI",
        "type": "string"
    },
    "mdn": {
        "value": "Mobile Device Number (MDN)",
        "type": "string"
    },
    "apn": {
        "value": "APN",
        "type": "string"
    },
    "ratePlan": {
        "value": "Rate Plan",
        "type": "string"
    },
    "rsrp": {
        "value": "RSRP",
        "type": "string"
    },
    "rsrq": {
        "value": "RSRQ",
        "type": "string"
    },
    "sinr": {
        "value": "SINR",
        "type": "string"
    },
    "rssi": {
        "value": "RSSI",
        "type": "string"
    },
    "replace4g5g": {
        "value": "Replace 4G/5G with Broadband/DIA",
        "type": "string"
    },
    "serialNumber": {
        "value": "Serial Number",
        "type": "string"
    },
    "publishedTn": {
        "value": "Published TN",
        "type": "string"
    },
    "temporaryTn": {
        "value": "Temporary TN",
        "type": "string"
    },
    "numberOfHandsets": {
        "value": "Number of Handsets",
        "type": "string"
    }
}')
