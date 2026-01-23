import { ProviderIntervalsView } from '../../../models/provider-intervals-view.model';

export function getIntervalAverages(groupMap: { [key: string]: ProviderIntervalsView[] }): [string, number][] {
  let averages: { [key: string]: number } = {};
  Object.keys(groupMap).forEach(key => {
    averages[key] = Math.ceil(groupMap[key].reduce((total: number, interval: ProviderIntervalsView) => total + interval.calendarDayIntervalTime, 0) / groupMap[key].length);
  });
  return sortAndLimit(averages);
}

export function sortAndLimit(averages: { [key: string]: number }): [string, number][] {
  let sorted: [string, number][] = Object.entries(averages)
    .sort((a, b) => b[1] - a[1]);
  let sortedData = sorted.slice(0, 10);
  return sortedData;
}
