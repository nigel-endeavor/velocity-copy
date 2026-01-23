export function arrangeDataInMonthlyOrder(groupedData: [string, number][], monthArray: string[]) {
  let data = [];
  let labels = [];
  for (let i = 0; i < monthArray.length; i++) {
    let month = monthArray[i];
    let monthFound = false;
    for (let j = 0; j < groupedData.length; j++) {
      if (month === groupedData[j][0]) {
        data.push(groupedData[j][1]);
        labels.push(groupedData[j][0]);
        monthFound = true;
      }
    }
    if (!monthFound) {
      data.push(0);
      labels.push(month);
    }
  }
  return [labels, data];
}
