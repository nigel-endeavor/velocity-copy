import ChartDataLabels from "chartjs-plugin-datalabels";

export type DashboardFilterKeys
  = 'masterCustomerSearchCriteria'
  | 'endCustomerSearchCriteria'
  | 'providersSearchCriteria'
  ;

export type DashboardOptionsKeys
  = 'masterCustomers'
  | 'endCustomers'
  | 'providers'
  ;

export type TabFilterKeys
  = 'wip'
  | 'providers'
  | 'financials'
  | 'inventory'
  ;

export const ProvidersMap = {
  'PROVIDER_ORDER_SUBMITTED_TO_DATA_PROVISIONING_COMPLETE': 'installIntervals',
  'SITE_SURVEY_SUBMIT_TO_SITE_SURVEY_COMPLETE': 'surveyIntervals'
}


export const DOUGHNUT_CHART_CONFIG = {
  plugins: [ChartDataLabels],
  type: 'doughnut',
  data: {},
  options: {
    plugins: {
      title: {
        display: false,
      },
      legend: {
        display: false
      },
      datalabels: {
        color: '#FFFFFF',
        font: {
          family: 'Roboto'
        }
      }
    }
  }
}
export const BAR_CHART_CONFIG = {
  plugins: [ChartDataLabels],
  type: 'bar',
  data: {},
  options: {
    indexAxis: 'y',
    scales: {
      x: {
        border: {
          display: false
        },
        grid: {
          display: false
        }
      },
      y: {
        border: {
          display: false
        },
        grid: {
          display: false,
        },

      }
    },
    maintainAspectRatio: false,
    plugins: {
      title: {
        display: false,
      },
      legend: {
        display: false
      },
      datalabels: {
        color: '#FFFFFF',
        font: {
          family: 'Roboto'
        }
      }
    }
  }
}
// Default chart.js colors
export const DEFAULT_COLORS = [
  '#36a2eb',
  '#ff6384',
  '#ff9f40',
  // '#ffcd56', hard to read white labels
  '#4bc0c0',
  '#9966ff', 
  '#c9cbcf'
]
