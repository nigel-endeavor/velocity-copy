import { ChartTypeRegistry } from 'chart.js';

export interface ChartConfig {
  chartJSConfig?: any,
  chartContainerId: string,
  chartType?: keyof ChartTypeRegistry,
  others?: string[],
  expanded?: boolean
  groupByKey?: string
}
