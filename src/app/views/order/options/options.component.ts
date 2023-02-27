import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'freshfood-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.scss'],
})
export class OptionsComponent implements OnInit {
  @ViewChild('standardCheckboxRef') standardCheckbox;
  @ViewChild('expressCheckboxRef') expressCheckbox;

  @Input() optionsForm: FormGroup;

  startDate: Date = new Date();
  endDate: Date = new Date();

  constructor() {}

  ngOnInit(): void {
    this.endDate.setDate(this.startDate.getDate() + 21);
    // console.log(this.startDate);
    // console.log(this.endDate);
  }

  onCheck(deliveryType) {
    // console.log(deliveryType);
    // console.log(this.standardCheckbox);
    if (deliveryType === 'Standard') {
      this.standardCheckbox.nativeElement.checked = true;
      this.expressCheckbox.nativeElement.checked = false;
      this.optionsForm.patchValue({ deliveryType: 1.99 });
    } else {
      this.standardCheckbox.nativeElement.checked = false;
      this.expressCheckbox.nativeElement.checked = true;
      this.optionsForm.patchValue({ deliveryType: 4.99 });
    }
    // console.log(this.optionsForm.value);
  }
}
