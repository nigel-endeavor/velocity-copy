import { Component } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { Chart, ChartTypeRegistry } from 'chart.js';
import { ActivationAttemptView } from 'src/app/models/activation-attempt-view-model';
import { ExcelService } from 'src/app/services/excel-service';
import { WithDestroy, untilComponentDestroyed } from 'src/app/utilities';
import { loadServiceIntervals, loadProviderAverages } from '../../ngrx/dashboards.actions';
import { getServiceIntervals, getAverages, getIsLoading } from '../../ngrx/dashboards.selectors';
import { arrangeDataInMonthlyOrder } from '../../utils/arrangeDataInMonthlyOrder';
import { groupByDate } from '../../utils/groupBytDate';

@Component({
  selector: 'app-dashboard-activations',
  templateUrl: './dashboard-activations.component.html',
  styleUrls: ['../../dashboards.component.scss', './dashboard-activations.component.scss']
})
@WithDestroy
export class DashboardActivationsComponent {

  activationEventLengthChart: any;
  activationEventLengthChartConfig = {
    chartJSConfig: {
      type: 'line',
      options: {
        responsive: true,
        plugins: {

        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Previous 6 Months'
            },
            grid: {
              display: false
            }
          },
          y: {
            title: {
              display: true,
              text: 'Average Activation Length (minutes)'
            },
            grid: {
              display: false
            }
          }
        }
      }
    },
    chartContainerId: 'activationEventLengthChart',
    chartType: 'line' as keyof ChartTypeRegistry,
    expanded: false
  };
  activationSuccessRateChart: any;
  activationSuccessRateChartConfig = {
    chartJSConfig: {
      type: 'line',
      options: {
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            title: {
              display: false
            }
          },
          scales: {
            x: {
              stacked: true,
              grid: {
                display: false
              }
            },
            y: {
              stacked: true,
              grid: {
                display: false
              }
            }
          }
        }
      }
    },
    chartContainerId: 'activationSuccessRateChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false
  };

  last6Months: string[];

  eventActivationMonthAve: [string, number][];
  monthlyAverages: [string, number][];
  attemptNumber: { month: string, values: any[] }

  public serviceIntervals$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getServiceIntervals)
  );

  public averages$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getAverages)
  );

  public isLoading$ = this.store.pipe(select(getIsLoading));

  constructor(
    public excelService: ExcelService,
    private store: Store
  ) { }

  activationAttemptData: ActivationAttemptView[];
  activationSuccessData: ActivationAttemptView[];

  ngOnInit(): void {
    this.last6Months = [];
    for (let i = 0; i < 6; i++) {
      let date = new Date();
      date.setDate(1);
      date.setMonth(date.getMonth() - i);
      let label = `${date.toLocaleString('default', { month: 'long' })}`;
      this.last6Months.unshift(label);
    }

    // allow to limit datasets  by 'Client Location info' and 'Client Location type' dropdowns
    this.serviceIntervals$.subscribe((response: ActivationAttemptView[]) => {
      this.activationSuccessData = response;
      let attemptNumber = this.groupBy('attemptNumber', response);
      let datasets = []
      for (let key in attemptNumber) {
        let monthlyAttempts = groupByDate('serviceComplete', attemptNumber[key])
        let value: any = [];
        for (let monthName in monthlyAttempts) {
          value.push([monthName, monthlyAttempts[monthName].length])
        }
        let monthlyArranged = arrangeDataInMonthlyOrder(value, this.last6Months);
        datasets.push(monthlyArranged[1])
      }
      let datasetsArr = []
      for (let i = 0; i < datasets.length; i++) {
        datasetsArr.push({
          label: 'Attempt Number ' + (i + 1),
          data: datasets[i],
        })
      }
      let data = {
        labels: this.last6Months,
        datasets: datasetsArr
      };
      this.destroyChart(this.activationSuccessRateChartConfig.chartContainerId);
      // @ts-ignore
      this.activationSuccessRateChart = new Chart(this.activationSuccessRateChartConfig.chartContainerId, {
        ...this.activationSuccessRateChartConfig.chartJSConfig,
        data: data,
      });

      //bar chart
      this.activationAttemptData = response;
      let eventActivationGroup = groupByDate('serviceComplete', response);
      this.eventActivationMonthAve = this.getServiceActivationMonthlyAve(eventActivationGroup);
      let eventArranged = arrangeDataInMonthlyOrder(this.eventActivationMonthAve, this.last6Months);
      let serviceActivationGroup = this.groupBy('serviceId', response);

      // gets the average serviceActivationInterval and latest date for each service Id. converts the date to a month name
      let serviceIntervals: any[][] = [];
      Object.keys(serviceActivationGroup).forEach(key => {
        let keyAverage: any[] = [];
        let latestDate: any[] = [];

        for (let i = 0; i < serviceActivationGroup[key].length; i++) {
          keyAverage.push(serviceActivationGroup[key][i].serviceActivationInterval);
          latestDate.push(serviceActivationGroup[key][i].serviceComplete);
          if (serviceActivationGroup[key][i].serviceComplete >= latestDate[0]) {
            latestDate.unshift(serviceActivationGroup[key][i].serviceComplete);
          }
          if (serviceActivationGroup[key].length == i + 1) {
            let average = keyAverage.reduce((total: number, interval: any) => total + interval, 0) / (i + 1);
            let date = new Date(latestDate[0]);
            let month = `${date.toLocaleString('default', { month: 'long' })}`;
            serviceIntervals.push([month, average]);
            latestDate = [];
          }
        }
      })
      //groups data by month
      let groupedByMonth = this.groupBy('0', serviceIntervals);
      this.monthlyAverages = [];
      // calculates monthly average
      for (let i = 0; i < Object.keys(groupedByMonth).length; i++) {
        let month = Object.keys(groupedByMonth)[i];
        let monthAve = groupedByMonth[Object.keys(groupedByMonth)[i]].reduce((total: number, interval: any) => total + interval[1], 0) / groupedByMonth[Object.keys(groupedByMonth)[i]].length;
        this.monthlyAverages.push([month, monthAve]);
      }
      // organizes data in monthly order
      let serviceArranged = arrangeDataInMonthlyOrder(this.monthlyAverages, this.last6Months);
      this.destroyChart('activationEventLengthChart');
      // @ts-ignore
      this.activationEventLengthChart = new Chart(this.activationEventLengthChartConfig.chartContainerId, {
          ...this.activationEventLengthChartConfig.chartJSConfig,
          data: {
            labels: this.last6Months,
            datasets: [
              {
                label: 'Event Duration',
                data: eventArranged[1],
                backgroundColor: '#3871C1'
              },
              {
                label: 'Site Cumulative Average',
                data: serviceArranged[1],
                backgroundColor: '#5DCADE'
              }
            ]
          },
        }
      );

    });

    this.store.dispatch(loadServiceIntervals());
    this.store.dispatch(loadProviderAverages({ monthArray: this.last6Months }));
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }

  private groupBy(fieldName: string, records: any[]): { [key: string]: any[] } {
    return records.filter(service => service[fieldName]).reduce((result, record) => {
      (result[record[fieldName]] = result[record[fieldName]] || []).push(record);
      return result;
    }, {});
  }

  private sortAndLimit(averages: { [key: string]: number }): [string, number][] {
    let sorted: [string, number][] = Object.entries(averages)
      .sort((a, b) => b[1] - a[1]);
    let sortedData = sorted.slice(0, 10);
    return sortedData;
  }


  private getServiceActivationMonthlyAve(groupMap: { [key: string]: ActivationAttemptView[] }): [string, number,][] {
    let averages: { [key: string]: number, } = {};
    Object.keys(groupMap).forEach(key => {
      averages[key] = groupMap[key].reduce((total: number, interval: ActivationAttemptView) => total + interval.serviceActivationInterval, 0) / groupMap[key].length;
    });
    return this.sortAndLimit(averages);
  }
}
