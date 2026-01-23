import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { Jeop } from '../../models/jeop.model';
import { JeopSearchCriteria } from '../../models/jeop-search-criteria.model';
import { PaginatedResult } from '../../models/paginated-result.model';
import { JeopsFullComponent } from './jeops-full/jeops-full.component';
import { JeopService } from '../../services/jeop.service';
import { CommonModule, DatePipe } from '@angular/common';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import {select, Store} from "@ngrx/store";
import {editEnabled} from "../../order-detail-page/ngrx/order-details.selectors";

@Component({
  standalone: true,
  selector: 'app-jeops',
  templateUrl: './jeops.component.html',
  imports: [
    CommonModule,
    JeopsFullComponent,
    DatePipe
  ],
  styleUrls: ['./jeops.component.scss', '../../order-detail-page/form-styles.scss']
})
export class JeopsComponent implements OnInit, OnChanges {

  @Input() type: string = '';
  @Input() parentId: number;
  @Input() orderId: number;
  @Input() companyId: number;
  @Input() maxJeops: number;
  @Input() headerClass: string = 'label';
  @Input() customCSS: any;
  @Output() jeopsLoaded: any = new EventEmitter<Jeop[]>();

  @ViewChild('jeopsFull') jeopsFull: JeopsFullComponent;

  title: string;

  jeops: Jeop[] = new Array();
  showContent: boolean;
  manageJeopsTip = 'Use this interface to record and manage jeopardies (blockers) to workflow.';


  constructor(
    public oes: OrderEditService,
    private store: Store,
    private jeopService: JeopService
  ) { }

  ngOnInit(): void {
    this.onTypeChanged();
  }

  // run this when component inputs change
  showDialog: boolean = false;

  ngOnChanges(changes:SimpleChanges): void {
    if (changes['parentId'] || changes['type']) {
      this.onTypeChanged();
      this.getJeops();
    }
  }

  private onTypeChanged() {
    this.title = this.type.charAt(0).toUpperCase() + this.type.slice(1) + ' Jeops';
    this.jeopService.setType(this.type);
    this.orderId = this.type == 'order' ? Number(this.parentId) : this.orderId;
  }

  getJeops() {
    let searchCriteria: JeopSearchCriteria = new JeopSearchCriteria();
    searchCriteria[this.type + 'Id'] = this.parentId;
    searchCriteria.limit = this.maxJeops;
    searchCriteria.isOpen = true;

    this.jeops = new Array();
    this.jeopService.search(searchCriteria)
      .subscribe((res: PaginatedResult<Jeop>) => {
          if (res && res.collection) {
            this.jeops.push(...res.collection);

            if (this.maxJeops && this.jeops.length > this.maxJeops) {
              this.jeops.length = this.maxJeops;
            }
            this.jeops.forEach(jeop => {
              if (this.type == 'location') {
                jeop['locationId'] = this.parentId;
              } else if (this.type == 'service') {
                jeop['serviceId'] = this.parentId;
              }
            });
            this.jeopsLoaded.emit(this.jeops.filter(jeop => jeop.level == this.type));
          }
        }
      );
  }

  openDialog() {
    this.showContent = true;
    setTimeout(() => {
      this.jeopsFull.openDialog();
    }, 1);
  }

  closeDialog() {
    this.showContent = false;
    this.getJeops();
  }
}
