import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'freshfood-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  active = 'store';
  constructor() {}

  ngOnInit(): void {}
}
