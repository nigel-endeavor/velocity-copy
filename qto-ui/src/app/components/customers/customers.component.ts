import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent implements OnInit {
  private rootUrl = '/customers';

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    //@ts-ignore
    let url = this.route.snapshot['_routerState'].url;
    if (url.endsWith('customers')) {
      if (localStorage.getItem('customers-worklist')) {
        this.router.navigate([`${this.rootUrl}/${localStorage.getItem('customers-worklist')}`]);
      } else {
        this.router.navigate([`${this.rootUrl}/masterCustomers`]);
      }
    }
  }
}
