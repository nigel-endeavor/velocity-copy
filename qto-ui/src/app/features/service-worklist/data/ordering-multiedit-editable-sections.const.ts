import { InputTypes } from '../../multi-edit/enums/input-types.enum';

export const ORDERING_MULTIEDIT_EDITABLE_SECTIONS =  [
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
      name: 'Level of Effort',
        value: 'levelOfEffort',
        type: InputTypes.Select,
        select: {
          label: 'levelOfEffort',
          value: 'levelOfEffort'
        },
        result: 'string'
      }, {
        name: 'Location Type',
        value: 'clientLocationType',
        type: InputTypes.String
      }, {
        name: 'Location Info',
        value: 'clientLocationInfo',
        type: InputTypes.String
      }, {
        name: 'Service Note',
        value: 'serviceNote',
        type: InputTypes.String
      }, {
      //
      // Jeop form except end date
        name: 'Service Jeopardy',
        type: InputTypes.Form,
        fields: [
          {
            name: 'Start Date',
            value: 'jeopStartDate',
            type: InputTypes.DateTime,
            result: 'number',
            required: true
          }, {
            name: 'Description',
            value: 'jeopDescription',
            type: InputTypes.Select,
            result: 'string',
            select: {
              label: 'display',
              value: 'display'
            },
            required: true
          }, {
            name: 'Responsibility',
            value: 'jeopResponsibility',
            type: InputTypes.Select,
            result: 'string',
            select: {
              label: 'display',
              value: 'display'
            },
            required: true
          }, {
            name: 'Assigned To',
            value: 'jeopAssignedTo',
            type: InputTypes.Select,
            result: 'string',
            select: {
              label: 'displayName',
              value: 'displayName'
            }
          }, {
            name: 'Note',
            value: 'jeopNote',
            type: InputTypes.String
          }
        ]
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
        name: 'Network protocol',
        value: 'networkProtocol',
        type: InputTypes.Select,
        select: {
          label: 'display',
          value: 'value'
        },
        result: 'string'
      }, {
        name: 'Media Type',
        value: 'mediaType',
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
      }
    ]
  },
  {
    name: 'Milestones',
    fields: [
      {
        name: 'Site Survey Submit',
        value: 'SITE_SURVEY_SUBMIT',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Provider Order Submitted',
        value: 'PROVIDER_ORDER_SUBMITTED',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Network Provider FOC',
        value: 'NETWORK_PROVIDER_FOC',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Customer Requested Install',
        value: 'CUSTOMER_REQUESTED_INSTALL',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Change In Assignment',
        value: 'CHANGE_IN_ASSIGNMENT',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'QA Check Open',
        value: 'QA_CHECK_OPEN',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: '1st Vendor Invoice Date',
        value: 'FIRST_VENDOR_INVOICE',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Returned to Order Group',
        value: 'RETURNED_TO_ORDER_GROUP',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Returned to Sales',
        value: 'RETURNED_TO_SALES',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Billing Review Complete',
        value: 'BILLING_REVIEW_COMPLETE',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'On Hold',
        value: 'ON_HOLD',
        type: InputTypes.DateTime,
        result: 'number'
      }, {
        name: 'Close Jeops',
        value: 'closeJeops',
        type: InputTypes.Select,
        select: {
          label: 'value',
          value: 'value'
        },
        result: 'string'
      }, {
        name: 'Follow Up Date',
        value: 'followUpDate',
        type: InputTypes.DateTime,
        result: 'number'
      }
    ]
  }
]

export const MILESTONE_CODES = [
  'SITE_SURVEY_SUBMIT',
  'PROVIDER_ORDER_SUBMITTED',
  'NETWORK_PROVIDER_FOC',
  'CUSTOMER_REQUESTED_INSTALL',
  'CHANGE_IN_ASSIGNMENT',
  'QA_CHECK_OPEN',
  'FIRST_VENDOR_INVOICE',
  'RETURNED_TO_ORDER_GROUP',
  'RETURNED_TO_SALES',
  'BILLING_REVIEW_COMPLETE',
  'ON_HOLD'
]
