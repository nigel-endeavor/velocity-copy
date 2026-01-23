import { Component } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { getInventoryCounts, getInventoryValuation, getIsLoading, getNewInventory, getTabFiltersByKey } from '../../ngrx/dashboards.selectors';
import { CHART_COLOR_PALETTE, untilComponentDestroyed, WithDestroy } from 'src/app/utilities';
import { ChartConfig } from '../../interfaces/chart-config.interface';
import { Chart } from 'chart.js';
import { DashboardDataset } from 'src/app/models/dashboard-dataset.model';
import { loadInventoryCounts, loadInventoryValuation, loadNewInventory, updateTabFilters } from '../../ngrx/dashboards.actions';
import { ExcelService } from 'src/app/services/excel-service';
import { map } from 'rxjs';

@Component({
  selector: 'app-dashboard-inventory',
  templateUrl: './dashboard-inventory.component.html',
  styleUrls: ['../../dashboards.component.scss', './dashboard-inventory.component.scss']
})
@WithDestroy
export class DashboardInventoryComponent {

  //inventory valuation chart
  inventoryValuationChart: any;
  inventoryValuationChartConfig = {
    chartContainerId: 'inventoryValuationChart',
    expanded: true
  };
  inventoryValuations: DashboardDataset;
  inventoryValuationNumOfMonths: number;

  public inventoryValuation$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getInventoryValuation)
  );
  //end inventory valuation chart

  //inventory count chart
  inventoryCountChart: any;
  inventoryCountChartConfig = {
    chartContainerId: 'inventoryCountChart',
    expanded: true
  };
  inventoryCounts: DashboardDataset;
  inventoryCountNumOfMonths: number;

  public inventoryCounts$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getInventoryCounts)
  );
  //end inventory count chart

  //new inventory chart
  newInventoryChart: any;
  newInventoryChartConfig = {
    chartContainerId: 'newInventoryChart',
    expanded: true
  };
  newInventory: DashboardDataset;
  newInventoryNumOfMonths: number;

  public newInventory$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getNewInventory)
  );
  //end new inventory chart

  colorpalette = CHART_COLOR_PALETTE;
  numOfMonthsOpts = [6, 12, 18, 24, 36];

  public filters$ = this.store.pipe(
    untilComponentDestroyed(this),
    select(getTabFiltersByKey('inventory')),
    map(tabFilters => {
      //@ts-ignore
      this.inventoryValuationNumOfMonths = tabFilters.inventoryValuation?.numOfMonths;
      //@ts-ignore
      this.inventoryCountNumOfMonths = tabFilters.inventoryCounts?.numOfMonths;
      //@ts-ignore
      this.newInventoryNumOfMonths = tabFilters.newInventory?.numOfMonths;
      return {
        inventoryValuation: {
          //@ts-ignore
          numOfMonths: tabFilters.inventoryValuation?.numOfMonths
        },
        inventoryCounts: {
          //@ts-ignore
          numOfMonths: tabFilters.inventoryCounts?.numOfMonths
        },
        newInventory: {
          //@ts-ignore
          numOfMonths: tabFilters.newInventory?.numOfMonths
        }
      }
    })
  );

  public isLoading$ = this.store.pipe(select(getIsLoading));

  constructor(
    private store: Store,
    private excelService: ExcelService
  ) { }

  ngOnInit() {
    this.inventoryValuation$.subscribe((inventoryValuation: DashboardDataset) => {
      this.inventoryValuations = inventoryValuation;
      this.createLineChart(this.inventoryValuationChartConfig, inventoryValuation, 0);
    });
    this.inventoryCounts$.subscribe((inventoryCounts: DashboardDataset) => {
      this.inventoryCounts = inventoryCounts;
      this.createLineChart(this.inventoryCountChartConfig, inventoryCounts, 0);
    });
    this.newInventory$.subscribe((newInventory: DashboardDataset) => {
      this.newInventory = newInventory;
      this.createBarChart(this.newInventoryChartConfig, newInventory);
    });

    this.store.dispatch(loadInventoryValuation({numOfMonths: 6}));
    this.store.dispatch(loadInventoryCounts({numOfMonths: 6}));
    this.store.dispatch(loadNewInventory({numOfMonths: 6}));
  }

  createInventoryValuationChart(numOfMonths: number) {
    if (!numOfMonths) {
      numOfMonths = 6;
    }
    this.store.dispatch(updateTabFilters({
      tabKey: 'inventory', key: 'inventoryValuation', value: { numOfMonths }
    }));
    this.store.dispatch(loadInventoryValuation({numOfMonths}));
  }

  createInventoryCountsChart(numOfMonths: number) {
    if (!numOfMonths) {
      numOfMonths = 6;
    }
    this.store.dispatch(updateTabFilters({
      tabKey: 'inventory', key: 'inventoryCounts', value: { numOfMonths }
    }));
    this.store.dispatch(loadInventoryCounts({numOfMonths}));
  }

  createNewInventoryChart(numOfMonths: number) {
    if (!numOfMonths) {
      numOfMonths = 6;
    }
    this.store.dispatch(updateTabFilters({
      tabKey: 'inventory', key: 'newInventory', value: { numOfMonths }
    }));
    this.store.dispatch(loadNewInventory({numOfMonths}));
  }

  private createLineChart(chartConfig: ChartConfig, dataset: DashboardDataset, stepSize: number = 0) {
    this.destroyChart(chartConfig.chartContainerId);
    let labels = dataset.labels;
    let data = dataset.data;
    let componentKey = chartConfig.chartContainerId as keyof DashboardInventoryComponent;
    // setTimeout(() => {
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
                  return tooltipItem.formattedValue.includes('.') ? '$' + tooltipItem.formattedValue : tooltipItem.formattedValue;
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
      });
    // }, 0);
  }

  private createBarChart(chartConfig: ChartConfig, dataset: DashboardDataset) {
    this.destroyChart(chartConfig.chartContainerId);
    let labels = dataset.labels;
    let data = dataset.data;
    let componentKey = chartConfig.chartContainerId as keyof DashboardInventoryComponent;
    // setTimeout(() => {
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
          indexAxis: 'x',
          plugins: {
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
            y: {
              ticks: {
                color: 'black',
                autoSkip: false
              }
            }
          }
        }
      });
    // }, 0);
  }

  private destroyChart(chartId: string) {
    var chart = Chart.getChart(chartId);
    if (chart) {
      chart.destroy();
    }
  }

  export(dataset: DashboardDataset, fileName: string) {
    let data = dataset.data.map((item, index) => {
      return {
        month: dataset.labels[index],
        value: item
      }
    });
    this.excelService.exportAsExcelFile(data, fileName);
  }
}
