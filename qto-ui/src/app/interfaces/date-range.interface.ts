export interface ComparableDateRange extends DateRange {
  isEmpty: boolean;
  dateRange: DateRange | null;
  dateCohort?: string | null;
  date?: any;
  comparison?: any;
}

export interface DateRange {
  startDate: string;
  endDate: string;
}
