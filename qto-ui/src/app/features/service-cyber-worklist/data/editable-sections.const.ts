import { InputTypes } from "../../multi-edit/enums/input-types.enum";

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
      }, {
        name: 'i90 Project Manager',
        value: 'i90ProjectManager',
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
    name: 'Provider',
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
        name: 'Customer Requested Install',
        value: 'CUSTOMER_REQUESTED_INSTALL',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Technical Data Gathering Form Sent',
        value: 'TECH_DATA_GATHERING_FORM_SENT',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Technical Data Gathering Meeting Scheduled',
        value: 'TECH_DATA_GATHERING_MEETING_SCHEDULED',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Technical Data Gathering Meeting Completed',
        value: 'TECH_DATA_GATHERING_MEETING_COMPLETED',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Email USM Anywhere Template Requirements',
        value: 'EMAIL_USM_TEMPLATE_REQ',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Inventory Assignment Verified',
        value: 'INVENTORY_ASSIGNMENT_VERIFIED',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'New USM Anywhere Server Build',
        value: 'NEW_USM_SERVER_BUILD',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Implementation QA',
        value: 'IMPLEMENTATION_QA',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Verify Assets in SIEM DB',
        value: 'VERIFY_ASSETS_SIEM_DB',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Verify Logging Data Sources',
        value: 'VERIFY_LOGGING_DATA_SOURCES',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Schedule Vulnerability Scans',
        value: 'SCHEDULE_VULNERABILITY_SCANS',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Bulk Alarm Tuning Phase',
        value: 'BULK_ALARM_TUNING_PHASE',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'SIEM Event Filtering',
        value: 'SIEM_EVENT_FILTERING',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Filters Built for Reports',
        value: 'FILTERS_BUILT_FOR_REPORTS',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Default Alarm Rule Additions',
        value: 'DEFAULT_ALARM_RULE_ADDITIONS',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Custom Alarm Rule Additions',
        value: 'CUSTOM_ALARM_RULE_ADDITIONS',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Forward Alarms to USM Central',
        value: 'FORWARD_ALARMS_TO_USM_CENTRAL',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      {
        name: 'Forward Alarms to D3/SOC Live',
        value: 'FORWARD_ALARMS_TO_D3_SOC_LIVE',
        //2023-05-11T15:29:34.000+00:00
        type: InputTypes.DateTime,
        result: 'number'
      },
      // {
      //   name: 'Host List Provided by Client',
      //   value: 'HOST_LIST_PROVIDED_BY_CLIENT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Halcyon Package Given to Client',
      //   value: 'HALCYON_PACKAGE_GIVEN_TO_CLIENT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Halcyon Deployed to Hosts',
      //   value: 'HALCYON_DEPLOYED_TO_HOSTS',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Devops Notified of Halcyon Addition',
      //   value: 'DEVOPS_NOTIFIED_OF_HALCYON_ADDITION',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Halcyon API Token Added to D3',
      //   value: 'HALCYON_API_TOKEN_ADDED_TO_D3',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'D3 Connection Verified',
      //   value: 'D3_CONNECTION_VERIFIED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'End Learning Mode',
      //   value: 'END_LEARNING_MODE',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Test Email Sent to Client',
      //   value: 'TEST_EMAIL_SENT_TO_CLIENT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Deploy Consulting Tenant',
      //   value: 'DEPLOY_CONSULTING_TENANT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Deploy Vulnerability Scans',
      //   value: 'DEPLOY_VULNERABILITY_SCANS',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Provided Customer with Report',
      //   value: 'PROVIDED_CUSTOMER_WITH_REPORT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Discuss Future Cyrisma Management',
      //   value: 'DISCUSS_FUTURE_CYRISMA_MANAGEMENT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Review Existing CA and MFA Policies',
      //   value: 'REVIEW_EXISTING_CA_AND_MFA_POLICIES',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Sign In Policies Enabled',
      //   value: 'SIGN_IN_POLICIES_ENABLED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Conditional Access Policy Verification',
      //   value: 'CONDITIONAL_ACCESS_POLICY_VERIFICATION',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Geographic Restrictions Enabled',
      //   value: 'GEOGRAPHIC_RESTRICTIONS_ENABLED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Device Compliance Enabled',
      //   value: 'DEVICE_COMPLIANCE_ENABLED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Password Reset Enabled for Self Service',
      //   value: 'PASSWORD_RESET_ENABLED_FOR_SELF_SERVICE',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Break Glass Account Configured',
      //   value: 'BREAK_GLASS_ACCOUNT_CONFIGURED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'PIM Enablement',
      //   value: 'PIM_ENABLEMENT',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      // {
      //   name: 'Implementation Verified',
      //   value: 'IMPLEMENTATION_VERIFIED',
      //   //2023-05-11T15:29:34.000+00:00
      //   type: InputTypes.DateTime,
      //   result: 'number'
      // },
      {
        name: 'Follow Up Date',
        value: 'followUpDate',
        type: InputTypes.DateTime,
        result: 'number'
      }
    ]
  }
]

export const MILESTONE_CODES = [
  'CUSTOMER_REQUESTED_INSTALL',
  'TECH_DATA_GATHERING_FORM_SENT',
  'TECH_DATA_GATHERING_MEETING_SCHEDULED',
  'TECH_DATA_GATHERING_MEETING_COMPLETED',
  'EMAIL_USM_TEMPLATE_REQ',
  'INVENTORY_ASSIGNMENT_VERIFIED',
  'NEW_USM_SERVER_BUILD',
  'IMPLEMENTATION_QA',
  'VERIFY_ASSETS_SIEM_DB',
  'VERIFY_LOGGING_DATA_SOURCES',
  'SCHEDULE_VULNERABILITY_SCANS',
  'BULK_ALARM_TUNING_PHASE',
  'SIEM_EVENT_FILTERING',
  'FILTERS_BUILT_FOR_REPORTS',
  'DEFAULT_ALARM_RULE_ADDITIONS',
  'CUSTOM_ALARM_RULE_ADDITIONS',
  'FORWARD_ALARMS_TO_USM_CENTRAL',
  'FORWARD_ALARMS_TO_D3_SOC_LIVE',
  // 'HOST_LIST_PROVIDED_BY_CLIENT',
  // 'HALCYON_PACKAGE_GIVEN_TO_CLIENT',
  // 'HALCYON_DEPLOYED_TO_HOSTS',
  // 'DEVOPS_NOTIFIED_OF_HALCYON_ADDITION',
  // 'HALCYON_API_TOKEN_ADDED_TO_D3',
  // 'D3_CONNECTION_VERIFIED',
  // 'END_LEARNING_MODE',
  // 'TEST_EMAIL_SENT_TO_CLIENT',
  // 'DEPLOY_CONSULTING_TENANT',
  // 'DEPLOY_VULNERABILITY_SCANS',
  // 'PROVIDED_CUSTOMER_WITH_REPORT',

  // 'DISCUSS_FUTURE_CYRISMA_MANAGEMENT',
  // 'REVIEW_EXISTING_CA_AND_MFA_POLICIES',
  // 'SIGN_IN_POLICIES_ENABLED',
  // 'CONDITIONAL_ACCESS_POLICY_VERIFICATION',
  // 'GEOGRAPHIC_RESTRICTIONS_ENABLED',
  // 'DEVICE_COMPLIANCE_ENABLED',
  // 'PASSWORD_RESET_ENABLED_FOR_SELF_SERVICE',
  // 'BREAK_GLASS_ACCOUNT_CONFIGURED',
  // 'PIM_ENABLEMENT',
  // 'IMPLEMENTATION_VERIFIED'
]
