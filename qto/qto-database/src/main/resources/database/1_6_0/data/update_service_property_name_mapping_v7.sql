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
          "serviceBilledTo": {
            "value": "Service Billed To",
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
          },
          "accountPasscode": {
            "value": "Account Passcode",
            "type": "string"
          },
          "crossConnectId": {
            "value": "Cross Connect ID",
            "type": "string"
          },
          "crossConnectRoom": {
            "value": "Room",
            "type": "string"
          },
          "crossConnectRack": {
            "value": "Rack",
            "type": "string"
          },
          "crossConnectPort": {
            "value": "Port",
            "type": "string"
          },
          "crossConnectType": {
            "value": "XC Type",
            "type": "string"
          },
          "crossConnectDataCenterName": {
            "value": "Colo/Data Center Name",
            "type": "string"
          },
          "projectName": {
            "value": "Project Name",
            "type": "string"
          },
          "productType": {
            "value": "Product Type",
            "type": "string"
          },
          "portSpeed": {
            "value": "Port Speed",
            "type": "string"
          },
          "eaSpeed": {
            "value": "EA Speed",
            "type": "string"
          },
          "mtu": {
            "value": "MTU",
            "type": "string"
          },
          "mux": {
            "value": "MUX",
            "type": "string"
          },
          "vlanForEline": {
            "value": "CE-VLAN for E-Line",
            "type": "string"
          },
          "vlanTagging": {
            "value": "VLAN Tagging",
            "type": "string"
          },
          "vlanId": {
            "value": "VLAN ID",
            "type": "string"
          },
          "cableCategory": {
            "value": "Cable Category",
            "type": "string"
          },
          "cableShielding": {
            "value": "Cable Shielding",
            "type": "string"
          },
          "dataCenterName": {
            "value": "Colo/Data Center Name",
            "type": "string"
          },
          "providerCircuitId": {
            "value": "Provider Circuit ID",
            "type": "string"
          },
          "accessType": {
            "value": "Access Type",
            "type": "string"
          },
          "accessHours": {
            "value": "Access Hours",
            "type": "string"
          },
          "manned": {
            "value": "Manned",
            "type": "string"
          },
          "loaRequired": {
            "value": "LOA Required",
            "type": "string"
          },
          "handoffFiberMode": {
            "value": "Handoff Fiber Mode",
            "type": "string"
          },
          "handoffConnectorType": {
            "value": "Handoff Connector Type",
            "type": "string"
          },
          "clliCode": {
            "value": "CLLI Code",
            "type": "string"
          },
          "popClli": {
            "value": "POP CLLI",
            "type": "string"
          },
          "alternatePopClli": {
            "value": "Alternate POP CLLI",
            "type": "string"
          },
          "floor": {
            "value": "Floor",
            "type": "string"
          },
          "npaNxx": {
            "value": "NPA/NXX",
            "type": "string"
          },
          "cfa": {
            "value": "CFA",
            "type": "string"
          },
          "zCarrierCircuitId": {
            "value": "Z Provider Circuit ID",
            "type": "string"
          },
          "zDataCenterName": {
            "value": "Z Colo/Data Center Name",
            "type": "string"
          },
          "zProviderCircuitId": {
            "value": "Z Provider Circuit ID",
            "type": "string"
          },
          "zBuildingStatus": {
            "value": "Z Building Status",
            "type": "string"
          },
          "zAccessType": {
            "value": "Z Access Type",
            "type": "string"
          },
          "zInterfaceConnector": {
            "value": "Z Interface Connector",
            "type": "string"
          },
          "zAccessHours": {
            "value": "Z Access Hours",
            "type": "string"
          },
          "zManned": {
            "value": "Z Manned",
            "type": "string"
          },
          "zLoaRequired": {
            "value": "Z LOA Required",
            "type": "string"
          },
          "zInsideWiringRequired": {
            "value": "Z Inside Wiring Required",
            "type": "string"
          },
          "zHandoffMediaType": {
            "value": "Z Handoff Media Type",
            "type": "string"
          },
          "zHandoffFiberMode": {
            "value": "Z Handoff Fiber Mode",
            "type": "string"
          },
          "zHandoffConnectorType": {
            "value": "Z Handoff Connector Type",
            "type": "string"
          },
          "zClliCode": {
            "value": "Z CLLI Code",
            "type": "string"
          },
          "zPopClli": {
            "value": "Z POP CLLI",
            "type": "string"
          },
          "zAlternatePopClli": {
            "value": "Z Alternate POP CLLI",
            "type": "string"
          },
          "zFloor": {
            "value": "Z Floor",
            "type": "string"
          },
          "zNpaNxx": {
            "value": "Z NPA/NXX",
            "type": "string"
          },
          "zDmarc": {
            "value": "Z DMARC",
            "type": "string"
          },
          "zCfa": {
            "value": "Z CFA",
            "type": "string"
          },
          "zMrc": {
            "value": "Z End MRC",
            "type": "currency"
          },
          "zNrc": {
            "value": "Z End NRC",
            "type": "currency"
          },
          "trunkGroup": {
            "value": "Trunk Group",
            "type": "string"
          },
          "zAddress": {
            "value": {
              "address1": {
                "value": "Address Line 1",
                "type": "string"
              },
              "address2": {
                "value": "Address Line 2",
                "type": "string"
              },
              "city": {
                "value": "City",
                "type": "string"
              },
              "state": {
                "value": "State/Province/Region",
                "type": "string"
              },
              "postalCode": {
                "value": "ZIP/Postal Code",
                "type": "string"
              },
              "country": {
                "value": "Country",
                "type": "string"
              }
            },
            "type": "object"
          },
          "accessSpeed": {
            "value": "Access Speed",
            "type": "string"
          },
          "jobNumber": {
            "value": "Job Number",
            "type": "string"
          },
         "annualRecurringCost": {
            "value": "Annual Recurring Cost",
            "type": "currency"
          }
        }')