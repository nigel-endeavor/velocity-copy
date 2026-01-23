import { Component, OnDestroy, OnInit } from '@angular/core';
import { WipServiceView } from '../../../../models/wip-service-view.model';
import Chart from 'chart.js/auto';
import ChartDataLabels from "chartjs-plugin-datalabels";
import { getStatusColor, WithDestroy, untilComponentDestroyed } from '../../../../utilities';
import { ExcelService } from '../../../../services/excel-service';
import { BAR_CHART_CONFIG, DEFAULT_COLORS, DOUGHNUT_CHART_CONFIG } from '../../data/dashboards.consts';
import { ChartConfig } from '../../interfaces/chart-config.interface';
import { ChartTypeRegistry } from 'chart.js';
import { select, Store } from '@ngrx/store';
import {
  getAllWipServiceViews,
  getIsLoading,
  getTabFiltersByKey,
  getWipServiceJeops,
  getWipLocationJeops,
  getWipServiceViews,
  getFilters
} from '../../ngrx/dashboards.selectors';
import {
  loadAllWipServices,
  loadWipServiceJeops,
  loadWipLocationJeops,
  loadWipServices,
  updateTabFilters
} from '../../ngrx/dashboards.actions';
import { WipServiceJeopView } from '../../../../models/wip-service-jeop-view.model';
import { WipLocationJeopView } from '../../../../models/wip-location-jeop-view.model';
import { take } from 'rxjs';
import { Router } from '@angular/router';
import { getBaseUrl } from 'src/app/utilities';
import { SERVICE_STATUSES } from '../../../service-worklist/data/service-statuses.enum';
import { TerminalServiceStatuses } from '../../../../models/constants/terminal-service-statuses';
import { LocationStatuses } from '../../../../models/constants/location-statuses';
import { TerminalLocationStatuses } from '../../../../models/constants/terminal-location-statuses';

@Component({
  selector: 'app-dashboard-wip',
  templateUrl: './dashboard-wip.component.html',
  styleUrls: ['../../dashboards.component.scss']
})
@WithDestroy
export class DashboardWipComponent implements OnInit, OnDestroy {

  public filters$= this.store.pipe(select(getFilters));

  provisionerChart: any;
  statusesFirst: any;
  statusesSecond: any;
  vertekPmChart: any;
  clientPmChart: any;
  jeopResponsibilityChart: any;
  locationJeopResponsibilityChart: any;
  averageServiceJeopDelayIntervalChart: any;
  averageLocationJeopDelayIntervalChart: any;
  openServiceJeopsFilter: boolean = true;
  openLocationJeopsFilter: boolean = true;

  vertekPMChartConfig = {
    chartJSConfig: {...DOUGHNUT_CHART_CONFIG},
    chartContainerId: 'vertekPmChart',
    chartType: 'doughnut' as keyof ChartTypeRegistry,
    others: [],
    expanded: false,
    groupByKey: 'vertekProjectManager'
  }

  clientPmChartConfig = {
    chartJSConfig: {...DOUGHNUT_CHART_CONFIG},
    chartContainerId: 'clientPmChart',
    chartType: 'doughnut' as keyof ChartTypeRegistry,
    others: [],
    expanded: false,
    groupByKey: 'clientProjectManager'
  }

  provisionerChartConfig = {
    chartJSConfig: {...DOUGHNUT_CHART_CONFIG},
    chartContainerId: 'provisionerChart',
    chartType: 'doughnut' as keyof ChartTypeRegistry,
    others: [],
    expanded: false,
    groupByKey: 'provisioner'
  }

  jeopResponsibilityChartConfig = {
    chartJSConfig: {...DOUGHNUT_CHART_CONFIG},
    chartContainerId: 'jeopResponsibilityChart',
    chartType: 'doughnut' as keyof ChartTypeRegistry,
    others: [],
    expanded: false
  }

