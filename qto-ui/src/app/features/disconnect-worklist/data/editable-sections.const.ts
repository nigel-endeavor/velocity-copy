import { InputTypes } from '../../../features/multi-edit/enums/input-types.enum';

export const EDITABLE_SECTIONS =  [
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
      }
    ]
  },
  {
    name: 'General',
    fields: [
      {
        name: 'Disconnect Reason',
        value: 'disconnectReason',
        type: InputTypes.Select,
        result: 'string',
        select: {
          label: 'display',
          value: 'value'
        }
      }, {
        name: 'Disconnect Order #',
        value: 'providerOrderNum',
        type: InputTypes.String
      }, {
        name: 'Early Termination Fee',
        value: 'earlyTerminationFee',
        type: InputTypes.Number
      }, {
        name: 'Service Note',
        value: 'serviceNote',
        type: InputTypes.String
      }
    ]
  },
  {
    name: 'Milestones',
    fields: [
      {
        name: 'Customer Requested Disconnect',
        value: 'CUSTOMER_REQUESTED_DISCONNECT',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Provider Order Submitted',
        value: 'PROVIDER_ORDER_SUBMITTED',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Network Provider FOC',
        value: 'NETWORK_PROVIDER_FOC',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Provider Disconnect Complete',
        value: 'PROVIDER_DISCONNECT_COMPLETE',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Billing Review Complete',
        value: 'BILLING_REVIEW_COMPLETE',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }
    ]
  }
]

export const MILESTONE_CODES = [
  'CUSTOMER_REQUESTED_DISCONNECT',
  'PROVIDER_ORDER_SUBMITTED',
  'NETWORK_PROVIDER_FOC',
  'PROVIDER_DISCONNECT_COMPLETE',
  'BILLING_REVIEW_COMPLETE'
]
