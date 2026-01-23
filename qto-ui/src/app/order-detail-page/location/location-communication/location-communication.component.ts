import { SubjectInterface } from 'src/app/models/subject.model';
import {
  Component,
  ElementRef,
  OnInit,
  ViewChild
} from '@angular/core';
import { Location } from 'src/app/models/location.model';
import { Message, MessageThread } from '../../../models/message-thread.model';
import { MessageThreadService } from '../../../services/message-thread.service';
import { SecurityUtilService } from '../../../services/security-util.service';
import { SubjectService } from '../../../services/subject.service';
import { FormControl, NgForm } from '@angular/forms';
import { OrderEditService } from '../../order-edit.service';
import { ActivatedRoute } from '@angular/router';
import { debounceTime, Subject } from 'rxjs';
import { Service } from '../../../models/service.model';
import { select, Store } from '@ngrx/store';
import { getSelectedLocationOrService } from '../../ngrx/order-details.selectors';


@Component({
  selector: 'app-location-communication',
  templateUrl: './location-communication.component.html',
  styleUrls: ['./location-communication.component.scss'],
})
export class LocationCommunicationComponent implements OnInit {
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));

  @ViewChild('messageList') messageListRef: ElementRef;
  @ViewChild('form') form: NgForm;
  subjectDropdownControl = new FormControl<SubjectInterface[]>([]);

  subjects: SubjectInterface[];
  messageThreads: MessageThread[];
  filteredThreads: MessageThread[];

  userSubject: SubjectInterface;
  searchQuery: '';
  selectedMessageThread: MessageThread;

  creatingNew: boolean = false;
  editingWatchers: boolean = false;
  newMessage: Message = new Message();

  private search: Subject<string> = new Subject();
  public location: Location;

  helpfulMessage: string = 'From this section, you can send an internal message to the provisioner assigned to this location.  Simply create a thread Subject Title and enter your message below.  You can choose to notify additional users from the "Additionally Notify" button.\n\nYou can manage multiple communication threads about this location from the left hand naviagation.'

  constructor(
    private subjectService: SubjectService,
    private messageThreadService: MessageThreadService,
    private securityUtilService: SecurityUtilService,
    private oes: OrderEditService,
    private route: ActivatedRoute,
    private store: Store,

  ) { }

  ngOnInit(): void {
    this.selectedLocationOrService$.subscribe((location: Service | Location | null) => {
      if (location instanceof Location) {
        this.location = location
        this.subjectService.getSubjects(location.orderId).subscribe((res: SubjectInterface[]) => {
          this.subjects = res;
          this.subjectService.getUserSubject().subscribe((res: SubjectInterface) => {
            this.userSubject = res;
          });
          this.messageThreadService.findByLocationId(location.id).subscribe((res: MessageThread[]) => {
            this.messageThreads = res;
            this.filteredThreads = this.messageThreads;
            if (this.messageThreads.length > 0) {
              this.route.queryParams.subscribe(params => {
                let threadId = params['threadId'];
                let messageId = params['messageId'];
                this.setSelectedThread(this.messageThreads.find(t => t.id == threadId) || this.messageThreads[0], messageId);
              });
            } else {
              this.onNewMessageClicked();
            }
          });
        });
      }
    });

    this.search.pipe(debounceTime(500)).subscribe((searchQuery: string) => {
      this.filteredThreads = this.messageThreads.filter(item => {
        return item.title.includes(searchQuery);
      });
    });
  }

  onSearchInput(): void {
    this.search.next(this.searchQuery);
  }

  onNewMessageClicked(): void {
    this.creatingNew = true;
    this.editingWatchers = false;
    this.subjectDropdownControl.setValue([]);
    this.selectedMessageThread = new MessageThread();
    this.selectedMessageThread.locationId = this.location.id;
  }

  onClearClicked(): void {
    if (this.creatingNew && this.messageThreads.length > 0) {
      this.creatingNew = false;
      this.selectedMessageThread = this.messageThreads[0];
    }
    this.newMessage = new Message();
  }

  onSendClicked(): void {
    if (this.form.invalid) {
      throw new Error('Message subject is required');
    }

    if (this.creatingNew) {
      this.selectedMessageThread.subjectIds = this.subjectDropdownControl.value?.map(s => s.id) || [];
      this.selectedMessageThread.messages.push(this.newMessage);
      this.messageThreadService.save(this.selectedMessageThread).subscribe((message: MessageThread) => {
        this.messageThreadService.findByLocationId(this.location.id).subscribe((res: MessageThread[]) => {
          this.messageThreads = res;
          this.filteredThreads = [this.messageThreads[0], ...this.filteredThreads];
          this.setSelectedThread(this.messageThreads[0]);
          this.newMessage.body = '';
        });
      });
      this.creatingNew = false;
    } else {
      this.messageThreadService.createMessage(this.selectedMessageThread.id, this.newMessage).subscribe((res: MessageThread) => {
        this.newMessage = new Message();
        let index = this.messageThreads.findIndex(t => t.id == res.id);
        if (index != undefined) {
          this.messageThreads[index] = res;
        }
        this.setSelectedThread(res);
      })
    }
  }

  onWatchersClicked(): void {
    this.editingWatchers = !this.editingWatchers;
    if (!this.editingWatchers) {
      let subjectIds = this.subjectDropdownControl.value?.map(s => s.id) || [];
      this.messageThreadService.setSubjects(this.selectedMessageThread.id, subjectIds).subscribe((res: MessageThread) => {
        this.messageThreadService.findByLocationId(this.location.id).subscribe((res2: MessageThread[]) => {
          this.messageThreads = res2;
          this.setSelectedThread(res2.find(t => t.id == res.id)!);
        });
      });
    }
  }

  setSelectedThread(thread: MessageThread, messageId?: number): void {
    this.selectedMessageThread = thread;
    this.subjectDropdownControl.setValue(this.getSelectedSubjects(this.selectedMessageThread.subjectIds));
    setTimeout(() => {
      if (messageId) {
        let message = this.selectedMessageThread.messages.find(m => m.id == messageId);
        if (message) {
          let scrollIndex = this.selectedMessageThread.messages.indexOf(message) || 0;
          this.messageListRef.nativeElement.children[scrollIndex].scrollIntoView();
        }
      } else {
        this.messageListRef.nativeElement.scrollTop = this.messageListRef.nativeElement.scrollHeight;
      }
    }, 100);
    this.creatingNew = false;
    this.editingWatchers = false;
  }

  getSelectedSubjects(subjectIds: number[]): SubjectInterface[] {
    return this.subjects?.filter(s => subjectIds.includes(s.id));
  }
}
