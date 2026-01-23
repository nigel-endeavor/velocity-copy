import { Component, Input, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { WipServiceView } from '../../../../models/wip-service-view.model';
import { ExcelService } from '../../../../services/excel-service';
import { WipService } from '../../../../services/wip.service';
import Chart from 'chart.js/auto';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { ChartTypeRegistry } from 'chart.js';
import { ChartConfig } from '../../interfaces/chart-config.interface';
import { CHART_COLOR_PALETTE, untilComponentDestroyed, WithDestroy } from '../../../../utilities';
import { select, Store } from '@ngrx/store';
import {
  getIncrementalNetwork,
  getIsLoading,
  getMonthlySpend,
  getTabFiltersByKey,
  getUnbillableNetworkExpenseAccrual
} from '../../ngrx/dashboards.selectors';
import {
  loadIncrementalNetworkSpend,
  loadMonthlySpend,
  loadUnbillableNetworkExpenseAccrual,
  updateTabFilters
} from '../../ngrx/dashboards.actions';
import { map } from 'rxjs';


@Component({
  selector: 'app-dashboard-financials',
  templateUrl: './dashboard-financials.component.html',
  styleUrls: ['../../dashboards.component.scss', './dashboard-financials.component.scss']
})
@WithDestroy
export class DashboardFinancialsComponent implements OnInit {

  colorpalette = CHART_COLOR_PALETTE;
  limitOptions: string[] = ['10', '20'];
  selectedLimit: string = this.limitOptions[0];
  providerOpts: string[];
  totalUnbillableNetworkExpenseAccrual: string = '$0';
  incrementalNetworkSpendSelectedProviders: string;
  unbillableNetworkExpenseAccrualSelectedProviders: string;

  monthlySpendChart: any;
  monthlySpendChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'monthlySpendChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };
  incrementalNetworkSpendChart: any;
  incrementalNetworkSpendChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'incrementalNetworkSpendChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };
  unbillableNetworkExpenseAccrualChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'unbillableNetworkExpenseAccrualChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };

  wipServiceViews: WipServiceView[];
  monthlySpendData: WipServiceView[];
  incrementalNetworkSpendData: WipServiceView[];
  unbillableNetworkExpenseAccrualData: WipServiceView[];

  showMonthlySpend: boolean = true;
  showIncrementalNetworkSpend: boolean = true;
  showUnbillableNetworkExpenseAccrual: boolean = true;

  public monthlySpend$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getMonthlySpend)
  );

  public unbillableNetworkExpenseAccrual$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getUnbillableNetworkExpenseAccrual)
  );

  public incrementalNetwork$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getIncrementalNetwork)
  );

  public filters$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getTabFiltersByKey('financials')),
    map(item => {
      // @ts-ignore
      this.selectedLimit = item.monthlySpend?.limit;
      // @ts-ignore
      this.unbillableNetworkExpenseAccrualSelectedProviders = item.unbillableNetwork?.selectedProviders;
      // @ts-ignore
      this.incrementalNetworkSpendSelectedProviders = item.incrementalNetwork?.selectedProviders;
      return {
        monthlySpend: {
          // @ts-ignore
          limit: item.monthlySpend?.limit,
        },
        unbillableNetwork: {
          // @ts-ignore
          selectedProviders: item.unbillableNetwork?.selectedProviders,
        },
        incrementalNetwork: {
          // @ts-ignore
          selectedProviders: item.incrementalNetwork?.selectedProviders,
        }
      }
    })
  );

  public isLoading$ = this.store.pipe(select(getIsLoading));

  constructor(
    private wipService: WipService,
    private store: Store,
    public excelService: ExcelService,
    private lookupValueService: LookupValueService
  ) { }
  ngOnInit(): void {
    this.lookupValueService.getValues('PROVIDER', null).subscribe((values: string[]) => { this.providerOpts = values; });
    this.monthlySpend$.subscribe((wipServiceViews: WipServiceView[]) => {
      this.monthlySpendData = wipServiceViews;
      let providerGroups = this.groupBy('provider', wipServiceViews);
      let providerSpend = this.getMonthlySpend(providerGroups);
      this.createBarChart(true, this.monthlySpendChartConfig, providerSpend, undefined, 'y');
    });
    this.unbillableNetworkExpenseAccrual$.subscribe((wipServiceViews: WipServiceView[]) => {
      this.unbillableNetworkExpenseAccrualData = wipServiceViews;
      if (this.unbillableNetworkExpenseAccrualSelectedProviders != null && this.unbillableNetworkExpenseAccrualSelectedProviders.length > 0) {
        this.unbillableNetworkExpenseAccrualData = this.unbillableNetworkExpenseAccrualData.filter(s => this.unbillableNetworkExpenseAccrualSelectedProviders.includes(s.provider));
      }
      //get earliest dateprovisioningcompletedate from unbillable accruals
      let dates : Date[] = this.unbillableNetworkExpenseAccrualData.map(s => s.dataProvisioningCompleteDate);
      let earliestDate = dates.sort()[0];
      //loop through all months from earliest date to now
      let months: [string, number][] = [];
      let currentDate = new Date(earliestDate);
      currentDate.setDate(1);
      let now = new Date();
      while (currentDate.getFullYear() < now.getFullYear() || currentDate.getMonth() < now.getMonth()) {
        let monthYear = `${currentDate.getMonth() + 1}-${currentDate.getFullYear()}`;
        let label = `${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`;
        let monthTotal = this.unbillableNetworkExpenseAccrualData.filter(s => {
          let date = new Date(s.dataProvisioningCompleteDate);
          return `${date.getMonth() + 1}-${date.getFullYear()}` == monthYear;
        }).reduce((accumulator, service) => {
          //get number of days between now and dateprovisioningcompletedate
          let dataProvisioningCompleteDate = new Date(service.dataProvisioningCompleteDate);
          let days = Math.round((now.getTime() - dataProvisioningCompleteDate.getTime()) / (1000 * 3600 * 24));
          //divide service mrc by average number of days in month 30
          let dailyMrc = service.serviceMrc / 30;
          //multiply by number of days between current date and dateprovisioningcompletedate
          let currentExpense = dailyMrc * days;
          return accumulator + currentExpense;
        }, 0);
        months.push([label, monthTotal]);
        currentDate.setMonth(currentDate.getMonth() + 1);
      }
      let total = months.reduce((accumulator, month) => {
        return accumulator + month[1];
      }, 0);
      let formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
      });
      this.totalUnbillableNetworkExpenseAccrual = formatter.format(total);
      this.createBarChart(false, this.unbillableNetworkExpenseAccrualChartConfig, months);
    });
    this.incrementalNetwork$.subscribe((wipServiceViews: WipServiceView[]) => {
      this.incrementalNetworkSpendData = wipServiceViews;
      if (this.incrementalNetworkSpendSelectedProviders != null && this.incrementalNetworkSpendSelectedProviders.length > 0) {
        this.incrementalNetworkSpendData = this.incrementalNetworkSpendData.filter(s => this.incrementalNetworkSpendSelectedProviders.includes(s.provider));
      }

      //for the past 12 months, aggregate service mrc by month.
      let last12Months: [string, number][] = [];
      for (let i = 0; i < 12; i++) {
        let date = new Date();
        date.setDate(1);
        date.setMonth(date.getMonth() - i);
        let monthYear = `${date.getMonth() + 1}-${date.getFullYear()}`;
        let label = `${date.toLocaleString('default', { month: 'long' })} ${date.getFullYear()}`;

        let monthTotal = this.incrementalNetworkSpendData.filter(s => {
          let date = new Date(s.dataProvisioningCompleteDate);
          return `${date.getMonth() + 1}-${date.getFullYear()}` == monthYear;
        }).reduce((accumulator, service) => {
          return accumulator + (service.serviceMrc ? service.serviceMrc : 0);
        }, 0);

        last12Months.push([label, monthTotal]);
      }
      //reverse the array so the months are in order
      last12Months.reverse();

      this.createLineChart(this.incrementalNetworkSpendChartConfig, last12Months);
    });

    this.createMonthlySpendChart(10);
    this.createIncrementalNetworkSpend();
    this.createUnbillableNetworkExpenseAccrual();
  }

  createMonthlySpendChart(limit: number) {
    this.store.dispatch(updateTabFilters({
      tabKey: 'financials', key: 'monthlySpend', value: { limit }
    }))
    this.store.dispatch(loadMonthlySpend());
  }

  createIncrementalNetworkSpend() {
    this.store.dispatch(loadIncrementalNetworkSpend())
  }

  createUnbillableNetworkExpenseAccrual() {
    this.store.dispatch(loadUnbillableNetworkExpenseAccrual())
  }

  onSelectedProvidersChanged(providers: string, chart: string) {
    if (chart === 'incremental_network_spend') {
      this.store.dispatch(updateTabFilters({
        tabKey: 'financials', key: 'incrementalNetwork', value: { selectedProviders: providers }
      }))
      this.createIncrementalNetworkSpend();
    } else {
      this.store.dispatch(updateTabFilters({
        tabKey: 'financials', key: 'unbillableNetwork', value: { selectedProviders: providers }
      }))
      this.createUnbillableNetworkExpenseAccrual();
    }
  }

  private getMonthlySpend(groupMap: { [key: string]: WipServiceView[] }): [string, number][] {
    let sums: { [key: string]: number } = {};
    Object.keys(groupMap).forEach(key => {
      sums[key] = groupMap[key].reduce(function (accumulator, service) {
        return accumulator + (service.serviceMrc ? service.serviceMrc : 0);
      }, 0);
    });
    return this.sortAndLimit(sums);
  }

  private createBarChart(dollars: boolean, chartConfig: ChartConfig, labelData: [string, number][], stepSize: number = 0, indexAxis: string = 'x') {
    const monthlySpendTicks = {
      ticks: {
        color: 'black',
        stepSize: stepSize,
        callback: function (value: string | number, index: number, values: string[] | number[]) {
          value = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
          return '$' + value;
        }
      },
      grid: {
        display: false
      }
    };
    const networkExpenseTicks = {
      ticks: {
        color: 'black',
        stepSize: stepSize
      },
      grid: {
        display: false
      }
    };


    this.destroyChart(chartConfig.chartContainerId);
    let labels = labelData.map(data => data[0]);
    let data = labelData.map(data => data[1]);
    let componentKey = chartConfig.chartContainerId as keyof DashboardFinancialsComponent;
    setTimeout(() => {
      // @ts-ignore
      this[componentKey] = new Chart(chartConfig.chartContainerId, {
        type: 'bar',
        data: {
          labels,
          datasets: [{
            data,
            backgroundColor: this.colorpalette
          }]
        },
        options: {
          indexAxis: indexAxis === 'x' ? 'x' : 'y',
          plugins: {
            tooltip: {
              callbacks: {
                label: (tooltipItem) => {
                  return '$' + tooltipItem.formattedValue;
                }
              }
            },
            title: {
              display: false,
            },
            legend: {
              display: false,
              labels: {
                font: {
                  family: 'Roboto'
                }
              }
            }
          },
          scales: {
            x: (dollars ? monthlySpendTicks : networkExpenseTicks),
            y: {
              ticks: {
                color: 'black',
                autoSkip: false
              }
            }
          }
        }
      },);
    }, 0);
  }

  private createLineChart(chartConfig: ChartConfig, labelData: [string, number][], stepSize: number = 0) {
    this.destroyChart(chartConfig.chartContainerId);
    let labels = labelData.map(data => data[0]);
    let data = labelData.map(data => data[1]);
    let componentKey = chartConfig.chartContainerId as keyof DashboardFinancialsComponent;
    setTimeout(() => {
      // @ts-ignore
      this[componentKey] = new Chart(chartConfig.chartContainerId, {
        type: 'line',
        data: {
          labels,
          datasets: [{
            data: data,
            fill: true
          }]
        },
        options: {
          indexAxis: 'x',
          plugins: {
            tooltip: {
              callbacks: {
                label: (tooltipItem) => {
                  return '$' + tooltipItem.formattedValue;
                }
              }
            },
            title: {
              display: false,
            },
            legend: {
              display: false,
              labels: {
                font: {
                  family: 'Roboto'
                }
              }
            }
          },
          scales: {
            x: {
              ticks: {
                color: 'black',
                stepSize: stepSize
              },
              grid: {
                display: false
              }
            },
            y: {
              ticks: {
                color: 'black',
                precision: 0
              },
              beginAtZero: true
            }
          }
        }
      },);
    }, 0);
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
    return sorted.slice(0, parseInt(this.selectedLimit));
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }
}
