export function groupByDate(fieldName: string, records: any[]): { [key: string]: any[] } {
  return records.filter(service => service[fieldName]).reduce((result, record) => {
    let d = new Date(record[fieldName])
    let month = `${d.toLocaleString('default', { month: 'long' })}`;
    if (!result[month]) {
      result[month] = []
    }
    result[month].push(record);
    return result;
  }, {});
}
