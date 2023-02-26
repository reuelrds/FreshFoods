import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'freshfood-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss'],
})
export class AddressComponent implements OnInit {
  @Input() addressForm: FormGroup;
  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {}
}
