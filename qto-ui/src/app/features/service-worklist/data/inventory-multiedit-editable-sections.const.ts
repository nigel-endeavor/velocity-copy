import { InputTypes } from '../../multi-edit/enums/input-types.enum';

export const INVENTORY_MULTIEDIT_EDITABLE_SECTIONS =  [
  {
    name: 'Assignments',
    fields: [
      {
        name: 'Provisioner',
        value: 'provisioner',
        type: InputTypes.NativeSelect,
        select: {
          label: 'displayName',
          value: 'id'
        },
        result: 'number'
      }, {
        name: 'i90 Project Manager',
        value: 'vertekProjectManager',
        type: InputTypes.NativeSelect,
        select: {
          label: 'displayName',
          value: 'id'
        },
        result: 'number'
      }, {
        name: 'Client PM',
        value: 'clientProjectManager',
        type: InputTypes.Select,
        select: {
          label: 'display',
          value: 'display'
        },
        result: 'number'
      }, {
        name: 'QA Manager',
        value: 'qaManager',
        type: InputTypes.NativeSelect,
        select: {
          label: 'displayName',
          value: 'id'
        },
        result: 'number'
      }
    ]
  },
  {
    name: 'General',
    fields: [
      {
        name: 'Master Customer',
        value: 'masterCustomer',
        type: InputTypes.NativeSelect,
        select: {
          label: 'name',
          value: 'id'
        }
      }, {
        name: 'End Customer',
        value: 'endCustomer',
        type: InputTypes.NativeSelect,
        select: {
          label: 'name',
          value: 'id'
        }
      }, {
        name: 'Service Type',
        value: 'clientServiceType',
        type: InputTypes.String
      }, {
        name: 'Service Info',
        value: 'clientServiceInfo',
        type: InputTypes.String
      }, {
        name: 'Location Type',
        value: 'clientLocationType',
        type: InputTypes.String
      }, {
        name: 'Location Info',
        value: 'clientLocationInfo',
        type: InputTypes.String
      }
    ]
  },
  {
    name: 'General - Service Billing',
    fields: [
      {
        name: 'Upload Speed',
        value: 'uploadSpeed',
        type: InputTypes.Select,
        select: {
          label: 'display',
          value: 'value'
        },
        result: 'string'
      }, {
        name: 'Download Speed',
        value: 'downloadSpeed',
        type: InputTypes.Select,
        select: {
          label: 'display',
          value: 'value'
        },
        result: 'string'
      }, {
        name: 'MRC',
        value: 'mrc',
        type: InputTypes.Number
      }, {
        name: 'NRC',
        value: 'nrc',
        type: InputTypes.Number
      }, {
        name: 'Contract Signed Date',
        value: 'contractSignedDate',
        type: InputTypes.DateTime
      }, {
        name: 'Contract Term',
        value: 'contractTerm',
        type: InputTypes.Select,
        select: {
          label: 'display',
          value: 'display'
        },
        result: 'string'
      }
    ]
  },
  {
    name: 'Network Provider',
    fields: [
      {
        name: 'Provider',
        value: 'provider',
        type: InputTypes.NativeSelect,
        select: {
          label: 'display',
          value: 'value',
          searchBy: 'value'
        },
        result: 'string'
      }, {
        name: 'Account number / BAN',
        value: 'accountNumber', 
        type: InputTypes.String
      },{
        name: 'Summary Bill',
        value: 'summaryBill',
        type: InputTypes.String
      }, {
        name: 'Provider Circuit ID',
        value: 'providerCircuitId',
        type: InputTypes.String
      }
    ]
  }
]
