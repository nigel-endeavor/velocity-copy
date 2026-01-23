import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Company } from 'src/app/models/company.model';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 

@Component({
  standalone: true,
  selector: 'app-automated-email',
  templateUrl: './automated-email.component.html',
  styleUrls: ['./automated-email.component.scss'],
  imports: [MatCheckboxModule, FormsModule, CommonModule]
})
export class AutomatedEmailComponent implements OnInit, OnChanges {
  company: Company;
  @Input() automatedEmailsEnabled: boolean | undefined;
  @Input() automateEmailAddresses: string[] = [];
  @Input() editingAutomatedEmails: boolean = false;

  newEmailInput: string = '';

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['automatedEmailsEnabled']) {
      if (this.automatedEmailsEnabled === false && this.editingAutomatedEmails) {
        this.automateEmailAddresses = [];
      }
    }
  }

  ngOnInit(): void {}

  addEmail(): void {
    if (this.newEmailInput && this.isValidEmail(this.newEmailInput)) {
      if (this.automateEmailAddresses.length < 5) {
        this.automateEmailAddresses.push(this.newEmailInput);  
        this.newEmailInput = '';  // Clear the input after adding
      } else {
        alert('You can only add up to 5 email addresses');
      }
    } else {
      alert('Invalid email address or empty input');
    }
  }

  isValidEmail(email: string): boolean {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(email);
  }
}
