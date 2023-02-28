import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  private _message = new Subject<{ message: String; action: String }>();
  $snackBarMessage = this._message.asObservable();

  constructor(private _snackBar: MatSnackBar) {}

  displaySnackBar(message, action) {
    // this._message.next({ message, action });
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
