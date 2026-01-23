import { InputTypes } from '../../../features/multi-edit/enums/input-types.enum';

export const EDITABLE_SECTIONS =  [
  {
    name: 'Dispute General',
    fields: [
      {
        name: 'Dispute Type',
        value: 'disputeType',
        type: InputTypes.NativeSelect,
        select: {
          label: 'display',
          value: 'value',
          searchBy: 'value'
        },
        result: 'string'
      }, 
      {
        name: 'Invoice #',
        value: 'invoiceNum',
        type: InputTypes.String
      }, 
      {
        name: 'Vendor Tracking #',
        value: 'vendorTrackingNum',
        type: InputTypes.String
      },
      {
        name: 'Dispute Note',
        value: 'disputeNote',
        type: InputTypes.String
      }
    ]
  },
  {
    name: 'Disputed Values',
    fields: [
      {
        name: 'Dispute MRC',
        value: 'amountDisputedMrc',
        type: InputTypes.Number
      }, {
        name: 'Disputed NRC',
        value: 'amountDisputedNrc',
        type: InputTypes.Number
      }
    ]
  },
  {
    name: 'Milestones',
    fields: [
      {
        name: 'Open Date',
        value: 'openDate',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Follow Up Date',
        value: 'disputeFollowUpDate',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Credit Recognized Date',
        value: 'creditRecognizedDate',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Billing Review Complete Date',
        value: 'billingReviewCompleteDate',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Closed Date',
        value: 'disputeClosedDate',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      }
    ]
  },
  {
    name: 'Assignments',
    fields: [
      {
        name: 'Dispute Assignment',
        value: 'disputeAssignment',
        type: InputTypes.NativeSelect,
        select: {
          label: 'displayName',
          value: 'displayName',
          searchBy: 'displayName'
        },
        result: 'string'
      }
    ]
  }
]

export const MILESTONE_CODES = []
