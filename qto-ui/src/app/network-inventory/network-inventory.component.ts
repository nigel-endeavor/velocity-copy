import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-network-inventory',
  templateUrl: './network-inventory.component.html',
  styleUrls: ['./network-inventory.component.scss']
})
export class NetworkInventoryComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    //@ts-ignore
    if (!this.route.snapshot['_routerState'].url.includes('order')) {
      if (localStorage.getItem('inventory-worklist')) {
        this.router.navigate(['/inventory/' + localStorage.getItem('inventory-worklist')]);
      } else {
        this.router.navigate(['/inventory/locations']);
      }
    }
  }
}