  averageServiceJeopDelayIntervalChartConfig = {
    chartJSConfig: {
      plugins: [ChartDataLabels],
      type: 'bar',
      data: {},
      options: {
        indexAxis: 'x',
        scales: {
          x: {
            title: {
              display: true,
              text: 'Jeopardy Responsibility'
            },
            border: {
              display: false
            },
            grid: {
              display: false
            },
            ticks: {
              display: false
            }
          },
          y: {
            title: {
              display: true,
              text: 'Days'
            },
            border: {
              display: false
            },
            grid: {
              display: false,
            }
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
    },
    chartContainerId: 'averageServiceJeopDelayIntervalChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    others: [],
    expanded: false
  }

  locationJeopResponsibilityChartConfig = {
    chartJSConfig: {...DOUGHNUT_CHART_CONFIG},
    chartContainerId: 'locationJeopResponsibilityChart',
    chartType: 'doughnut' as keyof ChartTypeRegistry,
    others: [],
    expanded: false
  }

  averageLocationJeopDelayIntervalChartConfig = {
    chartJSConfig: {
      plugins: [ChartDataLabels],
      type: 'bar',
      data: {},
      options: {
        indexAxis: 'x',
        scales: {
          x: {
            title: {
              display: true,
              text: 'Jeopardy Responsibility'
            },
            border: {
              display: false
            },
            grid: {
              display: false
            },
            ticks: {
              display: false
            }
          },
          y: {
            title: {
              display: true,
              text: 'Days'
            },
            border: {
              display: false
            },
            grid: {
              display: false,
            }
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
    },
    chartContainerId: 'averageLocationJeopDelayIntervalChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    others: [],
    expanded: false
  }

  statusesFirstConfig: ChartConfig = {
    chartJSConfig: BAR_CHART_CONFIG,
    chartContainerId: 'statusesFirst',
    chartType: 'bar'
  }

  statusesSecondConfig = {
    chartJSConfig: BAR_CHART_CONFIG,
    chartContainerId: 'statusesSecond',
    chartType: 'bar'
  }

  wipServiceViews: WipServiceView[];
  wipServiceStatusData: WipServiceView[];
  wipJeopResponsibilityData: WipServiceView[];
  wipLocationJeopResponsibilityData: WipLocationJeopView[];
  wipAverageServiceJeopDelayIntervalData: WipServiceJeopView[];
  wipAverageLocationJeopDelayIntervalData: WipLocationJeopView[];
  allWipServiceViews: WipServiceView[];

  statusCounts: Map<string, number> = new Map();

  public wipServiceViews$ =  this.store.pipe(
    untilComponentDestroyed(this),
    select(getWipServiceViews));

  public allWipServiceViews$ =  this.store.pipe(
    untilComponentDestroyed(this),
    select(getAllWipServiceViews)).subscribe(res => {
    this.allWipServiceViews = res;
  });

  public wipServiceJeops$ =  this.store.pipe(
    untilComponentDestroyed(this),
    select(getWipServiceJeops)).subscribe(wipServiceJeopViews => {
      this.wipJeopResponsibilityData = wipServiceJeopViews.filter(jeop => !jeop.endDate);

      let responsibilityGroups = this.groupBy('responsibility', this.wipJeopResponsibilityData);
      this.createPieChart('jeopResponsibilityChart', this.jeopResponsibilityChartConfig, responsibilityGroups);
    }
  );

  public wipServiceJeopDelayInterval$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getWipServiceJeops)).subscribe(wipServiceJeopViews => {
      this.createWipServiceJeopDelayIntervalChart(wipServiceJeopViews);
    }
  );

  public wipLocationJeops$ =  this.store.pipe(
    untilComponentDestroyed(this),
    select(getWipLocationJeops)).subscribe(wipLocationJeopViews => {
      this.wipLocationJeopResponsibilityData = wipLocationJeopViews.filter(jeop => !jeop.endDate);

      let responsibilityGroups = this.groupBy('responsibility', this.wipLocationJeopResponsibilityData);
      this.createPieChart('locationJeopResponsibilityChart', this.locationJeopResponsibilityChartConfig, responsibilityGroups);
    }
  );

  public wipLocationJeopDelayInterval$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getWipLocationJeops)).subscribe(wipLocationJeopViews => {
      this.createWipLocationJeopDelayIntervalChart(wipLocationJeopViews);
    }
  );

  public tabFilters$ =  this.store.pipe(
    untilComponentDestroyed(this),
    select(getTabFiltersByKey('wip')));

  constructor(
    private store: Store,
    public excelService: ExcelService,
    public router: Router
  ) { }

  allStatuses = false;

  showWipCount = true;
  showEmployeeAssignment = true;
  showServiceLevelJeops = true;
  showLocationLevelJeops = true;

  public isLoading$ = this.store.pipe(select(getIsLoading));

  ngOnInit(): void {
    this.store.dispatch(loadAllWipServices());
    this.createAssignmentWipCharts();
    this.store.dispatch(loadWipServiceJeops());
    this.store.dispatch(loadWipLocationJeops());
  }

  private createAssignmentWipCharts() {
    this.store.dispatch(loadWipServices());
    this.wipServiceViews$.subscribe((wipServiceViews: WipServiceView[]) => {
      this.wipServiceViews = wipServiceViews;
      this.createStatusCountCharts();

      let provisionerGroups = this.groupBy('provisioner', wipServiceViews);
      let vertekPmGroups = this.groupBy('vertekProjectManager', wipServiceViews);
      let clientPmGroups = this.groupBy('clientProjectManager', wipServiceViews);

      clientPmGroups['All Others'] = this.getTopItems(clientPmGroups, this.clientPmChartConfig);
      provisionerGroups['All Others'] = this.getTopItems(provisionerGroups, this.provisionerChartConfig);
      vertekPmGroups['All Others'] = this.getTopItems(vertekPmGroups, this.vertekPMChartConfig);
      this.createPieChart('provisionerChart', this.provisionerChartConfig, provisionerGroups);
      this.createPieChart('vertekPmChart', this.vertekPMChartConfig, vertekPmGroups);
      this.createPieChart('clientPmChart', this.clientPmChartConfig, clientPmGroups);
    });
  }

  getTopItems(list: {[p: string]: WipServiceView[]}, config: ChartConfig, numberToGet = 3) {
    const keys = Object.keys(list);
    const realKeys: string[] = [];
    let allOthers: any[] = [];
    config.others = [];
    keys.forEach(key => {
      if (!realKeys.includes(key)) {
        let maxItem = {
          length: list[key].length,
          key: key
        };
        keys.forEach(secondaryKey => {
          if (!realKeys.includes(secondaryKey)) {
            if (maxItem.length <= list[secondaryKey].length) {
              maxItem = {
                length: list[secondaryKey].length,
                key: secondaryKey
              }
            }
          }
        });
        if (realKeys.length < numberToGet) {
          realKeys.push(maxItem.key);
        }
      }
    });
    keys.forEach(key => {
      if (!realKeys.includes((key))) {
        allOthers = [
          ...allOthers,
          ...list[key]
        ];
        // @ts-ignore
        config.others.push(key);
        delete list[key];
      }
    });
    return allOthers
  }

  createStatusCountCharts() {
    if (this.allStatuses) {
      this.createStatusCounts(this.allWipServiceViews);
    } else {
      this.createStatusCounts(this.wipServiceViews);
    }
  }

  private createStatusCounts(wipServiceViews: WipServiceView[]) {
    this.wipServiceStatusData = wipServiceViews;
    let notZerolabels = [];
    let notZerovalues = [];
    let serviceGroups = this.groupBy('serviceStatus', wipServiceViews);
    let telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    if (telecomClient) {
    this.statusCounts = new Map([
      ['On Hold', 0],
      ['Pending Assignment', 0],
      ['Engineer Assigned', 0],
      ['In Progress', 0],
      ['Site Survey In Progress', 0],
      ['Site Survey Complete', 0],
      ['Network Provider Construction', 0],
      ['Network Provider Construction Complete', 0],
      ['Provisioning', 0],
      ['Circuit Complete', 0],
      ['Installation Issue', 0],
      ['Activation Requested', 0],
      ['Activation Scheduled', 0],
      ['Service Complete', 0],
      ['Disconnect Complete', 0],
      ['Service Cancelled', 0],
      ['Change In Assignment', 0]
    ]);
    } else {
      this.statusCounts = new Map([
        ['Pending Assignment', 0],
        ['Engineer Assigned', 0],
        ['Technical Data Gathering', 0],
        ['Front End Client Engagement', 0],
        ['Technical Data Gathering Complete', 0],
        ['Consulting & Vulnerability Scan', 0],
        ['Devops Engagement', 0],
        ['Inventory Assignment', 0],
        ['Results Presented', 0],
        ['D3 Implementation', 0],
        ['Future Engagement Conversation', 0],
        ['Server Build', 0],
        ['Implementation QA', 0],
        ['End Learning Mode', 0],
        ['Asset & Logging Verification', 0],
        ['Final Test Email Process', 0],
        ['Alarm Tuning', 0],
        ['SIEM Event Filtering', 0],
        ['Filter/report Building', 0],
        ['Alarm Rule Building', 0],
        ['On Hold', 0],
        ['Service Complete', 0],
        ['Service Cancelled', 0],
        ['Change In Assignment', 0],
      ]);
    }
    for (const [key, value] of Object.entries(serviceGroups)) {
      this.statusCounts.set(key, value.length);
    }

    for (const item of Array.from(this.statusCounts.keys())) {
      if (this.statusCounts.get(item)) {
        notZerolabels.push(item)
        notZerovalues.push(this.statusCounts.get(item))
      }
    }
    const colors = (this.allStatuses ? Array.from(this.statusCounts.keys()) : notZerolabels).map(item =>  getStatusColor(item));
    const dataFirst = {
      labels: this.allStatuses ? Array.from(this.statusCounts.keys()).slice(0, 9) : notZerolabels,
        datasets: [{
        data: this.allStatuses ? Array.from(this.statusCounts.values()).slice(0, 9) : notZerovalues,
        backgroundColor: this.allStatuses ? colors.slice(0, 9) : colors,
        borderRadius: 12,
        minBarLength: 20,
      }]
    };
    setTimeout(() => {
      this.destroyChart('statusesFirst');
      // @ts-ignore
      this.statusesFirst  = new Chart(this.statusesFirstConfig.chartContainerId, {
        ...this.statusesFirstConfig.chartJSConfig,
        data: dataFirst
      },);
    }, 0);
    if (this.allStatuses) {
      const dataSecond = {
        labels: Array.from(this.statusCounts.keys()).slice(9),
        datasets: [{
          data: Array.from(this.statusCounts.values()).slice(9),
          backgroundColor: colors.slice(9),
          borderRadius: 12,
          minBarLength: 20,
        }]
      };
      setTimeout(() => {
        this.destroyChart('statusesSecond');
        // @ts-ignore
        this.statusesSecond = new Chart(this.statusesSecondConfig.chartContainerId, {
          ...this.statusesSecondConfig.chartJSConfig,
          data: dataSecond
        },);
      }, 0)
    }
  }

  groupBy(fieldName: string, records: any[]): { [key: string]: WipServiceView[] | WipServiceJeopView[] } {
    return records.filter(service => service[fieldName]).reduce((result, record) => {
      (result[record[fieldName]] = result[record[fieldName]] || []).push(record);
      return result;
    }, {});
  }

  private createPieChart(chartId: string, config: ChartConfig, groupMap: { [key: string]: WipServiceView[] }) {
    this.destroyChart(chartId);

    // Convert the object to an array of key-value pairs
    let sorted = Object.entries(groupMap);

    // Sort the array based on keys
    sorted.sort((a, b) => a[0].localeCompare(b[0]));

    // Create a new object from the sorted array
    let sortedResult: { [key: string]: any } = {};
    sorted.forEach(([key, value]) => {
      sortedResult[key] = value;
    });

    let labels = Object.keys(sortedResult);
    let data = Object.values(sortedResult).map(group => group.length);
    let componentKey = chartId as keyof DashboardWipComponent;
    config.chartJSConfig.data = {
      labels,
      datasets: [{
        data,
        backgroundColor: DEFAULT_COLORS
      }]
    };
    // @ts-ignore
    this[componentKey] = new Chart(chartId, {
      ...config.chartJSConfig
    },);
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }

  getStatusColor(status: any) {
    return getStatusColor(status);
  }

  //used in template by keyvalue pipe to retain order of map entries
  asIsOrder() {
    return 1;
  }

  toggleChartType(chartConfig: ChartConfig, newChartType: string) {
    this.destroyChart(chartConfig.chartContainerId);
    let componentKey = chartConfig.chartContainerId as keyof DashboardWipComponent;

    if (this[componentKey]) {
      //if the selected chart type is the default chart type, use the provided config
      //note that, if we want to continue supporting these different types for each, we might want to
      //provide aan individualized config for each type
      if (newChartType === chartConfig.chartType) {
        // @ts-ignore
        this[componentKey] = new Chart(chartConfig.chartContainerId, {
          ...chartConfig.chartJSConfig,
          data: chartConfig.chartJSConfig.data
        });
      } else {
        let options= (newChartType === 'bar' || newChartType === 'line')  ? {
          scales: {
            x: {
              ticks: {
                display: false
              },
              border: {
                display: false
              },
              grid: {
                display: false
              }
            },
            y: {
              ticks: {
                display: false
              },
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
              // display: newChartType !== 'line',
              color: '#FFFFFF',
              backgroundColor: DEFAULT_COLORS,
              borderRadius: 6,
              font: {
                family: 'Roboto'
              }
            }
          },
          layout: {
            padding: {
                left: 15,
                right: 15,
                top: 15,
                bottom: 15
            }
          }
        } : DOUGHNUT_CHART_CONFIG.options;

        // @ts-ignore
        this[componentKey] = new Chart(chartConfig.chartContainerId, {
          ...DOUGHNUT_CHART_CONFIG,
          data: chartConfig.chartJSConfig.data,
          options,
          type: newChartType as keyof  ChartTypeRegistry
        });
      }
    }
  }

  getDotColor(chart: any, index: number) {
    if (chart?.config._config.type === 'doughnut') {
      return chart.legend?.legendItems[index]?.fillStyle || ''
    } else if (chart && chart.config._config.data
      && chart.config._config.data.datasets.length > 0 && chart.config._config.data.datasets[0].data.length > 0 && chart.config._config.data.datasets[0].data.length > index) {
      return DEFAULT_COLORS[index % DEFAULT_COLORS.length];
    }
    return ''
  }

  getDotText(chart: any, index: number) {
    if (chart?.config._config.type === 'doughnut') {
      return chart.legend?.legendItems[index]?.text || '';
    } else if (chart) {
      return chart.config._config.data.labels[index]
    }
    return ''
  }

  download(groupBy: string, filename: string): void {
    const groupedData = this.groupBy(groupBy, this.wipServiceViews);
    this.excelService.exportAsDetailedExcelFile(groupedData, filename)
  }

  expandChart(config: ChartConfig): void {
    config.expanded = true;
    const groupedData = this.groupBy(config.groupByKey || '', this.wipServiceViews);
    this.createPieChart(config.chartContainerId, config, groupedData);
  }

  shrinkChart(config: ChartConfig): void {
    config.expanded = false;
    const groupedData = this.groupBy(config.groupByKey || '', this.wipServiceViews);
    groupedData['All Others'] = this.getTopItems(groupedData, config);
    this.createPieChart(config.chartContainerId, config, groupedData);
  }

  updateTabFilters(key: string, value: string | boolean) {
    this.store.dispatch(updateTabFilters({
      tabKey: 'wip',
      key,
      value
    }))
  }

  createWipServiceJeopDelayIntervalChart(wipServiceJeopViews: WipServiceJeopView[]) {
    if (this.openServiceJeopsFilter) {
      this.wipAverageServiceJeopDelayIntervalData = wipServiceJeopViews.filter(jeop => !jeop.endDate);
    } else {
      this.wipAverageServiceJeopDelayIntervalData = wipServiceJeopViews;
    }
    let grouped = this.wipAverageServiceJeopDelayIntervalData.filter((record => record['responsibility'])).reduce((result, record) => {
      let key = record['responsibility'] as string;
      (result[key] = result[key] || []).push(record);
      return result;
    }, {} as { [key: string]: any[] });
    let averageIntervals: { [key: string]: number } = {};

    // for (let key in grouped) {
    //   let sum = 0;
    //   (grouped[key] as Array<any>).forEach((item: WipServiceJeopView) => {
    //     let endDate = item.endDate ? new Date(item.endDate) : new Date();
    //     let dayDifference = Math.ceil((endDate.getTime() - new Date(item.startDate).getTime()) / (24 * 60 * 60 * 1000));
    //
    //     sum += dayDifference;
    //   });
    //   averageIntervals[key] = Math.ceil(sum / grouped[key].length);
    // }



    for (let key in grouped) {
      let sum = 0;

      (grouped[key] as WipLocationJeopView[]).forEach((item: WipLocationJeopView) => {
        let endDate = item.endDate ? new Date(item.endDate) : new Date();
        let startDate = new Date(item.startDate);

        let businessDays = this.getBusinessDays(startDate, endDate);
        sum += businessDays;
      });

      averageIntervals[key] = Math.ceil(sum / grouped[key].length);
    }

    // Convert the object to an array of key-value pairs
    let sorted = Object.entries(averageIntervals);

    // Sort the array based on keys
    sorted.sort((a, b) => a[0].localeCompare(b[0]));

    // Create a new object from the sorted array
    let sortedResult: { [key: string]: number } = {};
    sorted.forEach(([key, value]) => {
      sortedResult[key] = value;
    });

    this.destroyChart('averageServiceJeopDelayIntervalChart');
    let labels = Object.keys(sortedResult);
    let data = Object.values(sortedResult);
    let componentKey = 'averageServiceJeopDelayIntervalChart' as keyof DashboardWipComponent;
    this.averageServiceJeopDelayIntervalChartConfig.chartJSConfig.data = {
      labels,
      datasets: [{
        data,
        backgroundColor: DEFAULT_COLORS
        // borderRadius: 12
      }]
    };
    // @ts-ignore
    this[componentKey] = new Chart('averageServiceJeopDelayIntervalChart', {
      ...this.averageServiceJeopDelayIntervalChartConfig.chartJSConfig
    },);
  }

  getBusinessDays(startDate: Date, endDate: Date): number {
    let count = 0;
    let currentDate = new Date(startDate);
    currentDate.setDate(currentDate.getDate() + 1);
    while (currentDate <= endDate) {
        const dayOfWeek = currentDate.getDay();
        if (dayOfWeek !== 0 && dayOfWeek !== 6) { // Exclude Sunday (0) & Saturday (6)
            count++;
        }
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return count;
}


  createWipLocationJeopDelayIntervalChart(wipLocationJeopViews: WipLocationJeopView[]) {
    if (this.openLocationJeopsFilter) {
      this.wipAverageLocationJeopDelayIntervalData = wipLocationJeopViews.filter(jeop => !jeop.endDate);
    } else {
      this.wipAverageLocationJeopDelayIntervalData = wipLocationJeopViews;
    }
    let grouped = this.wipAverageLocationJeopDelayIntervalData.filter((record => record['responsibility'])).reduce((result, record) => {
      let key = record['responsibility'] as string;
      (result[key] = result[key] || []).push(record);
      return result;
    }, {} as { [key: string]: any[] });
    let averageIntervals: { [key: string]: number } = {};

    for (let key in grouped) {
      let sum = 0;

      (grouped[key] as WipLocationJeopView[]).forEach((item: WipLocationJeopView) => {
        let endDate = item.endDate ? new Date(item.endDate) : new Date();
        let startDate = new Date(item.startDate);

        let businessDays = this.getBusinessDays(startDate, endDate);
        sum += businessDays;
      });

      averageIntervals[key] = Math.ceil(sum / grouped[key].length);
    }

    // Convert the object to an array of key-value pairs
    let sorted = Object.entries(averageIntervals);

    // Sort the array based on keys
    sorted.sort((a, b) => a[0].localeCompare(b[0]));

    // Create a new object from the sorted array
    let sortedResult: { [key: string]: number } = {};
    sorted.forEach(([key, value]) => {
      sortedResult[key] = value;
    });

    this.destroyChart('averageLocationJeopDelayIntervalChart');
    let labels = Object.keys(sortedResult);
    let data = Object.values(sortedResult);
    let componentKey = 'averageLocationJeopDelayIntervalChart' as keyof DashboardWipComponent;
    this.averageLocationJeopDelayIntervalChartConfig.chartJSConfig.data = {
      labels,
      datasets: [{
        data,
        backgroundColor: DEFAULT_COLORS
        // borderRadius: 12
      }]
    };
    // @ts-ignore
    this[componentKey] = new Chart('averageLocationJeopDelayIntervalChart', {
      ...this.averageLocationJeopDelayIntervalChartConfig.chartJSConfig
    },);
  }

  updateAverageServiceJeopDelayIntervalChart() {
    this.store.pipe(untilComponentDestroyed(this), select(getWipServiceJeops)).subscribe(wipServiceJeopViews => {
      this.createWipServiceJeopDelayIntervalChart(wipServiceJeopViews);
    });
  }

  updateAverageLocationJeopDelayIntervalChart() {
    this.store.pipe(untilComponentDestroyed(this), select(getWipLocationJeops)).subscribe(wipLocationJeopViews => {
      this.createWipLocationJeopDelayIntervalChart(wipLocationJeopViews);
    });
  }

  onChartDoubleClick(event: any, chart: any, property: string, target: string) {
    const points = chart.getElementsAtEventForMode(event, 'nearest', { intersect: true }, true);
    if (points.length) {
      const firstPoint = points[0];
      // the status
      const label = chart.data.labels[firstPoint.index];
      // the filters
      this.filters$.pipe(take(1)).subscribe(filters => {
        const queryParams: { [key: string]: string } = {
          parentCompanyName: filters.selectedMasterCompanies,
          companyName: filters.selectedEndCompanies,
          provider: filters.selectedProviders,
          serviceType: filters.selectedServiceTypes
        };
        // Dynamically add the property parameter to the queryParams object
        queryParams[property] = label;
        // in case of a disconnect status, redirect to the disconnects worklist
        if (property == 'status' && target == 'services' && ['Disconnect Complete', 'Disconnect In Progress'].includes(label)) {
          target = 'disconnects';
          if (label == 'Disconnect Complete') {
            queryParams['pendingDisconnect'] = 'false';
          }
        }
        if (event.currentTarget.id === 'provisionerChart' || event.currentTarget.id === 'vertekPmChart' || event.currentTarget.id === 'clientPmChart') {
          queryParams['hideTerminalStatuses'] = 'true';
        }
        if ('openJeopResponsibilities' === property && 'services' === target) {
          queryParams['hideTerminalStatuses'] = 'true';
        } else if ('openJeopResponsibilities' === property && 'locations' === target) {
          queryParams['hideTerminalStatuses'] = 'true';
        }
        const url = this.router.serializeUrl(this.router.createUrlTree([target], { queryParams }));
        //current tab for testing
        // window.location.href = `${getBaseUrl()}${url}`;
        //new tab
        window.open(`${getBaseUrl()}${url}`, '_blank');
      });
    }
  }

  public ngOnDestroy() {}
}
