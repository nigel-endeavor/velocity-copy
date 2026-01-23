import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {
  public isLoading = false;

  constructor() { }

  public exportAsExcelFile(json: any[], excelFileName: string): void {
    this.isLoading = true;
    let newJson = this.fixedJson(json);
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(newJson);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }

  public exportAsDetailedExcelFile(json: Record<string, any[]>, excelFileName: string): void {
    this.isLoading = true;
    const keys = Object.keys(json);
    let sheets: Record<string, any> = {};
    keys.forEach(key => {
      let newJson = this.fixedJson(json[key]);
      const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(newJson);
      sheets[key] = worksheet;
    });
    const workbook: XLSX.WorkBook = { Sheets: { ...sheets }, SheetNames: keys };
    try {
      const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
      this.saveAsExcelFile(excelBuffer, excelFileName);
    } finally {
      this.isLoading = false;
    }
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + '_data_' + (new Date()).toLocaleDateString() + EXCEL_EXTENSION);
    this.isLoading = false;
  }

  private fixedJson(json: any[]): any[] {
    let newArray: any[] = [];
    json.forEach(record => {
      let newRecord: any = {};
      Object.keys(record).forEach(key => {
        let value = record[key];
        //fix date format
        if (key.toLowerCase().includes('date') && value) {
          value = (new Date(record[key])).toLocaleDateString();
        }
        let newKey = key
          // insert a space before all caps
          .replace(/([A-Z])/g, ' $1')
          // uppercase the first character
          .replace(/^./, str => str.toUpperCase())
          .replace('Id', 'ID');
        newRecord[newKey] = value;
      });
      newArray.push(newRecord);
    })
    return newArray;
  }
}
