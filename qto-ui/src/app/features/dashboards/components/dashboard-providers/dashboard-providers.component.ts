import { Component, Input, OnChanges, OnInit, SimpleChanges, OnDestroy } from '@angular/core';
import { ExcelService } from '../../../../services/excel-service';
import Chart from 'chart.js/auto';
import { ProvidersService } from '../../../../services/providers.service';
import { ProviderIntervalsView } from '../../../../models/provider-intervals-view.model';
import { WipServiceView } from '../../../../models/wip-service-view.model';
import { ChartTypeRegistry } from 'chart.js';
import { CHART_COLOR_PALETTE, untilComponentDestroyed, WithDestroy } from '../../../../utilities';
import { select, Store } from '@ngrx/store';
import {
  getProviderReliance,
  getInstallIntervals,
  getIsLoading,
  getSurveyIntervals,
  getTabFiltersByKey
} from '../../ngrx/dashboards.selectors';
import { loadProviderSurveyIntervals, loadProviderInstallIntervals, loadProviderReliance, updateTabFilters } from '../../ngrx/dashboards.actions';
import { map } from 'rxjs';
import { getIntervalAverages, sortAndLimit } from '../../utils/intervaAverages';
import ChartDataLabels from "chartjs-plugin-datalabels";

@Component({
  selector: 'app-dashboard-providers',
  templateUrl: './dashboard-providers.component.html',
  styleUrls: ['../../dashboards.component.scss', './dashboard-providers.component.scss']
})
@WithDestroy
export class DashboardProvidersComponent implements OnInit {

  colorpalette = CHART_COLOR_PALETTE;
  installIntervalChart: any;
  installIntervalChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'installIntervalChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };
  surveyIntervalChart: any;
  surveyIntervalChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'surveyIntervalChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };
  providerRelianceChart: any;
  providerRelianceChartConfig = {
    chartJSConfig: {},
    chartContainerId: 'providerRelianceChart',
    chartType: 'bar' as keyof ChartTypeRegistry,
    expanded: false,
  };

  showInstallInterval: boolean = true;
  installIntervalsData: ProviderIntervalsView[];

  showSurveyInterval: boolean = true;
  surveyIntervalsData: ProviderIntervalsView[];


  showProviderReliance: boolean = true;
  providerRelianceData: WipServiceView[];

  public filters$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getTabFiltersByKey('providers')),
    map(item => {
      return {
        provider: {
          // @ts-ignore
          allTime: item.provider?.allTime,
        },
        survey: {
          // @ts-ignore
          allTime: item.survey?.allTime
        },
        reliance: {
          // @ts-ignore
          allTime: item.reliance?.allTime,
        }
      }
    })
  );

  public installIntervals$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getInstallIntervals)
  );

  public surveyIntervals$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getSurveyIntervals)
  );

  public providerReliance$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getProviderReliance)
  );

  public isLoading$ = this.store.pipe(select(getIsLoading));

  constructor(
    private providersService: ProvidersService,
    private store: Store,
    public excelService: ExcelService
  ) { }

  ngOnInit(): void {
    this.installIntervals$.subscribe((installIntervals: ProviderIntervalsView[]) => {
      this.installIntervalsData = installIntervals;

      let providerGroups = this.groupBy('provider', installIntervals);
      let providerAverages = getIntervalAverages(providerGroups);
      this.createBarChart('installIntervalChart', providerAverages, 0, "6 Month Average Length of Provider Order Handling Time (Days)");
    });

    this.surveyIntervals$.subscribe((surveyIntervals: ProviderIntervalsView[]) => {
      this.surveyIntervalsData = surveyIntervals;

      let providerGroups = this.groupBy('provider', surveyIntervals);
      let providerAverages = getIntervalAverages(providerGroups);
      this.createBarChart('surveyIntervalChart', providerAverages, 0, "Survey Interval in Days");
    });

    this.providerReliance$.subscribe((providerReliance: WipServiceView[]) => {
      this.providerRelianceData = providerReliance;

      let providerGroups = this.groupBy('provider', providerReliance);
      let serviceCounts = this.getServiceCounts(providerGroups);
      this.createBarChart('providerRelianceChart', serviceCounts, 10, "Count of Services");
    });

    this.store.dispatch(loadProviderInstallIntervals());
    this.store.dispatch(loadProviderSurveyIntervals());
    this.store.dispatch(loadProviderReliance({
      chart: 'reliance'
    }));
  }

  createInstallIntervalChart(filters: any) {
    this.store.dispatch(updateTabFilters({
      tabKey: 'providers', key: 'provider', value: filters
    }));
    this.store.dispatch(loadProviderInstallIntervals());
  }

  createSurveyIntervalChart(filters: any) {
    this.store.dispatch(updateTabFilters({
      tabKey: 'providers', key: 'survey', value: filters
    }));
    this.store.dispatch(loadProviderSurveyIntervals());
  }

  createProviderRelianceChart(filters: any) {
    this.store.dispatch(updateTabFilters({
      tabKey: 'providers', key: 'reliance', value: filters
    }));
    this.store.dispatch(loadProviderReliance({
      chart: 'reliance'
    }));
  }


  private getServiceCounts(groupMap: { [key: string]: WipServiceView[] }): [string, number][] {
    let counts: { [key: string]: number } = {};
    Object.keys(groupMap).forEach(key => {
      counts[key] = groupMap[key].length;
    });
    return sortAndLimit(counts);
  }

  groupBy(fieldName: string, records: any[]): { [key: string]: any[] } {
    return records.filter(service => service[fieldName]).reduce((result, record) => {
      (result[record[fieldName]] = result[record[fieldName]] || []).push(record);
      return result;
    }, {});
  }

  private createBarChart(chartId: string,
    providerAverages: [string, number][],
    stepSize: number = 0,
    xAxis: string) {
    this.destroyChart(chartId);
    let labels = providerAverages.map(providerAverage => providerAverage[0]);
    let data = providerAverages.map(providerAverage => providerAverage[1]);
    let componentKey = chartId as keyof DashboardProvidersComponent;
    setTimeout(() => {
      // @ts-ignore
      this[componentKey] = new Chart(chartId, {
        plugins: [ChartDataLabels],
        type: 'bar',
        data: {
          labels,
          datasets: [{
            data,
            backgroundColor: this.colorpalette
          }],
        },
        options: {
          responsive: true,
          indexAxis: 'y',
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
          },
          scales: {
            x: {
              title: {
                display: xAxis ? true : false,
                text: xAxis
              },
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
                color: 'black'
              }
            }
          }
        }
      },);
    }, 0);
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }
}
