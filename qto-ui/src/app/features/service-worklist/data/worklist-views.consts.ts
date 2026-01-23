export const SERVICE_WORKLIST_VIEWS = {
  orderReceipt: {
    name: 'Order Receipt',
    filterBuilder: {
      condition: {
        operator: 'AND',
        filters: [
          {
            propertyName: 'provisioner',
            operator: 'EQUAL',
            value: 'Unassigned'
          }, {
            propertyName: 'onHoldDate',
            operator: 'EQUAL',
            value: ''
          }
        ]
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'created'
    }
  },
  pendingOrderSubmission: {
    name: 'Pending Order Submission',
    filterBuilder: {
      filter: {
        propertyName: 'status',
        operator: 'ONEOF',
        value: 'Engineer Assigned,Site Survey Complete'
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'created'
    }
  },
  siteSurveysPending: {
    name: 'Site Surveys Pending',
    filterBuilder: {
      filter: {
        propertyName: 'status',
        operator: 'EQUALS',
        value: 'Site Survey'
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'siteSurveySubmit'
    }
  },
  constructionPending: {
    name: 'Construction Pending',
    filterBuilder: {
      filter: {
        propertyName: 'status',
        operator: 'EQUALS',
        value: 'Network Provider Construction'
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'networkProviderConstructionStartDate'
    }
  },

  pendingFOC: {
    name: 'Pending FOC',
    filterBuilder: {
      condition: {
        operator: 'OR',
        conditions: [
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'accessCircuitFOCDate',
                operator: 'EMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'ONEOF',
                value: 'DIA,Ethernet,Wavelength'
              }
            ]
          },
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'networkProviderFOC',
                operator: 'EMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'NOTEMPTY',
                value: ''
              }
            ]
          },
        ],
        filters: [{
          propertyName: 'status',
          operator: 'EQUAL',
          value: 'Network Provider Construction Complete'
        }]
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'providerOrderSubmitted'
    }
  },

  upcomingFOC: {
    name: 'Upcoming FOC',
    filterBuilder: {
      condition: {
        operator: 'OR',
        conditions: [
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'accessCircuitFOCDate',
                operator: 'ISBIGGER',
                value: '$today'
              }, {
                propertyName: 'networkProviderFOC',
                operator: 'ISEMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'ONEOF',
                value: 'DIA,Ethernet,Wavelength'
              }
            ]
          },
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'networkProviderFOC',
                operator: 'ISBIGGER',
                value: '$today'
              }, {
                propertyName: 'dataProvisioningComplete',
                operator: 'ISEMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'NOTEMPTY',
                value: ''
              }
            ]
          },
        ]
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'date??'
    }
  },

  pastDueFOC: {
    name: 'Past Due FOC',
    filterBuilder: {
      condition: {
        operator: 'OR',
        conditions: [
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'accessCircuitFOCDate',
                operator: 'ISLESS',
                value: '$today'
              }, {
                propertyName: 'networkProviderFOC',
                operator: 'ISEMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'ONEOF',
                value: 'DIA,Ethernet,Wavelength'
              }
            ]
          },
          {
            operator: 'AND',
            filters: [
              {
                propertyName: 'providerOrderSubmittedDate',
                operator: 'NOTEMPTY',
                value: ''
              }, {
                propertyName: 'networkProviderFOC',
                operator: 'ISLOWER',
                value: '$today'
              }, {
                propertyName: 'dataProvisioningComplete',
                operator: 'ISEMPTY',
                value: ''
              }, {
                propertyName: 'type',
                operator: 'NOTEMPTY',
                value: ''
              }
            ]
          },
        ]
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'providerOrderSubmitted'
    }
  },

  circuitComplete: {
    name: 'Circuit Complete',
    filterBuilder: {
      filter: {
        propertyName: 'status',
        operator: 'EQUAL',
        value: 'Circuit Complete'
      }
    },
    sortings: {
      sortDir: 'ASC',
      sortField: 'dataProvisioningComplete'
    }
  },
}


export interface Filter {
  propertyName: string;
  operator: string;
  value: any
}

export interface Condition {
  operator: any;
  filters?: Filter[]
  conditions?: Condition[]
}

export interface FilterBuilder {
  condition?: Condition
  filter?: Filter
}
