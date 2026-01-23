export const ACTIVATION_WORKLIST_COMPONENTS = [
  { name: 'Client Location ID', propertyName: 'clientLocationId', type: 'text', hidden: false, filterable: true },
  { name: 'Client Service ID', propertyName: 'clientServiceId', type: 'text', hidden: false, filterable: true },
  { name: 'Scheduled Attempt Status', propertyName: 'scheduledAttemptStatus', type: 'text', hidden: false, filterable: true },
  { name: 'Master Customer', propertyName: 'parentCompanyName', filterType: 'select', type: 'text', hidden: false, filterable: true },
  { name: 'Internal Tech Assigned', propertyName: 'internalTechAssigned', type: 'text', hidden: false, filterable: true },
  { name: 'Activation Scheduled Date', propertyName: 'scheduledCheckInTime', type: 'date', hidden: false, filterable: true },
  { name: 'Scheduled Check In Time', propertyName: 'scheduledCheckInTime', type: 'time', hidden: false, filterable: true },
  { name: 'Actual Check In Time', propertyName: 'fieldTechCheckIn', type: 'time', hidden: false, filterable: true },
  { name: 'Last Update By', propertyName: 'lastUpdateBy', type: 'text', hidden: false, filterable: true },
  { name: 'Location Type', propertyName: 'clientLocationType', type: 'text', hidden: false, filterable: true },
  { name: 'Location Info', propertyName: 'clientLocationInfo', type: 'text', hidden: false, filterable: true },
  { name: 'TTU Equivalent', propertyName: 'ttuEquivalent', type: 'text', hidden: false, filterable: true },
]

export const META_DATA_ORDER = [
  {
    name: 'Schedule Date Confirmed',
    color: '#3871c1',
  }, {
    name: 'In Progress',
    color: '#5dcade',
  }, {
    name: 'Complete',
    color: '#2a9041'
  }, {
    name: 'Partial Complete - Pending Re-Schedule',
    color: '#fb9d19'
  }, {
    name: 'Incomplete - Pending Re-Schedule',
    color: '#d73f49'
  }, {
    name: 'Cancelled',
    color: '#565656'
  }
]
