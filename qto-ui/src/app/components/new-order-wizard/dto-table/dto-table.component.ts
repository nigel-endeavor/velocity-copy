import { Component, ElementRef, EventEmitter, Input, OnInit, Output, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { CommonColumn } from 'src/app/interfaces/columns.interface';
import { DTO_TABLE_COLUMNS } from './dto-table-columns.const';
import { OrderCreateDto, OrderCreateLocation, OrderCreateService } from 'src/app/models/order-create-dto.model';
import { plainToInstance } from 'class-transformer';
import { Address } from 'src/app/models/address.model';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';

@Component({
  selector: 'app-dto-table',
  templateUrl: './dto-table.component.html',
  styleUrls: ['./dto-table.component.scss']
})
export class DtoTableComponent implements OnInit {

  @ViewChild('headerTable') headerTable: ElementRef;
  @ViewChild('tWrapHead') tWrapHead: ElementRef;
  @ViewChild('tWrapBody') tWrapBody: ElementRef;
  @ViewChildren('tr', { read: ElementRef }) trElements: QueryList<ElementRef>;
  @ViewChildren('resizer', { read: ElementRef }) resizerElements: QueryList<ElementRef>;

  @Input() tableData: OrderCreateDto[] = [];
  @Input() selectedRow: any;
  @Output() rowClick = new EventEmitter<string>();
  @Output() rowDblClick = new EventEmitter<string>();

  columns: CommonColumn[] = DTO_TABLE_COLUMNS;
  rows: any[] = [];
  autoCreateClientServiceId: boolean = false;


  constructor(
    private companyConfigService: CompanyConfigPropertyService
  ) { }

  ngOnInit(): void {
    setTimeout(() => {
      this.initResizableColumns();
    }, 1);
    this.companyConfigService.getValue('AUTO_CREATE_CLIENT_SERVICE_ID').subscribe((res) => {
      if (res && res.value) {
        this.autoCreateClientServiceId = JSON.parse(res.value.toLowerCase());
      }
    });
  }

  //Nested property updates don't trigger ngOnChanges default change detection
  //ngDoCheck is used to implement our own customer change detection
  //this hook fires A LOT, be careful with what you put in here it can lead to performance issues
  oldOrder: OrderCreateDto | null = null;
  oldLocations: OrderCreateLocation[] = [];
  oldServices: OrderCreateService[] = [];
  oldServiceLength: number = 0;
  oldLocationLength: number = 0;
  ngDoCheck() {
    let changeDetected = false;
    let order = this.tableData[0];
    let locations = this.tableData.map(o => o.locations).flat();
    let services = this.tableData.map(o => o.locations).flat().map(l => l.services).flat();
    let serviceLength = services.length;
    let locationLength = locations.length;
    if (this.oldOrder?.endCustomer?.id !== order?.endCustomer?.id
        || this.oldOrder?.masterCustomer?.id !== order?.masterCustomer?.id
        || this.oldOrder?.clientOrderId !== order?.clientOrderId
        || this.oldServices.some((service, i) => service.clientServiceId !== services[i]?.clientServiceId)
        || this.oldServices.some((service, i) => service.serviceType !== services[i]?.serviceType)
        || this.oldServices.some((service, i) => service.provider !== services[i]?.provider)
        || this.oldServices.some((service, i) => service.linkedOrBundled !== services[i]?.linkedOrBundled)
        || this.oldServices.some((service, i) => service.linkedBundledClientServiceId !== services[i]?.linkedBundledClientServiceId)
        || this.oldServices.some((service, i) => service.link !== services[i]?.link)
        || this.oldLocations.some((location, i) => location.address?.toString() !== locations[i]?.address.toString())
        || this.oldLocations.some((location, i) => location.clientOrderId !== locations[i]?.clientOrderId)
        || this.oldLocations.some((location, i) => location.clientLocationId !== locations[i]?.clientLocationId)
        || this.oldServiceLength !== serviceLength
        || this.oldLocationLength !== locationLength) {
      this.oldOrder = {...order};
      this.oldLocations = locations.map(location => ({ ...location, address: plainToInstance(Address, { ...location.address }) })) as OrderCreateLocation[];
      this.oldServices = services.map(service => ({ ...service })) as OrderCreateService[];
      this.oldServiceLength = serviceLength;
      this.oldLocationLength = locationLength;
      changeDetected = true;
    }

    if (changeDetected) {
      this.rows = this.tableData.flatMap(order => order.locations).map(l => l.services).flat().map((service: OrderCreateService) => {
        let location = this.tableData.flatMap(order => order.locations).find(l => l.services.includes(service));
        let order = this.tableData.find(o => o.locations.includes(location!));
        return {
          ...service,
          masterCustomer: order!.masterCustomer?.name,
          endCustomer: order!.endCustomer?.name,
          clientOrderId: order!.clientOrderId,
          clientLocationId: location?.clientLocationId,
          address: location?.address.toString(),
        }
      }).reverse();
      setTimeout(() => {
        this.initResizableColumns();
      }, 0);
    }
  }

  getStatusColor(row: any): string {
    if (row.serviceType && row.clientLocationId
      && (this.autoCreateClientServiceId || (!this.autoCreateClientServiceId && row.clientServiceId)) && row.endCustomer && row.masterCustomer
      && ((row.linkedOrBundled != 'none' && row.linkedBundledClientServiceId)
        || (row.linkedOrBundled == 'none' && !row.linkedBundledClientServiceId))) {
      return '#2A9041'; //green
    } else {
      return '#DD0000'; //red
    }
  }

  //used to determine if incoming click action is single or double click
  isSingleClick: boolean = true;
  //emits rowClick event, parent component will listen for event and handle action
  onRowClick(row: any) {
    this.isSingleClick = true;
    setTimeout(() => {
      if (this.isSingleClick) {
        this.rowClick.emit(row.uuid);
      }
    }, 200);
  }

  //emits rowDblClick event, parent component will listen for event and handle action
  onRowDblClick(row: any) {
    this.isSingleClick = false;
    this.rowDblClick.emit(row.link);
  }

  // HORIZONTAL SCROLL
  onHeaderScroll(event: any): void {
    let header = this.tWrapHead.nativeElement;
    let body = this.tWrapBody.nativeElement;
    if (header && body) {
      body.scrollLeft = header.scrollLeft;
    }
  }

  onBodyScroll(event: any): void {
    let header = this.tWrapHead.nativeElement;
    let body = this.tWrapBody.nativeElement;
    if (header && body) {
      header.scrollLeft = body.scrollLeft;
    }
  }
  // END HORIZONTAL SCROLL

  // RESIZABLE COLUMNS
  resizerStyles = {};
  private initResizableColumns(): void {
    const headerTable = this.headerTable.nativeElement;
    if (!headerTable) { return; }

    let index = 0;
    this.resizerElements.forEach(resizer => {
      let i = index++;
      this.resizerStyles = {'height': `${headerTable.offsetHeight - 10}px`};
      if (resizer.nativeElement.parentElement) {
        const th = resizer.nativeElement.parentElement;
        const tdArr = (this.trElements.map(row => row.nativeElement.children[i + 1]) as HTMLElement[]);
        tdArr.forEach(td => td.style.width = th.style.width);
        this.createColumnResizeEventListeners(th, resizer.nativeElement, tdArr);
      }
    });
  }

  //creates event listeners for column resizing
  private createColumnResizeEventListeners(th: HTMLElement, resizer: HTMLElement, tdArr: HTMLElement[]): void {
    let self = this;
    // Track the current position of mouse
    let x = 0;
    let w = 0;

    const mouseDownHandler = function(e: any) {
      e.stopPropagation();
      // Get the current mouse position
      x = e.clientX;
      // Calculate the current width of column
      const styles = window.getComputedStyle(th);
      w = parseInt(styles.width, 10);
      // Attach listeners for document's events
      document.addEventListener('mousemove', mouseMoveHandler);
      document.addEventListener('mouseup', mouseUpHandler);
    };

    const mouseMoveHandler = function(e: any) {
      // If the mouse is out of the window, and the last column resizer is being dragged, scroll the table as far as it will go
      if (e.clientX > window.innerWidth && self.resizerElements.toArray().findIndex(item => item.nativeElement === resizer) === self.resizerElements.length - 1) {
        let header = self.tWrapHead.nativeElement;
        let body = self.tWrapBody.nativeElement;
        header.scrollLeft = header.scrollWidth;
        body.scrollLeft = header.scrollWidth;
      }
      // Determine how far the mouse has been moved
      const dx = e.clientX - x;

      // Update the width of column
      let width = `${w + dx}px`
      th.style.width = width;
      tdArr.forEach(td => td.style.width = th.style.width);
    };

    // When user releases the mouse, remove the existing event listeners
    const mouseUpHandler = function () {
      document.removeEventListener('mousemove', mouseMoveHandler);
      document.removeEventListener('mouseup', mouseUpHandler);
    };

    resizer.addEventListener('mousedown', mouseDownHandler);
  }
  // END RESIZABLE COLUMNS
}
