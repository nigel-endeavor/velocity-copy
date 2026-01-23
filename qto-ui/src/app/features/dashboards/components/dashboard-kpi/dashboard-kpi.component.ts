import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { ExcelService } from '../../../../services/excel-service';
import { ChartTypeRegistry } from 'chart.js';
import { select, Store } from '@ngrx/store';
import { getAverages, getIsLoading, getServiceIntervals } from '../../ngrx/dashboards.selectors';
import { untilComponentDestroyed, WithDestroy} from '../../../../utilities';
import { loadProviderAverages, loadServiceIntervals } from '../../ngrx/dashboards.actions';

@Component({
  selector: 'app-dashboard-kpi',
  templateUrl: './dashboard-kpi.component.html',
  styleUrls: ['../../dashboards.component.scss', './dashboard-kpi.component.scss']
})
@WithDestroy
export class DashboardKpiComponent implements OnInit {

  networkDeliveryIntervalChart: any;
  networkDeliveryIntervalChartConfig = {
    chartJSConfig: {
      type: 'line',
      options: {
        aspectRatio: 2.5,
        responsive: true,
        interaction: {
          mode: 'nearest',
          axis: 'x',
          intersect: false
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
            stacked: true,
            title: {
              display: true,
              text: 'Average Business Days Count'
            },
            grid: {
              display: false
            }
          }
        },
        plugins: {
          title: {
            display: false
          }
        }
      },
    },
    chartContainerId: 'networkDeliveryIntervalChart',
    chartType: 'line' as keyof ChartTypeRegistry,
    expanded: false
  }

  last6Months: string[];

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

  showNetworkDeliveryIntervalChart: boolean = true;


  ngOnInit(): void {
    this.last6Months = [];
    for (let i = 0; i < 6; i++) {
      let date = new Date();
      date.setDate(1);
      date.setMonth(date.getMonth() - i);
      let label = `${date.toLocaleString('default', { month: 'long' })} ${date.getFullYear()}`;
      this.last6Months.unshift(label);
    }

    this.averages$.subscribe((averages: {
      cdiArranged: (string[] | number[])[],
      npiArranged: (string[] | number[])[]
    }) => {
      this.destroyChart('networkDeliveryIntervalChart');
      // @ts-ignore
      this.networkDeliveryIntervalChart = new Chart(this.networkDeliveryIntervalChartConfig.chartContainerId, {
          ...this.networkDeliveryIntervalChartConfig.chartJSConfig,
          data: {
            labels: this.last6Months,
            datasets: [
              {
                label: 'Average Days: Order Receipt to Provider Install',
                data: averages.cdiArranged[1],
                fill: true,
                backgroundColor: '#3871C1',
              },
              {
                label: 'Average Days: Order Receipt to Order Complete',
                data: averages.npiArranged[1],
                fill: true,
                backgroundColor: '#2A9041'
              }
            ]
          }
        }
      );
    });

    this.store.dispatch(loadProviderAverages({ monthArray: this.last6Months }));
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }
}
